@echo off

set JAR_FILE=build\libs\deliveroo-cron-parser.jar

rem Check if the Jar file exists
if not exist "%JAR_FILE%" (
  echo Error: Jar file not found. Please build the project first.
  exit /b 1
)

java -jar %JAR_FILE% %*

