package lt.vu.persistence;

import lt.vu.entities.Ingredient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class IngredientDAO {

    @Inject
    private EntityManager em;

    public List<Ingredient> loadAll(Integer mealId) {
        return em.createNamedQuery("Ingredient.findAll", Ingredient.class).getResultList();
    }

    public void persist(Ingredient ingredient) { this.em.persist(ingredient); }

    public void remove(Ingredient ingredient) { this.em.remove(ingredient); }

    public Ingredient findOne(Integer id) { return em.find(Ingredient.class, id); }

}
