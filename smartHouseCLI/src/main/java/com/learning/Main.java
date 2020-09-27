package com.learning;

import com.learning.methods.MainCommand;
import com.learning.states.CurrentState;
import picocli.CommandLine;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int exitCode = 0;
		if(args.length == 1)
            initRetrofit( args[0]);
        else{
            System.out.println("Your should define address server in format like http://localhost:8080");
            System.exit(0);
        }

        System.out.println("Enter command:");
        Scanner scanner = new Scanner(System.in);
        String[] inputArgs = getInputArgs(scanner);
        while(inputArgs.length != 1 || !inputArgs[0].equals("exit")){
            exitCode = new CommandLine(new MainCommand()).execute(inputArgs);
            inputArgs = getInputArgs(scanner);
        }
        System.exit(exitCode);
    }

    static public void initRetrofit(String hostPort){
        CurrentState.getInstance().setRetrofit(new Retrofit.Builder()
                .baseUrl(hostPort)
                .addConverterFactory(GsonConverterFactory.create())
                .build());
    }

    private static String[] getInputArgs(Scanner scanner){
        return Arrays
                .stream(scanner.nextLine().split(" "))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }
}