package rsol.impala.service

val HIVE_DRIVER : String = "org.apache.hive.jdbc.HiveDriver"
val IMPALA_DRIVER : String = "com.cloudera.impala.jdbc41.Driver"
val DOMAIN : String = "your-domain-name"
val KDC_PORT : String = "88"
val dbName : String = "dbName"
val userId : String = "user"
val KEYTAB_PATH : String = userId + ".keytab"

fun loadDriver() {
    try {
        Class.forName(IMPALA_DRIVER);
    } catch (e : ClassNotFoundException) {
        e.printStackTrace()
        System.exit(1)
    }
}

fun getHiveJdbcDriverConnectionString() : String {
return "jdbc:hive2://impala-daemon-host:21050/" + dbName  +
        ";principal=impala/impala-daemon-host@your-domain" +
        ";ssl=true;sslTrustStore=C:/Program%20Files/Java/jre7/lib/security/cacerts";
}

fun getImpalaJdbcDriverConnectionString() : String {

    return "jdbc:impala://impala-daemon-host:21050/" +
    dbName +
    ";AuthMech=1;SSL=1;KrbRealm=your-domain-name;KrbHostFQDN=impala-daemon-host;KrbServiceName=impala;SSLTrustStore=C:/Program Files/Java/jre7/lib/security/cacerts;LogLevel=6;LogPath=c:/dev/impala";

}
