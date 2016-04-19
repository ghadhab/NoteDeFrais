package com.compta.firstak.notedefrais.app;

public class AppConfig {
	//public static String urlRacine="http://197.2.4.98:5689/Comptabilitee/rest/";

	public static String urlRacine=AppController.getInstance().getUrlRacine();
	//public static String urlRacine="http://192.168.1.17:8082/Comptabilitee/rest/";

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
	public static String url_Ajout_EntrepriseIndiv=urlRacine+"individuelle";
	public static String url_Ajout_Conseil=urlRacine+"conseil";
	public static String url_Ajout_President=urlRacine+"president";
	public static String url_Ajout_Actionnaire=urlRacine+"actionnaire";
	public static String url_Ajout_Suarl=urlRacine+"suarl";
	public static String AddURLAddFournisseur = urlRacine+"fournisseur";
	public static String AddURLAddEcritureComptable = urlRacine+"ecriturecomptables";

	public static String AddURLAddDesignation = urlRacine+"distance";

	public static String AddURLAddReference = urlRacine+"reference";

	public static String AddURLAddImmo = urlRacine+"amortissement";




	public static  String getURLGetClientById(int id_client)
	{
		String URL_GET_ClientById = urlRacine+"clients/find/";
		return URL_GET_ClientById+id_client;
	}

	public static  String URL_GET_FOURNISEUR(String MatriculeFiscal)
	{
		String URL_Archivage_ClientById = urlRacine+"fournisseur/findMatriculeFiscal/";
		return URL_Archivage_ClientById+MatriculeFiscal;
	}

	public static  String getURLGetFactureById(int id_facture,int id_client)
	{
		String URL_Get_FactureById = urlRacine+"facture/find/";
		return URL_Get_FactureById+id_facture+"/"+id_client;
	}


	public static  String DesactiverURLArchivageClientById(int id_client)
	{
		String URL_Archivage_ClientById = urlRacine+"clients/desactiver/";
		return URL_Archivage_ClientById+id_client;
	}


	public static  String URLGetAllFacture(int id_client)
	{
		String URL_Get_FactureById = urlRacine+"facture/GetFactureByClient/";
		return URL_Get_FactureById+id_client;
	}

	public static  String getURLGetEcritureByIdFacture(int id_Client)
	{
		String URL_Archivage_ClientById =urlRacine+"ecriturecomptables/findByIdClient/";
		return URL_Archivage_ClientById+id_Client;
	}

	public static  String getURLGetEcritureByIdFacture_Immo(int id_Client)
	{
		String URL_Archivage_ClientById =urlRacine+"ecriturecomptables/findByIdClientIMMO/";
		return URL_Archivage_ClientById+id_Client;
	}

	public static  String getURLGetFournisseur_Dec(int id_Client)
	{
		String URL_FournisseurDec_ClientById =urlRacine+"ecriturecomptables/findByTotalDecroissant/";
		return URL_FournisseurDec_ClientById+id_Client;
	}


	public static  String findFournisseurExploitation(int id_client,String fournisseur)
	{
		String URL_Get_FactureById = urlRacine+"ecriturecomptables/findFournisseurExploitationByIdClientFournisseur/";
		return URL_Get_FactureById+id_client+"/"+fournisseur;
	}


	public static  String Get_referenceAmortissement_by_num_Compte(int numcompte)
	{
		String URL_Get_referenceAmortissement_by_num_Compte = urlRacine+"referenceAmortissement/find/";
		return URL_Get_referenceAmortissement_by_num_Compte+numcompte;
	}



}
