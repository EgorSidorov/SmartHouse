package com.learning.model;

import lombok.Data;

import java.util.Collection;

@Data
public class Profile {
    Integer profileId;
    String name;
    Collection<House> houses;
}
