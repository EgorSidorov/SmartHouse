package com.learning.model;

import java.util.UUID;
import lombok.Data;

@Data
public class Button {
    UUID buttonId;
    String name;
    Integer buttonType;
    Integer value;
    Integer houseId;
}
