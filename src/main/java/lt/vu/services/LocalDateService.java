package lt.vu.services;

import javax.enterprise.inject.Specializes;

@Specializes
public class LocalDateService extends DateFormatService {

    public String date() {
        return java.time.LocalDate.now().toString();
    }
}
