package matteo.springframework.sfgrecipeproject.controllers;

import lombok.AllArgsConstructor;
import matteo.springframework.sfgrecipeproject.service.ImageService;
import matteo.springframework.sfgrecipeproject.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    // GET
    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));

        return "recipe/imageuploadform";
    }

    // POST
    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(Long.parseLong(id), file);

        return "redirect:/recipe/" + id + "/show";
    }
}
