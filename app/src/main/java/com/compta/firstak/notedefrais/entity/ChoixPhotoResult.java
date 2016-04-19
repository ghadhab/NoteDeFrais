package com.compta.firstak.notedefrais.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohamed on 24/11/2015.
 */
public class ChoixPhotoResult implements Parcelable {


    public String NoFacture;
    public String MatriculeFiscale;
    public String MF;
    public String TVA;
    public Double Tottal;
    public  Double HTVA;
    public String Tel;
    public  String Fax;
    public String Email;
    public String SiteWeb;
    public String Date;
    public String Designation;
    public String DesignationAtMin;



    public  ChoixPhotoResult(
            String NoFacture,
            String MatriculeFiscale,
            String MF,
            String TVA,
            Double Tottal,
            Double HTVA,
            String Tel,
            String Fax,
            String Email,
            String Site,
            String Date,
            String Designation,
            String DesignationAtMin
    )
    {
        super();
        this.NoFacture = NoFacture;
        this.MatriculeFiscale = MatriculeFiscale;
        this.MF = MF;
        this.TVA = TVA;
        this.Tottal = Tottal;
        this.HTVA = HTVA;
        this.Tel = Tel;
        this.Fax = Fax;
        this.Email=Email;
        this.SiteWeb=Site;
        this.Date=Date;
        this.Designation=Designation;
        this.DesignationAtMin=DesignationAtMin;

}
    public ChoixPhotoResult(Parcel in) {
        this.NoFacture = in.readString();
        this.MatriculeFiscale = in.readString();
        this.MF = in.readString();
        this.TVA = in.readString();
        this.Tottal = in.readDouble();
        this.HTVA = in.readDouble();
        this.Tel = in.readString();
        this.Fax = in.readString();
        this.Email = in.readString();
        this.SiteWeb = in.readString();
        this.Date = in.readString();
        this.Designation = in.readString();
        this.DesignationAtMin = in.readString();


    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(NoFacture);
        dest.writeString(MatriculeFiscale);
        dest.writeString(MF);
        dest.writeString(TVA);
        dest.writeDouble(Tottal);
        dest.writeDouble(HTVA);
        dest.writeString(Tel);
        dest.writeString(Fax);
        dest.writeString(Email);
        dest.writeString(SiteWeb);
        dest.writeString(Date);
        dest.writeString(Designation);
        dest.writeString(DesignationAtMin);

    }


    public static final Parcelable.Creator<ChoixPhotoResult> CREATOR = new Parcelable.Creator<ChoixPhotoResult>()
    {
        @Override
        public ChoixPhotoResult createFromParcel(Parcel source)
        {
            return new ChoixPhotoResult(source);
        }

        @Override
        public ChoixPhotoResult[] newArray(int size)
        {
            return new ChoixPhotoResult[size];
        }
    };


    public String getNoFacture() {
        return NoFacture;
    }

    public String getMatriculeFiscale() {
        return MatriculeFiscale;
    }

    public String getMF() {
        return MF;
    }

    public String getTVA() {
        return TVA;
    }

    public Double getTottal() {
        return Tottal;
    }

    public Double getHTVA() {
        return HTVA;
    }

    public String getTel() {
        return Tel;
    }

    public String getFax() {
        return Fax;
    }

    public String getEmail() {
        return Email;
    }

    public String getSiteWeb() {
        return SiteWeb;
    }

    public String getDate() {
        return Date;
    }

    public String getDesignation() {
        return Designation;
    }

    public String getDesignationAtMin() {
        return DesignationAtMin;
    }



    public static Parcelable.Creator<ChoixPhotoResult> getCreator()
    {
        return CREATOR;
    }


}