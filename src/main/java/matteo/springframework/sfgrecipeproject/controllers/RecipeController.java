package matteo.springframework.sfgrecipeproject.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matteo.springframework.sfgrecipeproject.commands.RecipeCommand;
import matteo.springframework.sfgrecipeproject.model.Recipe;
import matteo.springframework.sfgrecipeproject.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@AllArgsConstructor

@Controller
public class RecipeController {
    private final RecipeService recipeService;

    @RequestMapping("/recipes")
    public String getRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

    @RequestMapping({"/recipe/{id}/show", "/recipe/{id}"})
    public String showRecipeById(@PathVariable String id, Model model) {
        model.addAttribute("recipe",recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @RequestMapping({"/recipe/new", "/new"})
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand recipeCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + recipeCommand.getId() + "/show";
    }
}
