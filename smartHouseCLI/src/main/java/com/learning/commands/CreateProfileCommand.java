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

public class CreateProfileCommand extends Command {
    public CreateProfileCommand(List<String> arguments){
        super(arguments);
    }

    @Override
    public boolean execute() {
        if (CurrentState.getInstance().getState().getClass().equals(UndefinedState.class)) {
            return command();
        } else if (CurrentState.getInstance().getState().getClass().equals(HouseState.class)) {
            return incorrectState();
        } else if (CurrentState.getInstance().getState().getClass().equals(ProfileState.class)) {
            return incorrectState();
        }
        return false;
    }

    private boolean command(){
        if(arguments.size() != 1)
            return false;
        String profileName = arguments.get(0);
        ProfileRequestImpl profileRequest = new ProfileRequestImpl();
        Optional<Profile> profile = profileRequest.getProfileByName(profileName);
        if(!profile.isPresent()) {
            Profile createdProfile = new Profile();
            createdProfile.setHouses(new ArrayList<>());
            createdProfile.setName(profileName);
            CurrentState.getInstance().setProfileState(createdProfile);
            return true;
        } else {
            messageError = "Profile with this name already exists";
        }
        return false;
    }
}
