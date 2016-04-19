package com.compta.firstak.notedefrais.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohamed on 12/01/2016.
 */
public class Facture implements Parcelable{

    public String nameFacture;
    public int id;
    public String Nofacture;

    protected Facture(Parcel in) {
        nameFacture = in.readString();
        id = in.readInt();
        Nofacture = in.readString();
    }

    public static final Creator<Facture> CREATOR = new Creator<Facture>() {
        @Override
        public Facture createFromParcel(Parcel in) {
            return new Facture(in);
        }

        @Override
        public Facture[] newArray(int size) {
            return new Facture[size];
        }
    };

    public String getNofacture() {
        return Nofacture;
    }

    public void setNofacture(String nofacture) {
        Nofacture = nofacture;
    }




    public String getName() {
        return nameFacture;
    }

    public void setName(String name) {
        this.nameFacture = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



        public Facture(int id, String name,String NoFacture) {
            this.nameFacture = name;
            this.id = id;
            this.Nofacture=Nofacture;

        }

    public Facture() {
    }


    @Override
    public String toString() {
        return "Facture{" +
                "nameFacture='" + nameFacture + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameFacture);
        dest.writeInt(id);
        dest.writeString(Nofacture);
    }
}
