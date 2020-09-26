package com.learning.smartHouse.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class ButtonToDevicePK implements Serializable{
    @Column(name = "buttonId", columnDefinition = "BINARY(16)")//MYSQL CASE
    //@Type(type="uuid-char") ORACLE CASE
    UUID buttonId;
    @Column(name = "deviceId", columnDefinition = "BINARY(16)")//MYSQL CASE
    //@Type(type="uuid-char") ORACLE CASE
    UUID deviceId;
}
