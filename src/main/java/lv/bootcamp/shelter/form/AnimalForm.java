package lv.bootcamp.shelter.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * Form-backing object for server-rendered Thymeleaf pages.
 * Kept separate from API DTOs so form changes do not silently change the JSON contract.
 */
public record AnimalForm(

        @Schema(
                description = "Name of the animal",
                example = "Buddy"
        )
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Type is required")
        @Schema(
                description = "Type of the animal",
                example = "DOG"
        )
        AnimalType type,

        @Schema(
                description = "Breed of the animal",
                example = "Golden Retriever"
        )
        String breed,

        @Schema(
                description = "Age of the animal in years",
                example = "3"
        )
        @Min(value = 0, message = "Age cannot be negative")
        Integer age,

        @Schema(
                description = "Description of the animal",
                example = "A friendly and loyal dog"
        )
        String description,

        @Schema(
                description = "URL of the animal's image",
                example = "https://example.com/image.jpg"
        )
        String imageUrl
) {}