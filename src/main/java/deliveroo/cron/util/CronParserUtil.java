package deliveroo.cron.util;

import deliveroo.cron.exceptions.InvalidFieldValueException;
import deliveroo.cron.parsers.*;

public class CronParserUtil {

    private final String cronExpression;

    public CronParserUtil(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public void parseAndPrint() throws InvalidFieldValueException {
        String[] fields = cronExpression.split("\\s+");

        if (fields.length < 6) {
            System.err.println("Invalid cron expression format. Expected 6 fields.");
            return;
        }

        String[] fieldNames = {"minute", "hour", "day of month", "month", "day of week", "Year"};
        int commandStart =6;
        try {
           getFirstCommandField(fields[5]);
        }catch (NumberFormatException nfe){
            commandStart =5;
        }
        for (int i = 0; i < commandStart; i++) {
            String fieldName = formatFieldName(fieldNames[i]);
            String expandedValues = expandField(fields[i], i);
            System.out.printf("%-14s %s%n", fieldName, expandedValues);
        }

        StringBuilder commandField = new StringBuilder();
        for(int i= commandStart; i<fields.length-1; i++){
            commandField.append(fields[i]);
            commandField.append(" ");
        }
        commandField.append(fields[fields.length-1]);
        System.out.printf("%-14s %s%n", "command" , commandField);
    }

    private void getFirstCommandField(String field) throws NumberFormatException {
        if(field.contains("*")){
            field = field.replace("*", "");
        }
        if(field.contains("/")){
            field =  field.replace("/", "");
        }
        if(field.contains("-")){
            field = field.replace("-", "");
        }
        Integer.parseInt(field);
    }

    private String formatFieldName(String fieldName) {
        return fieldName.substring(0, Math.min(fieldName.length(), 14));
    }

    private String expandField(String field, int fieldIndex) throws InvalidFieldValueException {
        FieldParser fieldParser = getFieldParser(fieldIndex);
        return fieldParser.parseField(field);
    }

    private FieldParser getFieldParser(int fieldIndex) {
        return switch (fieldIndex) {
            case 0 -> // Minute field
                    new MinuteFieldParser();
            case 1 -> // Hour field
                    new HourFieldParser();
            case 2 -> // Day of month field
                    new DayOfMonthFieldParser();
            case 3 -> // Month field
                    new MonthFieldParser();
            case 4 -> // Day of week field
                    new DayOfWeekFieldParser();
            case 5 -> //parse the command
                     new YearFieldParser();

            default -> throw new IllegalArgumentException("Invalid field index");
        };
    }
}
