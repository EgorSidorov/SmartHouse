package com.learning.smartHouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "device", schema = "smartHouse")
@Data
public class Device {
    @Id
    @Column(columnDefinition = "BINARY(16)")//MYSQL CASE
    //@Type(type="uuid-char") ORACLE CASE
    UUID deviceId;
    @Column(name = "name",nullable = false)
    String name;
    @Column(name = "value",nullable = false)
    Integer value;
    @Column(name = "prevValue",nullable = false)
    Integer prevValue;
    @Column(name = "houseId")
    Integer houseId;
}
