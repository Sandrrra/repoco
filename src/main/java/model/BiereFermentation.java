package model;

//attributs en private

public class BiereFermentation {
	private int biereId;
	private int fermentationId;

	// ( ENCAPSULATION ) protege les attributs en utilisant des privates et des protecteds  dans une classe ou le package
	// Getters and Setters ont comme fonction de recuperer et de les modifier
	// pas de constructeur car java en crée un par défaut

	public int getBiereId() {
		return biereId;
	}

	public void setBiereId(int biereId) {
		this.biereId = biereId;
	}

	public int getFermentationId() {
		return fermentationId;
	}

	public void setFermentationId(int fermentationId) {
		this.fermentationId = fermentationId;
	}

	@Override

	// String toString nous sert à faire des tests 

	public String toString() {
		return "BiereFermentation [biereId=" + biereId + ", fermentationId=" + fermentationId + "]";
	}



}