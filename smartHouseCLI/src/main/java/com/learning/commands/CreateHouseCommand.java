package com.learning.commands;

import com.learning.model.House;
import com.learning.model.Profile;
import com.learning.states.CurrentState;
import com.learning.states.HouseState;
import com.learning.states.ProfileState;
import com.learning.states.UndefinedState;

import java.util.ArrayList;
import java.util.List;

public class CreateHouseCommand extends Command {
    public CreateHouseCommand(List<String> arguments){
        super(arguments);
    }

        @Override
        public boolean execute() {
            if (CurrentState.getInstance().getState().getClass().equals(UndefinedState.class)) {
                return incorrectState();
            } else if (CurrentState.getInstance().getState().getClass().equals(HouseState.class)) {
                return command();
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
        if(profile.getHouses().stream().filter(h -> h.getName().equals(houseName)).count() == 0) {
            House house = new House();
            house.setName(houseName);
            house.setButtons(new ArrayList<>());
            house.setDevices(new ArrayList<>());
            house.setButtonToDevices(new ArrayList<>());
            profile.getHouses().add(house);
            return true;
        } else {
            messageError = "House with this name already exists";
        }
        return false;
    }
}
