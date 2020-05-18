package lt.vu.usecases;

import lt.vu.interceptors.LoggedInvocation;
import lt.vu.services.Creator;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class GetAverageReviewScore implements Serializable {
    @Inject
    Creator creator;

    private CompletableFuture<Double> averageReviewScoreGenerationTask = null;

    @LoggedInvocation
    public String generateAverageReviewScore() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        averageReviewScoreGenerationTask = CompletableFuture.supplyAsync(() -> creator.createReviewScore());

        return  "/restaurantDetails.xhtml?faces-redirect=true&restaurantId=" + requestParameters.get("restaurantId");
    }

    public boolean isReviewScoreGenerationRunning() {
        return averageReviewScoreGenerationTask != null && !averageReviewScoreGenerationTask.isDone();
    }

    public String getReviewScoreGenerationStatus() throws ExecutionException, InterruptedException {
        if (averageReviewScoreGenerationTask == null) {
            return null;
        } else if (isReviewScoreGenerationRunning()) {
            return "Loading average restaurant review score...";
        }
        return "Average restaurant review score: " + averageReviewScoreGenerationTask.get();
    }
}