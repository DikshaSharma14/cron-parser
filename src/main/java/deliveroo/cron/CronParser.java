package deliveroo.cron;

import deliveroo.cron.exceptions.InvalidFieldValueException;
import deliveroo.cron.util.CronParserUtil;
public class CronParser {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please enter a valid cron expression of length 6 as an argument");
            System.exit(1);
        }

        String cronExpression = args[0];
      // String cronExpression = "/15 0 1,15 * 1-5  /usr/bin/find";
        CronParserUtil parserUtil = new CronParserUtil(cronExpression);
        try {
            parserUtil.parseAndPrint();
        }catch (InvalidFieldValueException exception){
            exception.printStackTrace();
        }
    }
}