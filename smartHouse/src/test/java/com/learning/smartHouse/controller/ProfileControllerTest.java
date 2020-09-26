package com.learning.smartHouse.controller;
import com.learning.smartHouse.model.Button;
import com.learning.smartHouse.model.Device;
import com.learning.smartHouse.model.House;
import com.learning.smartHouse.model.Profile;
import com.learning.smartHouse.repository.ProfileRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProfileControllerTest {

    @Autowired
    ProfileController profileController;
    @Autowired
    ProfileRepository profileRepository;

    @BeforeEach
    void beforeTest(){
        profileRepository.deleteAll();
        createFirstTestProfile();
        createSecondTestProfile();
    }

    @Test
    void saveProfile() {
        assertEquals(profileRepository.findAll().size() ,2);
        Profile profile = getFirstProfile();
        Collection<House> houses = profile.getHouses();
        House house = getTestHouse11(houses);
        Collection<Button> buttons = new ArrayList<>();
        Button button = new Button();
        button.setButtonType(1);
        button.setValue(12);
        button.setName("testButton");
        UUID uuid = UUID.randomUUID();
        button.setButtonId(uuid);
        buttons.add(button);
        house.setButtons(buttons);
        houses.clear();
        houses.add(house);
        profile.setHouses(houses);
        profileController.saveProfile(profile);
        profile = getFirstProfile();
        assertEquals(profile.getHouses().size(),1);
        house = getTestHouse11(profile.getHouses());
        assertEquals(house.getButtons().size(),1);
        assertEquals(house.getButtons().stream().findFirst().isPresent(),true);
        button = house.getButtons().stream().findFirst().get();
        assertEquals(button.getButtonId(),uuid);
    }

    @Test
    void getAllProfile() {
        assertEquals(profileRepository.findAll().size() ,2);
        Collection<House> houses = getFirstProfile().getHouses();
        assertEquals(houses.size() ,2);
        assertEquals(houses.
                stream().
                filter(house ->
                        house.getName().equals("testHouse11"))
                .count(), 1);
        assertEquals(houses.
                stream().
                filter(house ->
                        house.getName().equals("testHouse12"))
                .count(), 1);
        House house = getTestHouse11(houses);
        assertEquals(house.getButtons().size(),0);
        assertEquals(house.getDevices().size(),1);
        assertEquals(house.getDevices().
                stream().findFirst().
                get().getName(),"testDevice111");
    }

    @Test
    void deleteProfile() {
        assertEquals(profileRepository.findAll().size() ,2);
        profileController.deleteProfile("testProfile1");
        assertEquals(profileRepository.findAll().size() ,1);
    }

    void createFirstTestProfile(){
        Profile testProfile1 = new Profile();
        testProfile1.setName("testProfile1");
        Collection<House> testHouses1 = new ArrayList<>();
        House testHouse11 = new House();
        Collection<Device> testDevices111 = new ArrayList<>();
        Device testDevice1111 = new Device();
        testDevice1111.setName("testDevice111");
        testDevice1111.setDeviceId(UUID.randomUUID());
        testDevice1111.setValue(1);
        testDevice1111.setPrevValue(0);
        testDevices111.add(testDevice1111);
        testHouse11.setDevices(testDevices111);
        testHouse11.setName("testHouse11");
        House testHouse12 = new House();
        testHouse12.setName("testHouse12");
        testHouses1.add(testHouse11);
        testHouses1.add(testHouse12);
        testProfile1.setHouses(testHouses1);
        profileController.saveProfile(testProfile1);
    }

    void createSecondTestProfile(){
        Profile testProfile2 = new Profile();
        testProfile2.setName("testProfile2");
        Collection<House> testHouses2 = new ArrayList<>();
        House testHouse21 = new House();
        testHouse21.setName("testHouse21");
        House testHouse22 = new House();
        testHouse22.setName("testHouse22");
        testHouses2.add(testHouse21);
        testHouses2.add(testHouse22);
        testProfile2.setHouses(testHouses2);
        profileController.saveProfile(testProfile2);
    }

    Profile getFirstProfile(){
        return profileController
                .getProfilesByName("testProfile1");
    }

    House getTestHouse11(Collection<House> houses){
        return houses
                .stream()
                .filter(h -> h.getName().equals("testHouse11"))
                .findFirst().get();
    }
}