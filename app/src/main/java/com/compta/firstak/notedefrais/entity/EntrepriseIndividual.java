package com.compta.firstak.notedefrais.entity;

/**
 * Created by UserSytem on 01/06/2016.
 */
public class EntrepriseIndividual {
    int id;
    String nomIndividuelle;
    String prenomIndividuelle;
    String matriculeFiscaleEI;
    String adresseEI;
    int idClient;

    public EntrepriseIndividual(int id, String nomIndividuelle, String prenomIndividuelle, String matriculeFiscaleEI, int idClient, String adresseEI) {
        this.id = id;
        this.nomIndividuelle = nomIndividuelle;
        this.prenomIndividuelle = prenomIndividuelle;
        this.matriculeFiscaleEI = matriculeFiscaleEI;
        this.idClient = idClient;
        this.adresseEI = adresseEI;
    }

    public String getNomIndividuelle() {
        return nomIndividuelle;
    }

    public void setNomIndividuelle(String nomIndividuelle) {
        this.nomIndividuelle = nomIndividuelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenomIndividuelle() {
        return prenomIndividuelle;
    }

    public void setPrenomIndividuelle(String prenomIndividuelle) {
        this.prenomIndividuelle = prenomIndividuelle;
    }

    public String getMatriculeFiscaleEI() {
        return matriculeFiscaleEI;
    }

    public void setMatriculeFiscaleEI(String matriculeFiscaleEI) {
        this.matriculeFiscaleEI = matriculeFiscaleEI;
    }

    public String getAdresseEI() {
        return adresseEI;
    }

    public void setAdresseEI(String adresseEI) {
        this.adresseEI = adresseEI;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}
