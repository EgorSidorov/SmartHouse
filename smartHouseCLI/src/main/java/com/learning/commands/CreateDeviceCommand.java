package com.learning.commands;

import com.learning.model.Device;
import com.learning.model.House;
import com.learning.states.CurrentState;
import com.learning.states.HouseState;
import com.learning.states.ProfileState;
import com.learning.states.UndefinedState;
import java.util.List;
import java.util.UUID;

public class CreateDeviceCommand extends Command{
    public CreateDeviceCommand(List<String> arguments){
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
        if(house.getDevices().stream().filter(d -> d.getName().equals(deviceName)).count() == 0) {
            Device device = new Device();
            device.setName(deviceName);
            device.setDeviceId(UUID.randomUUID());
            device.setValue(0);
            device.setPrevValue(0);
            house.getDevices().add(device);
            return true;
        } else {
            messageError = "Device with this name already exists";
        }
        return false;
    }
}
