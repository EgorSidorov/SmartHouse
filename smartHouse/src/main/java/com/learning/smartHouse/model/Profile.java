package com.learning.smartHouse.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "profile", schema = "smartHouse")
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer profileId;
    @Column(nullable = false, unique = true, length = 100)
    String name;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,targetEntity = House.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "profileId", referencedColumnName = "profileId")
    Collection<House> houses;
}
