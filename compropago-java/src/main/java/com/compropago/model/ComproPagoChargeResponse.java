package com.compropago.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComproPagoChargeResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("short_id")
    @Expose
    private String shortId;
    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("exp_date")
    @Expose
    private String expDate;
    @SerializedName("live_mode")
    @Expose
    private boolean liveMode;
    @SerializedName("order_info")
    @Expose
    private ComproPagoOrderInfo orderInfo;
    @SerializedName("fee_details")
    @Expose
    private ComproPagoFeeDetails feeDetails;
    @SerializedName("instructions")
    @Expose
    private ComproPagoInstructions instructions;

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
     * The shortId
     */
    public String getShortId() {
        return shortId;
    }

    /**
     *
     * @param shortId
     * The short_id
     */
    public void setShortId(String shortId) {
        this.shortId = shortId;
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
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
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
     * The expDate
     */
    public String getExpDate() {
        return expDate;
    }

    /**
     *
     * @param expDate
     * The exp_date
     */
    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    /**
     *
     * @return
     * The liveMode
     */
    public boolean isLiveMode() {
        return liveMode;
    }

    /**
     *
     * @param liveMode
     * The live_mode
     */
    public void setLiveMode(boolean liveMode) {
        this.liveMode = liveMode;
    }

    /**
     *
     * @return
     * The orderInfo
     */
    public ComproPagoOrderInfo getOrderInfo() {
        return orderInfo;
    }

    /**
     *
     * @param orderInfo
     * The order_info
     */
    public void setOrderInfo(ComproPagoOrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    /**
     *
     * @return
     * The feeDetails
     */
    public ComproPagoFeeDetails getFeeDetails() {
        return feeDetails;
    }

    /**
     *
     * @param feeDetails
     * The fee_details
     */
    public void setFeeDetails(ComproPagoFeeDetails feeDetails) {
        this.feeDetails = feeDetails;
    }

    /**
     *
     * @return
     * The instructions
     */
    public ComproPagoInstructions getInstructions() {
        return instructions;
    }

    /**
     *
     * @param instructions
     * The instructions
     */
    public void setInstructions(ComproPagoInstructions instructions) {
        this.instructions = instructions;
    }

}