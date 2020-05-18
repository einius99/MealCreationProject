package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.Random;

@ApplicationScoped
@Alternative
public class ReviewScoreCreator implements Creator {

    public Double createReviewScore() {
        Double createdReviewScore = new Random().nextDouble();

        return createdReviewScore;
    }

}