package com.compta.firstak.notedefrais;

import android.app.Activity;
import android.content.Context;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by mohamed on 02/07/2015.
 */
public class ParsingOcrResult extends Activity {
      public  String MatriculeFiscale;
    public  String MF;
     public  String TVA;
    public  String Tel;
     public  String Fax;
    public  String Email;
    public  String SiteWeb;
    public static String DesignationAtMin;
    public static String HtvaString;
    static String  Designation,Code;
    public  String NoFacture;
     public  String Date;
    public  Double TotalTTC=0.0;
    public  Double HTVA=0.0;
     public  Double Tottal=0.0;
    public static Double  Number=0.0;
    static  String  Poucentage;
  //  static  int TvaNumber;
   // static int p=0;
  Boolean TestNfacture;
    Boolean TestMatriculeFiscale;
    Boolean TestMF;
    Boolean TestTottalTTC;
    Boolean TestTVA;
    Boolean TestTel;
    Boolean TestFax;
    Boolean TestHtva;
    Boolean TestMail;
    Boolean TestSite;
    Boolean TestDate;
    public Boolean getTestMatriculeFiscale() {
        return TestMatriculeFiscale;
    }

    public Boolean getTestNfacture() {
        return TestNfacture;
    }

    public Boolean getTestMF() {
        return TestMF;
    }

    public Boolean getTestTottalTTC() {
        return TestTottalTTC;
    }

    public Boolean getTestTVA() {
        return TestTVA;
    }

    public Boolean getTestTel() {
        return TestTel;
    }

    public Boolean getTestFax() {
        return TestFax;
    }

    public Boolean getTestHtva() {
        return TestHtva;
    }

    public Boolean getTestMail() {
        return TestMail;
    }

    public Boolean getTestSite() {
        return TestSite;
    }

    public Boolean getTestDate() {
        return TestDate;
    }



    public String[] getOcrWords() {
        return ocrWords;
    }

    public  String[] ocrWords;
    public static Double TVAd;

    public String getMatriculeFiscale() {
        return MatriculeFiscale;
    }


    public String getMF() {
        return MF;
    }

    public String getTVA() {
        return TVA;
    }


    public  String getTel() {
        return Tel;
    }


    public String getFax() {
        return Fax;
    }


    public double getHTVA() {
        return HTVA;
    }


    public Double Tottal() {
        return Tottal;
    }


    public String getNoFacture() {
        return NoFacture;
    }

    public String getDate() {
        return Date;
    }


    public String getEmail() {
        return Email;
    }


    public String getSiteWeb() {
        return SiteWeb;
    }


    public String getDesignationAtMin() {
        return DesignationAtMin;
    }


    public String getDesignation() {
        return Designation;
    }


    public String getCode() {
        return Code;
    }



