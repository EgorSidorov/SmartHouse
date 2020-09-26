package com.learning.states;

import com.learning.commands.*;
import com.learning.model.Profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ProfileState extends UndefinedState {
    protected Profile profile = new Profile();

    public ProfileState(){
        allowCommands.add("createHouse");
        allowCommands.add("createProfile");
        allowCommands.add("deleteHouse");
        allowCommands.add("deleteHouse");
        allowCommands.add("deleteProfile");
        allowCommands.add("saveProfile");
        allowCommands.add("setHouse");
        allowCommands.add("setProfile");
        allowCommands.add("undoState");
        allowCommands.add("showCurrentProfile");
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "ProfileState";
    }
}
