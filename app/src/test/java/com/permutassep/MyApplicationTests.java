package com.permutassep;

import com.lalongooo.permutassep.BuildConfig;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.net.URI;
import java.util.List;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MyApplicationTests {

    @Before
    public void setUp() throws Exception {
        // setup
    }

    @Test
    public void testSomething() throws Exception {
        String url = "http://permuta-sep-dev.herokuapp.com/reset-password/?token=5c58f5hbabsklh1poa1d5e3cu0t327&email=hdez.jeduardo@gmail.com";
        List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), "UTF-8");

        for (NameValuePair param : params) {
            System.out.println(param.getName() + " : " + param.getValue());
        }
    }
}