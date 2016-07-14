package com.compta.firstak.notedefrais.entity;

import android.widget.Button;
import android.widget.EditText;

import com.compta.firstak.notedefrais.R;

/**
 * Created by UserSytem on 03/06/2016.
 */
public class PresidentConseil {

    int id;
    String nomPrenom;
    String fonction;
    String duMandat;
    String auMandat;
    int idClient;

    public PresidentConseil(int id, String nomPrenom, String fonction, String duMandat, String auMandat, int idClient) {
        this.id = id;
        this.nomPrenom = nomPrenom;
        this.fonction = fonction;
        this.duMandat = duMandat;
        this.auMandat = auMandat;
        this.idClient = idClient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public String getDuMandat() {
        return duMandat;
    }

    public void setDuMandat(String duMandat) {
        this.duMandat = duMandat;
    }

    public String getAuMandat() {
        return auMandat;
    }

    public void setAuMandat(String auMandat) {
        this.auMandat = auMandat;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}
