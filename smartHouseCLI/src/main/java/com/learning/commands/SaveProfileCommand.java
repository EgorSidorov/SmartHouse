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
import java.util.Optional;

public class SaveProfileCommand extends Command {
    public SaveProfileCommand(List<String> arguments){
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
        Optional<Profile> profile = Optional.ofNullable(((ProfileState) CurrentState.getInstance().getState()).getProfile());
        ProfileRequestImpl profileRequest = new ProfileRequestImpl();
        if(profileRequest.saveProfile(profile.get())) {
            CurrentState.getInstance().setUndefinedState();
            return true;
        } else {
            messageError = "Error save.";
        }
        return false;
    }
}
