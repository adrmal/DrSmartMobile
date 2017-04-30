package net.azurewebsites.drsmart2016.drsmartmobile.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Token {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private long expiresIn;
    @SerializedName("userName")
    private String userName;
    @SerializedName(".issued")
    private String issued;
    @SerializedName(".expires")
    private String expires;

}
