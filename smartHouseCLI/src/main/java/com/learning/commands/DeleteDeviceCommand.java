package com.learning.commands;

import com.learning.model.Device;
import com.learning.model.House;
import com.learning.model.Profile;
import com.learning.states.CurrentState;
import com.learning.states.HouseState;
import com.learning.states.ProfileState;
import com.learning.states.UndefinedState;

import java.util.*;
import java.util.stream.Collectors;

public class DeleteDeviceCommand extends Command {
    public DeleteDeviceCommand(List<String> arguments){
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
        if(arguments.size() != 1)
            return false;
        String deviceName = arguments.get(0);
        House house = ((HouseState)CurrentState.getInstance().getState()).getHouse().get();
        Optional<Device> device = house.getDevices().stream().filter(d -> d.getName().equals(deviceName)).findFirst();
        if(device.isPresent()){
            UUID deviceId = device.get().getDeviceId();
            house.setButtonToDevices(
                    house.getButtonToDevices()
                            .stream()
                            .filter(btd -> !btd.getButtonToDevicePK().getDeviceId().equals(deviceId))
                            .collect(Collectors.toList())
            );
            return true;
        } else {
            messageError = "Device with this name not found";
        }
        return false;
    }
}
