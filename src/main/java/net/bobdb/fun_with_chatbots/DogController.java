package net.bobdb.fun_with_chatbots;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dogs")
class DogController {

    private final DogService dogService;

    public DogController(DogService dogService) {
       this.dogService = dogService;
    }

    @GetMapping("")
    List<Dog> findAllDogs() {
        return dogService.findAll();
    }

    @PostMapping("/search")
    ResponseEntity<List<Dog>> findDogs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String breed,
            @RequestParam(required = false) String description) {

        List<Dog> dogs = dogService.findDogsWithCustomSearch(name,breed, description);

        return ResponseEntity.ok(dogs);
    }

    @PostMapping("/search/example")
    List<Dog> findDogsByExample(@RequestBody @Valid Dog dog) {
        return dogService.findDogsByExample(dog);
    }

    @PostMapping("/search/example/one")
    Dog findOneDogByExample(@RequestBody @Valid Dog dog) {
        return dogService.findOneDogByExample(dog)
                .orElseThrow(()-> new NoResultException("no doggies found"));
    }

    @PostMapping("/count")
    long countByExample(@RequestBody @Valid Dog dog) {
        return dogService.countByExample(dog);
    }

    @PostMapping("/exists")
    boolean existByExample(@RequestBody @Valid Dog dog) {
        return dogService.existsByExample(dog);
    }

}
