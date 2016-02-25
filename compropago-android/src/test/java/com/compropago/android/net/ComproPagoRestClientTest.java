package com.compropago.android.net;

import com.compropago.android.model.ComproPagoChargeRequest;
import com.compropago.android.model.ComproPagoChargeResponse;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ComproPagoRestClientTest {

    @Test
    public void testCharge() {


        /**
         * JSON Example
         *
         {
         "order_id":"SMGCURL1",
         "order_price":75.0,
         "order_name":"SAMSUNG GOLD CURL",
         "image_url":"https://test.amazon.com/5f4373",
         "customer_name":"Alejandra Leyva",
         "customer_email":"noreply@compropago.com",
         "payment_type":"OXXO"
         }
         *
         */

        ComproPagoChargeRequest mChargeRequest = new ComproPagoChargeRequest();
        mChargeRequest.setOrderId("PS_ORDERID");
        mChargeRequest.setOrderPrice(30.0);
        mChargeRequest.setOrderName("PSDonation");
        mChargeRequest.setImageUrl("http://lh3.googleusercontent.com/fsjhgyYnBZdNnE2CHuWPACuAWfT1XkKCWIN3khCGe27TBUXrVngNXJJUzktuO9kHng=w170-rw");
        mChargeRequest.setCustomerName("Jorge E. Hernàndez");
        mChargeRequest.setCustomerEmail("hdez.jeduardo@gmail.com");
        mChargeRequest.setPaymentType("OXXO");


        ComproPagoService comproPagoService = new ComproPagoRestClient().get();
        ComproPagoChargeResponse mChargeResponse = comproPagoService.charge(mChargeRequest);
        assertNotNull(mChargeResponse);

    }

}