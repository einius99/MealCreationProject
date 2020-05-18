package lt.vu.decorators;

import javax.enterprise.inject.Default;

@Default
public class MainInformation implements Information {

    @Override
    public String info() {
        return "Restaurants list is always up to date! ";
    }
}
