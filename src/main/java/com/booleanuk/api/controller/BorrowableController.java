package com.booleanuk.api.controller;

import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Borrowable;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.BorrowableRepository;
import com.booleanuk.api.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("borrowables")
public class BorrowableController {
    @Autowired
    private BorrowableRepository repository;

    @PostMapping("book")
    public ResponseEntity<ApiResponse<?>> createBook (@RequestBody Book bookDetails) {
        if (!bookDetails.isValid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified book, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Book book = new Book(bookDetails.getTitle(), bookDetails.getAuthor(), bookDetails.getPublisher(), bookDetails.getGenre());

        ApiResponse<Book> response = new ApiResponse<>("success", repository.save(book));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("game")
    public ResponseEntity<ApiResponse<?>> createGame (@RequestBody Game gameDetails) {
        if (!gameDetails.isValid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified game, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Game game = new Game(gameDetails.getTitle(), gameDetails.getPublisher(), gameDetails.getGenre(), gameDetails.getReleaseYear());

        ApiResponse<Game> response = new ApiResponse<>("success", repository.save(game));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Borrowable>>> readAll() {
        List<Borrowable> borrowables = this.repository.findAll();
        ApiResponse<List<Borrowable>> response = new ApiResponse<>("success", borrowables);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> readBook(@PathVariable int id) {
        Optional<Borrowable> bor = this.repository.findById(id);

        if (bor.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No item with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Borrowable borrowable = bor.get();
        ApiResponse<Borrowable> response = new ApiResponse<>("success", repository.save(borrowable));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<ApiResponse<?>> updateBook (@PathVariable int id, @RequestBody Book bookDetails) {
        Optional<Borrowable> bor = this.repository.findById(id);

        if (bor.isEmpty() || !bor.get().getType().equals("book")) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No book with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        if (!bookDetails.isValid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified book, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Book book = (Book) bor.get();
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublisher(bookDetails.getPublisher());
        book.setGenre(bookDetails.getGenre());

        ApiResponse<Book> response = new ApiResponse<>("success", repository.save(book));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/game/{id}")
    public ResponseEntity<ApiResponse<?>> updateGame (@PathVariable int id, @RequestBody Game gameDetails) {
        Optional<Borrowable> bor = this.repository.findById(id);

        if (bor.isEmpty() || !bor.get().getType().equals("game")) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No game with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        if (!gameDetails.isValid()) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified game, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Game game = (Game) bor.get();
        game.setTitle(gameDetails.getTitle());
        game.setPublisher(gameDetails.getPublisher());
        game.setGenre(gameDetails.getGenre());
        game.setReleaseYear(gameDetails.getReleaseYear());

        ApiResponse<Game> response = new ApiResponse<>("success", repository.save(game));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> delete (@PathVariable int id) {
        Optional<Borrowable> bor = this.repository.findById(id);

        if (bor.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No item with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Borrowable borrowable = bor.get();
        repository.delete(borrowable);
        ApiResponse<Borrowable> response = new ApiResponse<>("success", borrowable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
