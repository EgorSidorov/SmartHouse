package com.learning.states;

import com.learning.model.House;
import com.learning.model.Profile;
import retrofit2.Retrofit;

public class CurrentState {
    private State state;
    private static CurrentState currentState = null;
    private Retrofit retrofit;

    private CurrentState()
    {
        state = new UndefinedState();
    }

    public static CurrentState getInstance()
    {
        if (currentState == null)
            currentState = new CurrentState();

        return currentState;
    }

    public State getState() {
        return state;
    }

    public void setUndefinedState() {
        UndefinedState undefinedState = new UndefinedState();
        this.state = undefinedState;
    }

    public void setProfileState(Profile profile) {
        ProfileState profileState = new ProfileState();
        profileState.setProfile(profile);
        this.state = profileState;
    }

    public void setHouseState(Profile profile, String houseName) {
        HouseState houseState = new HouseState();
        houseState.setProfile(profile);
        houseState.setHouseName(houseName);
        this.state = houseState;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
}
