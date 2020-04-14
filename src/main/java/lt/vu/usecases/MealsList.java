package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Meal;
import lt.vu.entities.Restaurant;
import lt.vu.persistence.MealDAO;
import lt.vu.persistence.RestaurantDAO;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Model
@RequestScoped
public class MealsList {

    @Inject
    private MealDAO mealDAO;

    @Inject
    private RestaurantDAO restaurantDAO;

    @Getter
    @Setter
    private Meal meal = new Meal();

    @Getter
    @Setter
    private Restaurant restaurant = new Restaurant();

    @Getter
    private List<Meal> mealList;

    @PostConstruct
    public void init(){
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer restaurantId = Integer.parseInt(requestParameters.get("restaurantId"));
        this.restaurant = restaurantDAO.findOne(restaurantId);
        loadMeals(restaurantId);
    }

    private void loadMeals(Integer restaurantId) {
        this.mealList = mealDAO.loadAll(restaurantId);
    }

    @Transactional
    public String createMeal() {
        meal.setRestaurant(this.restaurant);
        mealDAO.persist(meal);
        return "meals?faces-redirect=true&restaurantId=" + this.restaurant.getId();
    }

}
