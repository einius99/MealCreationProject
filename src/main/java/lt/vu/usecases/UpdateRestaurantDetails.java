package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Restaurant;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.RestaurantDAO;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

@ViewScoped
@Named
@Getter @Setter
public class UpdateRestaurantDetails implements Serializable {

    private Restaurant restaurant;

    @Inject
    private RestaurantDAO restaurantDAO;

    @PostConstruct
    private void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer restaurantId = Integer.parseInt(requestParameters.get("restaurantId"));
        this.restaurant = restaurantDAO.findOne(restaurantId);
    }

    @Transactional
    @LoggedInvocation
    public String updateRestaurant() {
        try{
            restaurantDAO.update(this.restaurant);
        } catch (OptimisticLockException e) {
            return "/restaurantDetails.xhtml?faces-redirect=true&restaurantId=" + this.restaurant.getId() + "&error=optimistic-lock-exception";
        }
        return "index.xhtml?&faces-redirect=true";
    }
}
