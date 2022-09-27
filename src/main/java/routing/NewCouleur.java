package routing;

import java.io.IOException;
import java.sql.SQLException;

import dataaccess.CouleurDAO;
import dataaccess.FermentationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Couleur;

/**
 * Servlet implementation class NewGenre
 */

//HERITAGE ( extends ) la classe NewCouleur herite de la classe HTTPServlet

public class NewCouleur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewCouleur() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "";
		String couleurId = request.getParameter("couleurId");
		Couleur couleur = null;

		if (couleurId == null) {
			couleur = new Couleur();
		} else {
			try {
				couleur = CouleurDAO.getCouleurById(Integer.parseInt(couleurId));
			} catch (Exception e) {
				message = "oops";
			}
		}

		request.setAttribute("message", message);
		HttpSession session = request.getSession();
		// Put genre in the request for the next page
		
		session.setAttribute("couleur", couleur);
		getServletContext().getRequestDispatcher("/WEB-INF/newcouleur.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String message = "";
		Couleur couleur = (Couleur) session.getAttribute("couleur");
		couleur.setCouleurNom(request.getParameter("nom"));
		couleur.setCouleurDescription(request.getParameter("description"));

		try {
			if (couleur.getCouleurId() > 0) {
				// existe dejà il faut donc la mettre à jour
				CouleurDAO.updateCouleur(couleur);
				message = "Couleur updated";
			} else {
				CouleurDAO.insertCouleur(couleur);
				message = "Couleur created";
			}
		} catch (SQLException e) {
			message = "Enter a new name.";
		}

		request.setAttribute("message", message);
		session.setAttribute("couleur", couleur);
		getServletContext().getRequestDispatcher("/WEB-INF/newcouleur.jsp").forward(request, response);
	}
}
