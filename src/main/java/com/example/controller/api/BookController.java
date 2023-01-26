package com.example.controller.api;

import com.example.model.dto.book.BookRq;
import com.example.model.dto.book.BookRs;
import com.example.model.dto.search.SearchRq;
import com.example.service.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService BookService) {
        this.service = BookService;
    }

    @GetMapping
    @Operation(summary = "Get list of all Books.")
    public ResponseEntity<List<BookRs>> getAll() {
        val result = service.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated Books.")
    public ResponseEntity<Page<BookRs>> getAllPaginated(@RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                        @RequestParam(defaultValue = "title") String sortBy) {
        val page = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        val result = service.getAllPaginated(page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get Book by given id.")
    public ResponseEntity<BookRs> getById(@PathVariable("id") Long id) {
        val result = service.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new Book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created.")
    })
    public ResponseEntity<BookRs> add(@RequestBody BookRq dto) {
        BookRs result = service.add(dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookRs> update(@PathVariable("id") Long id, @RequestBody BookRq dto
    ) {
        BookRs result = service.update(id, dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    @Operation(summary = "Gets a list of Books by search criteria.")
    @ApiResponses(value = {
            @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "200", description = "Successfully found."),
            @ApiResponse(responseCode = "400", description = "Validation exception."),
            @ApiResponse(responseCode = "401", description = "UnBookized."),
            @ApiResponse(responseCode = "404", description = "Not found."),
            @ApiResponse(responseCode = "403", description = "Forbidden.")
    })
    public ResponseEntity<Page<BookRs>> search(@RequestBody SearchRq dto) {
        Page<BookRs> result = service.search(dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
