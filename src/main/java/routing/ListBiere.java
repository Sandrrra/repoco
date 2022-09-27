package routing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dataaccess.CouleurDAO;
import dataaccess.BiereDAO;
import dataaccess.FermentationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Biere;

/**
 * Servlet implementation class ListJeux
 */

//HERITAGE ( extends ) la classe ListBiere herite de la classe HTTPServlet
public class ListBiere extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListBiere() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer fermentationId = null;
		Integer couleurId = null;
		String page = "/WEB-INF/showbieres.jsp";
		

		try {
			Optional<String> couleur = Optional.ofNullable(request.getParameter("couleur"));
			Optional<String> fermentation = Optional.ofNullable(request.getParameter("fermentation"));

			if (couleur.isEmpty()) {
				couleurId = 0;
			} else if (couleur.get().equals("all")) {
				couleurId = 0;
			} else if (couleur.get().equals("new")) {
				page = "/NewCouleur";
				getServletContext().getRequestDispatcher(page).forward(request, response);
				return;
			} else {
				couleurId = Integer.parseInt(couleur.get());
			}

			if (fermentation.isEmpty()) {
				fermentationId = 0;
			} else if (fermentation.get().equals("all")) {
				fermentationId = 0;
			} else if (fermentation.get().equals("new")) {
				// TODO Add this servlet
				page = "/NewFermentation";
				getServletContext().getRequestDispatcher(page).forward(request, response);
				return;
			} else {
				fermentationId = Integer.parseInt(fermentation.get());
			}

			List<Biere> bieres;
			if (couleurId == 0 && fermentationId == 0) {
				bieres = BiereDAO.getAllBieres();
			} else {
				if (couleurId == 0) {
					// Selection by Fermentation only
					bieres = BiereDAO.getBiereByFermentationId(fermentationId);
				} else if (fermentationId == 0) {
					// Selection by couleur only
					bieres = BiereDAO.getBiereByCouleurId(couleurId);
				} else {
					// Selection by couleur and fermentation
					bieres = BiereDAO.getBiereByCouleurAndFermentation(couleurId, fermentationId);
				}
			}
			// Données pour la page suivante
			
			request.setAttribute("biere", bieres);
			request.setAttribute("selected", couleurId);
			request.setAttribute("selectedPlateforme", fermentationId);

			HttpSession session = request.getSession();
			session.setAttribute("couleur", CouleurDAO.getAllCouleur());
			session.setAttribute("fermentation", FermentationDAO.getAllFermentation());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher(page).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
