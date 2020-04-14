package lt.vu.persistence;

import lt.vu.entities.Meal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class MealDAO {

    @Inject
    private EntityManager em;

    public List<Meal> loadAll(Integer restaurantId) {
        return em.createNamedQuery("Meal.findAll", Meal.class).setParameter("restaurantId", restaurantId).getResultList();
    }

    public void persist(Meal meal) {
        this.em.persist(meal);
    }

    public Meal findOne(Integer id) {
        return em.find(Meal.class, id);
    }
}
