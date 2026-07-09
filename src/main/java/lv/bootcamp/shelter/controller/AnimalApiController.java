package lv.bootcamp.shelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lv.bootcamp.shelter.dto.AnimalCreateRequest;
import lv.bootcamp.shelter.dto.AnimalResponse;
import lv.bootcamp.shelter.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for shelter animal endpoints.
 * Returns JSON — does not render HTML pages.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/animals")
@Tag(
        name = "Animals",
        description = "Operations for managing shelter animals")
public class AnimalApiController {

    private final AnimalService animalService;
    private final Authentication authentication;

    @GetMapping
    @Operation(
            summary = "List all animals",
            description = "Returns all animals in shelter.")
    @ApiResponse(
            responseCode = "200",
            description = "Animal list returned successfully")
    public List<AnimalResponse> findAll() {
        return animalService.findAll();
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Get animal by ID",
            description = "Returns a specific animal by its ID.")
    @ApiResponse(
            responseCode = "200",
            description = "Animal returned successfully")
    @ApiResponse(
            responseCode = "404",
            description = "Animal not found")
    public ResponseEntity<AnimalResponse> findById(@Parameter(description = "ID of the animal") @PathVariable Long id) {
        return animalService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lists adopted animals. Restricted to ROLE_ADMIN — see SecurityConfig.
     * Read-only, so it's a good endpoint for testing role-based JWT authorization:
     * calling it repeatedly (e.g. with/without a token, or with a ROLE_USER token)
     * has no side effects, unlike {@code POST /api/animals}.
     */
    @GetMapping("/adopted")
    @Operation(
            summary = "List adopted animals",
            description = "Returns all adopted animals in the shelter.")
    @ApiResponse(
            responseCode = "200",
            description = "Adopted animal list returned successfully")
    public List<AnimalResponse> findAdopted() {
        return animalService.findAdopted();
    }

    /**
     * Creates a new animal. Restricted to ROLE_ADMIN — see SecurityConfig.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new animal",
            description = "Creates a new animal in the shelter.")
    @ApiResponse(
            responseCode = "201",
            description = "Animal created")
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request body")
    public AnimalResponse create(@RequestBody @Valid AnimalCreateRequest request) {
        return animalService.create(request);
    }

    /**
     * Adopts an animal as the currently logged-in user. Restricted to ROLE_USER
     * (not ROLE_ADMIN) — see SecurityConfig.
     */
    @PostMapping("/{id}/adopt")
    @Operation(
            summary = "Adopt an animal from shelter",
            description = "Adopts an animal as the currently logged-in user.")
    @ApiResponse(
            responseCode = "200",
            description = "Animal adopted successfully")
    @ApiResponse(
            responseCode = "404",
            description = "Animal not found")
    public ResponseEntity<AnimalResponse> adopt(@Parameter(description = "ID of the animal") @PathVariable Long id, Authentication authentication) {

        return animalService.adopt(id, authentication.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleAlreadyAdopted(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
