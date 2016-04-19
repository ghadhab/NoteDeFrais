package com.compta.firstak.notedefrais.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohamed on 16/02/2016.
 */
public class Fournisseur implements Parcelable {

    protected Fournisseur(Parcel in) {
        Nom_Fournisseur = in.readString();
        Count = in.readInt();
        Code = in.readString();
    }

    public static final Creator<Fournisseur> CREATOR = new Creator<Fournisseur>() {
        @Override
        public Fournisseur createFromParcel(Parcel in) {
            return new Fournisseur(in);
        }

        @Override
        public Fournisseur[] newArray(int size) {
            return new Fournisseur[size];
        }
    };

    public Double getTottal() {
        return Tottal;
    }

    public void setTottal(Double tottal) {
        Tottal = tottal;
    }

    public String getNom_Fournisseur() {
        return Nom_Fournisseur;
    }

    public void setNom_Fournisseur(String nom_Fournisseur) {
        Nom_Fournisseur = nom_Fournisseur;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    String Nom_Fournisseur;
    Double Tottal;
    int Count;
    String Code;

    public Fournisseur(){

    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public Fournisseur(String Nom_Fournisseur,Double Tottal, int Count,String Code){
        this.Nom_Fournisseur= Nom_Fournisseur;
        this.Tottal=Tottal;
        this.Count= Count;
        this.Code=Code;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Nom_Fournisseur);
        dest.writeInt(Count);
        dest.writeString(Code);
    }
}
