package com.learning.smartHouse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "house", schema = "smartHouse")
@Data
public class House {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer houseId;

    @Column(nullable = false)
    String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, targetEntity = Device.class)
    @JoinColumn(name = "houseId", referencedColumnName = "houseId")
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(value = FetchMode.SUBSELECT)
    Collection<Device> devices;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, targetEntity = Button.class)
    @JoinColumn(name = "houseId", referencedColumnName = "houseId")
    @Fetch(value = FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    Collection<Button> buttons;

    @Column(name = "profileId")
    Integer profileId;

    /*@ManyToOne
    @JoinColumn(name = "profileId", referencedColumnName = "profileId")
    @JsonBackReference
    Profile profile;*/

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER, targetEntity = ButtonToDevice.class)
    @JoinColumn(name = "houseId", referencedColumnName = "houseId")
    @Fetch(value = FetchMode.SUBSELECT)
    @NotFound(action = NotFoundAction.IGNORE)
    Collection<ButtonToDevice> buttonToDevices;
}
