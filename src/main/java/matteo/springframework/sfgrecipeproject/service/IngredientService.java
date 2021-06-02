package matteo.springframework.sfgrecipeproject.service;


import matteo.springframework.sfgrecipeproject.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
