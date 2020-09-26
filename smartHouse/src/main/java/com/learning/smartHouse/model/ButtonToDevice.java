package com.learning.smartHouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "buttonToDevice", schema = "smartHouse")
@Data
public class ButtonToDevice {
    @EmbeddedId
    private ButtonToDevicePK buttonToDevicePK;
    @Column(name = "houseId")
    Integer houseId;
}
