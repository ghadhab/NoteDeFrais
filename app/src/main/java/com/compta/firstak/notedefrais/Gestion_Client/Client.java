package com.compta.firstak.notedefrais.Gestion_Client;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohamed on 12/01/2016.
 */
public class Client implements Parcelable {

    public String name;
    public int id;
    public String email;
    public String phone;
    public String fax;
    public String adresse;
    public String matriculeFiscale;
    public String registre;
    public String capital;
    public String activite;
    public String constitution;
    public String formeSociete;
    public String archivage;


    protected Client(Parcel in) {
        name = in.readString();
        id = in.readInt();
        email = in.readString();
        phone = in.readString();
        fax = in.readString();
        adresse = in.readString();
        matriculeFiscale = in.readString();
        registre = in.readString();
        capital = in.readString();
        activite = in.readString();
        constitution = in.readString();
        formeSociete = in.readString();
        archivage = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getRegistre() {
        return registre;
    }

    public void setRegistre(String registre) {
        this.registre = registre;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public String getFormeSociete() {
        return formeSociete;
    }

    public void setFormeSociete(String formeSociete) {
        this.formeSociete = formeSociete;
    }

    public String getArchivage() {
        return archivage;
    }

    public void setArchivage(String archivage) {
        this.archivage = archivage;
    }



        public Client(int id,String name,String email,String phone, String fax,String adresse,String matriculeFiscale,String registre,String capital, String activite,String constitution,String formeSociete, String archivage) {
            this.name = name;
            this.id = id;
            this.email=email;
            this.phone=phone;
            this.fax=fax;
            this.adresse=adresse;
            this.matriculeFiscale=matriculeFiscale;
            this.registre=registre;
            this.capital=capital;
            this.activite=activite;
            this.constitution=constitution;
            this.formeSociete=formeSociete;
            this.archivage=archivage;
        }

    public Client() {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(fax);
        dest.writeString(adresse);
        dest.writeString(matriculeFiscale);
        dest.writeString(registre);
        dest.writeString(capital);
        dest.writeString(activite);
        dest.writeString(constitution);
        dest.writeString(formeSociete);
        dest.writeString(archivage);
    }
}
