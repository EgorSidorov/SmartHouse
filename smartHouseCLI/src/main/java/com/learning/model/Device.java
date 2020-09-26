package com.learning.model;
import lombok.Data;

import java.util.UUID;

@Data
public class Device {
    UUID deviceId;
    String name;
    Integer value;
    Integer prevValue;
    Integer houseId;
}
