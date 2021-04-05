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

import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreServiceImpl;
import com.ensta.librarymanager.service.MembreServiceImpl;
import com.ensta.librarymanager.service.ServiceException;



public class LivreListServlet extends HttpServlet {
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();

		List<Livre> livres = new ArrayList<Livre>();
		try {
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