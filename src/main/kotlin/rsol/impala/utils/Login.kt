package rsol.impala.utils

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.security.UserGroupInformation
import java.io.File

fun hadoopLogin()  {
    System.setProperty("java.security.krb5.kdc", StringBuilder(DOMAIN)
            .append(":").append(KDC_PORT).toString())
    System.setProperty("sun.security.krb5.debug", "false");
    System.setProperty("solr.kerberos.jaas.appname", "Client");
    System.setProperty("java.security.krb5.realm", DOMAIN)
    System.setProperty("javax.security.auth.useSubjectCredsOnly", "false")


    val keytab_path = File("src/main/resources/user.keytab").absolutePath.toString()
    System.setProperty("java.security.auth.login.config", File("src/main/resources/jaas.conf").absolutePath.toString())
    println(File("src/main/resources/jaas.conf").absolutePath.toString())

    val conf = Configuration()
    conf.set("hadoop.security.authentication", "Kerberos")
    UserGroupInformation.setConfiguration(conf)
    UserGroupInformation.loginUserFromKeytab(StringBuilder(userId)
            .append("@").append(DOMAIN).toString(), keytab_path)

    println("login successful ...")
}
