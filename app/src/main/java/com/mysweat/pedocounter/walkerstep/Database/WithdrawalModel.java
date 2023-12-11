package com.mysweat.pedocounter.walkerstep.Database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithdrawalModel {

@SerializedName("Response")
@Expose
private String response;

public String getResponse() {
return response;
}

public void setResponse(String response) {
this.response = response;
}

}