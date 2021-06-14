package matteo.springframework.sfgrecipeproject.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matteo.springframework.sfgrecipeproject.commands.RecipeCommand;
import matteo.springframework.sfgrecipeproject.exceptions.NotFoundException;
import matteo.springframework.sfgrecipeproject.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final String RECIPE_RECIPE_FORM_URL = "recipe/recipeform";

    // GET
    @GetMapping("/recipes")
    public String getRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

    @GetMapping({"/recipe/{id}/show", "/recipe/{id}"})
    public String getRecipeById(@PathVariable String id, Model model) {
        model.addAttribute("recipe",recipeService.findById(Long.parseLong(id)));
        return "recipe/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
        return RECIPE_RECIPE_FORM_URL;
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        recipeService.deleteById(Long.parseLong(id));
        return "redirect:/";
    }

    /* form */
    @GetMapping({"/recipe/new", "/new"})
    public String getNewRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return RECIPE_RECIPE_FORM_URL;
    }

    // POST
    /* form */
    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
        RecipeCommand recipeCommand = recipeService.saveRecipeCommand(command);

        if (bindingResultHasErrors(bindingResult)) {
            return RECIPE_RECIPE_FORM_URL;
        }
        return String.format("redirect:/recipe/%d/show", recipeCommand.getId());
    }

    private boolean bindingResultHasErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                         .forEach(objectError -> log.debug(objectError.toString()));
            return true;
        }
        return false;
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/recipe/404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}
