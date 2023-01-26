package com.example.controller.api;

import com.example.model.dto.ClaimRequestRs;
import com.example.model.dto.author.AuthorRq;
import com.example.model.dto.author.AuthorRs;
import com.example.model.dto.search.SearchRq;
import com.example.service.author.AuthorService;
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
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService authorService) {
        this.service = authorService;
    }

    @GetMapping
    @Operation(summary = "Get list of all authors.")
    public ResponseEntity<List<AuthorRs>> getAll() {
        val result = service.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated authors.")
    public ResponseEntity<Page<AuthorRs>> getAllPaginated(@RequestParam(defaultValue = "0") Integer pageNo,
                                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                                          @RequestParam(defaultValue = "fullName") String sortBy) {
        val page = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        val result = service.getAllPaginated(page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get author by given id.")
    public ResponseEntity<AuthorRs> getById(@PathVariable("id") Long id) {
        val result = service.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new author.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created.")
    })
    public ResponseEntity<AuthorRs> add(@RequestBody AuthorRq dto) {
        AuthorRs result = service.add(dto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AuthorRs> update(@PathVariable("id") Long id, @RequestBody AuthorRq dto
    ) {
        AuthorRs result = service.update(id, dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    @Operation(summary = "Gets a list of Authors by search criteria.")
    @ApiResponses(value = {
            @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "200", description = "Successfully found."),
            @ApiResponse(responseCode = "400", description = "Validation exception."),
            @ApiResponse(responseCode = "401", description = "Unauthorized."),
            @ApiResponse(responseCode = "404", description = "Not found."),
            @ApiResponse(responseCode = "403", description = "Forbidden.")
    })
    public ResponseEntity<Page<AuthorRs>> search(@RequestBody SearchRq dto) {
        Page<AuthorRs> result = service.search(dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
