package com.learning.states;

import com.learning.commands.Command;
import com.learning.model.ButtonToDevice;
import com.learning.model.House;

import java.util.*;

public class HouseState extends ProfileState {
    private String HouseName = null;
    Collection<ButtonToDevice> buttonToDevices;

    public HouseState(){
        allowCommands.add("callButton");
        allowCommands.add("callButtonDevice");
        allowCommands.add("clearDevice");
        allowCommands.add("createButton");
        allowCommands.add("createDevice");
        allowCommands.add("createHouse");
        allowCommands.add("createProfile");
        allowCommands.add("deleteButton");
        allowCommands.add("deleteDevice");
        allowCommands.add("deleteHouse");
        allowCommands.add("deleteDevice");
        allowCommands.add("deleteHouse");
        allowCommands.add("deleteProfile");
        allowCommands.add("mapButtonToDevice");
        allowCommands.add("saveProfile");
        allowCommands.add("setHouse");
        allowCommands.add("setProfile");
        allowCommands.add("undoState");
        allowCommands.add("showCurrentProfile");
    }

    public String getHouseName() {
        return HouseName;
    }

    public Optional<House> getHouse() {
        return profile.getHouses().stream().filter(house -> house.getName().equals(HouseName)).findFirst();
    }

    public void setHouseName(String houseName) {
        HouseName = houseName;
    }

    public Collection<ButtonToDevice> getButtonToDevices() {
        return buttonToDevices;
    }

    public void setButtonToDevices(Collection<ButtonToDevice> buttonToDevices) {
        this.buttonToDevices = buttonToDevices;
    }

    @Override
    public String toString() {
        return "HouseState";
    }
}
