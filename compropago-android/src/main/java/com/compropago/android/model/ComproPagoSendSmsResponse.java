package com.compropago.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComproPagoSendSmsResponse {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("data")
    @Expose
    private SmsResponseData mSmsResponseData;

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The object
     */
    public String getObject() {
        return object;
    }

    /**
     * @param object The object
     */
    public void setObject(String object) {
        this.object = object;
    }

    /**
     * @return The data
     */
    public SmsResponseData getSmsResponseData() {
        return mSmsResponseData;
    }

    /**
     * @param mSmsResponseData The data
     */
    public void setSmsResponseData(SmsResponseData mSmsResponseData) {
        this.mSmsResponseData = mSmsResponseData;
    }


    public class SmsResponseObject {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("object")
        @Expose
        private String object;
        @SerializedName("short_id")
        @Expose
        private String shortId;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The object
         */
        public String getObject() {
            return object;
        }

        /**
         * @param object The object
         */
        public void setObject(String object) {
            this.object = object;
        }

        /**
         * @return The shortId
         */
        public String getShortId() {
            return shortId;
        }

        /**
         * @param shortId The short_id
         */
        public void setShortId(String shortId) {
            this.shortId = shortId;
        }

    }


    public class SmsResponseData {

        @SerializedName("object")
        @Expose
        private SmsResponseObject mSmsResponseObject;

        /**
         * @return The object
         */
        public SmsResponseObject getSmsResponseObject() {
            return mSmsResponseObject;
        }

        /**
         * @param mSmsResponseObject The object
         */
        public void setSmsResponseObject(SmsResponseObject mSmsResponseObject) {
            this.mSmsResponseObject = mSmsResponseObject;
        }

    }

}