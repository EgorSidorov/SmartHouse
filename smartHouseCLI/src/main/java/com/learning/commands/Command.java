package com.learning.commands;
import com.learning.states.CurrentState;

import java.util.List;

public abstract class Command {
    public Command(List<String> arguments){
        this.arguments = arguments;
    }

    String messageError = null;
    List<String> arguments = null;
    public abstract boolean execute();

    protected boolean incorrectState(){
        messageError = "Incorrect state:"+ CurrentState.getInstance().getState().toString();
        return false;
    }

    public String getMessageError() {
        return messageError;
    }
}
