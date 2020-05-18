package lt.vu.services;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatService {

    public String date() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date newDate = new Date();
        var date = dateFormat.format(newDate);

        return date;
    }

}
