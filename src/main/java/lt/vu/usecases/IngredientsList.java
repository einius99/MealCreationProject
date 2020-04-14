package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Meal;
import lt.vu.mybatis.dao.IngredientMapper;
import lt.vu.mybatis.dao.MealIngredientMapper;
import lt.vu.mybatis.model.Ingredient;
import lt.vu.mybatis.model.MealIngredient;
import lt.vu.persistence.MealDAO;

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
public class IngredientsList {

    @Inject
    private MealDAO mealDAO;

    @Inject
    private IngredientMapper ingredientMapper;

    @Inject
    private MealIngredientMapper mealIngredientMapper;

    @Getter
    @Setter
    private MealIngredient mealIngredient = new MealIngredient();

    @Getter
    @Setter
    private Meal meal = new Meal();

    @Getter
    @Setter
    private Ingredient ingredient = new Ingredient();

    @Getter
    private List<Ingredient> selectedIngredients;

    @Getter
    private List<Ingredient> allIngredients;


    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer mealId = Integer.parseInt(requestParameters.get("mealId"));
        this.meal = mealDAO.findOne(mealId);
        this.loadAllIngredients();
        this.loadSelectedIngredients(mealId);
    }

    private void loadAllIngredients() {
        this.allIngredients = ingredientMapper.selectAll();
    }

    private void loadSelectedIngredients(Integer mealId) {
        this.selectedIngredients = ingredientMapper.selectIngredientsByMeal(mealId);
    }

    @Transactional
    public String addIngredient() {
        mealIngredient.setMealsId(this.meal.getId());
        mealIngredient.setIngredientsId(this.ingredient.getId());
        mealIngredientMapper.insert(mealIngredient);
        return "ingredients?faces-redirect=true&mealId=" + this.meal.getId();
    }

    public String deleteIngredient() {
        mealIngredientMapper.deleteByIngredientId(this.ingredient.getId());
        return "ingredients?faces-redirect=true&mealId=" + this.meal.getId();
    }

}
