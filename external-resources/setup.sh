#!/bin/sh

installdir=$(pwd -P)

rm -rf $installdir/config/liquid-server.jks

rm -rf $installdir/config/liquid-server.crt

rm -rf $installdir/config/liquid-client.crt

rm -rf $installdir/config/liquid-client.jks

rm -rf $installdir/config/liquid-client-trust.jks

rm -rf $installdir/config/liquid-server-trust.jks

pass=$(date +%s | sha256sum | base64 | head -c 32 ; echo)

echo $pass>$installdir/config/password.txt

$JAVA_HOME/bin/keytool -genkey -alias liquid -keyalg RSA -keystore $installdir/config/liquid-server.jks -storepass $pass -keypass $pass -dname "cn=Liquid, ou=Liquid, o=Liquid, c=NL" -validity 10000

$JAVA_HOME/bin/keytool -export -alias liquid -keystore $installdir/config/liquid-server.jks -file $installdir/config/liquid-server.crt -storepass $pass -keypass $pass

$JAVA_HOME/bin/keytool -genkey -alias liquid-client -keyalg RSA -keystore $installdir/config/liquid-client.jks -storepass $pass -keypass $pass -dname "cn=Liquid-client, ou=Liquid, o=Liquid, c=NL" -validity 10000

$JAVA_HOME/bin/keytool -export -alias liquid-client -keystore $installdir/config/liquid-client.jks -file $installdir/config/liquid-client.crt -storepass $pass -keypass $pass

$JAVA_HOME/bin/keytool -import -alias liquid -keystore $installdir/config/liquid-client-trust.jks -file $installdir/config/liquid-server.crt -storepass $pass -keypass $pass -noprompt

$JAVA_HOME/bin/keytool -import -alias liquid-client -keystore $installdir/config/liquid-server-trust.jks -file $installdir/config/liquid-client.crt -storepass $pass -keypass $pass -noprompt