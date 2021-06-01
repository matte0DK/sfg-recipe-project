package matteo.springframework.sfgrecipeproject.converters;

import lombok.Synchronized;
import matteo.springframework.sfgrecipeproject.commands.CategoryCommand;
import matteo.springframework.sfgrecipeproject.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source){
        if (source == null) {
            return null;
        }

        final Category category = new Category();

        category.setId(source.getId());
        category.setDescription(source.getDescription());

        return category;
    }
}
