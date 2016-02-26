package com.compropago.android;

import com.compropago.android.model.ComproPagoChargeRequest;
import com.compropago.android.model.ComproPagoChargeResponse;
import com.compropago.android.net.ComproPagoRestClient;
import com.compropago.android.net.ComproPagoService;

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

    public static void charge(String orderId, double orderPrice, String orderName, String imageUrl, String customerName, String customerEmail, String paymentType, final Callback callback) {

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
                callback.onSuccess(mComproPagoChargeResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onFailure(error.getResponse().getStatus(), error.getMessage());
            }
        });
    }

    interface Callback {
        void onSuccess(ComproPagoChargeResponse comproPagoChargeResponse);

        void onFailure(int errorCode, String message);
    }
}