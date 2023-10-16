package com.gambasoftware.pochibernate.api;

import com.gambasoftware.pochibernate.api.converters.AuthorConverter;
import com.gambasoftware.pochibernate.api.models.AuthorModel;
import com.gambasoftware.pochibernate.data.AuthorService;
import com.gambasoftware.pochibernate.data.ManualDBConnectionService;
import com.gambasoftware.pochibernate.data.entities.Author;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private AuthorService authorService;
    private ManualDBConnectionService manualDBConnectionService;

    public AuthorController(AuthorService authorService, ManualDBConnectionService manualDBConnectionService) {
        this.authorService = authorService;
        this.manualDBConnectionService = manualDBConnectionService;
    }

    @PostMapping("/authors")
    public void create(@RequestBody Author author) {
        authorService.executeAllOperations(author);
    }

    @GetMapping("/authors")
    public ResponseEntity getAuthor() {
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors.stream()
                .map(AuthorConverter::convert)
                .collect(Collectors.toList()));
    }

    @GetMapping("/authors/{authorId}")
    public ResponseEntity getAuthor(@PathVariable String authorId) {
        Author author = authorService.get(authorId);
        return ResponseEntity.ok(AuthorConverter.convert(author));
    }

    @PostMapping("/manual/authors")
    public void create(@RequestBody String name) {
        manualDBConnectionService.executeAllOperations(name);
    }
}
