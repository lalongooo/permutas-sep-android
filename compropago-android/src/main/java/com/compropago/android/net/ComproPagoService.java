package com.compropago.android.net;


import com.compropago.android.model.ComproPagoChargeRequest;
import com.compropago.android.model.ComproPagoChargeResponse;
import com.compropago.android.model.ComproPagoSendSmsResponse;
import com.compropago.android.model.ComproPagoVerifyChargeResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ComproPagoService {

    @POST("/v1/charges")
    void charge(@Body ComproPagoChargeRequest chargeRequest, Callback<ComproPagoChargeResponse> chargeResponse);

    @GET("/v1/charges/{payment_id}")
    void verifyCharge(@Path("payment_id") String paymentId, Callback<ComproPagoVerifyChargeResponse> verifyChargeResponse);

    @POST("/v1/charges/{payment_id}/sms")
    void sendSmsInstructions(@Path("customer_phone") String customerPhoneNumber, Callback<ComproPagoSendSmsResponse> sendSmsResponse);

    @POST("/v1/charges")
    ComproPagoChargeResponse charge(@Body ComproPagoChargeRequest chargeRequest);

    @GET("/v1/charges/{payment_id}")
    ComproPagoVerifyChargeResponse verifyCharge(@Path("payment_id") String paymentId);

    @POST("/v1/charges/{payment_id}/sms")
    ComproPagoSendSmsResponse sendSmsInstructions(@Path("customer_phone") String customerPhoneNumber);


}