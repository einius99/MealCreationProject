package lt.vu.usecases;

import lt.vu.decorators.Information;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class InformationSection {

    @Inject
    private Information information;

    public String info() {
        return information.info();
    }
}
