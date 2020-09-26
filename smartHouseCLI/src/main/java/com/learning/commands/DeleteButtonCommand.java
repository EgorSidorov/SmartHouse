package com.learning.commands;

import com.learning.model.Button;
import com.learning.model.House;
import com.learning.states.CurrentState;
import com.learning.states.HouseState;
import com.learning.states.ProfileState;
import com.learning.states.UndefinedState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class DeleteButtonCommand extends Command {
    public DeleteButtonCommand(List<String> arguments){
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
        Optional<Button> button = house.getButtons().stream().filter(d -> d.getName().equals(buttonName)).findFirst();
        if(button.isPresent()){
            UUID buttonId = button.get().getButtonId();
            house.setButtonToDevices(
                    house.getButtonToDevices()
                            .stream()
                            .filter(btd -> !btd.getButtonToDevicePK().getButtonId().equals(buttonId))
                            .collect(Collectors.toList())
            );
            return true;
        } else {
            messageError = "Button with this name not found";
        }
        return false;
    }
}
