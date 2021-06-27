package matteo.springframework.sfgrecipeproject.service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import matteo.springframework.sfgrecipeproject.commands.IngredientCommand;
import matteo.springframework.sfgrecipeproject.converters.IngredientCommandToIngredient;
import matteo.springframework.sfgrecipeproject.converters.IngredientToIngredientCommand;
import matteo.springframework.sfgrecipeproject.model.Ingredient;
import matteo.springframework.sfgrecipeproject.model.Recipe;
import matteo.springframework.sfgrecipeproject.repositories.RecipeRepository;
import matteo.springframework.sfgrecipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Transactional

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (optionalRecipe.isEmpty()) {
            // todo implement error
            log.error("recipe id not found, Id: " + recipeId);
            return null;
        }

        Recipe recipe = optionalRecipe.get();

        Optional<IngredientCommand> optionalIngredient = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();

        if (optionalIngredient.isEmpty()) {
            // todo implement error
            log.error("ingredient id not found, Id: " + id);
            return null;
        }

        return optionalIngredient.get();
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (recipeOptional.isEmpty()) { //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            ingredientFound.setUom(unitOfMeasureRepository
                    .findById(command.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
        } else {
            //add new Ingredient
            Ingredient ingredient = ingredientCommandToIngredient.convert(command);

            if (ingredient == null) {
                throw new RuntimeException();
            }
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> optionalSavedIngredient = savedRecipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();

        // check by description
        if (optionalSavedIngredient.isEmpty()) {
            optionalSavedIngredient = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                    .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                    .filter(ingredient -> ingredient.getUom().getId().equals(command.getUnitOfMeasure().getId()))
                    .findFirst();
        }

        //to do check for fail
        return ingredientToIngredientCommand.convert(optionalSavedIngredient.get());
    }

    @Override
    public void deleteById(Long recipeId, Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            Optional<Ingredient> optionalIngredient = getIngredients(recipe, id);

            checkIfIdIsFound(optionalIngredient, id);
            recipeRepository.save(deleteIngredient(recipe, optionalIngredient));
        } else {
            log.debug("Recipe id not found. id: " + recipeId);
        }
    }
    private Optional<Ingredient> getIngredients(Recipe recipe, Long id) {
        log.debug("found recipe");
        return recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst();
    }
    private void checkIfIdIsFound(Optional<Ingredient> optionalIngredient, Long id) {
        if (optionalIngredient.isEmpty()) {
            log.debug("Ingredient id not found. id: " + id);
        }
    }
    private Recipe deleteIngredient(Recipe recipe, Optional<Ingredient> optionalIngredient) {
        Ingredient ingredientToDelete = optionalIngredient.get();
        log.debug("found ingredient");

        ingredientToDelete.setRecipe(null);
        recipe.getIngredients().remove(optionalIngredient.get());
        return recipe;
    }
}
