package dataaccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    // attributs 

public class ConnectionFactory {
	private static String dbUrl;
	private static String userName;
	private static String password;
	private static ConnectionFactory connectionFactory = null;
	

	// récupere les informations ( login, password et l'adresse url ) de connection dans le fichier texte ( dbConnection.txt )
	//et les insere dans les variables ci-dessus
	 
	private ConnectionFactory() {
		URL url = getClass().getResource("/dbConnection.txt");
		try (InputStream inputStream = url.openStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			int index = 0;
			while (reader.ready()) {
				String line = reader.readLine();
				switch (index++) {
				case 0 -> dbUrl = line;
				case 1 -> userName = line;
				case 2 -> password = line;
				}
			}
			
			// attrape les erreurs et les affiche
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// chargement du driver jdbc qui sert à faire la connection avec la base de données  
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
       
	// on envoye les informations de connection au driver qui se connecte à la base de données
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbUrl, userName, password);
	}
    
	// si la connection est vide on crée une nouvelle 
	
	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}
}