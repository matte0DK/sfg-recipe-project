package matteo.springframework.sfgrecipeproject.converters;

import matteo.springframework.sfgrecipeproject.commands.CategoryCommand;
import matteo.springframework.sfgrecipeproject.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {
    public static final Long ID_VALUE = Long.valueOf(1);
    public static final String DESCRIPTION = "description";
    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        // given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        // when
        CategoryCommand categoryCommand = converter.convert(category);

        // then
        assert categoryCommand != null;
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}