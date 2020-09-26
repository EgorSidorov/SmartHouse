package com.learning.model;
import lombok.Data;

import java.util.Collection;

@Data
public class House {
    Integer houseId;
    Integer profileId;
    String name;
    Collection<Device> devices;
    Collection<Button> buttons;
    Collection<ButtonToDevice> buttonToDevices;
}
