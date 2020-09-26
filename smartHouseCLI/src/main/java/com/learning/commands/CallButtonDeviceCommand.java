package com.learning.commands;

import com.learning.model.Button;
import com.learning.model.Device;
import com.learning.model.House;
import com.learning.states.CurrentState;
import com.learning.states.HouseState;
import com.learning.states.ProfileState;
import com.learning.states.UndefinedState;

import java.util.List;
import java.util.Optional;

public class CallButtonDeviceCommand extends Command {
    public CallButtonDeviceCommand(List<String> arguments){
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
        String buttonName = arguments.get(0);
        String deviceName = arguments.get(1);
        House house = ((HouseState)CurrentState.getInstance().getState()).getHouse().get();
        if((house.getButtons().stream().filter(b -> b.getName().equals(buttonName)).count() == 1
                || buttonName.equals("on")
                || buttonName.equals("off")
                || buttonName.equals("undo")) &&
        house.getDevices().stream().filter(d -> d.getName().equals(deviceName)).count() == 1) {
            Button button;
            if(buttonName .equals("on")
                    || buttonName .equals("off")
                    || buttonName .equals("undo")){
                button = new Button();
                button.setName(buttonName);
                if(button.getName().equals("undo"))
                    button.setButtonType(100);
                if(button.getName().equals("on")) {
                    button.setButtonType(101);
                }
                if(button.getName().equals("off")) {
                    button.setButtonType(102);
                }
            }
            else{
                button =
                        house.getButtons()
                                .stream()
                                .filter(b -> b.getName().equals(buttonName))
                                .findFirst().get();
            }
            Device device =
                    house.getDevices()
                            .stream()
                            .filter(b -> b.getName().equals(deviceName))
                            .findFirst().get();
            switch (button.getButtonType()){
                case 1:
                    device.setPrevValue(device.getValue());
                    device.setValue(button.getValue());
                    break;
                case 2:
                    device.setPrevValue(device.getValue());
                    device.setValue(device.getValue() + button.getValue());
                    break;
                case 3:
                    device.setPrevValue(device.getValue());
                    device.setValue(device.getValue() - button.getValue());
                    break;
                case 100:
                    device.setValue(device.getPrevValue());
                    device.setPrevValue(0);
                    break;
                case 101:
                    device.setPrevValue(device.getValue());
                    device.setValue(1);
                    break;
                case 102:
                    device.setPrevValue(device.getValue());
                    device.setValue(0);
                    break;
                default:
                    break;
            }
            return true;
        } else {
            messageError = "Button with this name not found";
        }
        return false;
    }
}
