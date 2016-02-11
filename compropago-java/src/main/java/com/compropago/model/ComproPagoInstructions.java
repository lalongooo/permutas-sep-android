package com.compropago.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

public class ComproPagoInstructions {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("step_1")
    @Expose
    private String step1;
    @SerializedName("step_2")
    @Expose
    private String step2;
    @SerializedName("step_3")
    @Expose
    private String step3;
    @SerializedName("note_extra_comition")
    @Expose
    private String noteExtraComition;
    @SerializedName("note_expiration_date")
    @Expose
    private String noteExpirationDate;
    @SerializedName("note_confirmation")
    @Expose
    private String noteConfirmation;
    @SerializedName("details")
    @Expose
    private ComproPagoDetails details;

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
     * The step1
     */
    public String getStep1() {
        return step1;
    }

    /**
     *
     * @param step1
     * The step_1
     */
    public void setStep1(String step1) {
        this.step1 = step1;
    }

    /**
     *
     * @return
     * The step2
     */
    public String getStep2() {
        return step2;
    }

    /**
     *
     * @param step2
     * The step_2
     */
    public void setStep2(String step2) {
        this.step2 = step2;
    }

    /**
     *
     * @return
     * The step3
     */
    public String getStep3() {
        return step3;
    }

    /**
     *
     * @param step3
     * The step_3
     */
    public void setStep3(String step3) {
        this.step3 = step3;
    }

    /**
     *
     * @return
     * The noteExtraComition
     */
    public String getNoteExtraComition() {
        return noteExtraComition;
    }

    /**
     *
     * @param noteExtraComition
     * The note_extra_comition
     */
    public void setNoteExtraComition(String noteExtraComition) {
        this.noteExtraComition = noteExtraComition;
    }

    /**
     *
     * @return
     * The noteExpirationDate
     */
    public String getNoteExpirationDate() {
        return noteExpirationDate;
    }

    /**
     *
     * @param noteExpirationDate
     * The note_expiration_date
     */
    public void setNoteExpirationDate(String noteExpirationDate) {
        this.noteExpirationDate = noteExpirationDate;
    }

    /**
     *
     * @return
     * The noteConfirmation
     */
    public String getNoteConfirmation() {
        return noteConfirmation;
    }

    /**
     *
     * @param noteConfirmation
     * The note_confirmation
     */
    public void setNoteConfirmation(String noteConfirmation) {
        this.noteConfirmation = noteConfirmation;
    }

    /**
     *
     * @return
     * The details
     */
    public ComproPagoDetails getDetails() {
        return details;
    }

    /**
     *
     * @param details
     * The details
     */
    public void setDetails(ComproPagoDetails details) {
        this.details = details;
    }

}