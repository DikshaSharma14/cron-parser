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

        if (fields.length != 6) {
            System.err.println("Invalid cron expression format. Expected 6 fields.");
            return;
        }

        String[] fieldNames = {"minute", "hour", "day of month", "month", "day of week", "command"};

        for (int i = 0; i < fields.length; i++) {
            String fieldName = formatFieldName(fieldNames[i]);
            String expandedValues = expandField(fields[i], i);
            System.out.printf("%-14s %s%n", fieldName, expandedValues);
        }
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
                     new CommandParser();

            default -> throw new IllegalArgumentException("Invalid field index");
        };
    }
}
