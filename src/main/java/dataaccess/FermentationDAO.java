package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Fermentation;


//Affiche la liste de tous les produits avec ses attributs 

public class FermentationDAO {
	public static List<Fermentation> getAllFermentation() throws SQLException {
		return getFermentation(Optional.empty(), Optional.empty());
	}

	// recupere le produit en fonction de son id 

	public static Fermentation getFermentationByFermentationId(Integer fermentationId) throws SQLException {
		Optional<String> whereClause = Optional.ofNullable("where fermentation.fermentation_id = ?");
		return getFermentation(whereClause, Optional.ofNullable(fermentationId))
				.stream()
				.findAny()
				.get();
	}

	private static List<Fermentation> getFermentation(Optional<String> whereClause, Optional<Integer> id)
			throws SQLException {
		List<Fermentation> fermentationList = new ArrayList<>();

		String q = "Select fermentation_id, fermentation_nom, fermentation_description "
				+ "FROM fermentation ";

		if (whereClause.isPresent()) {
			q += whereClause.get();
		}

		// essaye avec les ressources PreparedStatement implements AutoCloseable ( la connection se ferme tout seul )
		// ConnectionFactory est une usine qui fournit une connexion.
		// PreparedStatement est plus sûr qu'une déclaration normale (pas d'injection SQL).
		// pour envoyer la requête à la base de données.

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			if (id.isPresent()) {
				p.setInt(1, id.get());
			}

			// exécute la requête et obtient des données 

			try (ResultSet rs = p.executeQuery()) {

				// Stocke les données dans un objet 

				while (rs.next()) {
					Fermentation fermentation = new Fermentation();

					fermentation.setFermentationId(rs.getInt("fermentation_id"));
					fermentation.setFermentationNom(rs.getString("fermentation_nom"));
					fermentation.setFermentationDescription(rs.getString("fermentation_description"));

					fermentationList.add(fermentation);
				}
			}
		}

		return fermentationList;
	}

	// insere une nouvelle ligne dans la table fermentation

	public static void insertFermentation(Fermentation fermentation) throws SQLException {
		String q = "insert fermentation values(null,?,?)";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {

			p.setString(1, fermentation.getFermentationNom());
			p.setString(2, fermentation.getFermentationDescription());

			int affectedRows = p.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating fermentation failed, no rows affected.");
			}

			try (ResultSet generatedKeys = p.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					fermentation.setFermentationId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating fermentation failed, no ID obtained.");
				}
			}
		}
	}

	// efface une ligne de la table fermentation grace à son id 

	public void deleteFermentationById(int fermentationId) throws SQLException {
		String q = "Delete from fermentation where fermentation_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, fermentationId);
			p.execute();
		}
	}

	// fait une mise à jour d'une ligne grace à son id  

	public static void updateFermentation(Fermentation fermentation) throws SQLException {
		String q = "update fermentation set fermentation_nom = ?,"
				+ "fermentation_description=? "
				+ "where fermentation_id = ?";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setString(1, fermentation.getFermentationNom());
			p.setString(2, fermentation.getFermentationDescription());
			p.setInt(3, fermentation.getFermentationId());

			p.execute();
		}
	}
	// Affiche tous les element de la table bierefermentation

	public static List<Fermentation> getBiereFermentation(int biereId) throws SQLException {
		List<Fermentation> fermentationList = new ArrayList<>();
		String q = "select p.fermentation_id, p.fermentation_nom, p.fermentation_description, "
				+ "(select count(*) from bierefermentation jp where jp.biere_id=? and p.fermentation_id=jp.fermentation_id) as checked "
				+ "from fermentation p";

		try (Connection connection = ConnectionFactory.getInstance().getConnection();
				PreparedStatement p = connection.prepareStatement(q)) {
			p.setInt(1, biereId);
			try (ResultSet rs = p.executeQuery()) {

				// répete les  résultats

				while (rs.next()) {
					Fermentation fermentation = new Fermentation();

					fermentation.setFermentationId(rs.getInt("fermentation_Id"));
					fermentation.setFermentationNom(rs.getString("fermentation_nom"));
					fermentation.setFermentationDescription(rs.getString("fermentation_description"));
					fermentation.setChecked(rs.getBoolean("checked"));

					fermentationList.add(fermentation);
				}
			}
		}

		// renvoye la liste fermentation

		return fermentationList;
	}
}
