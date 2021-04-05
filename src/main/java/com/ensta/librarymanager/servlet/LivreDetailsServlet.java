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


public class LivreDetailsServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
            int idL =Integer.valueOf(request.getParameter("idLivre"));
            
            emprunts = empruntService.getListCurrentByLivre(idL);

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("emprunts", emprunts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_details.jsp");
		dispatcher.forward(request, response);
    }
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<Livre>();
        
        try {
            int id =Integer.valueOf(request.getParameter("id"));
            String titre =String.valueOf(request.getParameter("titre"));
            String auteur =String.valueOf(request.getParameter("auteur"));
            String isbn =String.valueOf(request.getParameter("isbn"));
            Livre livre=new Livre(id,titre, auteur, isbn);

            livreService.update(livre);

            livres = livreService.getList();

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        request.setAttribute("livres", livres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_list.jsp");
		dispatcher.forward(request, response);
	}
}

