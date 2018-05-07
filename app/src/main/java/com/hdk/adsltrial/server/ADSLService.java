package com.hdk.adsltrial.server;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ADSLService {
  //  RouterInfo routerinfo = new RouterInfo();
    //RequestBody body = RequestBody.create(MediaType.parse("application/json"), routerinfo.toString());
    @POST("/")
    Call <Void> sendRouterData (@Body DiagnosticData data);

    @POST("feedback")
    Call <Void> sendFeeback (@Body FeedbackData data);
}
