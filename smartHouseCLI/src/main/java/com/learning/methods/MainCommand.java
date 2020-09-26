package com.learning.methods;

import com.learning.states.CurrentState;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.*;
import java.util.concurrent.Callable;

@Command(name = "MainCommander", mixinStandardHelpOptions = true, version = "1.0",
        description = "Web CLI for smart house")
public class MainCommand implements Callable<Integer> {

    @Option(names = {"--callButton"}, description = "Call Button")
    private String callButton;

    @Option(names = {"--callButtonDevice"}, description = "Call Button for Device without mapping", split = ",")
    private String[] callButtonDevice;

    @Option(names = {"--showCurrentProfile"}, description = "Show current profile.")
    private Boolean showCurrentProfile;

    @Option(names = {"--createButton"}, description = "Create new button", split = ",")
    private String[] createButton;

    @Option(names = {"--createDevice"}, description = "Create new device")
    private String createDevice;

    @Option(names = {"--createHouse"}, description = "Create new house")
    private String createHouse;

    @Option(names = {"--createProfile"}, description = "Create new profile")
    private String createProfile;

    @Option(names = {"--deleteButton"}, description = "Delete button")
    private String deleteButton;

    @Option(names = {"--deleteDevice"}, description = "Delete device")
    private String deleteDevice;

    @Option(names = {"--deleteHouse"}, description = "Delete house")
    private String deleteHouse;

    @Option(names = {"--deleteProfile"}, description = "Delete Profile")
    private String deleteProfile;

    @Option(names = {"--mapButtonToDevice"}, description = "Connect button and device", split = ",")
    private String[] mapButtonToDevice;

    @Option(names = {"--saveProfile"}, description = "Save profile")
    private Boolean saveProfile;

    @Option(names = {"--setHouse"}, description = "Set house")
    private String setHouse;

    @Option(names = {"--setProfile"}, description = "Set profile")
    private String setProfile;

    @Option(names = {"--undoState"}, description = "Undo state to last. For house state profile. For profile set undefined.")
    private Boolean undoState;


    public Integer call() throws Exception {
        com.learning.commands.Command command = CurrentState.getInstance().getState().validatedRun(buildCommands());
        if(command != null){
            if(!command.execute()) {
                if(command.getMessageError() != null) {
                    System.out.println(command.getMessageError());
                } else {
                    System.out.println("Error executing command.");
                }
            }
        } else {
            System.out.println("Error command.");
        }
        return 0;
    }

    private Map<String,List<String>> buildCommands(){
        Map<String, List<String>> resMap = new TreeMap<>();
        resMap.put("callButton", oneElements(callButton));
        resMap.put("showCurrentProfile",zeroElements(showCurrentProfile));
        resMap.put("createButton",threeElements(createButton));
        resMap.put("createDevice",oneElements(createDevice));
        resMap.put("createHouse",oneElements(createHouse));
        resMap.put("createProfile",oneElements(createProfile));
        resMap.put("deleteButton",oneElements(deleteButton));
        resMap.put("deleteDevice",oneElements(deleteDevice));
        resMap.put("deleteHouse",oneElements(deleteHouse));
        resMap.put("deleteProfile",oneElements(deleteProfile));
        resMap.put("mapButtonToDevice",twoElements(mapButtonToDevice));
        resMap.put("saveProfile",zeroElements(saveProfile));
        resMap.put("setHouse",oneElements(setHouse));
        resMap.put("setProfile",oneElements(setProfile));
        resMap.put("undoState",zeroElements(undoState));
        resMap.put("callButtonDevice",twoElements(callButtonDevice));
        return resMap;
    }

    private List<String> oneElements(String elem){
        if(elem != null && elem.length() >= 1)
            return Collections.singletonList(elem);
        return null;
    }

    private List<String> twoElements(String[] elems){
        if(elems != null && elems.length == 2
                && elems[0].length() >= 1 && elems[1].length() >= 1)
            return Arrays.asList(elems);
        return null;
    }

    private List<String> threeElements(String[] elems){
        if(elems != null && elems.length == 3
                && elems[0].length() >= 1 && elems[2].length() >= 1
                && (elems[1].equals("plus") || elems[1].equals("minus") || elems[1].equals("set")))
            return Arrays.asList(elems);
        return null;
    }

    private List<String> zeroElements(Boolean value){
        if(value != null && value == true)
            return new ArrayList<>();
        return null;
    }
}