package com.compropago.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComproPagoCustomer {

    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_phone")
    @Expose
    private String customerPhone;

    /**
     *
     * @return
     * The customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @param customerName
     * The customer_name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     * @return
     * The customerEmail
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     *
     * @param customerEmail
     * The customer_email
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     *
     * @return
     * The customerPhone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     *
     * @param customerPhone
     * The customer_phone
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

}