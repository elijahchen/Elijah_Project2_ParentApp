package com.elijahdesign.parentapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Elijah on 7/27/2016.
 */
public interface JSONData {

    @GET("{userID}")
    Call<ResponseData> getClient(@Path("userID") String userId);

    @PUT("{userID}")
    Call<ClientData> putClient(@Body ClientData clientId,
                                @Path("userID") String userId);

    @PATCH("{userID}")
    Call<ClientData> patchClient(@Body ClientData clientId,
                                 @Path("userID") String userId);
}
