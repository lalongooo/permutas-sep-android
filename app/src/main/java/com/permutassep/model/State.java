package com.permutassep.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class State implements Parcelable {

    @Expose
    private short id;
    private String mStateName;
    private String mShortCode;

    public State(short id, String stateName) {
        this.id = id;
        this.mStateName = stateName;
    }

    public State(short id, String stateName, String shortCode) {
        this.id = id;
        this.mStateName = stateName;
        this.mShortCode = shortCode;
    }

    public String getStateName() {
        return mStateName;
    }
    public void setStateName(String stateName) {
        this.mStateName = stateName;
    }
    public short getId() {
        return id;
    }
    public void setId(short id) {
        this.id = id;
    }
    public String getShortCode() {
        return mShortCode;
    }
    public void setShortCode(String shortCode) {
        this.mShortCode = shortCode;
    }

    @Override
    public int describeContents() {
        return id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(mStateName);
    }

    class StateParcelableCreator implements Creator<State> {
        public State createFromParcel(Parcel source) {
            return new State(source);
        }
        public State[] newArray(int size) {
            return new State[size];
        }
    }

    public State(Parcel source) {
        this.id = Short.valueOf(String.valueOf(source.readInt()));
        this.mStateName = source.readString();
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", mStateName='" + mStateName + '\'' +
                '}';
    }
}
