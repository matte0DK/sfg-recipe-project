package matteo.springframework.sfgrecipeproject.converters;

import lombok.AllArgsConstructor;
import lombok.Synchronized;
import matteo.springframework.sfgrecipeproject.commands.IngredientCommand;
import matteo.springframework.sfgrecipeproject.model.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        IngredientCommand ingredientCommand = new IngredientCommand();

        ingredientCommand.setId(ingredient.getId());
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setUnitOfMeasure(uomConverter.convert(ingredient.getUom()));
        ingredientCommand.setDescription(ingredient.getDescription());

        return ingredientCommand;
    }
}
