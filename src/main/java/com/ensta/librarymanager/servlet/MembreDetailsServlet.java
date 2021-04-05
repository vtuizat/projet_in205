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


public class MembreDetailsServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try {
            int idL =Integer.valueOf(request.getParameter("idMembre"));
            
            emprunts = empruntService.getListCurrentByMembre(idL);

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("emprunts", emprunts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_details.jsp");
		dispatcher.forward(request, response);
    }
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

        MembreServiceImpl membreService = MembreServiceImpl.getInstance();
        List<Membre> membres = new ArrayList<Membre>();
        
        try {
            int id =Integer.valueOf(request.getParameter("id"));
            String nom =String.valueOf(request.getParameter("nom"));
            String prenom =String.valueOf(request.getParameter("prenom"));
            String adresse =String.valueOf(request.getParameter("adresse"));
            String email =String.valueOf(request.getParameter("email"));
            String telephone =String.valueOf(request.getParameter("telephone"));
            Membre membre=new Membre(id,nom,prenom,adresse,email,telephone);
            membreService.update(membre);

            membres = membreService.getList();

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
        request.setAttribute("membres", membres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_list.jsp");
		dispatcher.forward(request, response);
	}
}

