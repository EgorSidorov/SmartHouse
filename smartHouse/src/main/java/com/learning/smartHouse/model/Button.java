package com.learning.smartHouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "button", schema = "smartHouse")
@Data
public class Button {
    @Id
    @Column(columnDefinition = "BINARY(16)")//MYSQL CASE
    //@Type(type="uuid-char") ORACLE CASE
    UUID buttonId;
    @Column(name = "buttonType", nullable = false)
    Integer buttonType;
    @Column(name = "value",nullable = false)
    Integer value;
    @Column(name = "name",nullable = false)
    String name;
    @Column(name = "houseId")
    Integer houseId;
}
