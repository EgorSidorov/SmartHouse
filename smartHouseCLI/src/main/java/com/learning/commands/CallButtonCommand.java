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

public class CallButtonCommand extends Command {
    public CallButtonCommand(List<String> arguments){
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
        String buttonName = arguments.get(0);
        House house = ((HouseState)CurrentState.getInstance().getState()).getHouse().get();
        if(house.getButtons().stream().filter(b -> b.getName().equals(buttonName)).count() == 1) {
            Button button =
                    house.getButtons()
                            .stream()
                            .filter(b -> b.getName().equals(buttonName))
                            .findFirst().get();
            house.getButtonToDevices()
                    .stream()
                    .filter(btd -> btd.getButtonToDevicePK().getButtonId().equals(button.getButtonId()))
                    .forEach(btd -> {
                        Optional<Device> device =
                                house.getDevices().
                                        stream().filter(d -> d.getDeviceId().
                                        equals(btd.getButtonToDevicePK().getDeviceId())).findFirst();
                        if(device.isPresent()){
                            switch (button.getButtonType()){
                                case 1:
                                    device.get().setPrevValue(device.get().getValue());
                                    device.get().setValue(button.getValue());
                                    break;
                                case 2:
                                    device.get().setPrevValue(device.get().getValue());
                                    device.get().setValue(device.get().getValue() + button.getValue());
                                    break;
                                case 3:
                                    device.get().setPrevValue(device.get().getValue());
                                    device.get().setValue(device.get().getValue() - button.getValue());
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                        );
            return true;
        } else {
            messageError = "Button with this name not found";
        }
        return false;
    }
}
