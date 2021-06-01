package matteo.springframework.sfgrecipeproject.converters;

import matteo.springframework.sfgrecipeproject.commands.IngredientCommand;
import matteo.springframework.sfgrecipeproject.commands.UnitOfMeasureCommand;
import matteo.springframework.sfgrecipeproject.model.Ingredient;
import matteo.springframework.sfgrecipeproject.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {
    private static final Recipe RECIPE = new Recipe();
    private static final Long ID_VALUE = Long.valueOf(1L);
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(4);
    private static final String DESCRIPTION = "description";
    private static final Long UOM_ID = Long.valueOf(2L);
    IngredientCommandToIngredient ingredientConverter;

    @BeforeEach
    void setUp() {
        ingredientConverter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void testNullObject() throws Exception {
        assertNull(ingredientConverter.convert(null));
    }

    @Test
    void testEmptyObjet() throws Exception {
        assertNotNull(ingredientConverter.convert(new IngredientCommand()));
    }

    @Test
    void convert() {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setAmount(AMOUNT);

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);

        ingredientCommand.setDescription(DESCRIPTION);

        // when
        Ingredient ingredient = ingredientConverter.convert(ingredientCommand);

        // then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(UOM_ID, ingredient.getUom().getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }

    @Test
    void convertWithNullUom() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();

        // when
        Ingredient ingredient = ingredientConverter.convert(ingredientCommand);

        // then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }
}