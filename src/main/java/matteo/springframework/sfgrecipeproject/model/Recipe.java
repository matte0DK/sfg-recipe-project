package matteo.springframework.sfgrecipeproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@ToString

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    @ToString.Exclude
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ToString.Exclude
    private Set<Category> categories = new HashSet<>();

    @Builder
    public Recipe(Long id, String description, Integer prepTime, Integer cookTime,
                  Integer servings, String source, String url, String directions,
                  Set<Ingredient> ingredients, Byte[] image, Difficulty difficulty,
                  Notes notes, Set<Category> categories) {

        this.id = id;
        this.description = description;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.source = source;
        this.url = url;
        this.directions = directions;
        this.ingredients = ingredients;
        this.image = image;
        this.difficulty = difficulty;
        this.notes = notes;
        this.categories = categories;
    }

    public Notes setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
        return notes;
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.remove(ingredient);
    }
}
