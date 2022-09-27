package model;

//attributs en private

public class Fermentation {
	private int fermentationId;
	private String fermentationNom;
	private String fermentationDescription;
	private boolean checked;

	// ( ENCAPSULATION ) protege les attributs en utilisant des privates et des protecteds dans une classe ou le package
	// Getters and Setters ont comme fonction de recuperer et de les modifier
	// pas de constructeur car java en crée un par défaut

	public int getFermentationId() {
		return fermentationId;
	}

	public void setFermentationId(int fermentationId) {
		this.fermentationId = fermentationId;
	}

	public String getFermentationNom() {
		return fermentationNom;
	}

	public void setFermentationNom(String nom) {
		this.fermentationNom = nom;
	}

	public String getFermentationDescription() {
		return fermentationDescription;
	}

	public void setFermentationDescription(String fermentationDescription) {
		this.fermentationDescription = fermentationDescription;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override

	// String toString nous sert à faire des tests comme le Sysout

	public String toString() {
		return "Fermentation [fermentationId=" + fermentationId + ", fermentationNom=" + fermentationNom
				+ ", fermentationDescription=" + fermentationDescription + ", checked=" + checked + "]";
	}


}