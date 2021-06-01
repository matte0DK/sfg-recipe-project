package matteo.springframework.sfgrecipeproject.converters;

import matteo.springframework.sfgrecipeproject.commands.CategoryCommand;
import matteo.springframework.sfgrecipeproject.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {
    public static final Long ID_VALUE = Long.valueOf(1);
    public static final String DESCRIPTION = "description";
    CategoryCommandToCategory converter;


    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        // given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        // when
        Category category = converter.convert(categoryCommand);

        // then
        assert category != null;
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}