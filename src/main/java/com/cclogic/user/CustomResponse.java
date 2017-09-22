package com.cclogic.user;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;

import java.util.HashMap;

/**
 * Created by Nishant on 9/18/2017.
 */

@AllArgsConstructor
public class CustomResponse {
    private String message;

    public String getResponse(){
        HashMap<String, String> response = new HashMap<>();
        response.put("message", message);
        Gson gson = new Gson();
        return gson.toJson(response);
    }
}
