package com.learning.smartHouse.controller;

import com.learning.smartHouse.model.ButtonType;
import com.learning.smartHouse.repository.ButtonTypeRepository;
import com.learning.smartHouse.service.DatabaseActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/buttonType")
public class ButtonTypeController {
    @Autowired
    private ButtonTypeRepository buttonTypeRepository;
    @Autowired
    private DatabaseActiveService databaseActiveService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Collection<ButtonType> getAllButtonTypes(){
        return buttonTypeRepository.findAll();
    }
}
