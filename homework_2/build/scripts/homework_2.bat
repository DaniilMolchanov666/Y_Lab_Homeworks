@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  homework_2 startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and HOMEWORK_2_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\homework_2-1.0-SNAPSHOT.jar;%APP_HOME%\lib\tomcat-catalina-11.0.0-M24.jar;%APP_HOME%\lib\tomcat-embed-jasper-9.0.56.jar;%APP_HOME%\lib\tomcat-embed-core-11.0.0-M24.jar;%APP_HOME%\lib\tomcat-jsp-api-11.0.0-M24.jar;%APP_HOME%\lib\tomcat-util-scan-11.0.0-M24.jar;%APP_HOME%\lib\tomcat-api-11.0.0-M24.jar;%APP_HOME%\lib\tomcat-coyote-11.0.0-M24.jar;%APP_HOME%\lib\tomcat-servlet-api-11.0.0-M24.jar;%APP_HOME%\lib\lombok-mapstruct-binding-0.2.0.jar;%APP_HOME%\lib\mapstruct-1.6.0.jar;%APP_HOME%\lib\jjwt-api-0.12.6.jar;%APP_HOME%\lib\tomcat-embed-logging-juli-9.0.0.M6.jar;%APP_HOME%\lib\postgresql-42.7.2.jar;%APP_HOME%\lib\liquibase-commercial-4.29.1.jar;%APP_HOME%\lib\liquibase-core-4.29.1.jar;%APP_HOME%\lib\tomcat-util-11.0.0-M24.jar;%APP_HOME%\lib\tomcat-juli-11.0.0-M24.jar;%APP_HOME%\lib\tomcat-annotations-api-11.0.0-M24.jar;%APP_HOME%\lib\tomcat-jni-11.0.0-M24.jar;%APP_HOME%\lib\tomcat-jaspic-api-11.0.0-M24.jar;%APP_HOME%\lib\tomcat-embed-el-9.0.56.jar;%APP_HOME%\lib\ecj-3.18.0.jar;%APP_HOME%\lib\checker-qual-3.42.0.jar;%APP_HOME%\lib\opencsv-5.9.jar;%APP_HOME%\lib\snakeyaml-2.2.jar;%APP_HOME%\lib\jaxb-api-2.3.1.jar;%APP_HOME%\lib\commons-io-2.16.1.jar;%APP_HOME%\lib\commons-collections4-4.4.jar;%APP_HOME%\lib\commons-text-1.12.0.jar;%APP_HOME%\lib\commons-lang3-3.14.0.jar;%APP_HOME%\lib\tomcat-el-api-11.0.0-M24.jar


@rem Execute homework_2
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %HOMEWORK_2_OPTS%  -classpath "%CLASSPATH%" com.ylab.Main %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable HOMEWORK_2_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%HOMEWORK_2_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
