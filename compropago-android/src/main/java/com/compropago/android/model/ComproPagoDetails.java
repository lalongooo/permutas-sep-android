package com.compropago.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComproPagoDetails {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("store")
    @Expose
    private String store;
    @SerializedName("bank_account_number")
    @Expose
    private String bankAccountNumber;
    @SerializedName("bank_name")
    @Expose
    private String bankName;

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
     * The bankAccountNumber
     */
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    /**
     *
     * @param bankAccountNumber
     * The bank_account_number
     */
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    /**
     *
     * @return
     * The bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     *
     * @param bankName
     * The bank_name
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}