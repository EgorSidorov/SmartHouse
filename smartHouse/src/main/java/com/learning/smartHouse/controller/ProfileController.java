package com.learning.smartHouse.controller;


import com.learning.smartHouse.model.Profile;
import com.learning.smartHouse.repository.ProfileRepository;
import com.learning.smartHouse.service.DatabaseActiveService;
import com.learning.smartHouse.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private DatabaseActiveService databaseActiveService;

    @RequestMapping(value = "/getByName", method = RequestMethod.GET)
    public Profile getProfilesByName(String profileName){
        if(databaseActiveService.getActive().get()==true) {
            return getProfileByNameCachePut(profileName);
        } else {
            return getProfileByNameCacheable(profileName);
        }
    }

    @CachePut(cacheNames = "profile", key="#profileName",unless = "#result == null")
    public Profile getProfileByNameCachePut(String profileName){
        return profileRepository.findAllByName(profileName).stream().findFirst().orElse(null);
    }

    @Cacheable(cacheNames = "profile", key="#profileName")
    public Profile getProfileByNameCacheable(String profileName){
        return profileRepository.findAllByName(profileName).stream().findFirst().orElse(null);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @CachePut(cacheNames = "profile", key="#profile.name")
    public void saveProfile(@RequestBody(required = true) Profile profile){
        profileService.saveProfile(profile);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @CacheEvict(cacheNames = "profile", key="#profileName")
    public void deleteProfile(String profileName){
        profileService.deleteProfile(profileName);
    }
}
