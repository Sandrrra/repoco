package routing;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dataaccess.CouleurDAO;
import dataaccess.BiereDAO;
import dataaccess.FermentationDAO;

import model.Biere;

/**
 * Servlet implementation class BeerPage
 */


// HERITAGE ( extends ) la classe BierePage herite de la classe HTTPServlet
public class BierePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BierePage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// recuoere l'id du produit ( pour le modifier )
		// sinon crée un nouveau produit

		try {

			String biereId = request.getParameter("biereId");


			// si la biere id n'existe pas, on crée un nouveau objet biére
			// sinon il récupere l'id et va recuperer les informations de la biére et les met dans un objet
			// il récupere toutes les informations, le met dans une session et va sur la page JSP 
			// en cas d'erreur il nous dirige sur la page erreur 
			Biere biere = null;
			if (biereId == null) {
				biere = new Biere();
			} else {
				biere = BiereDAO.getBiereById(Integer.parseInt(biereId));
			}
			request.setAttribute("couleurs", CouleurDAO.getAllCouleur());

			HttpSession session = request.getSession();
			session.setAttribute("biere", biere);
			session.setAttribute("fermentations", FermentationDAO.getBiereFermentation(biere.getBiereId()));

			getServletContext().getRequestDispatcher("/WEB-INF/bierepage.jsp").forward(request, response);
		} catch (Exception e) {
			getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "";

		HttpSession session = request.getSession();
		Biere biere = (Biere) session.getAttribute("biere");

		biere.setBiereNom(request.getParameter("nom"));
		biere.setBiereDescription(request.getParameter("description"));

		biere.setPays(request.getParameter("pays"));
		biere.setGout(request.getParameter("gout"));
		int genreId = Integer.parseInt(request.getParameter("couleurs"));
		biere.setCouleurId(genreId);

		try {
			double degree = Double.parseDouble(request.getParameter("degree"));
			biere.setDegree(degree);
		} catch (NumberFormatException e) {
			message = "Please enter a valid degree";
		}
		try {
			double prix = Double.parseDouble(request.getParameter("prix"));
			biere.setPrix(prix);
		} catch (NumberFormatException e) {
			message = "Please enter a valid price";
		}

		try {

			LocalDate dateCommerce = LocalDate.parse(request.getParameter("dateCommerce"));
			Date date = Date.valueOf(dateCommerce);
			biere.setDateCommerce(date);
		} catch (java.time.format.DateTimeParseException e) {
			message = "Please enter a date in yyyy-mm-dd format";
		}

		BiereDAO dao = new BiereDAO();

		if (biere.getBiereNom().isBlank() ||
				biere.getBiereDescription().isBlank()) {
			message = "You must fill in all fields.";
		} else {
			try {
				if (biere.getBiereId() == 0) {
					dao.insertBiere(biere);
					message = "Biere added.";
				} else {
					dao.updateBiere(biere);
					message = "Biere updated.";
				}

				String[] fermentations = request.getParameterValues("fermentations");
				dao.clearFermentation(biere.getBiereId());
				if (fermentations != null) {
					dao.updateFermentation(biere.getBiereId(), fermentations);
				}
				session.setAttribute("fermentations", FermentationDAO.getBiereFermentation(biere.getBiereId()));
			} catch (SQLException e) {
				message = "There was an error.";
			}
		}
		session.setAttribute("biere", biere);

		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher("/WEB-INF/bierepage.jsp").forward(request, response);
	}
}
