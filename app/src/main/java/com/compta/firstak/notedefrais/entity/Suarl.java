package com.compta.firstak.notedefrais.entity;

/**
 * Created by UserSytem on 30/05/2016.
 */
public class Suarl {
    int id;
    String nomAssociesuarl;
    int nombrePartSuarl;
    double nombreNominale;
    int totalAssociesuarl;
    int idClient;

    public Suarl ()
    {

    }

    public Suarl(String nomAssociesuarl, int nombrePartSuarl, double nombreNominale, int totalAssociesuarl) {
        this.nomAssociesuarl = nomAssociesuarl;
        this.nombrePartSuarl = nombrePartSuarl;
        this.nombreNominale = nombreNominale;
        this.totalAssociesuarl = totalAssociesuarl;
    }

    public Suarl(int id, String nomAssociesuarl, int nombrePartSuarl, double nombreNominale, int totalAssociesuarl,int idClient) {
        this.id = id;
        this.nomAssociesuarl = nomAssociesuarl;
        this.nombrePartSuarl = nombrePartSuarl;
        this.nombreNominale = nombreNominale;
        this.totalAssociesuarl = totalAssociesuarl;
        this.idClient=idClient;
    }

    public double getNombreNominale() {
        return nombreNominale;
    }

    public void setNombreNominale(double nombreNominale) {
        this.nombreNominale = nombreNominale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomAssociesuarl() {
        return nomAssociesuarl;
    }

    public void setNomAssociesuarl(String nomAssociesuarl) {
        this.nomAssociesuarl = nomAssociesuarl;
    }

    public int getNombrePartSuarl() {
        return nombrePartSuarl;
    }

    public void setNombrePartSuarl(int nombrePartSuarl) {
        this.nombrePartSuarl = nombrePartSuarl;
    }




    public int getTotalAssociesuarl() {
        return totalAssociesuarl;
    }

    public void setTotalAssociesuarl(int totalAssociesuarl) {
        this.totalAssociesuarl = totalAssociesuarl;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "Suarl{" +
                "id=" + id +
                ", nomAssociesuarl='" + nomAssociesuarl + '\'' +
                ", nombrePartSuarl=" + nombrePartSuarl +
                ", nombreNominale=" + nombreNominale +
                ", totalAssociesuarl=" + totalAssociesuarl +
                '}';
    }


}
