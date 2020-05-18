package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.Random;

@ApplicationScoped
@Alternative
public class AverageReviewScoreCreator implements Creator {

    public Double createReviewScore() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        Double createdAverageReviewScore = (double)Math.round((new Random().nextDouble() * 9) * 100d) / 100d;

        return createdAverageReviewScore;
    }

}