    public static ParsingOcrResult parsingOcr(String  GetOcrResult) {
        Double maxNumber = Double.MIN_VALUE;
        int minValue= Integer.MAX_VALUE;
        int minValue1= Integer.MAX_VALUE;
        int minValue2= Integer.MAX_VALUE;
        int minValue3= Integer.MAX_VALUE;
        int minValue4= Integer.MAX_VALUE;
        int minValue5= Integer.MAX_VALUE;
        int minValue6= Integer.MAX_VALUE;
        int minValue7= Integer.MAX_VALUE;
        int entier=0;
        int entier1=0;
        int entier2=0;
        int entier3=0;
        int entier4=0;
        int entier5=0;
        int entier6=0;
        int entier7=0;
        Double timbre=0.0;



        ParsingOcrResult resultOcr = new ParsingOcrResult();
        String textLower = GetOcrResult.toLowerCase();
        String replacement = " ";
        String delimiterChars = "[|?~'*()<\\\":>+\\\\[\\\\]']";
        String Result0 = textLower.replaceAll(delimiterChars, replacement);
        resultOcr.ocrWords = Result0.split("\\s+");
        for (String ss : resultOcr.ocrWords) {

           // System.out.println(ss);
        }
        for (int i = 0; i < resultOcr.ocrWords.length; i++) {

            int ComputeDistanceFiscale=getLevenshteinDistance("fiscal",resultOcr.ocrWords[i]);
            // Log.i("fiscale Compute  ",ocrWords[i]+"   "+getLevenshteinDistance("fiscale",ocrWords[i])+"  distance  : "+minValue );
            int ComputeDistanceCommerce=getLevenshteinDistance("commerce",resultOcr.ocrWords[i]);
            int ComputeDistanceMf=getLevenshteinDistance("mf",resultOcr.ocrWords[i]);
            // Log.i("MF compute ",ocrWords[i]+"   "+getLevenshteinDistance("mf",ocrWords[i])+"  distance  : "+minValue1 );
            int ComputeDistanceTva=getLevenshteinDistance("tva",resultOcr.ocrWords[i]);
            // Log.i("TVA compute ",ocrWords[i]+"   "+getLevenshteinDistance("tva",ocrWords[i])+"  distance  : "+minValue2 );
            int ComputeDistanceFax=getLevenshteinDistance("fax",resultOcr.ocrWords[i]);
            // Log.i("FAX compute ",ocrWords[i]+"   "+getLevenshteinDistance("fax",ocrWords[i])+"  distance  : "+minValue3 );
            int ComputeDistanceTel=getLevenshteinDistance("tel",resultOcr.ocrWords[i]);
            //  Log.i("Tel compute ",ocrWords[i]+"   "+getLevenshteinDistance("tel",ocrWords[i])+"  distance  : "+minValue4 );
            int ComputeDistanceFacture=getLevenshteinDistance("no",resultOcr.ocrWords[i]);
            int ComputeDistanceDate=getLevenshteinDistance("date",resultOcr.ocrWords[i]);
            // Log.i("Date compute ",ocrWords[i]+"   "+getLevenshteinDistance("date",ocrWords[i])+"  distance  : "+minValue5 );

           //---------------------Matricule Fiscale-----------------------------

            if(ComputeDistanceFiscale<=minValue ){
                minValue=ComputeDistanceFiscale;
                // System.out.println("fiscale Compute  "+ocrWords[i]+"   "+getLevenshteinDistance("fiscale",ocrWords[i])+"  distance  : "+minValue );
                if(ComputeDistanceFiscale==entier){
                    resultOcr.MatriculeFiscale= resultOcr.ocrWords[i+1];
                    //resultOcr.TestMatriculeFiscale=true;
                }
            }

            if(resultOcr.ocrWords[i].length()>6){
                if((resultOcr.ocrWords[i].replace("/","").replace("-","").length()==13)||(resultOcr.ocrWords[i].replace("/","").replace("-","").length()==12)){
                    String a=resultOcr.ocrWords[i].substring(0, 6);
                    String b=resultOcr.ocrWords[i].substring(resultOcr.ocrWords[i].length()-1);
                    if(tryParseTVA(a)&&tryParseTVA(b)){
                        resultOcr.MatriculeFiscale= resultOcr.ocrWords[i];
                        resultOcr.TestMatriculeFiscale=true;
                    }

                }
                if((resultOcr.ocrWords[i].replace("/","").replace("-","").length()==8)||(resultOcr.ocrWords[i].replace("/","").replace("-","").length()==7)){
                    String a=resultOcr.ocrWords[i].substring(0, 6);
                    String b=resultOcr.ocrWords[i].substring(resultOcr.ocrWords[i].length()-1);
                    if(tryParseTVA(a)&&tryParseTVA(b)==false){
                        resultOcr.MatriculeFiscale= resultOcr.ocrWords[i];
                        resultOcr.TestMatriculeFiscale=true;
                    }

                }

            }
           /* if(resultOcr.MatriculeFiscale.endsWith("000")){
                resultOcr.TestMatriculeFiscale=true;
            }*/




            //---------------------MF-----------------------------

            if(ComputeDistanceMf<=minValue1){
                minValue1=ComputeDistanceMf;
                // System.out.println("MF compute "+ocrWords[i]+"   "+getLevenshteinDistance("mf",ocrWords[i])+"  distance  : "+minValue1 );
                if(ComputeDistanceMf==entier1){
                    resultOcr.MF=resultOcr.ocrWords[i+1];
                   if(resultOcr.MF.endsWith("000")) {
                       resultOcr.TestMF = true;
                   }
                }
                else{
                    resultOcr.MF="";
                    resultOcr.TestMF=false;
                }
            }
            //---------------------TVA-----------------------------
            if(ComputeDistanceTva<=minValue2){
                minValue2=ComputeDistanceTva;
               /* if(ComputeDistanceTva==entier2 && Isnumber(resultOcr.ocrWords[i+1])){
                    resultOcr.TVA=resultOcr.ocrWords[i+1];
                }*/
              if( resultOcr.TVA==null){
                  resultOcr.TVA="0.0";
                  resultOcr.TestTVA=false;
                }
            }
            if(resultOcr.ocrWords[i].contains("%")){
                resultOcr.TVA=resultOcr.ocrWords[i];
                String TvaString =  resultOcr.TVA.replace("%","");
                resultOcr.TestTVA=true;
//                TvaNumber=Integer.getInteger(TvaString);
                Log.i("TVA1 : ", resultOcr.TVA);
            }
            //---------------------Fax-----------------------------
           /* if(ComputeDistanceFax<=minValue3) {
                minValue3 = ComputeDistanceFax;
                // System.out.println("FAX compute "+ocrWords[i]+"   "+getLevenshteinDistance("fax",ocrWords[i])+"  distance  : "+minValue3 );
                if (ComputeDistanceFax == entier3 ) {
                    String Fax1 = GetNumeroTelOrFax(ocrWords, i);
                    if (Fax1.length() == 8 || (Fax1.length() == 11 && Fax1.contains("216"))) {
                        Fax = Fax1;
                    } else {
                        Fax = "Error  " + "(" + Fax1 + ")";
                    }
                } else */
            if (resultOcr.ocrWords[i].contains("fax")) {
                String Fax2 = GetNumeroTelOrFax(resultOcr.ocrWords, i);
                if (Fax2.length() == 8 || (Fax2.length() == 11 && Fax2.contains("216"))) {
                    resultOcr.Fax = Fax2;
                    resultOcr.TestFax=true;

                } else {
                    resultOcr.Fax = "Error  " + "(" + Fax2 + ")";
                    resultOcr.TestFax=false;
                }
            }


            //---------------------TEL-----------------------------
          /*  if(ComputeDistanceTel<=minValue4) {
                minValue4 = ComputeDistanceTel;
                // System.out.println("Tel compute "+ocrWords[i]+"   "+getLevenshteinDistance("tel",ocrWords[i])+"  distance  : "+minValue4 );
                if (ComputeDistanceTel == entier4) {
                    String Tel1 = GetNumeroTelOrFax(ocrWords, i);
                    if (Tel1.length() == 8 || (Tel1.length() == 11 && Tel1.contains("216"))) {
                        Tel = Tel1;
                    } else {*/

           if (resultOcr.ocrWords[i].contains("tel") || resultOcr.ocrWords[i].contains("tél") ||resultOcr.ocrWords[i].contains("+216")) {
                String Tel2 = GetNumeroTelOrFax(resultOcr.ocrWords, i);
              String Tel3=resultOcr.ocrWords[i+1];
                if (Tel2.length() == 8 || (Tel2.length() == 11 && Tel2.contains("216"))) {
                    resultOcr.Tel = Tel2;
                    resultOcr.TestTel=true;
                } else {
                    Log.i("Errooooooooooor : ", Tel2);
                    resultOcr.Tel = "Error  " + "(" + Tel2 + ")";
                    if(Tel2.isEmpty()){
                        resultOcr.Tel = "Error  " + "(" + Tel3 + ")";
                   resultOcr.TestTel=false;
                    }
                }
                //Log.i("Teeeel : ", Tel);
            }
            // }
            //    }
            //  }


            //---------------------Total TTC-----------------------------
            if(Isnumber(resultOcr.ocrWords[i]) && (resultOcr.ocrWords[i].contains(".") || resultOcr.ocrWords[i].contains(","))){
                resultOcr.TotalTTC=Number;
                Log.i("Total ttc : ",Double.toString( resultOcr.TotalTTC));
                if( resultOcr.TotalTTC>maxNumber){
                    maxNumber= resultOcr.TotalTTC;
                    //Log.i("Total ttc : ",Double.toString(TotalTTC));
                    resultOcr.TestTottalTTC=true;
                }
            }
            if(resultOcr.TotalTTC.isNaN()){
                resultOcr.TestTottalTTC=false;
            }
           //-----------------------Mantant HTVA -------------------------------
            if((Isnumber(resultOcr.ocrWords[i]) && (resultOcr.ocrWords[i].contains(".") || resultOcr.ocrWords[i].contains(",")))&&(resultOcr.ocrWords[i-1].contains("htva"))){
               // HTVA =ocrWords[i];
                HtvaString=resultOcr.ocrWords[i];
                resultOcr.TestHtva=true;
                Log.i("Mantant HTVA : ",HtvaString);
            }



            //---------------------Date-----------------------------
           /* if(ComputeDistanceDate<=minValue5){
                minValue5=ComputeDistanceDate;
                if((ComputeDistanceDate==entier5 ||resultOcr.ocrWords[i].contains("du")||resultOcr.ocrWords[i].contains("le"))&&(resultOcr.ocrWords[i+1].contains("/")||resultOcr.ocrWords[i+1].contains("-")||Isnumber(resultOcr.ocrWords[i+1]))){
                    //Date=ocrWords[i+1];
                }
            }*/
            if(resultOcr.ocrWords[i].contains("/")||resultOcr.ocrWords[i].contains("-")){
                    boolean isDate,isDate1 = false;
                    String date1 = resultOcr.ocrWords[i].replace("o","0");
                    String datePattern = "\\d{1,2}/\\d{1,2}/\\d{2,4}";
                    String datePattern1 = "\\d{1,2}-\\d{1,2}-\\d{2,4}";
                    isDate = date1.matches(datePattern);
                    isDate1=date1.matches(datePattern1);
                    if(isDate==true||isDate1==true){
                        resultOcr.Date=date1;
                        resultOcr.TestDate=true;
                    }
            }
            if(resultOcr.Date==""){
                Calendar c = Calendar.getInstance();
                resultOcr.Date=c.getTime().toString();
                resultOcr.TestDate=false;

            }

            if(resultOcr.ocrWords[i].contains("/")||resultOcr.ocrWords[i].contains("-")){
                boolean isDate,isDate1 = false;
                String date1 = resultOcr.ocrWords[i].replace("o","0");
                String datePattern = "\\d{1,2}/\\d{1,2}/\\d{2,4}";
                String datePattern1 = "\\d{1,2}-\\d{1,2}-\\d{2,4}";
                isDate = date1.matches(datePattern);
                isDate1=date1.matches(datePattern1);
                if(isDate==true||isDate1==true){
                    resultOcr.Date=date1;
                    resultOcr.TestDate=true;
                }

            }


            //---------------------No Facture-----------------------------

         /*   if(ComputeDistanceFacture<minValue6){
                minValue6=ComputeDistanceFacture;
                if(ComputeDistanceFacture==entier6){
                    resultOcr.NoFacture=resultOcr.ocrWords[i+1];
                }else  ||(resultOcr.ocrWords[i].contains("n"))&&Isnumber(resultOcr.ocrWords[i+1])*/
            if(resultOcr.ocrWords[i].contains("no")||(resultOcr.ocrWords[i].contains("n°"))){
                    resultOcr.NoFacture=resultOcr.ocrWords[i+1];
                }
          //  }

            //---------------------Email-----------------------------
            if(resultOcr.ocrWords[i].contains("@")&&(resultOcr.ocrWords[i].contains(".net")||resultOcr.ocrWords[i].contains(".com")||resultOcr.ocrWords[i].contains(".fr")||resultOcr.ocrWords[i].contains(".tn"))){
                resultOcr.Email=resultOcr.ocrWords[i];
                if(resultOcr.Email.endsWith("com")||resultOcr.Email.endsWith("tn")||resultOcr.Email.endsWith("fr")){
                    resultOcr.TestMail=true;
                }
                else{
                    resultOcr.TestMail=false;
                }
            }
            //---------------------Site-----------------------------
            if((resultOcr.ocrWords[i].contains("www")||resultOcr.ocrWords[i].contains("http"))&&(resultOcr.ocrWords[i].contains(".net")||resultOcr.ocrWords[i].contains(".com")||resultOcr.ocrWords[i].contains(".fr")||resultOcr.ocrWords[i].contains(".tn"))){
                resultOcr.SiteWeb=resultOcr.ocrWords[i];
                if(resultOcr.SiteWeb.endsWith("com")||resultOcr.SiteWeb.endsWith("tn")||resultOcr.SiteWeb.endsWith("fr")){
                    resultOcr.TestSite=true;
                }
                else{
                    resultOcr.TestMail=false;
                }
            }
            if(resultOcr.ocrWords[i].contains("timbr")){
timbre=0.5;
            }

        }

        resultOcr.Tottal=maxNumber;
       /* if( resultOcr.TVA!="") {
            try {
                if (resultOcr.TVA.contains(".") || resultOcr.TVA.contains(",") || resultOcr.TVA.contains("%")) {
                    if (resultOcr.TVA.contains("%")) {

                        TVAi =(int) Double.parseDouble(resultOcr.TVA.replace("%", "").replace("#", ""));
                        int a=TVAi;
                    } else {
                        TVAi = Integer.parseInt(resultOcr.TVA);
                    }
                    if (resultOcr.Tottal != null && TVAi != null && timbre!=0.0 ) {
                        resultOcr.HTVA = (((resultOcr.Tottal-timbre / (1 + (TVAi / 100))) * 100) / 100);
                    }
                    else if(resultOcr.Tottal != null && TVAi != null && timbre==0.0) {
                        resultOcr.HTVA = (((resultOcr.Tottal / (1 + (TVAi / 100))) * 100) / 100);
                    }
                    else {
                        resultOcr.HTVA = Double.parseDouble(HtvaString);
                    }
                }
            }
            catch(Exception e){

            }
        }*/

        if( resultOcr.TVA!="") {
                if (resultOcr.TVA.contains(".") || resultOcr.TVA.contains(",") || resultOcr.TVA.contains("%")) {
                    if (resultOcr.TVA.contains("%")) {
                        Log.i("TVA%","R"+resultOcr.TVA+"R");
String TVAA=resultOcr.TVA.replace("%", "").replace("#", "").replace("l","1").replace("o","0");
                        if (tryParseTVA(TVAA)){
                            TVAd = Double.parseDouble(TVAA);
                            if (resultOcr.Tottal != null && TVAd != null && timbre!=0.0 ) {
                                resultOcr.HTVA = ((((resultOcr.Tottal-0.5) / (1 + (TVAd / 100))) * 100) / 100);
                            }
                            else if(resultOcr.Tottal != null && TVAd != null && timbre==0.0) {
                                resultOcr.HTVA = (((resultOcr.Tottal / (1 + (TVAd / 100))) * 100) / 100);
                            }
                            else {
                                resultOcr.HTVA = Double.parseDouble(HtvaString);
                            }
                            resultOcr.TestHtva=true;
                   }

                }
            }
        }
        if(resultOcr.HTVA.isNaN()){
            resultOcr.TestHtva=false;
        }



        entier=minValue;
        entier1=minValue1;
        entier2=minValue2;
        entier3=minValue3;
        entier4=minValue4;
        entier5=minValue5;
        entier6=minValue6;
        entier7=minValue7;



        return resultOcr;

    }
 public static boolean tryParseTVA(String value) {
        try {
Log.i("double","l"+value+"l");
          Double a= Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }





    public static String GetSubString(String[] words, String[] wordsOfPlanComptable, int index)
    {
        ArrayList<String> wordList= new ArrayList<>(Arrays.asList(words));
        String aaaa= TextUtils.join(" ", wordList.subList(index, index + wordsOfPlanComptable.length).toArray());
        return aaaa;
    }

    private static String GetNumeroTelOrFax(String[] words, int indexTel)
    {
        String result="";
        int index = indexTel + 1;
        while (index < words.length && IsPhoneFaxNumber(words[index]))
        {
            result += words[index];
            index++;
        }
        return result;
    }

    public static boolean IsPhoneFaxNumber(String word)
    {
        boolean IsPhoneFax = true;
        try{
            Integer.parseInt(word.replace("+", "").replace("-", "").replace(".", "").replace(" ", "").replace("/","").replace("fax","").replace("tel","").replace("[","1").replace("]","1"));
        }catch(NumberFormatException e){
            IsPhoneFax = false;
        }
        return IsPhoneFax;
    }

    public static boolean Isnumber(String word)
    {

        boolean IsNumber = true;
        try{
            Number= Double.parseDouble(word.replace(".", ".").replace(",", ".").replace("+", "/").replace(" ", "").replace("-", "/"));
        }catch(NumberFormatException e){
            IsNumber = false;
        }
        return IsNumber;
    }
    public static int getLevenshteinDistance(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int n = s.length();
        int m = t.length();

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }
        if (n > m) {
            String tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }
        int p[] = new int[n+1];
        int d[] = new int[n+1];
        int _d[];
        int i;
        int j;
        char t_j;
        int cost;
        for (i = 0; i<=n; i++) {
            p[i] = i;
        }
        for (j = 1; j<=m; j++) {
            for (i=1; i<=n; i++) {
                t_j = t.charAt(j-1);
                d[0] = j;
                cost = s.charAt(i-1)==t_j ? 0 : 1;
                d[i] = Math.min(Math.min(d[i-1]+1, p[i]+1),  p[i-1]+cost);
            }
            _d = p;
            p = d;
            d = _d;
        }
        return p[n];
    }
    public static void ParseDesignation(String DesignatationTextMain) {

        int minDistance = Integer.MAX_VALUE;

        String textLower2 =  DesignatationTextMain.toLowerCase();
        String replacement2 =  " ";
        String delimiterChars2 = "[|?~'*()<\\\":>+\\\\[\\\\]']";
        String Result2 = textLower2.replaceAll(delimiterChars2, replacement2);
        String[] DesignationText = Result2.split("\\s+");
        for(int j=1;j<MainActivity.Dictionnaire.size();j++) {
             Designation=MainActivity.Dictionnaire.get(j);
            String textLower1 = Designation.toLowerCase();
            String replacement1 = " ";
            String delimiterChars1 = "[|?~'*()<\\\":>+\\\\[\\\\]']";
            String ResultDesignation = textLower1.replaceAll(delimiterChars1, replacement1);
            String[] MotClé = ResultDesignation.split("\\s+");
            for (int k = 0; k < DesignationText.length - MotClé.length; k++) {
                int computeDistance = getLevenshteinDistance(ResultDesignation, GetSubString(DesignationText, MotClé, k));
                System.out.println("Substring   "+  GetSubString(DesignationText, MotClé, k)+"      Désignationnnn:     "+ ResultDesignation);
                if (computeDistance < minDistance || (computeDistance == minDistance && DesignationText.length > DesignationAtMin.length())) {
                    minDistance = computeDistance;
                    DesignationAtMin = Designation.toString();
                    Code=MainActivity.Dictionnaire.get(j - 1);
                    //System.out.println("DÃ©signationnnn Resuuuult1   "+ ResultDesignation+"        min distance          "+minDistance);
                }
                if (ResultDesignation.equals( GetSubString(DesignationText, MotClé, k))) {
                    //System.out.println("Désignationnnn Resuuuult  "+ ResultDesignation);
                    DesignationAtMin = Designation.toString();
                    Code=MainActivity.Dictionnaire.get(j - 1);
                }
            }
            Log.i("  ", "distaaaaaaaaance " + Integer.toString(minDistance) + "       " + DesignationAtMin.toString() + "    Code  " + Code);

        }
        Log.i("  ", "distaaaaaaaaance " + Integer.toString(minDistance) + "       " + DesignationAtMin.toString()   );
    }
 /*  public static void ParseDesignation(String DesignatationTextMain) {
       int computeDistance=0;
       int minDistance = Integer.MAX_VALUE;
       String textLower2 =  DesignatationTextMain.toLowerCase();
       String replacement2 = " ";
       String delimiterChars2 = "[|?~'*()<\\\":>+\\\\[\\\\]']";
       String Result2 = textLower2.replaceAll(delimiterChars2, replacement2);
       String[] DesignationText = Result2.split("\\s+");
       for(int j=1;j<MainActivity.Dictionnaire.size();j++) {
           Designation=MainActivity.Dictionnaire.get(j);
           String textLower1 = Designation.toLowerCase();

           String replacement1 = " ";
           String delimiterChars1 = "[|?~'*()<\\\":>+\\\\[\\\\]']";
           String ResultDesignation = textLower1.replaceAll(delimiterChars1, replacement1);
           //String[] MotClé = ResultDesignation.split("\\s+");
                computeDistance = getLevenshteinDistance(ResultDesignation, Result2);
               if (computeDistance < minDistance || (computeDistance == minDistance && DesignationText.length > DesignationAtMin.length())) {
                   minDistance = computeDistance;
                   DesignationAtMin = Designation.toString();
                   Code=MainActivity.Dictionnaire.get(j - 1);
                   String Poucentage=String.valueOf((computeDistance * 100) / DesignationAtMin.length());
                   Log.i("LOOOG",Designation + "   " + computeDistance + " DistanceMin     " + minDistance + "   Pourcentage : " + Poucentage);
               }
               if (ResultDesignation == Result2) {
                   DesignationAtMin = Designation.toString();
                   Code=MainActivity.Dictionnaire.get(j - 1);
               }
           }
       Log.i("  ", "distaaaaaaaaance " + Integer.toString(minDistance) + "       " + DesignationAtMin.toString() + "    Code  " + Code);

   }
*/

}