package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;

/**
 * The type Field parser.
 */
public abstract class FieldParser {

    /**
     * Gets max value.
     *
     * @return the max value
     */
    protected abstract int getMaxValue();

    /**
     * Parse field string.
     *
     * @param field the cron field
     * @return the string
     * @throws InvalidFieldValueException the invalid field value exception
     */
    public abstract String parseField(String field) throws InvalidFieldValueException;


}
