package com.ensta.librarymanager.model;

public class Livre{
    private int id;
    private String titre;
    private String auteur;
    private String isbn;

    public Livre(){}

    public Livre(int id,String titre,String auteur,String isbn){
        this.id=id;
        this.titre=titre;
        this.auteur=auteur;
        this.isbn=isbn;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    public void setTitre(String titre){
        this.titre=titre;
    }
    public String getTitre(){
        return this.titre;
    }
    public void setAuteur(String auteur){
        this.auteur=auteur;
    }
    public String getAuteur(){
        return this.auteur;
    }
    public void setIsbn(String isbn){
        this.isbn=isbn;
    }
    public String getIsbn(){
        return this.isbn;
    }
    
    public String toString(){
        return "Livre "+id+" : "+titre+", "+auteur+",ISBN: "+isbn;
    }
}