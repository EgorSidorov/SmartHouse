package com.learning.smartHouse.repository;

import com.learning.smartHouse.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    long deleteByName(String name);
    Collection<Profile> findAllByName(String name);
    Profile save(Profile s);
}
