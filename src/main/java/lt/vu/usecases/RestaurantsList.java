package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Restaurant;
import lt.vu.persistence.RestaurantDAO;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
@RequestScoped
public class RestaurantsList {

    @Inject
    private RestaurantDAO restaurantDAO;

    @Getter
    @Setter
    private Restaurant restaurant = new Restaurant();

    @Getter
    private List<Restaurant> restaurantList;

    @PostConstruct
    public void init(){
        loadAllRestaurants();
    }

    private void loadAllRestaurants(){
        this.restaurantList = restaurantDAO.loadAll();
    }

    @Transactional
    public String createRestaurant() {
        this.restaurantDAO.persist(restaurant);
        return "index?faces-redirect=true";
    }

}