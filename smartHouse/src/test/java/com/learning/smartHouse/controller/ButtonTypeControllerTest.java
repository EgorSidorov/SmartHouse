package com.learning.smartHouse.controller;

import com.learning.smartHouse.model.ButtonType;
import com.learning.smartHouse.repository.ButtonTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ButtonTypeControllerTest {

    @Autowired
    ButtonTypeController buttonTypeController;

    @Test
    void getAllButtonTypes() {
        Collection<ButtonType> buttonTypes = buttonTypeController.getAllButtonTypes();
        assertEquals(buttonTypes.size(),3);
        assertEquals(buttonTypes.stream().filter(buttonType -> buttonType.getName() == "set").count(),1);
        assertEquals(buttonTypes.stream().filter(buttonType -> buttonType.getName() == "plus").count(),1);
        assertEquals(buttonTypes.stream().filter(buttonType -> buttonType.getName() == "minus").count(),1);
    }
}