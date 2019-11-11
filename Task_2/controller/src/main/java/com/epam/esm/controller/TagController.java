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

    @GetMapping("/{id}")
    public TagDTO getTag(@PathVariable long id) {
        return tagService.getByID(id);
    }

    @GetMapping()
    public List<TagDTO> getAllTags(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "5") int size) {
        return (List<TagDTO>) tagService.getAll(page, size);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO saveTag(@RequestBody @Valid TagDTO tag) {
        return tagService.add(tag);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagService.delete(id);
    }

    /*@GetMapping
    public void welcome(SecurityContextHolderAwareRequestWrapper request) {
        boolean b = request.isUserInRole("ROLE_ADMIN");
        System.out.println("ROLE_ADMIN=" + b);

        boolean c = request.isUserInRole("ROLE_USER");
        System.out.println("ROLE_USER=" + c);
    }*/

}
