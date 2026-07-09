package lv.bootcamp.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * JSON request body for creating a new animal via the REST API.
 * Status is not included; all new animals start as AVAILABLE.
 */
@Schema(description = "Represents the request body for creating a new animal")
public record AnimalCreateRequest(

        @NotBlank(message = "Name is required")
        @Schema(
                description = "The name of the animal",
                example = "Buddy"
        )
        String name,

        @NotNull(message = "Type is required")
        @Schema(
                description = "The type of the animal",
                example = "DOG"
        )
        AnimalType type,

        @Schema(
                description = "The breed of the animal",
                example = "Golden Retriever"
        )
        String breed,

        @Min(value = 0, message = "Age cannot be negative")
        @Schema(
                description = "The age of the animal",
                example = "3"
        )
        Integer age,

        @Schema(
                description = "A description of the animal",
                example = "A friendly and loyal dog"
        )
        String description,

        @Schema(
                description = "The URL of the animal's image",
                example = "https://example.com/image.jpg"
        )
        String imageUrl
) {}