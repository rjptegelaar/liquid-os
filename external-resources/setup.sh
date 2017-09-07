#!/bin/bash

rm -rf $LIQUID_HOME/config/liquid-server.jks

rm -rf $LIQUID_HOME/config/liquid-server.crt

rm -rf $LIQUID_HOME/config/liquid-client.crt

rm -rf $LIQUID_HOME/config/liquid-client.jks

rm -rf $LIQUID_HOME/config/liquid-client-trust.jks

rm -rf $LIQUID_HOME/config/liquid-server-trust.jks

pass=$(date +%s | sha256sum | base64 | head -c 32 ; echo)

echo $pass>$LIQUID_HOME/config/password.txt

$JAVA_HOME/bin/keytool -genkey -alias liquid -keyalg RSA -keystore $LIQUID_HOME/config/liquid-server.jks -storepass $pass -keypass $pass -dname "cn=Liquid, ou=Liquid, o=Liquid, c=NL" -validity 10000

$JAVA_HOME/bin/keytool -export -alias liquid -keystore $LIQUID_HOME/config/liquid-server.jks -file $LIQUID_HOME/config/liquid-server.crt -storepass $pass -keypass $pass

$JAVA_HOME/bin/keytool -genkey -alias liquid-client -keyalg RSA -keystore $LIQUID_HOME/config/liquid-client.jks -storepass $pass -keypass $pass -dname "cn=Liquid-client, ou=Liquid, o=Liquid, c=NL" -validity 10000

$JAVA_HOME/bin/keytool -export -alias liquid-client -keystore $LIQUID_HOME/config/liquid-client.jks -file $LIQUID_HOME/config/liquid-client.crt -storepass $pass -keypass $pass

$JAVA_HOME/bin/keytool -import -alias liquid -keystore $LIQUID_HOME/config/liquid-client-trust.jks -file $LIQUID_HOME/config/liquid-server.crt -storepass $pass -keypass $pass -noprompt

$JAVA_HOME/bin/keytool -import -alias liquid-client -keystore $LIQUID_HOME/config/liquid-server-trust.jks -file $LIQUID_HOME/config/liquid-client.crt -storepass $pass -keypass $pass -noprompt