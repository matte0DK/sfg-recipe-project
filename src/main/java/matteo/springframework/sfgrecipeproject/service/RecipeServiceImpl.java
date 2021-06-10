package matteo.springframework.sfgrecipeproject.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matteo.springframework.sfgrecipeproject.commands.RecipeCommand;
import matteo.springframework.sfgrecipeproject.converters.RecipeCommandToRecipe;
import matteo.springframework.sfgrecipeproject.converters.RecipeToRecipeCommand;
import matteo.springframework.sfgrecipeproject.exceptions.NotFoundException;
import matteo.springframework.sfgrecipeproject.model.Recipe;
import matteo.springframework.sfgrecipeproject.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the recipe service");

        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipe -> recipeSet.add(recipe));
        return recipeSet;
    }

    @Override
    public Recipe findById(long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isEmpty()) {
            throw new NotFoundException("Recipe not found for id nr. : " + id);
        }
        return optionalRecipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("saved recipeId: " + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findCommandById(long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
