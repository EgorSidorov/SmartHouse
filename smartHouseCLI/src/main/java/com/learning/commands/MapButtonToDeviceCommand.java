package com.learning.commands;

import com.learning.model.*;
import com.learning.states.CurrentState;
import com.learning.states.HouseState;
import com.learning.states.ProfileState;
import com.learning.states.UndefinedState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MapButtonToDeviceCommand extends Command {
    public MapButtonToDeviceCommand(List<String> arguments){
        super(arguments);
    }

    @Override
    public boolean execute() {
        if (CurrentState.getInstance().getState().getClass().equals(UndefinedState.class)) {
            return incorrectState();
        } else if (CurrentState.getInstance().getState().getClass().equals(HouseState.class)) {
            return command();
        } else if (CurrentState.getInstance().getState().getClass().equals(ProfileState.class)) {
            return incorrectState();
        }
        return false;
    }

    private boolean command(){
        if(arguments.size() != 2)
            return false;
        HouseState houseState = (HouseState)CurrentState.getInstance().getState();
        Optional<House> house = houseState.getHouse();
        String buttonName = arguments.get(0);
        String deviceName = arguments.get(1);
        if(house.isPresent()){
            Collection<ButtonToDevice> buttonToDevices = house.get().getButtonToDevices();
            Optional<Button> button = house.get().getButtons().stream().filter(b -> b.getName().equals(buttonName)).findFirst();
            Optional<Device> device = house.get().getDevices().stream().filter(d -> d.getName().equals(deviceName)).findFirst();
            if(button.isPresent() && device.isPresent()){
                ButtonToDevice buttonToDevice = new ButtonToDevice();
                ButtonToDevicePK buttonToDevicePK = new ButtonToDevicePK();
                buttonToDevicePK.setButtonId(button.get().getButtonId());
                buttonToDevicePK.setDeviceId(device.get().getDeviceId());
                buttonToDevice.setButtonToDevicePK(buttonToDevicePK);
                if(!buttonToDevices.contains(buttonToDevice)) {
                    buttonToDevices.add(buttonToDevice);
                    return true;
                } else {
                    messageError = "This mapping already exists.";
                }
            } else {
                messageError = "Button or device not found";
            }
        }
        return false;
    }
}
