package matteo.springframework.sfgrecipeproject.service;

import matteo.springframework.sfgrecipeproject.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(long id);
}
