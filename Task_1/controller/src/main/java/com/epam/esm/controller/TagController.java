package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tags")
public class TagController extends ResourceController {

    @Autowired
    private TagService tagService;

    protected TagController(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @GetMapping("/{id}")
    public @ResponseBody Tag getTag(@PathVariable long id)
    {
        return tagService.getTag(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTag(@RequestBody @Valid Tag tag) {
        tagService.addTag(tag);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
    }

}
