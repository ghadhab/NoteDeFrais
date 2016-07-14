package com.compta.firstak.notedefrais.entity;

/**
 * Created by UserSytem on 03/06/2016.
 */
public class ConseilAdministration {

    int id;
    String nomPrenom;
    String duMandat;
    String auMandat;
    int idClient;

    public ConseilAdministration(int id, String nomPrenom, String duMandat, String auMandat, int idClient) {
        this.id = id;
        this.nomPrenom = nomPrenom;
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
