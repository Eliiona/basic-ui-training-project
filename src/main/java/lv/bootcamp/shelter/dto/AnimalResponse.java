package lv.bootcamp.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lv.bootcamp.shelter.model.AnimalStatus;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * Response body for a single animal returned by the API.
 * {@code adoptionNote} is only populated for ADMIN callers (see AnimalService#toResponse) —
 * everyone else just sees the plain {@code status}.
 */
@Schema(
        description = "Represents the response body for a single animal returned by the API"
)
public record AnimalResponse(
        @Schema(
                description = "The unique identifier of the animal",
                example = "1"
        )
        Long id,
        @Schema(
                description = "The name of the animal",
                example = "Buddy"
        )
        String name,
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
                description = "The status of the animal",
                example = "AVAILABLE"
        )
        AnimalStatus status,
        @Schema(
                description = "The URL of the animal's image",
                example = "https://example.com/image.jpg"
        )
        String imageUrl,
        @Schema(
                description = "A note about the animal's adoption",
                example = "Needs more exercise"
        )
        String adoptionNote
) {}
