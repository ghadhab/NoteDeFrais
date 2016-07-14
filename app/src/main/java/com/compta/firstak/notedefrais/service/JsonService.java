package com.compta.firstak.notedefrais.service;



import android.util.Log;


import com.compta.firstak.notedefrais.entity.Facture;
import com.compta.firstak.notedefrais.entity.Client;
import com.compta.firstak.notedefrais.entity.EcritureRow;
import com.compta.firstak.notedefrais.entity.Fournisseur;
import com.compta.firstak.notedefrais.entity.Salarier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {
    public static double Total;

    public static ArrayList<Client> getClients(JSONArray jArrayClients) {

        ArrayList<Client> clientsList= new ArrayList<Client>();
        try {

            System.out.println("sssssssssssss *****JARRAY*****" + jArrayClients.length());
            JSONObject json_data_article;
            for (int index = 0; index < jArrayClients.length(); index++) {
                json_data_article = jArrayClients.getJSONObject(index);
                Client client=new Client();
                client.setId(json_data_article.getInt("id"));
                client.setName(json_data_article.getString("raisonSociale"));
                client.setEmail(json_data_article.getString("email"));
                client.setPhone(json_data_article.getString("phone"));
                client.setFax(json_data_article.getString("fax"));
                client.setAdresse(json_data_article.getString("adresse"));
                client.setMatriculeFiscale(json_data_article.getString("matriculeFiscale"));
                client.setRegistre(json_data_article.getString("registre"));
                client.setCapital(json_data_article.getString("capital"));
                client.setActivite(json_data_article.getString("activite"));
                client.setConstitution(json_data_article.getString("constitution"));
                client.setFormeSociete(json_data_article.getString("formeSociete"));
                if (!json_data_article.isNull("archivage"))
                client.setArchivage(json_data_article.getString("archivage"));
                else
                {
                    client.setArchivage("");
                }
                Log.i("clients", client.getName());
                clientsList.add(client);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return clientsList;
    }




    public static ArrayList<Salarier> getSalarie(JSONArray jArraySalarie) {

        ArrayList<Salarier> salarierList= new ArrayList<Salarier>();
        try {

            System.out.println("sssssssssssss *****JARRAY*****" + jArraySalarie.length());
            JSONObject json_data_article;
            for (int index = 0; index < jArraySalarie.length(); index++) {
                json_data_article = jArraySalarie.getJSONObject(index);
                Salarier salarie=new Salarier();

                salarie.setId(json_data_article.getInt("id"));
                salarie.setAdresse(json_data_article.getString("adresse"));
                salarie.setEmail(json_data_article.getString("email"));
                salarie.setFonction(json_data_article.getString("fonction"));
                salarie.setMatricule(json_data_article.getString("matricule"));
                salarie.setNbrEnfant(json_data_article.getString("nbrEnfant"));
                salarie.setNom(json_data_article.getString("nom"));
                salarie.setNumCnss(json_data_article.getString("numCnss"));
                salarie.setPrenom(json_data_article.getString("prenom"));
                salarie.setSalaireBrut(json_data_article.getString("salaireBrut"));
                salarie.setSituation(json_data_article.getString("situation"));
              //salarie.setDateNaissance(json_data_article.getString("dateNaissance"));
                salarie.setDateNaissance("23/10/1988");
                salarie.setCin(json_data_article.getString("cin"));
                salarierList.add(salarie);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return salarierList;
    }




    public static ArrayList<Facture> getListFactures(JSONArray jArrayFactures) {

        ArrayList<Facture> facturessList= new ArrayList<Facture>();
        Facture factureSelectFacture =new Facture();
        factureSelectFacture.setId(-1);
        factureSelectFacture.setName("");
        factureSelectFacture.setNofacture("");
        facturessList.add(factureSelectFacture);
        try {

            System.out.println("sssssssssssss *****JARRAY*****" + jArrayFactures.length());
            JSONObject json_data_facture;
            for (int index = 0; index < jArrayFactures.length(); index++) {
                json_data_facture = jArrayFactures.getJSONObject(index);
                Facture facture=new Facture();
                facture.setId(json_data_facture.getInt("id"));
                facture.setName(json_data_facture.getString("fournisseur"));
                facture.setNofacture(json_data_facture.getString("numFacture"));

                Log.i("factures", facture.getName()+"  "+facture.getNofacture());
                facturessList.add(facture);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return facturessList;
    }





   public static ArrayList<EcritureRow> getListEcriture(JSONArray jArrayEcriture) {

        ArrayList<EcritureRow> ecrituressList= new ArrayList<EcritureRow>();
        //EcritureRow EcritureByRow =new EcritureRow();
        try {

            System.out.println("sssssssssssss *****JARRAY1*****" + jArrayEcriture.length());
            JSONObject json_data_facture;
            for (int index = 0; index < jArrayEcriture.length(); index++) {
                json_data_facture = jArrayEcriture.getJSONObject(index);
                EcritureRow ecritureByRow =new EcritureRow();
                if(!json_data_facture.isNull("next"))
                    ecritureByRow.setColonneNext(json_data_facture.getString("next"));
                if(!json_data_facture.isNull("colonne1"))
                ecritureByRow.setColonne1(json_data_facture.getString("colonne1"));
                if(!json_data_facture.isNull("colonne2"))
                ecritureByRow.setColonne2(json_data_facture.getString("colonne2"));
                if(!json_data_facture.isNull("colonne3"))
                ecritureByRow.setColonne3(json_data_facture.getString("colonne3"));
                if(!json_data_facture.isNull("colonne4"))
                ecritureByRow.setColonne4(json_data_facture.getString("colonne4"));
                if(!json_data_facture.isNull("colonne5"))
                ecritureByRow.setColonne5(json_data_facture.getString("colonne5"));
                if(!json_data_facture.isNull("colonne6"))
                ecritureByRow.setColonne6(json_data_facture.getString("colonne6"));
                if(!json_data_facture.isNull("colonne7"))
                ecritureByRow.setColonne7(json_data_facture.getString("colonne7"));
                if(!json_data_facture.isNull("colonne8"))
                ecritureByRow.setColonne8(json_data_facture.getString("colonne8"));
                if(!json_data_facture.isNull("colonne9"))
                ecritureByRow.setColonne9(json_data_facture.getString("colonne9"));
                if(!json_data_facture.isNull("colonne10"))
                ecritureByRow.setColonne10(json_data_facture.getString("colonne10"));
                if(!json_data_facture.isNull("colonne11"))
                ecritureByRow.setColonne11(json_data_facture.getString("colonne11"));
                if(!json_data_facture.isNull("colonne12"))
                ecritureByRow.setColonne12(json_data_facture.getString("colonne12"));
                if(!json_data_facture.isNull("colonne13"))
                ecritureByRow.setColonne13(json_data_facture.getString("colonne13"));
                if(!json_data_facture.isNull("colonne14"))
                ecritureByRow.setColonne14(json_data_facture.getString("colonne14"));
                if(!json_data_facture.isNull("colonne15"))
                ecritureByRow.setColonne15(json_data_facture.getString("colonne15"));
                if(!json_data_facture.isNull("colonne16"))
                ecritureByRow.setColonne16(json_data_facture.getString("colonne16"));
                if(!json_data_facture.isNull("colonne17"))
                ecritureByRow.setColonne17(json_data_facture.getString("colonne17"));
                if(!json_data_facture.isNull("colonne18"))
                ecritureByRow.setColonne18(json_data_facture.getString("colonne18"));
                if(!json_data_facture.isNull("colonne19"))
                ecritureByRow.setColonne19(json_data_facture.getString("colonne19"));
                if(!json_data_facture.isNull("colonne20"))
                ecritureByRow.setColonne20(json_data_facture.getString("colonne20"));
                if(!json_data_facture.isNull("colonne21"))
                ecritureByRow.setColonne21(json_data_facture.getString("colonne21"));
                if(!json_data_facture.isNull("colonne22"))
                ecritureByRow.setColonne22(json_data_facture.getString("colonne22"));
                if(!json_data_facture.isNull("colonne23"))
                ecritureByRow.setColonne23(json_data_facture.getString("colonne23"));
                if(!json_data_facture.isNull("colonne24"))
                ecritureByRow.setColonne24(json_data_facture.getString("colonne24"));
                if(!json_data_facture.isNull("colonne25"))
                ecritureByRow.setColonne25(json_data_facture.getString("colonne25"));
                if(!json_data_facture.isNull("colonne26"))
                ecritureByRow.setColonne26(json_data_facture.getString("colonne26"));
                if(!json_data_facture.isNull("colonne27"))
                ecritureByRow.setColonne27(json_data_facture.getString("colonne27"));
                if(!json_data_facture.isNull("colonne28"))
                ecritureByRow.setColonne28(json_data_facture.getString("colonne28"));
                if(!json_data_facture.isNull("colonne29"))
                ecritureByRow.setColonne29(json_data_facture.getString("colonne29"));
                if(!json_data_facture.isNull("colonne30"))
                ecritureByRow.setColonne30(json_data_facture.getString("colonne30"));
                ecrituressList.add(ecritureByRow);
                Log.i("Neeext",json_data_facture.getString("next"));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ecrituressList;
    }

    public static ArrayList<EcritureRow> getListEcritureByRow(JSONArray jArrayEcriture) {

        ArrayList<EcritureRow> ecrituressList= new ArrayList<EcritureRow>();
        //EcritureRow EcritureByRow =new EcritureRow();
        try {

            System.out.println("sssssssssssss *****JARRAY1*****" + jArrayEcriture.length());
            JSONObject json_data_facture;
            for (int index = 0; index < jArrayEcriture.length(); index++) {
                json_data_facture = jArrayEcriture.getJSONObject(index);
                EcritureRow ecritureByRow1 =new EcritureRow();
                ecritureByRow1.setColonne1(json_data_facture.getString("colonne1"));
                ecritureByRow1.setColonne2(json_data_facture.getString("colonne2"));
                ecritureByRow1.setColonne3(json_data_facture.getString("colonne3"));
                ecritureByRow1.setColonne4(json_data_facture.getString("colonne4"));
                ecritureByRow1.setColonne5(json_data_facture.getString("colonne5"));
                ecritureByRow1.setColonne6(json_data_facture.getString("colonne6"));
                ecritureByRow1.setColonne7(json_data_facture.getString("colonne7"));
                ecritureByRow1.setColonne8(json_data_facture.getString("colonne8"));
                ecritureByRow1.setColonne9(json_data_facture.getString("colonne9"));
                ecritureByRow1.setColonne10(json_data_facture.getString("colonne10"));
                ecrituressList.add(ecritureByRow1);

                EcritureRow ecritureByRow2 =new EcritureRow();
                ecritureByRow2.setColonne11(json_data_facture.getString("colonne11"));
                ecritureByRow2.setColonne12(json_data_facture.getString("colonne12"));
                ecritureByRow2.setColonne13(json_data_facture.getString("colonne13"));
                ecritureByRow2.setColonne14(json_data_facture.getString("colonne14"));
                ecritureByRow2.setColonne15(json_data_facture.getString("colonne15"));
                ecritureByRow2.setColonne16(json_data_facture.getString("colonne16"));
                ecritureByRow2.setColonne17(json_data_facture.getString("colonne17"));
                ecritureByRow2.setColonne18(json_data_facture.getString("colonne18"));
                ecritureByRow2.setColonne19(json_data_facture.getString("colonne19"));
                ecritureByRow2.setColonne20(json_data_facture.getString("colonne20"));
                ecrituressList.add(ecritureByRow2);

                EcritureRow ecritureByRow3 =new EcritureRow();
                ecritureByRow3.setColonne21(json_data_facture.getString("colonne21"));
                ecritureByRow3.setColonne22(json_data_facture.getString("colonne22"));
                ecritureByRow3.setColonne23(json_data_facture.getString("colonne23"));
                ecritureByRow3.setColonne24(json_data_facture.getString("colonne24"));
                ecritureByRow3.setColonne25(json_data_facture.getString("colonne25"));
                ecritureByRow3.setColonne26(json_data_facture.getString("colonne26"));
                ecritureByRow3.setColonne27(json_data_facture.getString("colonne27"));
                ecritureByRow3.setColonne28(json_data_facture.getString("colonne28"));
                ecritureByRow3.setColonne29(json_data_facture.getString("colonne29"));
                ecritureByRow3.setColonne30(json_data_facture.getString("colonne30"));
                ecrituressList.add(ecritureByRow3);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ecrituressList;
    }


    public static ArrayList<Fournisseur> getFournisseurDec(JSONArray jArrayFournisseurDec) {

        ArrayList<Fournisseur> FournisseursList= new ArrayList<Fournisseur>();
        Total=0;
        try {

            System.out.println("sssssssssssss *****JARRAY*****" + jArrayFournisseurDec.length());
            JSONObject json_data_article;
            for (int index = 0; index < jArrayFournisseurDec.length(); index++) {
                json_data_article = jArrayFournisseurDec.getJSONObject(index);
                Fournisseur fournisseur=new Fournisseur();
                fournisseur.setCode(json_data_article.getString("code"));
                fournisseur.setNom_Fournisseur(json_data_article.getString("fournisseur"));
                fournisseur.setTottal(json_data_article.getDouble("somme"));
                fournisseur.setCount(json_data_article.getInt("count"));
                Log.i("fournisseurdec", fournisseur.getNom_Fournisseur());
                FournisseursList.add(fournisseur);
                Total+=json_data_article.getDouble("somme");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return FournisseursList;
    }

}
