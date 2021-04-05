package com.ensta.librarymanager.model;

import java.time.LocalDate;

public class Emprunt{
    private int id;
    private Membre membre;
    private Livre livre;
    private LocalDate dateRetour;
    private LocalDate dateEmprunt;

    public Emprunt(){}

    public Emprunt(int id,Membre membre,Livre livre,LocalDate dateEmprunt,LocalDate dateRetour){
        this.id=id;
        this.membre=membre;
        this.livre=livre;
        this.dateRetour=dateRetour;
        this.dateEmprunt=dateEmprunt;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    public void setMembre(Membre membre){
        this.membre=membre;
    }
    public Membre getMembre(){
        return this.membre;
    }
    public void setLivre(Livre livre){
        this.livre=livre;
    }
    public Livre getLivre(){
        return this.livre;
    }
    public void setDateRetour(LocalDate dateRetour){
        this.dateRetour=dateRetour;
    }
    public LocalDate getDateRetour(){
        return this.dateRetour;
    }
    public void setDateEmprunt(LocalDate dateEmprunt){
        this.dateEmprunt=dateEmprunt;
    }
    public LocalDate getDateEmprunt(){
        return this.dateEmprunt;
    }

    public String toString(){
        return "Emprunt "+id+" : "+livre.getTitre()+" ("+livre.getId()+"), emprunté par "+membre.getPrenom()+" "+membre.getNom()+" ("+membre.getId()+") le "+dateEmprunt.toString()+" à rendre le "+dateRetour.toString();
    }
}
