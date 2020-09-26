package com.learning.commands;

import com.learning.model.Profile;
import com.learning.states.CurrentState;
import com.learning.states.HouseState;
import com.learning.states.ProfileState;
import com.learning.states.UndefinedState;

import java.util.List;

public class ShowCurrentProfileCommand extends Command {
    public ShowCurrentProfileCommand(List<String> arguments){
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
        Profile profile = ((ProfileState)CurrentState.getInstance().getState()).getProfile();
        System.out.println(profile.toString());
        return true;
    }
}
