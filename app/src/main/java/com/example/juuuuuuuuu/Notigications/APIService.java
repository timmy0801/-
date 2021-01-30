package com.example.juuuuuuuuu.Notigications;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA29j2A18:APA91bHCIZmap3v-wJnEilQCiBXfbjLaezGfFSMxHKR_p5_mKCQWPfHfuXKr9Ke21uUk-xJWvBAiheKa8sYvtKrnrmVu44-xQ0CrAx69wNhK-a-W_d2XppztRs3qyVKMU3fg3U2wMuDV"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotifacation(@Body Sender body);
}
