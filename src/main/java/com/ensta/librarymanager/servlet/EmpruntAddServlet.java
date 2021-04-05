package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.model.Livre;
import com.ensta.librarymanager.model.Membre;
import com.ensta.librarymanager.service.ServiceException;


import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.MembreServiceImpl;


public class EmpruntAddServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

		List<Livre> livres = new ArrayList<Livre>();
        List<Membre> membres = new ArrayList<Membre>();
		try {
            livres = livreService.getListDispo();
            membres = membreService.getListMembreEmpruntPossible();

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("membres", membres);
        request.setAttribute("livres", livres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/emprunt_add.jsp");
		dispatcher.forward(request, response);
    }
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
        
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        int idMembre=Integer.valueOf(request.getParameter("idMembre"));
        int idLivre=Integer.valueOf(request.getParameter("idLivre"));
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        try {
            empruntService.create(idMembre, idLivre, LocalDate.now());
            emprunts = empruntService.getListCurrent();

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        request.setAttribute("emprunts", emprunts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/emprunt_list.jsp");
		dispatcher.forward(request, response);
	}
}