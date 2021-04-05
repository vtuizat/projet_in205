package com.ensta.librarymanager.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.dao.DaoException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Abonnement;
import com.ensta.librarymanager.model.Membre;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.ensta.librarymanager.persistence.ConnectionManager;


public class EmpruntDaoImpl implements EmpruntDao{
	private static EmpruntDaoImpl instance;

    private EmpruntDaoImpl(){}

    public static EmpruntDaoImpl getInstance(){
        if (instance == null){
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }

    public List<Emprunt> getList() throws DaoException{
		ArrayList<Emprunt> L=new ArrayList<Emprunt>();
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt =
			connection.prepareStatement(
	"SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC");
			rs = stmt.executeQuery();
			

			while (rs.next()) { 
				Livre newLivre = new Livre(rs.getInt("idLivre"),rs.getString("titre"),rs.getString("auteur"),rs.getString("isbn"));
				Abonnement abo = Abonnement.BASIC;
				switch (rs.getString("abonnement")){
					case "basic": abo = Abonnement.BASIC;
					case "premium": abo = Abonnement.PREMIUM;
					case "vip": abo = Abonnement.VIP;
				}
				Membre newMembre = new Membre(rs.getInt("idMembre"),rs.getString("nom"),
				rs.getString("prenom"),rs.getString("adresse"),rs.getString("email"),rs.getString("telephone"),abo);

				Emprunt newEmprunt = new Emprunt(rs.getInt("id"),newMembre,newLivre,
				rs.getDate("dateEmprunt").toLocalDate(),rs.getDate("dateRetour").toLocalDate());

				L.add(newEmprunt);
			}
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de l'emprunt", e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return L;
	}
	public List<Emprunt> getListCurrent() throws DaoException{
		ArrayList<Emprunt> L=new ArrayList<Emprunt>();
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt =
			connection.prepareStatement(
	"SELECT e.id ASid, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL");
			rs = stmt.executeQuery();
			
			while (rs.next()) { 
				Livre newLivre = new Livre(rs.getInt("idLivre"),rs.getString("titre"),rs.getString("auteur"),rs.getString("isbn"));
				Abonnement abo = Abonnement.BASIC;
				switch (rs.getString("abonnement")){
					case "basic": abo = Abonnement.BASIC;
					case "premium": abo = Abonnement.PREMIUM;
					case "vip": abo = Abonnement.VIP;
				}
				Membre newMembre = new Membre(rs.getInt("idMembre"),rs.getString("nom"),
				rs.getString("prenom"),rs.getString("adresse"),rs.getString("email"),rs.getString("telephone"),abo);

				Emprunt newEmprunt = new Emprunt(rs.getInt("id"),newMembre,newLivre,
				rs.getDate("dateEmprunt").toLocalDate(),rs.getDate("dateRetour").toLocalDate());

				L.add(newEmprunt);

			}
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de l'emprunt", e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return L;
	}
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
		ArrayList<Emprunt> L=new ArrayList<Emprunt>();
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt =
			connection.prepareStatement(
	"SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?");
			stmt.setInt(1, idMembre);
			rs = stmt.executeQuery();
			while (rs.next()) { 
				Livre newLivre = new Livre(rs.getInt("idLivre"),rs.getString("titre"),rs.getString("auteur"),rs.getString("isbn"));
				Abonnement abo = Abonnement.BASIC;
				switch (rs.getString("abonnement")){
					case "basic": abo = Abonnement.BASIC;
					case "premium": abo = Abonnement.PREMIUM;
					case "vip": abo = Abonnement.VIP;
				}
				Membre newMembre = new Membre(rs.getInt("idMembre"),rs.getString("nom"),
				rs.getString("prenom"),rs.getString("adresse"),rs.getString("email"),rs.getString("telephone"),abo);
				
				LocalDate dateRetour = LocalDate.of(1,1,1);
				if(null!=rs.getDate("dateRetour")) dateRetour=rs.getDate("dateRetour").toLocalDate(); //fixes null dates
				
				Emprunt newEmprunt = new Emprunt(rs.getInt("id"),newMembre,newLivre,
				rs.getDate("dateEmprunt").toLocalDate(),dateRetour);
				L.add(newEmprunt);
			}
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de l'emprunt", e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return L;
		
	}
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
		ArrayList<Emprunt> L=new ArrayList<Emprunt>();
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt =
			connection.prepareStatement(
	"SELECT e.id ASid, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?");
			stmt.setInt(1, idLivre);
			rs = stmt.executeQuery();

			while (rs.next()) { 
				Livre newLivre = new Livre(rs.getInt("idLivre"),rs.getString("titre"),rs.getString("auteur"),rs.getString("isbn"));
				Abonnement abo = Abonnement.BASIC;
				switch (rs.getString("abonnement")){
					case "basic": abo = Abonnement.BASIC;
					case "premium": abo = Abonnement.PREMIUM;
					case "vip": abo = Abonnement.VIP;
				}
				Membre newMembre = new Membre(rs.getInt("idMembre"),rs.getString("nom"),
				rs.getString("prenom"),rs.getString("adresse"),rs.getString("email"),rs.getString("telephone"),abo);

				Emprunt newEmprunt = new Emprunt(rs.getInt("id"),newMembre,newLivre,
				rs.getDate("dateEmprunt").toLocalDate(),rs.getDate("dateRetour").toLocalDate());

				L.add(newEmprunt);

			}
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération de l'emprunt", e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return L;
	}
	public Emprunt getById(int id) throws DaoException{
		Emprunt emprunt =new Emprunt();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		try{
			connection = ConnectionManager.getConnection();
			stmt =
			connection.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next()){
				Livre livre = new Livre(rs.getInt("idLivre"),rs.getString("titre"),rs.getString("auteur"),rs.getString("isbn"));
				Abonnement abo = Abonnement.BASIC;
				switch (rs.getString("abonnement")){
					case "basic": abo = Abonnement.BASIC;
					case "premium": abo = Abonnement.PREMIUM;
					case "vip": abo = Abonnement.VIP;
				}
				Membre membre = new Membre(rs.getInt("idMembre"),rs.getString("nom"),
					rs.getString("prenom"),rs.getString("adresse"),rs.getString("email"),rs.getString("telephone"),abo);
				emprunt = new Emprunt(rs.getInt("id"),membre,livre,
					rs.getDate("dateEmprunt").toLocalDate(),rs.getDate("dateRetour").toLocalDate());
			}
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération", e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return emprunt;
	}
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException{
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt=
			connection.prepareStatement("INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?)",
			Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, idMembre);
			stmt.setInt(2, idLivre);
			stmt.setDate(3, Date.valueOf(dateEmprunt));
			stmt.setDate(4, null);

			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération", e);
		} finally {
			
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void update(Emprunt emprunt) throws DaoException{
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt=
			connection.prepareStatement("UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?");
			stmt.setInt(1, emprunt.getMembre().getId());
			stmt.setInt(2, emprunt.getLivre().getId());
			stmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
			stmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));
			stmt.setInt(5, emprunt.getId());

			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération", e);
		} finally {
			
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public int count() throws DaoException{
		int n=0;
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt=
			connection.prepareStatement("SELECT COUNT(id) AS count FROM emprunt");

			rs =stmt.executeQuery();
			if(rs.next()) n=rs.getInt("count");
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du compte", e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return n;
	}
}