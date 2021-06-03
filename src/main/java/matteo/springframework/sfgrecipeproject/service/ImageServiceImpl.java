package matteo.springframework.sfgrecipeproject.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matteo.springframework.sfgrecipeproject.model.Recipe;
import matteo.springframework.sfgrecipeproject.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
@Service
public class ImageServiceImpl implements ImageService{
    private final RecipeRepository recipeRepository;

    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        try {
            Recipe recipe = recipeRepository.findById(id).get();
            Byte[] imageBytes = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b :
                    file.getBytes()) {
                imageBytes[i++] = b;
            }

            recipe.setImage(imageBytes);

            recipeRepository.save(recipe);

        } catch (IOException ioException) {
            log.error("Error occured", ioException);
            ioException.printStackTrace();
        }
    }
}
