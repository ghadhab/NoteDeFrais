package com.compta.firstak.notedefrais.app;

public class AppConfig {
	public static String urlRacine="http://192.168.1.13:8082/Comptabilitee/rest/";
	// Server user login url
	public static String URL_LOGIN = "http://192.168.43.247/comptable/android_login_api/";

	// Server user register url
	public static String URL_REGISTER = "http://192.168.43.247/comptable/android_login_api/";

	public static String URL_GET_ALL_FOURNISEUR = urlRacine+"fournisseur/findall";
public static String UpdateURLUpdateClientById = urlRacine+"clients";
	public static String AddURLAddClient = urlRacine+"clients";
	public static String URLGetAllClients = urlRacine+"clients/findall";

	public static String AddURLAddFacture = urlRacine+"facture";

	public static String url_Ajout_SARL=urlRacine+"sarl";

	public static  String getURLGetClientById(int id_client)
	{
		String URL_GET_ClientById = urlRacine+"clients/find/";
		return URL_GET_ClientById+id_client;
	}

}
