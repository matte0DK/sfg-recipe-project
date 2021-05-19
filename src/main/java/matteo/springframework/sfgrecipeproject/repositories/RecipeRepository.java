package matteo.springframework.sfgrecipeproject.repositories;

import matteo.springframework.sfgrecipeproject.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {


}
