package com.learning.states;

import com.learning.commands.*;

import java.util.*;

public abstract class State {
    protected ArrayList<String> allowCommands = new ArrayList<String>();


    private boolean onlyOneMethod(Map<String, List<String>> actions){
        if(actions.isEmpty())
            return false;
        return Collections.frequency(actions.values(),null) == actions.size() -1;
    }

    protected Map.Entry<String, List<String>> findCommand(Map<String, List<String>> actions){
        if(!onlyOneMethod(actions))
            return null;
       return actions.entrySet()
                .stream()
                .filter(stringStringEntry -> stringStringEntry.getValue() != null)
                .findFirst().orElse(null);
    }

    public Command validatedRun(Map<String, List<String>> actions) {
        Map.Entry<String, List<String>> command = findCommand(actions);
        if(command == null)
            return null;
        if (allowCommands.contains(command.getKey()))
            return allCommands(command);
        else
            return null;
    }

    public Command allCommands(Map.Entry<String, List<String>> command){
        switch (command.getKey()){
            case "callButton":
                return new CallButtonCommand(command.getValue());
            case "showCurrentProfile":
                return new ShowCurrentProfileCommand(command.getValue());
            case "createButton":
                return new CreateButtonCommand(command.getValue());
            case "createDevice":
                return new CreateDeviceCommand(command.getValue());
            case "createHouse":
                return new CreateHouseCommand(command.getValue());
            case "createProfile":
                return new CreateProfileCommand(command.getValue());
            case "deleteButton":
                return new DeleteButtonCommand(command.getValue());
            case "deleteDevice":
                return new DeleteDeviceCommand(command.getValue());
            case "deleteHouse":
                return new DeleteHouseCommand(command.getValue());
            case "deleteProfile":
                return new DeleteProfileCommand(command.getValue());
            case "mapButtonToDevice":
                return new MapButtonToDeviceCommand(command.getValue());
            case "saveProfile":
                return new SaveProfileCommand(command.getValue());
            case "setHouse":
                return new SetHouseCommand(command.getValue());
            case "setProfile":
                return new SetProfileCommand(command.getValue());
            case "undoState":
                return new UndoStateCommand(command.getValue());
            case "callButtonDevice":
                return new CallButtonDeviceCommand(command.getValue());
            default:
                return null;
        }
    }


}
