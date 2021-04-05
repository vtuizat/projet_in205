package com.ensta.librarymanager.service;
import com.ensta.librarymanager.service.ServiceException;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.MembreDaoImpl;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.dao.DaoException;
import java.util.ArrayList;
import java.util.Date;



public class MembreServiceImpl implements MembreService {
    private static MembreServiceImpl instance;

    private MembreServiceImpl(){}

    public static MembreServiceImpl getInstance(){
        if (instance == null){
            instance = new MembreServiceImpl();
        }
        return instance;
    }

	public List<Membre> getList() throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
        List<Membre> membres = new ArrayList<Membre>();	
        try {
			membres = membreDao.getList();
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return membres;
    }
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
        EmpruntServiceImpl empruntServ = EmpruntServiceImpl.getInstance();

        List<com.ensta.librarymanager.model.Membre> membresP = new ArrayList<Membre>();	
        try {
            List<Membre> membres = new ArrayList<Membre>();	
            membres = membreDao.getList();
            for(Membre M:membres){
				
                if(empruntServ.isEmpruntPossible(M)) {
					membresP.add(M);
				}
            }
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return membresP;
    }
	public Membre getById(int id) throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		Membre membre = new Membre();
		try {
			membre = membreDao.getById(id);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return membre;
    }
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		int i = -1;
		if(nom=="" || nom==null) throw new ServiceException("Erreur : nom vide");
        if(prenom=="" || prenom==null) throw new ServiceException("Erreur : prenom vide");

		try {
			String nomUpper=nom.toUpperCase();
			i = membreDao.create(nomUpper,prenom,adresse,email,telephone);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return i;
    }
	public void update(Membre membre) throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		if(membre.getNom()=="" || membre.getNom()==null) throw new ServiceException("Erreur : nom vide");
        if(membre.getPrenom()=="" || membre.getPrenom()==null) throw new ServiceException("Erreur : prenom vide");

		try {
			membre.setNom(membre.getNom().toUpperCase());
			membreDao.update(membre);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
    }
	public void delete(int id) throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		try {
			membreDao.delete(id);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
    }
	public int count() throws ServiceException{
        MembreDaoImpl membreDao = MembreDaoImpl.getInstance();
		int i = -1;
		try {
			i = membreDao.count();
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return i;
    }

}
