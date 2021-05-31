package matteo.springframework.sfgrecipeproject.service;

import matteo.springframework.sfgrecipeproject.model.Recipe;
import matteo.springframework.sfgrecipeproject.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    RecipeService recipeService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() throws Exception {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet();
        Set<Recipe> recipes = recipeService.getRecipes();

        recipesData.add(recipe);

        // mockito methods
        when(recipeRepository.findAll()).thenReturn(recipesData);
        assertEquals(recipes.size(), 0);
        verify(recipeRepository, times(1)).findAll();
    }
}