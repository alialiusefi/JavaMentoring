package com.epam.esm.controller;

import com.epam.esm.service.TagService;
import com.java.esm.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/tags")
public class TagController extends TableController {

    @Autowired
    private TagService tagService;

    @Autowired
    protected TagController(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Tag getTag(
            @RequestParam(value = "id") long id)
    {
        System.out.println("Method!");
        return tagService.getTag(id);
    }


}
