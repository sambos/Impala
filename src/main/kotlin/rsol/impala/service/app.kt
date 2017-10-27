package rsol.impala.service

import com.beust.klaxon.*
import com.google.gson.GsonBuilder
import io.javalin.Context
import io.javalin.Javalin
import java.io.File
import java.sql.Connection
import java.sql.DriverManager



fun main(args: Array<String>) {

    hadoopLogin()
    loadDriver()
    //getImpalaResponse2()

    val app = Javalin.start(7000)
    app.get("/") { ctx -> ctx.result("Hello World") }

    //app.before(::verifyLogin)
    app.get("/api/user", ::getJsonResponse)
    app.get("/api/db", ::getImpalaResponse)

}

fun getJsonResponse(ctx : Context)  {
    val userService = MemoryUserService()
    val user = userService.getUser("1")
    val gson = GsonBuilder().setPrettyPrinting().create()
    ctx.result(gson.toJson(user))
}


fun getImpalaResponse2() {
    println("inside getImpalaResponse: ")
    try {
        println(getImpalaJdbcDriverConnectionString())
        val con = DriverManager.getConnection(getImpalaJdbcDriverConnectionString())
        println("connection string : " + con)
        val stmt = con.createStatement()
        val tname = "$dbName.$tableName"
        val sql = "select * from $tname limit 10"
        println("Running: " + sql)
        val res = stmt.executeQuery(sql)
        if (res.next()) {
            System.out.println("col1: " + res.getString(1) + " col2: "
                    + res.getString(2))
        }
        res.close()
        con.close()

    }
    catch (e: Exception) {
        e.printStackTrace()
    }
}

fun getImpalaResponse(ctx : Context) {
    println("inside getImpalaResponse: ")
    try {
        val con = DriverManager.getConnection(getImpalaJdbcDriverConnectionString())
        println("connection string : " + con)
        val stmt = con.createStatement()
        val tname = "$dbName.$tableName"
        val sql = "select * from $tname limit 10"
        println("Running: " + sql)
        val res = stmt.executeQuery(sql)
        if (res.next()) {
            System.out.println("col1: " + res.getString(1) + " col2: "
                    + res.getString(2))
        }
        res.close()
        con.close()

    }
    catch (e: Exception) {
        e.printStackTrace()
    }


    ctx.result("")
}
