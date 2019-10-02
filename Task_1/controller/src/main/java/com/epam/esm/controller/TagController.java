package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController extends ResourceController {

    @Autowired
    private TagService tagService;

    @Autowired
    protected TagController(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @GetMapping("/{id}")
    public @ResponseBody Tag getTag(@PathVariable long id)
    {
        return tagService.getTag(id);
    }



}
