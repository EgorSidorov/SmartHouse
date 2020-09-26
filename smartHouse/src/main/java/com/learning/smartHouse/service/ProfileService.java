package com.learning.smartHouse.service;

import com.learning.smartHouse.model.Profile;
import com.learning.smartHouse.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Component
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Profile saveProfile(Profile profile){
        return profileRepository.save(profile);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteProfile(String profileName){
        profileRepository.deleteByName(profileName);
    }
}
