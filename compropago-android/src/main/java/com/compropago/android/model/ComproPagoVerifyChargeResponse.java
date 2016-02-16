package com.compropago.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComproPagoVerifyChargeResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("paid")
    @Expose
    private Boolean paid;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("livemode")
    @Expose
    private Boolean livemode;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("refunded")
    @Expose
    private Boolean refunded;
    @SerializedName("fee")
    @Expose
    private String fee;
    @SerializedName("captured")
    @Expose
    private Boolean captured;
    @SerializedName("failure_message")
    @Expose
    private Object failureMessage;
    @SerializedName("failure_code")
    @Expose
    private Object failureCode;
    @SerializedName("amount_refunded")
    @Expose
    private Integer amountRefunded;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("dispute")
    @Expose
    private Object dispute;
    @SerializedName("api_version")
    @Expose
    private String apiVersion;

    @SerializedName("fee_details")
    @Expose
    private ComproPagoFeeDetails feeDetails;

    @SerializedName("order_info")
    @Expose
    private ComproPagoOrderInfo orderInfo;

    @SerializedName("customer")
    @Expose
    private ComproPagoCustomer customer;
    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The object
     */
    public String getObject() {
        return object;
    }

    /**
     *
     * @param object
     * The object
     */
    public void setObject(String object) {
        this.object = object;
    }

    /**
     *
     * @return
     * The created
     */
    public String getCreated() {
        return created;
    }

    /**
     *
     * @param created
     * The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     *
     * @return
     * The paid
     */
    public Boolean getPaid() {
        return paid;
    }

    /**
     *
     * @param paid
     * The paid
     */
    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    /**
     *
     * @return
     * The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     * The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     * The livemode
     */
    public Boolean getLivemode() {
        return livemode;
    }

    /**
     *
     * @param livemode
     * The livemode
     */
    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    /**
     *
     * @return
     * The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     * The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     * The refunded
     */
    public Boolean getRefunded() {
        return refunded;
    }

    /**
     *
     * @param refunded
     * The refunded
     */
    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    /**
     *
     * @return
     * The fee
     */
    public String getFee() {
        return fee;
    }

    /**
     *
     * @param fee
     * The fee
     */
    public void setFee(String fee) {
        this.fee = fee;
    }

    /**
     *
     * @return
     * The captured
     */
    public Boolean getCaptured() {
        return captured;
    }

    /**
     *
     * @param captured
     * The captured
     */
    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }

    /**
     *
     * @return
     * The failureMessage
     */
    public Object getFailureMessage() {
        return failureMessage;
    }

    /**
     *
     * @param failureMessage
     * The failure_message
     */
    public void setFailureMessage(Object failureMessage) {
        this.failureMessage = failureMessage;
    }

    /**
     *
     * @return
     * The failureCode
     */
    public Object getFailureCode() {
        return failureCode;
    }

    /**
     *
     * @param failureCode
     * The failure_code
     */
    public void setFailureCode(Object failureCode) {
        this.failureCode = failureCode;
    }

    /**
     *
     * @return
     * The amountRefunded
     */
    public Integer getAmountRefunded() {
        return amountRefunded;
    }

    /**
     *
     * @param amountRefunded
     * The amount_refunded
     */
    public void setAmountRefunded(Integer amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The dispute
     */
    public Object getDispute() {
        return dispute;
    }

    /**
     *
     * @param dispute
     * The dispute
     */
    public void setDispute(Object dispute) {
        this.dispute = dispute;
    }

    /**
     *
     * @return
     * The apiVersion
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     *
     * @param apiVersion
     * The api_version
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public ComproPagoCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(ComproPagoCustomer mCustomer) {
        customer = mCustomer;
    }

    public ComproPagoFeeDetails getFeeDetails() {
        return feeDetails;
    }

    public void setFeeDetails(ComproPagoFeeDetails mFeeDetails) {
        feeDetails = mFeeDetails;
    }

    public ComproPagoOrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(ComproPagoOrderInfo mOrderInfo) {
        orderInfo = mOrderInfo;
    }
}