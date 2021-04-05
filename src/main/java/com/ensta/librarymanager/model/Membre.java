package com.ensta.librarymanager.model;

public class Membre{
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private Abonnement abonnement;

    public Membre(){}

    public Membre(int id,String nom,String prenom,String adresse,String email,String telephone,Abonnement abonnement){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.adresse=adresse;
        this.email=email;
        this.telephone=telephone;
        this.abonnement=abonnement;
    }

    public Membre(int id,String nom,String prenom,String adresse,String email,String telephone){
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.adresse=adresse;
        this.email=email;
        this.telephone=telephone;
        this.abonnement=Abonnement.BASIC;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    public void setNom(String nom){
        this.nom=nom;
    }
    public String getNom(){
        return this.nom;
    }
    public void setPrenom(String prenom){
        this.prenom=prenom;
    }
    public String getPrenom(){
        return this.prenom;
    }
    public void setAdresse(String adresse){
        this.adresse=adresse;
    }
    public String getAdresse(){
        return this.adresse;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return this.email;
    }
    public void setTelephone(String telephone){
        this.telephone=telephone;
    }
    public String getTelephone(){
        return this.telephone;
    }
    public void setAbonnement(Abonnement abonnement){
        this.abonnement=abonnement;
    }
    public Abonnement getAbonnement(){
        return this.abonnement;
    }

    public String toString(){
        return "Membre "+id+" : "+prenom+", "+nom+", "+adresse+", "+email+", "+telephone+", abonnement"+abonnement;
    }


}

