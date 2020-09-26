package com.learning.Requests;

import com.learning.model.Profile;
import com.learning.states.CurrentState;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProfileRequestImpl {
    ProfileRequest profileService = CurrentState.getInstance().getRetrofit().create(ProfileRequest.class);

    public Optional<Profile> getProfileByName(String profileName){
        Optional<Profile> responseProfile = Optional.empty();
        try {
            Response<Profile> profile = profileService.getProfileByName(profileName).execute();
            if(profile.isSuccessful()){
                responseProfile = Optional.ofNullable(profile.body());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return responseProfile;
        }
    };

    public boolean saveProfile(Profile profile){
        Optional<Profile> responseProfile = Optional.empty();
        try {

            Response<Void> res = profileService.saveProfile(profile).execute();
            if(res.isSuccessful())
                return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    };

    public void deleteProfile(String profileName){
        try {
            profileService.deleteProfile(profileName).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
