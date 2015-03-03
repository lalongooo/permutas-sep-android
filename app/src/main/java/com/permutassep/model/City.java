package com.permutassep.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City implements Parcelable {

    @Expose
    private String id;
    @SerializedName("clave_entidad")
    @Expose
    private String claveEntidad;
    @SerializedName("nombre_entidad")
    @Expose
    private String nombreEntidad;
    @SerializedName("clave_municipio")
    @Expose
    private int claveMunicipio;
    @SerializedName("nombre_municipio")
    @Expose
    private String nombreMunicipio;
    @SerializedName("clave_inegi")
    @Expose
    private String claveInegi;
    @SerializedName("nombre_inegi")
    @Expose
    private String nombreInegi;
    @Expose
    private String minx;
    @Expose
    private String miny;
    @Expose
    private String maxx;
    @Expose
    private String maxy;
    @Expose
    private String lat;
    @Expose
    private String lng;

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
     * The claveEntidad
     */
    public String getClaveEntidad() {
        return claveEntidad;
    }

    /**
     *
     * @param claveEntidad
     * The clave_entidad
     */
    public void setClaveEntidad(String claveEntidad) {
        this.claveEntidad = claveEntidad;
    }

    /**
     *
     * @return
     * The nombreEntidad
     */
    public String getNombreEntidad() {
        return nombreEntidad;
    }

    /**
     *
     * @param nombreEntidad
     * The nombre_entidad
     */
    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    /**
     *
     * @return
     * The claveMunicipio
     */
    public int getClaveMunicipio() {
        return claveMunicipio;
    }

    /**
     *
     * @param claveMunicipio
     * The clave_municipio
     */
    public void setClaveMunicipio(int claveMunicipio) {
        this.claveMunicipio = claveMunicipio;
    }

    /**
     *
     * @return
     * The nombreMunicipio
     */
    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    /**
     *
     * @param nombreMunicipio
     * The nombre_municipio
     */
    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    /**
     *
     * @return
     * The claveInegi
     */
    public String getClaveInegi() {
        return claveInegi;
    }

    /**
     *
     * @param claveInegi
     * The clave_inegi
     */
    public void setClaveInegi(String claveInegi) {
        this.claveInegi = claveInegi;
    }

    /**
     *
     * @return
     * The nombreInegi
     */
    public String getNombreInegi() {
        return nombreInegi;
    }

    /**
     *
     * @param nombreInegi
     * The nombre_inegi
     */
    public void setNombreInegi(String nombreInegi) {
        this.nombreInegi = nombreInegi;
    }

    /**
     *
     * @return
     * The minx
     */
    public String getMinx() {
        return minx;
    }

    /**
     *
     * @param minx
     * The minx
     */
    public void setMinx(String minx) {
        this.minx = minx;
    }

    /**
     *
     * @return
     * The miny
     */
    public String getMiny() {
        return miny;
    }

    /**
     *
     * @param miny
     * The miny
     */
    public void setMiny(String miny) {
        this.miny = miny;
    }

    /**
     *
     * @return
     * The maxx
     */
    public String getMaxx() {
        return maxx;
    }

    /**
     *
     * @param maxx
     * The maxx
     */
    public void setMaxx(String maxx) {
        this.maxx = maxx;
    }

    /**
     *
     * @return
     * The maxy
     */
    public String getMaxy() {
        return maxy;
    }

    /**
     *
     * @param maxy
     * The maxy
     */
    public void setMaxy(String maxy) {
        this.maxy = maxy;
    }

    /**
     *
     * @return
     * The lat
     */
    public String getLat() {
        return lat;
    }

    /**
     *
     * @param lat
     * The lat
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     *
     * @return
     * The lng
     */
    public String getLng() {
        return lng;
    }

    /**
     *
     * @param lng
     * The lng
     */
    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return Integer.valueOf(claveEntidad) + Integer.valueOf(claveInegi);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(claveEntidad);
        dest.writeString(nombreEntidad);
        dest.writeInt(claveMunicipio);
        dest.writeString(nombreMunicipio);
        dest.writeString(claveInegi);
        dest.writeString(nombreInegi);
        dest.writeString(minx);
        dest.writeString(miny);
        dest.writeString(maxx);
        dest.writeString(maxy);
        dest.writeString(lat);
    }


    class CityParcelableCreator implements Creator<City> {
        public City createFromParcel(Parcel source) {
            return new City(source);
        }
        public City[] newArray(int size) {
            return new City[size];
        }
    }

    public City(Parcel source) {
        this.id = source.readString();
        this.claveEntidad = source.readString();
        this.nombreEntidad = source.readString();
        this.claveMunicipio = source.readInt();
        this.nombreMunicipio = source.readString();
        this.claveInegi = source.readString();
        this.nombreInegi = source.readString();
        this.minx = source.readString();
        this.miny = source.readString();
        this.maxx = source.readString();
        this.maxy = source.readString();
        this.lat = source.readString();
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", claveEntidad='" + claveEntidad + '\'' +
                ", nombreEntidad='" + nombreEntidad + '\'' +
                ", claveMunicipio=" + claveMunicipio +
                ", nombreMunicipio='" + nombreMunicipio + '\'' +
                ", claveInegi='" + claveInegi + '\'' +
                ", nombreInegi='" + nombreInegi + '\'' +
                ", minx='" + minx + '\'' +
                ", miny='" + miny + '\'' +
                ", maxx='" + maxx + '\'' +
                ", maxy='" + maxy + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}