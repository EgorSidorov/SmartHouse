package com.learning.states;


public class UndefinedState extends State {
    public UndefinedState(){
        allowCommands.add("createProfile");
        allowCommands.add("deleteProfile");
        allowCommands.add("setProfile");
    }

    @Override
    public String toString() {
        return "UndefinedState";
    }
}
