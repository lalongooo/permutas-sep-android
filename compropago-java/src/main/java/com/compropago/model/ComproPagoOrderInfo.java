package com.compropago.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComproPagoOrderInfo {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_price")
    @Expose
    private String orderPrice;
    @SerializedName("order_name")
    @Expose
    private String orderName;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("store")
    @Expose
    private String store;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("success_url")
    @Expose
    private String successUrl;

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
    public String getOrderPrice() {
        return orderPrice;
    }

    /**
     *
     * @param orderPrice
     * The order_price
     */
    public void setOrderPrice(String orderPrice) {
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
     * The paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     *
     * @param paymentMethod
     * The payment_method
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     *
     * @return
     * The store
     */
    public String getStore() {
        return store;
    }

    /**
     *
     * @param store
     * The store
     */
    public void setStore(String store) {
        this.store = store;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
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
     * The successUrl
     */
    public String getSuccessUrl() {
        return successUrl;
    }

    /**
     *
     * @param successUrl
     * The success_url
     */
    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

}