#!/bin/bash

JAR_FILE="build/libs/deliveroo-cron-parser.jar"

# Check if the Jar file exists
if [ ! -f "$JAR_FILE" ]; then
  echo "Error: Jar file not found. Please build the project first."
  exit 1
fi

# Run the Jar file with input arguments
java -jar "$JAR_FILE" "$@"
