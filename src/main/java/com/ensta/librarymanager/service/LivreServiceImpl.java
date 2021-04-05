package com.ensta.librarymanager.service;
import com.ensta.librarymanager.service.ServiceException;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.dao.DaoException;
import java.util.ArrayList;
import java.util.Date;



public class LivreServiceImpl implements LivreService {
    private static LivreServiceImpl instance;

    private LivreServiceImpl(){}

    public static LivreServiceImpl getInstance(){
        if (instance == null){
            instance = new LivreServiceImpl();
        }
        return instance;
    }

	public List<Livre> getList() throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<Livre>();	
        try {
			livres = livreDao.getList();
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return livres;
    }
	public List<Livre> getListDispo() throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        EmpruntServiceImpl empruntServ = EmpruntServiceImpl.getInstance();

        List<com.ensta.librarymanager.model.Livre> livresD = new ArrayList<Livre>();	
        try {
            List<com.ensta.librarymanager.model.Livre> livres = new ArrayList<Livre>();	

			livres = livreDao.getList();

            for(com.ensta.librarymanager.model.Livre L:livres){
                if(empruntServ.isLivreDispo(L.getId())){
                    livresD.add(L);
                }
            }
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return livresD;
    }
	public Livre getById(int id) throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        Livre livre = new Livre();	
        try {
			livre = livreDao.getById(id);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return livre;

    }
	public int create(String titre, String auteur, String isbn) throws ServiceException{
		LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		int i = -1;
        if(titre=="" || titre==null) throw new ServiceException("Erreur : titre vide");
		try {
			i = livreDao.create(titre, auteur, isbn);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return i;
    }
	public void update(Livre livre) throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
        if(livre.getTitre()=="" || livre.getTitre()==null) throw new ServiceException("Erreur : titre vide");
		try {
			livreDao.update(livre);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
    }
	public void delete(int id) throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		try {
			livreDao.delete(id);
		} catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} catch (NumberFormatException e2) {
			throw new ServiceException("Erreur lors du parsing: id=" + id, e2);
		}
    }
	public int count() throws ServiceException{
        LivreDaoImpl livreDao = LivreDaoImpl.getInstance();
		int i = -1;
		try {
			i = livreDao.count();
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return i;

    }
}
