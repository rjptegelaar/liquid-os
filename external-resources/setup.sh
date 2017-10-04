#!/bin/bash

echo "Please enter JDK home: "
read JDK_HOME
echo "JDK home set to: $JDK_HOME"

LIQUID_HOME="$PWD"

rm -rf $LIQUID_HOME/config/liquid-server.jks

rm -rf $LIQUID_HOME/config/liquid-server.crt

rm -rf $LIQUID_HOME/config/liquid-client.crt

rm -rf $LIQUID_HOME/config/liquid-client.jks

rm -rf $LIQUID_HOME/config/liquid-client-trust.jks

rm -rf $LIQUID_HOME/config/liquid-server-trust.jks

pass=$(date +%s | sha256sum | base64 | head -c 32 ; echo)

echo $pass>$LIQUID_HOME/config/password.txt

$JDK_HOME/bin/keytool -genkey -alias liquid -keyalg RSA -keystore $LIQUID_HOME/config/liquid-server.jks -storepass $pass -keypass $pass -dname "cn=Liquid, ou=Liquid, o=Liquid, c=NL" -validity 10000

$JDK_HOME/bin/keytool -export -alias liquid -keystore $LIQUID_HOME/config/liquid-server.jks -file $LIQUID_HOME/config/liquid-server.crt -storepass $pass -keypass $pass

$JDK_HOME/bin/keytool -genkey -alias liquid-client -keyalg RSA -keystore $LIQUID_HOME/config/liquid-client.jks -storepass $pass -keypass $pass -dname "cn=Liquid-client, ou=Liquid, o=Liquid, c=NL" -validity 10000

$JDK_HOME/bin/keytool -export -alias liquid-client -keystore $LIQUID_HOME/config/liquid-client.jks -file $LIQUID_HOME/config/liquid-client.crt -storepass $pass -keypass $pass

$JDK_HOME/bin/keytool -import -alias liquid -keystore $LIQUID_HOME/config/liquid-client-trust.jks -file $LIQUID_HOME/config/liquid-server.crt -storepass $pass -keypass $pass -noprompt

$JDK_HOME/bin/keytool -import -alias liquid-client -keystore $LIQUID_HOME/config/liquid-server-trust.jks -file $LIQUID_HOME/config/liquid-client.crt -storepass $pass -keypass $pass -noprompt

copy1= cp /opt/liquid-os/LiquidService /etc/init.d/

echo "Copied service file to init.d $copy1"

chmod1= chmod +x /etc/init.d/LiquidService

echo "Added execute rights to service file $chmod1"

chmod2= chmod +x /opt/liquid-os/setup.sh

echo "Added execute rights to setup file $chmod2"

chown1= chown -R liquid:liquid /opt/liquid-os/

echo "Changed ownership of home dir $chown1"