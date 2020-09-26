package com.learning.smartHouse.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "buttonType", schema = "smartHouse")
@Data
public class ButtonType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "typeId")
    Integer typeId;
    @Column(nullable = false)
    String name;
}
