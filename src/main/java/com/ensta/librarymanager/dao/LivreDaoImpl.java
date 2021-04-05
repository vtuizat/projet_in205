package com.ensta.librarymanager.dao;
import java.sql.ResultSet;
import java.util.List;

import com.ensta.librarymanager.dao.DaoException;
import com.ensta.librarymanager.model.Livre;
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


public class LivreDaoImpl implements LivreDao{
    private static LivreDaoImpl instance;

    private LivreDaoImpl(){}

    public static LivreDaoImpl getInstance(){
        if (instance == null){
            instance = new LivreDaoImpl();
        }
        return instance;
    }

    public List<Livre> getList() throws DaoException{
        ArrayList<Livre> L=new ArrayList<Livre>();
        ResultSet rs = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
            connection = ConnectionManager.getConnection();
            stmt =
            connection.prepareStatement("SELECT id, titre, auteur, isbn FROM livre");
            rs = stmt.executeQuery();
            
            while (rs.next()) { 
                Livre newLivre = new Livre(rs.getInt("id"),rs.getString("titre"),rs.getString("auteur"),rs.getString("isbn"));

                L.add(newLivre);

            }
        }
        catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du livre", e);
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
	public Livre getById(int id) throws DaoException{
        Livre livre = new Livre();
        Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
            connection = ConnectionManager.getConnection();
            stmt =
            connection.prepareStatement("SELECT id, titre, auteur, isbn FROM livre WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()) livre = new Livre(rs.getInt("id"),rs.getString("titre"),rs.getString("auteur"),rs.getString("isbn"));
        }
		catch (SQLException e) {
			throw new DaoException("Problème lors de la récupération du livre", e);
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
        return livre;
    }
	public int create(String titre, String auteur, String isbn) throws DaoException{
        int n=0;
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		try{
            connection = ConnectionManager.getConnection();
            stmt=
            connection.prepareStatement("INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, titre);
            stmt.setString(2, auteur);
            stmt.setString(3, isbn);

            stmt.executeUpdate();
            rs=stmt.getGeneratedKeys();
            if(rs.next()) n=rs.getInt(1);
        }
		catch (SQLException e) {
			throw new DaoException("Problème lors de la création du livre", e);
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
	public void update(Livre livre) throws DaoException{
        Connection connection = null;
		PreparedStatement stmt = null;
		try{
            connection = ConnectionManager.getConnection();
            stmt=
            connection.prepareStatement("UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?");
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getIsbn());
            stmt.setInt(4, livre.getId());

            stmt.executeUpdate();
        }
		catch (SQLException e) {
			throw new DaoException("Problème lors de l'update du livre", e);
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
            connection.prepareStatement("DELETE FROM livre WHERE id = ?");
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
		catch (SQLException e) {
			throw new DaoException("Problème lors de la supression du livre", e);
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
			connection.prepareStatement("SELECT COUNT(id) AS count FROM livre");

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