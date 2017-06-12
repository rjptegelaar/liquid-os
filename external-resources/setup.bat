@echo off

echo ======================================================================
set /P MasterPassword=Please enter a master password: 
echo ======================================================================

call del /Q %~dp0config\liquid-server.jks

call del /Q %~dp0config\liquid-server.crt

call del /Q %~dp0config\liquid-client.crt

call del /Q %~dp0config\liquid-client.jks

call del /Q %~dp0config\liquid-client-trust.jks

call del /Q %~dp0config\liquid-server-trust.jks


call %JAVA_HOME%\bin\keytool -genkey -alias liquid -keyalg RSA -keystore %~dp0config\liquid-server.jks -storepass %MasterPassword% -keypass %MasterPassword% -dname "cn=Liquid, ou=Liquid, o=Liquid, c=NL" -validity 10000

call %JAVA_HOME%\bin\keytool -export -alias liquid -keystore %~dp0config\liquid-server.jks -file %~dp0config\liquid-server.crt -storepass %MasterPassword% -keypass %MasterPassword%

call %JAVA_HOME%\bin\keytool -genkey -alias liquid-client -keyalg RSA -keystore %~dp0config\liquid-client.jks -storepass %MasterPassword% -keypass %MasterPassword% -dname "cn=Liquid-client, ou=Liquid, o=Liquid, c=NL" -validity 10000

call %JAVA_HOME%\bin\keytool -export -alias liquid-client -keystore %~dp0config\liquid-client.jks -file %~dp0config\liquid-client.crt -storepass %MasterPassword% -keypass %MasterPassword%

call %JAVA_HOME%\bin\keytool -import -alias liquid -keystore %~dp0config\liquid-client-trust.jks -file %~dp0config\liquid-server.crt -storepass %MasterPassword% -keypass %MasterPassword% -noprompt

call %JAVA_HOME%\bin\keytool -import -alias liquid-client -keystore %~dp0config\liquid-server-trust.jks -file %~dp0config\liquid-client.crt -storepass %MasterPassword% -keypass %MasterPassword% -noprompt