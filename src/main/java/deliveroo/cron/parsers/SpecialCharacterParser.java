package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The type Special character parser.
 */
public class SpecialCharacterParser {

    /**
     * Parse list.
     *
     * @param field    the cron field
     * @param start    the start the min value allowed for the field
     * @param maxValue the max value allowed for the field
     * @return the list
     * @throws InvalidFieldValueException the invalid field value exception
     */
    public static List<Integer> parse(String field, int start, int maxValue) throws InvalidFieldValueException {
        if (field.equals("*")) {
            return parseAllValues(start, maxValue);
        }

        if (field.contains("/")) {
            return parseStepValue(field, start, maxValue);
        }

        if (field.contains("-")) {
            return parseRangeValue(field, maxValue);
        }

        if (field.contains(",")) {
            return parseListValue(field, maxValue);
        }
        int singleValue = parseSingleValue(field);
        if(singleValue>maxValue){
            throw new InvalidFieldValueException(singleValue + "is greater than allowed max value" + maxValue);
        }
        return List.of(parseSingleValue(field));
    }

    private static List<Integer> parseAllValues( int start, int maxValue) {
        return IntStream.rangeClosed(start, maxValue)
                .boxed()
                .collect(Collectors.toList());
    }

    private static List<Integer> parseStepValue(String field, int start, int maxValue) throws InvalidFieldValueException {
        String[] parts = field.split("/");
        if(!parts[0].equals("*") && !parts[0].equals("")){
           start =  parseSingleValue(parts[0]);
        }
        int step = parseSingleValue(parts[1]);
        if(step > maxValue){
            throw new InvalidFieldValueException(field + "is greater than allowed max value" + maxValue);
        }else if(step < 0){
            throw new InvalidFieldValueException("Negative values are not allowed");
        }
        return IntStream.iterate(start, i -> i <= maxValue, i -> i + step)
                .boxed()
                .collect(Collectors.toList());
    }

    private static List<Integer> parseRangeValue(String field, int maxValue) throws InvalidFieldValueException {
        String[] parts = field.split("-");
        int start = parseSingleValue(parts[0]);
        int end = parseSingleValue(parts[1]);
        if(end > maxValue){
            throw new InvalidFieldValueException(field + "is greater than allowed max value" + maxValue);
        }else if(start < 0){
            throw new InvalidFieldValueException("Negative values are not allowed");
        }else if(end<start){
            throw new InvalidFieldValueException("Range should be in increasing order");
        }
        return IntStream.rangeClosed(start, end)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Parse list value list.
     *
     * @param field    the field
     * @param maxValue the max value
     * @return the list
     */
    static List<Integer> parseListValue(String field, int maxValue) {
        return Arrays.stream(field.split(","))
                .map(SpecialCharacterParser::parseSingleValue)
                .collect(Collectors.toList());
    }

    /**
     * Parse single value int.
     *
     * @param valueStr the value str
     * @return the int
     */
    static int parseSingleValue(String valueStr) {
        if(valueStr.contains("*")){
            valueStr = valueStr.replace("*", "");
        }
        // Handle specific values or special keywords like month names, day names, etc.
        return switch (valueStr.toUpperCase()) {
            case "JAN", "SUN" -> 1;
            case "FEB", "MON" -> 2;
            case "MAR", "TUE" -> 3;
            case "APR", "WED" -> 4;
            case "MAY", "THU" -> 5;
            case "JUN", "FRI" -> 6;
            case "JUL", "SAT" -> 7;
            case "AUG" -> 8;
            case "SEP" -> 9;
            case "OCT" -> 10;
            case "NOV" -> 11;
            case "DEC" -> 12;
            default -> Integer.parseInt(valueStr);
        };
    }
}
