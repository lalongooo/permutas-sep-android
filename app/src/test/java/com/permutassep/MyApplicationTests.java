package com.permutassep;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MyApplicationTests {

    @Before
    public void setUp() throws Exception {
        // setup
    }

    @Test
    public void testSomething() throws Exception {
        String phone = "8116916048";


        int n = 6;
        String g = "*";
        String repeated = new String(new char[n]).replace("\0", g);


        String hidden = phone.replace(phone.substring(0, 6), repeated);

        Assert.assertEquals("******6048", hidden);
    }

    /**
     * This test should replace the content before the "@" in an email address with "*"'s.
     */
    @Test
    public void testEmailNameReplacement() {
        String email = "hdez.jeduardo@gmail.com";
        String name = email.substring(0, email.indexOf("@"));
        String emailHidden = email.replace(name, new String(new char[name.length()]).replace("\0", "*"));
        Assert.assertEquals("*************@gmail.com", emailHidden);
    }
}