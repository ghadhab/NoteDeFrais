package com.compta.firstak.notedefrais.service;



import android.util.Log;


import com.compta.firstak.notedefrais.Gestion_Client.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonService {

/*

    public static ArrayList<Favoris> getFavoris(JSONObject favorisJsonFromJson) {

        ArrayList<Favoris> favorisesList= new ArrayList<Favoris>();
        try {
            JSONArray jArrayArticles = favorisJsonFromJson.getJSONArray("favoris");
            System.out.println("sssssssssssss *****JARRAY*****" + jArrayArticles.length());
            JSONObject json_data_article;
            for (int index = 0; index < jArrayArticles.length(); index++) {
                json_data_article = jArrayArticles.getJSONObject(index);
                json_data_article=json_data_article.getJSONObject("article");


                Favoris favoris=new Favoris();
                Article article = new Article();
                article.setId(json_data_article.getInt("id"));
                article.setTitre(json_data_article.getString("titre"));
                article.setDescription(json_data_article.getString("description"));
                article.setPrix(json_data_article.getDouble("prix"));
                article.setImagePath(ConfigServices.urlImage + json_data_article.getString("url_photo"));
                article.setDisponibilite(json_data_article.getBoolean("disponibilite"));
                article.setEnSolde(json_data_article.getBoolean("en_solde"));
                Log.i("status article",""+article.getId()+"EnSolde"+article.isEnSolde());
                if (article.isEnSolde())
                {
                    article.setPrixApresSolde(json_data_article.getDouble("prix_apres_solde"));
                }
                article.setDisponibilite(json_data_article.getBoolean("disponibilite"));
                Marque marque=new Marque();
                marque.setId(json_data_article.getJSONObject("marque").getInt("id"));
                marque.setNom(json_data_article.getJSONObject("marque").getString("nom"));
                article.setMarque(marque);
                favoris.setArticle(article);
                Log.i("favoris",article.getTitre());

                favorisesList.add(favoris);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return favorisesList;
    }

*/

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
                client.setArchivage(json_data_article.getString("archivage"));
                Log.i("clients", client.getName());

                clientsList.add(client);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return clientsList;
    }



}
