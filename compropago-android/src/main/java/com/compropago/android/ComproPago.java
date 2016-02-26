package com.compropago.android;

import com.compropago.android.model.ComproPagoChargeRequest;
import com.compropago.android.model.ComproPagoChargeResponse;
import com.compropago.android.model.ComproPagoSendSmsResponse;
import com.compropago.android.model.ComproPagoVerifyChargeResponse;
import com.compropago.android.net.ComproPagoRestClient;
import com.compropago.android.net.ComproPagoService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * By Jorge E. Hernandez (@lalongooo) 2016
 */
public class ComproPago {

    private static ComproPago mComproPago;
    private static ComproPagoService comproPagoService;

    public static ComproPago getInstance() {
        if (mComproPago == null) {
            mComproPago = new ComproPago();
        }

        if (comproPagoService == null) {
            comproPagoService = new ComproPagoRestClient().get();
        }
        return mComproPago;
    }

    public static void charge(String orderId, double orderPrice, String orderName, String imageUrl, String customerName, String customerEmail, String paymentType, final ChargeCallback mChargeCallback) {

        ComproPagoChargeRequest chargeRequest = new ComproPagoChargeRequest();
        chargeRequest.setOrderId(orderId);
        chargeRequest.setOrderPrice(orderPrice);
        chargeRequest.setOrderName(orderName);
        chargeRequest.setImageUrl(imageUrl);
        chargeRequest.setCustomerName(customerName);
        chargeRequest.setCustomerEmail(customerEmail);
        chargeRequest.setPaymentType(paymentType);

        comproPagoService.charge(chargeRequest, new retrofit.Callback<ComproPagoChargeResponse>() {
            @Override
            public void success(ComproPagoChargeResponse mComproPagoChargeResponse, Response response) {
                mChargeCallback.onSuccess(mComproPagoChargeResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                mChargeCallback.onFailure(error.getResponse().getStatus(), error.getMessage());
            }
        });
    }

    public static void verifyCharge(String paymentId, final VerifyChargeCallback verifyChargeCallback) {
        comproPagoService.verifyCharge(paymentId, new retrofit.Callback<ComproPagoVerifyChargeResponse>() {
            @Override
            public void success(ComproPagoVerifyChargeResponse mComproPagoVerifyChargeResponse, Response response) {
                verifyChargeCallback.onSuccess(mComproPagoVerifyChargeResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                verifyChargeCallback.onFailure(error.getResponse().getStatus(), error.getMessage());
            }
        });
    }

    public static void sendSms(String paymentId, String customerPhone, final SendSmsCallback sendSmsCallback) {
        comproPagoService.sendSmsInstructions(paymentId, customerPhone, new Callback<ComproPagoSendSmsResponse>() {
            @Override
            public void success(ComproPagoSendSmsResponse mComproPagoSendSmsResponse, Response response) {
                sendSmsCallback.onSuccess(mComproPagoSendSmsResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                sendSmsCallback.onFailure(error.getResponse().getStatus(), error.getMessage());
            }
        });
    }

    interface ChargeCallback {
        void onSuccess(ComproPagoChargeResponse comproPagoChargeResponse);

        void onFailure(int errorCode, String message);
    }

    interface VerifyChargeCallback {
        void onSuccess(ComproPagoVerifyChargeResponse verifyChargeResponse);

        void onFailure(int errorCode, String message);
    }

    interface SendSmsCallback {
        void onSuccess(ComproPagoSendSmsResponse verifyChargeResponse);

        void onFailure(int errorCode, String message);
    }
}