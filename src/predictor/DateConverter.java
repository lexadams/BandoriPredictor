/**
 * Converts date to a format usable by our model.
 *
 * @author Alexander "Lex" Adams
 */

package predictor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    private int start;
    private String target;

    public DateConverter(String startTime, String predictionTime) {
        Date startDate, predictionDate;

        // Convert inputs into date objects
        SimpleDateFormat acceptedFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a z");
        try {
            startDate = acceptedFormat.parse(startTime);
        } catch (ParseException e) {
            startDate = null;
            e.printStackTrace();
        }
        try {
            predictionDate = acceptedFormat.parse(predictionTime);
        } catch (ParseException e) {
            predictionDate = null;
            e.printStackTrace();
        }

        // Set time since event start
        start = (int) ((predictionDate.getTime() - startDate.getTime()) / 1000);

        // Reformat and set target time
        SimpleDateFormat reFormat = new SimpleDateFormat("EEE hh:mm a z");
        target = reFormat.format(predictionDate);
    }

    public int getStart() {
        return start;
    }

    public String getTarget() {
        return target;
    }
}