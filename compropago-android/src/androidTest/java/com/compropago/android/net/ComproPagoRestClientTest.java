package com.compropago.android.net;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * By Jorge E. Hernandez (@lalongooo) 2016
 */
public class ComproPagoRestClientTest {

    public void testRestClient(){
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse());
    }

}