package com.compropago.android.net;



import com.compropago.android.model.ComproPagoChargeRequest;
import com.compropago.android.model.ComproPagoChargeResponse;
import com.compropago.android.model.ComproPagoSendSmsResponse;
import com.compropago.android.model.ComproPagoVerifyChargeResponse;

import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ComproPagoService {

    @POST("/v1/charges")
    void charge(@Body ComproPagoChargeRequest chargeRequest, Callback<ComproPagoChargeResponse> chargeResponse);

    @GET("/v1/charges/{payment_id}")
    void verifyCharge(@Path("payment_id") String paymentId, Callback<ComproPagoVerifyChargeResponse> verifyChargeResponse);

    @POST("/v1/charges/{payment_id}/sms")
    void sendSmsInstructions(@Field("customer_phone") String customerPhoneNumber, Callback<ComproPagoSendSmsResponse> sendSmsResponse);
}