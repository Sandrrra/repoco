package model;

//attributs en private

public class Couleur {
	private int couleurId;
	private String couleurNom;
	private String couleurDescription;

	// ( ENCAPSULATION ) protege les attributs en utilisant des privates et des protecteds dans une classe ou le package
	// Getters and Setters ont comme fonction de recuperer et de les modifier
	// pas de constructeur car java en crée un par défaut


	public int getCouleurId() {
		return couleurId;
	}

	public void setCouleurId(int couleurId) {
		this.couleurId = couleurId;
	}

	public String getCouleurNom() {
		return couleurNom;
	}

	public void setCouleurNom(String couleurNom) {
		this.couleurNom = couleurNom;
	}

	public String getCouleurDescription() {
		return couleurDescription;
	}

	public void setCouleurDescription(String couleurDescription) {
		this.couleurDescription = couleurDescription;
	}

	@Override

	// String toString nous sert à faire  des testes 

	public String toString() {
		return "Couleur [couleurId=" + couleurId + ", couleurNom=" + couleurNom + ", couleurDescription="
				+ couleurDescription + "]";
	}


}