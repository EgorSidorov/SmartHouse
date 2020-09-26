package com.learning.commands;

import com.learning.model.Button;
import com.learning.model.House;
import com.learning.states.CurrentState;
import com.learning.states.HouseState;
import com.learning.states.ProfileState;
import com.learning.states.UndefinedState;
import java.util.List;
import java.util.UUID;

public class CreateButtonCommand extends Command {
    public CreateButtonCommand(List<String> arguments){
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
        if(arguments.size() != 3)
            return false;
        String buttonName = arguments.get(0);
        String typeValue = arguments.get(1);
        Integer buttonInt = typeValue.equals("set") ? 1 :
                            typeValue.equals("plus") ? 2 :
                            typeValue.equals("minus") ? 3 : 0;
        if(buttonInt.equals(0)){
            messageError = "Invalid button type";
            return false;
        }
        Integer value = Integer.valueOf(arguments.get(2));
        House house = ((HouseState)CurrentState.getInstance().getState()).getHouse().get();
        if(house.getButtons().stream().filter(b -> b.getName().equals(buttonName)).count() == 0) {
            Button button = new Button();
            button.setName(buttonName);
            button.setButtonType(buttonInt);
            button.setValue(value);
            button.setButtonId(UUID.randomUUID());
            house.getButtons().add(button);
            return true;
        } else {
            messageError = "Button with this name already exists";
        }
        return false;
    }
}
