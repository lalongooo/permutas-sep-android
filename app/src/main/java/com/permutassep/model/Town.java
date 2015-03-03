package com.permutassep.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Town implements Parcelable {

    @Expose
    private int id;
    @SerializedName("estado_id")
    @Expose
    private String estadoId;
    @SerializedName("municipio_clave")
    @Expose
    private String municipioClave;
    @SerializedName("municipio_id")
    @Expose
    private String municipioId;
    @Expose
    private String clave;
    @Expose
    private String nombre;
    @Expose
    private String latitud;
    @Expose
    private String longitud;
    @Expose
    private String altitud;

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The estadoId
     */
    public String getEstadoId() {
        return estadoId;
    }

    /**
     *
     * @param estadoId
     * The estado_id
     */
    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    /**
     *
     * @return
     * The municipioClave
     */
    public String getMunicipioClave() {
        return municipioClave;
    }

    /**
     *
     * @param municipioClave
     * The municipio_clave
     */
    public void setMunicipioClave(String municipioClave) {
        this.municipioClave = municipioClave;
    }

    /**
     *
     * @return
     * The municipioId
     */
    public String getMunicipioId() {
        return municipioId;
    }

    /**
     *
     * @param municipioId
     * The municipio_id
     */
    public void setMunicipioId(String municipioId) {
        this.municipioId = municipioId;
    }

    /**
     *
     * @return
     * The clave
     */
    public String getClave() {
        return clave;
    }

    /**
     *
     * @param clave
     * The clave
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     *
     * @return
     * The nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     * The nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     * The latitud
     */
    public String getLatitud() {
        return latitud;
    }

    /**
     *
     * @param latitud
     * The latitud
     */
    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    /**
     *
     * @return
     * The longitud
     */
    public String getLongitud() {
        return longitud;
    }

    /**
     *
     * @param longitud
     * The longitud
     */
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    /**
     *
     * @return
     * The altitud
     */
    public String getAltitud() {
        return altitud;
    }

    /**
     *
     * @param altitud
     * The altitud
     */
    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    @Override
    public int describeContents() {
        return Integer.valueOf(id) + Integer.valueOf(municipioClave) + Integer.valueOf(estadoId);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(estadoId);
        dest.writeString(municipioClave);
        dest.writeString(municipioId);
        dest.writeString(clave);
        dest.writeString(nombre);
        dest.writeString(latitud);
        dest.writeString(longitud);
        dest.writeString(altitud);
    }


    class TownParcelableCreator implements Creator<Town> {
        public Town createFromParcel(Parcel source) {
            return new Town(source);
        }
        public Town[] newArray(int size) {
            return new Town[size];
        }
    }

    public Town(Parcel source) {
        this.id = source.readInt();
        this.estadoId = source.readString();
        this.municipioClave = source.readString();
        this.municipioId = source.readString();
        this.clave = source.readString();
        this.nombre = source.readString();
        this.latitud = source.readString();
        this.longitud = source.readString();
        this.altitud = source.readString();
    }

    @Override
    public String toString() {
        return "Town{" +
                "id=" + id +
                ", estadoId='" + estadoId + '\'' +
                ", municipioClave='" + municipioClave + '\'' +
                ", municipioId='" + municipioId + '\'' +
                ", clave='" + clave + '\'' +
                ", nombre='" + nombre + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", altitud='" + altitud + '\'' +
                '}';
    }
}