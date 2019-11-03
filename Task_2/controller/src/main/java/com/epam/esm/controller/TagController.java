package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/v1/tags")
@RestController
@Validated
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PreAuthorize("hasAnyRole('ADMIN,USER,GUEST')")
    @GetMapping("/{id}")
    public TagDTO getTag(@PathVariable long id) {
        return tagService.getByID(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN,USER,GUEST')")
    @GetMapping()
    public List<TagDTO> getAllTags(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "5") int size) {
        return tagService.getAll(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO saveTag(@RequestBody @Valid TagDTO tag) {
        return tagService.add(tag);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagService.delete(id);
    }

}
