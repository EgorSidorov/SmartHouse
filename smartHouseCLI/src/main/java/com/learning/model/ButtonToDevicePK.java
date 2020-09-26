package com.learning.model;

import lombok.Data;

import java.util.UUID;

@Data
public class ButtonToDevicePK {
    UUID buttonId;
    UUID deviceId;
}
