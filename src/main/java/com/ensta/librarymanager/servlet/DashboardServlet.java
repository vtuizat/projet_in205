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
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.MembreServiceImpl;
import com.ensta.librarymanager.service.ServiceException;



public class DashboardServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

		List<Emprunt> emprunts = new ArrayList<Emprunt>();
        int nbLivres=0,nbMembres=0,nbEmprunts=0;
		try {
			nbLivres = livreService.count();
            nbMembres = membreService.count();
            nbEmprunts = empruntService.count();
            emprunts = empruntService.getListCurrent();

		} catch (ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("nbLivres", nbLivres);
        request.setAttribute("nbMembres", nbMembres);
        request.setAttribute("nbEmprunts", nbEmprunts);
        request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/dashboard.jsp");
		dispatcher.forward(request, response);
    }
}
