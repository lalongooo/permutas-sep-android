package com.compropago.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComproPagoFeeDetails {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("tax_percent")
    @Expose
    private String taxPercent;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("amount_refunded")
    @Expose
    private int amountRefunded;
    @SerializedName("application")
    @Expose
    private String application;
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
     * The tax
     */
    public String getTax() {
        return tax;
    }

    /**
     *
     * @param tax
     * The tax
     */
    public void setTax(String tax) {
        this.tax = tax;
    }

    /**
     *
     * @return
     * The taxPercent
     */
    public String getTaxPercent() {
        return taxPercent;
    }

    /**
     *
     * @param taxPercent
     * The tax_percent
     */
    public void setTaxPercent(String taxPercent) {
        this.taxPercent = taxPercent;
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
     * The amountRefunded
     */
    public int getAmountRefunded() {
        return amountRefunded;
    }

    /**
     *
     * @param amountRefunded
     * The amount_refunded
     */
    public void setAmountRefunded(int amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String mApplication) {
        application = mApplication;
    }
}