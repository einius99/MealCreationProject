package lt.vu.decorators;

import lt.vu.services.DateFormatService;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public class MainInformationDecorator implements Information {

    @Inject
    @Delegate
    @Any
    MainInformation information;

    @Inject
    private DateFormatService dateFormatService;

    @Override
    public String info() {
        String news = information.info();
        String lastUpdated = "Last updated in: ";
        return news + lastUpdated + dateFormatService.date();
    }
}


