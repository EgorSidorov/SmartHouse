package com.learning.commands;

import com.learning.states.CurrentState;
import com.learning.states.HouseState;
import com.learning.states.ProfileState;
import com.learning.states.UndefinedState;

import java.util.ArrayList;
import java.util.List;

public class UndoStateCommand extends Command {
    public UndoStateCommand(List<String> arguments){
        super(arguments);
    }

    @Override
    public boolean execute() {
        if (CurrentState.getInstance().getState().getClass().equals(UndefinedState.class)) {
            return incorrectState();
        } else if (CurrentState.getInstance().getState().getClass().equals(HouseState.class)) {
            CurrentState.getInstance().setProfileState(
                    ((ProfileState)CurrentState.getInstance().getState()).getProfile()
            );
            return true;
        } else if (CurrentState.getInstance().getState().getClass().equals(ProfileState.class)) {
            CurrentState.getInstance().setUndefinedState();
            return true;
        }
        return false;
    }
}
