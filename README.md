# Impala/Hive JDBC Connection Example
Connecting to cloudera impala. The code supplied provides a simple javalin based restful service example written in kotlin language.   
You may encounter conflict with jetty with javalin - hence jetty is excluded from the hive-service dependencies. `hive-service` package contains thrift client API that are used by the cloudera impala jdbc driver.

### Issues to know
You may encounter Jetty server conflicts. Javalin, hive and cloudera impala all depend on Jetty. Jetty versions for hive/impala are old and hence excluding as dependency.

### Self signed certificates
For connection to impala/hive with SSL and kerberos, you will need a self signed certificate. you may create a self signed certificate from the original certificate as follows:   
```
Make sure JAVA_HOME is set, and points to a JVM version 1.7 or greater.
Run the following command from the directory where you have got the root certificate YourRootCA.crt
You can export the root certificate from your local machine if you have tried to connect to the SSL url for the host you are trying to connect.

"$JAVA_HOME/bin/keytool" -storepasswd -new stormwatch -keystore "$JAVA_HOME/jre/lib/security/cacerts" -storepass changeit && echo "yes" | "$JAVA_HOME/bin/keytool" -import -trustcacerts -file ./YourRootCA.crt -alias rootca-alias -keystore "$JAVA_HOME/jre/lib/security/cacerts" -storepass password

```


## dependencies required for Hive Jdbc Driver
```config
compile ("org.apache.hive:hive-jdbc:1.1.0-cdh5.8.3"){
        exclude(module: "jetty-all")
    }

```

## dependencies required for Impala Jdbc Driver
```config
compile "com.cloudera.impala:ImpalaJDBC41:2.5.36"
compile "org.apache.thrift:libthrift:0.9.3"
compile ("org.apache.hive:hive-service:1.1.0-cdh5.8.3"){
        exclude(module: "jetty-all")
    }
```



## Two types of drivers to connect to impala
There are two types of drivers that you can use to connect to impala   
* Hive JDBC Driver (`org.apache.hive.jdbc.HiveDriver`)
* Cloudera Impala JDBC Driver (`com.cloudera.impala.jdbc41.Driver`) - This is recommended approach by cloudera

## Connection String for Hive
```code
"jdbc:hive2://impala-daemon-host:21050/" + dbName  +
        ";principal=impala/impala-daemon-host@DOMAIN" +
        ";ssl=true;sslTrustStore=C:/Program%20Files/Java/jre7/lib/security/cacerts";
```

## Connection String for Impala jdbc driver
If you are supplying certificate path and you have white space path, do not replace it with %20, replacing does not seem to work. Contrast it to the connection string with hive.

You can download the latest version of Impala JDBC driver (ImpalaJDBC41:2.5.41) from [Cloudera Driver Download](https://www.cloudera.com/downloads/connectors/impala/jdbc/2-5-41.html)

`Note: setting SSLTrustStore is optional if you have the cacerts under JAVA_HOME/jre/lib/security` 

```code
"jdbc:impala://impala-daemon-host:21050/" +
    dbName +
    ";AuthMech=1;SSL=1;KrbRealm=DOMAIN;KrbHostFQDN=impala-daemon-host;KrbServiceName=impala;SSLTrustStore=C:/Program Files/Java/jre7/lib/security/cacerts;LogLevel=6;LogPath=c:/dev/impala";

```
