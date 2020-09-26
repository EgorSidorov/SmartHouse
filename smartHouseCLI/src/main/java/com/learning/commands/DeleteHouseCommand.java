package com.learning.commands;

import com.learning.Requests.ProfileRequestImpl;
import com.learning.model.Profile;
import com.learning.states.CurrentState;
import com.learning.states.HouseState;
import com.learning.states.ProfileState;
import com.learning.states.UndefinedState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeleteHouseCommand extends Command {
    public DeleteHouseCommand(List<String> arguments){
        super(arguments);
    }

    @Override
    public boolean execute() {
        if (CurrentState.getInstance().getState().getClass().equals(UndefinedState.class)) {
            return incorrectState();
        } else if (CurrentState.getInstance().getState().getClass().equals(HouseState.class)) {
            return incorrectState();
        } else if (CurrentState.getInstance().getState().getClass().equals(ProfileState.class)) {
            return command();
        }
        return false;
    }
    private boolean command(){
        if(arguments.size() != 1)
            return false;
        String houseName = arguments.get(0);
        Profile profile = ((ProfileState)CurrentState.getInstance().getState()).getProfile();
        if(profile.getHouses().stream().filter(h -> h.getName().equals(houseName)).count() == 1) {
            profile.getHouses().stream().filter(house -> house.getName().equals(houseName)).findFirst().get();
            return true;
        } else {
            messageError = "House with this name not found";
        }
        return false;
    }
}
