package com.compropago.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComproPagoChargeRequest {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_price")
    @Expose
    private Double orderPrice;
    @SerializedName("order_name")
    @Expose
    private String orderName;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;

    /**
     *
     * @return
     * The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId
     * The order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return
     * The orderPrice
     */
    public Double getOrderPrice() {
        return orderPrice;
    }

    /**
     *
     * @param orderPrice
     * The order_price
     */
    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     *
     * @return
     * The orderName
     */
    public String getOrderName() {
        return orderName;
    }

    /**
     *
     * @param orderName
     * The order_name
     */
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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
     * The paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     *
     * @param paymentType
     * The payment_type
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

}