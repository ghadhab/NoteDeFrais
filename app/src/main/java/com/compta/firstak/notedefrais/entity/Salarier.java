package com.compta.firstak.notedefrais.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohamed on 12/01/2016.
 */
public class Salarier implements Parcelable {
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNbrEnfant() {
        return nbrEnfant;
    }

    public void setNbrEnfant(String nbrEnfant) {
        this.nbrEnfant = nbrEnfant;
    }

    public String getCin() {
        return Cin;
    }

    public void setCin(String cin) {
        Cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumCnss() {
        return numCnss;
    }

    public void setNumCnss(String numCnss) {
        this.numCnss = numCnss;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSalaireBrut() {
        return salaireBrut;
    }

    public void setSalaireBrut(String salaireBrut) {
        this.salaireBrut = salaireBrut;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;
    public String adresse;
    public String email;
    public String fonction;
    public String matricule;
    public String nbrEnfant;
    public String nom;
    public String numCnss;
    public String prenom;
    public String salaireBrut;
    public String situation;
    public String dateNaissance;
    public String NbrEnfant;
    public String Cin;

    public Salarier(int id,String adresse,String email,String fonction, String matricule,String nbrEnfant,String nom,String numCnss,String prenom, String salaireBrut,String situation,String dateNaissance, String NbrEnfant ,String Cin ) {
        this.adresse = adresse;
        this.id = id;
        this.email=email;
        this.fonction=fonction;
        this.matricule=matricule;
        this.nbrEnfant=nbrEnfant;
        this.nom=nom;
        this.numCnss=numCnss;
        this.prenom=prenom;
        this.salaireBrut=salaireBrut;
        this.situation=situation;
        this.dateNaissance=dateNaissance;
        this.NbrEnfant=NbrEnfant;
        this.Cin=Cin;
    }

    public Salarier() {

    }
    protected Salarier(Parcel in) {
        adresse = in.readString();
        email = in.readString();
        fonction = in.readString();
        matricule = in.readString();
        nbrEnfant = in.readString();
        nom = in.readString();
        numCnss = in.readString();
        prenom = in.readString();
        salaireBrut = in.readString();
        situation = in.readString();
        dateNaissance = in.readString();
        NbrEnfant = in.readString();
        Cin = in.readString();
    }

    public static final Creator<Salarier> CREATOR = new Creator<Salarier>() {
        @Override
        public Salarier createFromParcel(Parcel in) {
            return new Salarier(in);
        }

        @Override
        public Salarier[] newArray(int size) {
            return new Salarier[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adresse);
        dest.writeString(email);
        dest.writeString(fonction);
        dest.writeString(matricule);
        dest.writeString(nbrEnfant);
        dest.writeString(nom);
        dest.writeString(numCnss);
        dest.writeString(prenom);
        dest.writeString(salaireBrut);
        dest.writeString(situation);
        dest.writeString(dateNaissance);
        dest.writeString(NbrEnfant);
        dest.writeString(Cin);
    }
}
