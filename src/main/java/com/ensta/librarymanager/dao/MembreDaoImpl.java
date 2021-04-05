package com.ensta.librarymanager.dao;

import java.sql.ResultSet;
import java.util.List;

import com.ensta.librarymanager.dao.DaoException;
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


public class MembreDaoImpl implements MembreDao{
	private static MembreDaoImpl instance;

    private MembreDaoImpl(){}

    public static MembreDaoImpl getInstance(){
        if (instance == null){
            instance = new MembreDaoImpl();
        }
        return instance;
    }

    public List<Membre> getList() throws DaoException{
		ArrayList<Membre> L=new ArrayList<Membre>();
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt =
			connection.prepareStatement(
				"SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom");
			rs = stmt.executeQuery();
				
			while (rs.next()) { 
				Abonnement abo = Abonnement.BASIC;
				switch (rs.getString("abonnement")){
					case "basic": abo = Abonnement.BASIC;
					case "premium": abo = Abonnement.PREMIUM;
					case "vip": abo = Abonnement.VIP;
				}
				Membre newMembre = new Membre(rs.getInt("id"),rs.getString("nom"),
				rs.getString("prenom"),rs.getString("adresse"),rs.getString("email"),rs.getString("telephone"),abo);

				L.add(newMembre);
			}
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du membre", e);
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
	public Membre getById(int id) throws DaoException{
		Membre membre = new Membre();
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt =
			connection.prepareStatement("SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next()) {
				Abonnement abo = Abonnement.BASIC;
				switch (rs.getString("abonnement")){
					case "basic": abo = Abonnement.BASIC;
					case "premium": abo = Abonnement.PREMIUM;
					case "vip": abo = Abonnement.VIP;
				}
				membre = new Membre(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),
	rs.getString("adresse"),rs.getString("email"),rs.getString("telephone"),abo);
			}
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du membre", e);
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
		return membre;
	}
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException{
		int n=0;
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt=
			connection.prepareStatement(
				"INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?)",
			Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nom);
			stmt.setString(2, prenom);
			stmt.setString(3, adresse);
			stmt.setString(4, email);
			stmt.setString(5, telephone);
			stmt.setString(6, "basic");

			
			stmt.executeUpdate();
            rs=stmt.getGeneratedKeys();
            if(rs.next()) n=rs.getInt(1);
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la création", e);
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
	public void update(Membre membre) throws DaoException{
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt=
			connection.prepareStatement(
				"UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?");
			stmt.setString(1, membre.getNom());
			stmt.setString(2, membre.getPrenom());
			stmt.setString(3, membre.getAdresse());
			stmt.setString(4, membre.getEmail());
			stmt.setString(5, membre.getTelephone());

			String abo = "basic";
			switch (membre.getAbonnement()){
				case BASIC: abo = "basic";
				case PREMIUM: abo = "premium";
				case VIP: abo = "vip";
			}
			stmt.setString(6, abo);

			stmt.setInt(7, membre.getId());

			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de l'update'", e);
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
	public void delete(int id) throws DaoException{
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
			connection = ConnectionManager.getConnection();
			stmt=
			connection.prepareStatement("DELETE FROM membre WHERE id = ?");
			stmt.setInt(1, id);

			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new DaoException("Problème lors de la supression", e);
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
			connection.prepareStatement("SELECT COUNT(id) AS count FROM membre");

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