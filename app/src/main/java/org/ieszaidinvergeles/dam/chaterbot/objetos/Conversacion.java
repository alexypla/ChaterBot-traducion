package org.ieszaidinvergeles.dam.chaterbot.objetos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Conversacion implements Parcelable {

    private long id;
    private String fecha;

    public Conversacion() {
        this(0, String.valueOf(new Date()));
    }

    public Conversacion(int id, String fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    protected Conversacion(Parcel in) {
        id = in.readLong();
        fecha = in.readString();
    }

    public static final Creator<Conversacion> CREATOR = new Creator<Conversacion>() {
        @Override
        public Conversacion createFromParcel(Parcel in) {
            return new Conversacion(in);
        }

        @Override
        public Conversacion[] newArray(int size) {
            return new Conversacion[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Conversacion{" +
                "id=" + id +", " +
                "fecha=" + fecha +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(fecha);
    }
}
