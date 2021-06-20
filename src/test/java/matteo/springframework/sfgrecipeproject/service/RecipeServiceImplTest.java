package matteo.springframework.sfgrecipeproject.service;

import matteo.springframework.sfgrecipeproject.converters.RecipeCommandToRecipe;
import matteo.springframework.sfgrecipeproject.converters.RecipeToRecipeCommand;
import matteo.springframework.sfgrecipeproject.exceptions.NotFoundException;
import matteo.springframework.sfgrecipeproject.model.Recipe;
import matteo.springframework.sfgrecipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
Unit tests / Unit testing is code written to test code under test(in production)
* designed to test specific sections of code
* percentage of lines of code tested is code coverage (ideal coverage about 70-80% range)
* should be 'unity; and execute very fast
* should have no external dependencies (no database or spring context, etc
*/

class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    RecipeService recipeService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipes() throws Exception {
        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet();
        Set<Recipe> recipes = recipeService.getRecipes();

        recipesData.add(recipe);

        // mockito methods
        when(recipeRepository.findAll()).thenReturn(recipesData);
        assertEquals(recipes.size(), 0);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        Recipe returnedRecipe = recipeService.findById(1L);

        assertNotNull(returnedRecipe, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipeByIdTNotFoundTest() throws Exception {
        // given
        Optional<Recipe> optionalRecipe = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        // when
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> recipeService.findById(1L));

        //then
        assertEquals("Recipe not found for id nr. : 1", notFoundException.getMessage());
        assertTrue(notFoundException.getMessage().contains("not found"));
    }

    @Test
    void deleteRecipeByIdTest() throws Exception {
        Long idToDelete = 2L;
        recipeService.deleteById(idToDelete);

        // no when, since method has void as return type

        // then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}