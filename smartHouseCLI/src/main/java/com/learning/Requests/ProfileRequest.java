package com.learning.Requests;

import com.learning.model.Profile;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Optional;

public interface ProfileRequest {
    @GET("/api/profile/getByName")
    Call<Profile> getProfileByName(@Query("profileName") String profileName);
    @POST("/api/profile/save")
    Call<Void> saveProfile(@Body Profile profile);
    @DELETE("/api/profile/delete")
    Call<Void> deleteProfile(@Query("profileName") String profileName);
}
