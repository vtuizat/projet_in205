package com.ensta.librarymanager.service;
import com.ensta.librarymanager.service.ServiceException;
import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.dao.DaoException;
import java.util.ArrayList;
//import java.util.Date;
import java.time.LocalDate;
import java.sql.Date;





public class EmpruntServiceImpl implements EmpruntService {
    private static EmpruntServiceImpl instance;

    private EmpruntServiceImpl(){}

    public static EmpruntServiceImpl getInstance(){
        if (instance == null){
            instance = new EmpruntServiceImpl();
        }
        return instance;
    }

	public List<Emprunt> getList() throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts=new ArrayList<Emprunt>();
		try {
			emprunts = empruntDao.getList();
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return emprunts;
    }
	public List<Emprunt> getListCurrent() throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts=new ArrayList<Emprunt>();
		try {
			emprunts = empruntDao.getListCurrent();
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return emprunts;
    }
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts=new ArrayList<Emprunt>();
		try {
			emprunts = empruntDao.getListCurrentByMembre(idMembre);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return emprunts;
    }
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		List<Emprunt> emprunts=new ArrayList<Emprunt>();
		try {
			emprunts = empruntDao.getListCurrentByLivre(idLivre);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return emprunts;
    }
	public Emprunt getById(int id) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		Emprunt emprunt=new Emprunt();
		try {
			emprunt = empruntDao.getById(id);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return emprunt;
    }
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		try {
			empruntDao.create(idMembre,idLivre,dateEmprunt);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
    }
	public void returnBook(int id) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		try {
            com.ensta.librarymanager.model.Emprunt emprunt = empruntDao.getById(id);
            Date dateR = new Date(System.currentTimeMillis());
            emprunt.setDateRetour(dateR.toLocalDate());
			empruntDao.update(emprunt);
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
    }
	public int count() throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		int i = -1;
		try {
			i = empruntDao.count();
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return i;
    }
	public boolean isLivreDispo(int idLivre) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		boolean dispo =false;
		try {
			if(null==empruntDao.getListCurrentByLivre(idLivre)) dispo=true;
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return dispo;
    }
	public boolean isEmpruntPossible(Membre membre) throws ServiceException{
        EmpruntDaoImpl empruntDao = EmpruntDaoImpl.getInstance();
		boolean possible =false;
        List<Emprunt> emprunts=new ArrayList<Emprunt>();
		try {
            int maxBook =0;
            switch (membre.getAbonnement()){
				case BASIC: maxBook = 2;
				case PREMIUM: maxBook = 5;
				case VIP: maxBook = 20;
			}
            System.out.println(membre);
			emprunts = empruntDao.getListCurrentByMembre(membre.getId());

            int count=0;
            for(com.ensta.librarymanager.model.Emprunt E:emprunts){
                if(E.getDateRetour()!=null && E.getDateRetour()!=LocalDate.of(1,1,1)) count++; //emprunt ne compte que s'il n'est pas rendu
            }
            if(count < maxBook) possible=true;
		}  catch (DaoException e1) {
			System.out.println(e1.getMessage());			
		} 
		return possible;
    }
}
