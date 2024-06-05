# Cron Parser

This project is a command-line application for parsing cron expressions and expanding each field to show the times at which it will run. It follows the standard cron format with five time fields (minute, hour, day of month, month, and day of week) plus a command.

These fields, separated by white space, can contain any of the allowed values with various combinations of the allowed characters for that field. Table A-1 shows the fields in the expected order.


|   Name	            |             Allowed Values	    | Allowed Special Characters |
| :-----------------: |:------------------------------: |:--------------------------:|
|  Minutes            |              0-59               |          , - * /           |
|  Hours              |              0-23               |          , - * /           |
|  Day of month       |              1-31               |          , - * /           |
|  Month              |              0-11 or JAN-DEC    |          , - * /           |
|  Day of week        |              1-7 or SUN-SAT     |         , - * ? /          |


Testing of scenarios are performed using - https://www.atatus.com/tools/cron

Approach:
a) common attributes among different fields are
- has a name i.e. Minutes, Hour, Day of Month, Month, Day of Week.
- has an allowed range of values,e.g. hours and minutes are from 0 to 59.
- can have a special character either before or after and can have a different meaning.

     * - Any Value (Parser should show all the possible values)
     , - comma separates all the valid values (parser should show the values separated by commas)
     - - Range of values (Parser should show all the values in the given range from left to right)
     / - step values (Parser divides the valid range of values with the given number mentioned after the step)
     ? - Non specific value (used for day of the week or day of the month, parser should consider all the valid values)
     L - Last day of the month or week - not implemented
     W - Nearest weekday (used with Day of the month) - not implemented
     # - weekday of the month (used with Day of the week) - not implemented

### Prerequisites

- Java Development Kit (JDK).
- Gradle build tool.

### 1. Clone the Repository

Clone the repository to your local machine using Git:

```bash
git clone https://github.com/DikshaSharma14/cron-parser.git
cd cron-parser
```

### 2. Build the Project 
Navigate to the project root directory and build the project using Gradle:
```bash
./gradlew build     # On Unix/Linux
gradlew.bat build   # On Windows
```
This command will compile the Java source files, run tests, and generate the Jar file in build/libs directory.

### 3. Run the Application
   Unix/Linux Systems
   Use the provided run.sh shell script to run the application with input arguments:
   
```bash
./run-cron-parser.sh */15 0 1,15 * 1-5 /usr/bin/find
```
   Windows Systems
   Use the provided run.bat batch script to run the application with input arguments:
   
```batch
 run-cron-parser.bat */15 0 1,15 * 1-5 /usr/bin/find
```

Replace the input arguments (*/15 0 1,15 * 1-5 /usr/bin/find) with your inputs.

### Script Details
./run-crone-parser.sh: Shell script for Unix/Linux systems to run the Jar file with input arguments.
 run-crone-parser.bat : Batch script for Windows systems to run the Jar file with input arguments.

### Jar File Location
  After running the build command, the Jar file (cron-parser.jar) will be generated in the build/libs directory.

### Input Argument Format
  The application expects a single string argument in the standard cron format:

```
  */15 0 1,15 * 1-5 /usr/bin/find
```
