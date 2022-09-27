package model;

public class Biere {

	// attributs en private

	private int biereId;
	private String biereNom;
	private String biereDescription;
	private double prix;
	private java.sql.Date dateCommerce;
	private String pays;
	private Double degree;
	private String gout;
	private int couleurId;
	private String couleurNom;
	private int fermentationId;
	private String fermentationNom;


	// ( ENCAPSULATION ) protege les attributs en utilisant des privates et des protecteds dans une classe ou le package
	// Getters and Setters ont comme fonction de recuperer et de les modifier
	// pas de constructeur car java en crée un par défaut

	public int getBiereId() {
		return biereId;
	}

	public void setBiereId(int biereId) {
		this.biereId = biereId;
	}

	public String getBiereNom() {
		return biereNom;
	}

	public void setBiereNom(String biereNom) {
		this.biereNom = biereNom;
	}

	public String getBiereDescription() {
		return biereDescription;
	}

	public void setBiereDescription(String biereDescription) {
		this.biereDescription = biereDescription;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public java.sql.Date getDateCommerce() {
		return dateCommerce;
	}

	public void setDateCommerce(java.sql.Date dateCommerce) {
		this.dateCommerce = dateCommerce;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public Double getDegree() {
		return degree;
	}

	public void setDegree(Double degree) {
		this.degree = degree;
	}

	public String getGout() {
		return gout;
	}

	public void setGout(String gout) {
		this.gout = gout;
	}

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

	public String getFermentationNom() {
		return fermentationNom;
	}

	public void setFermentationNom(String fermentationNom) {
		this.fermentationNom = fermentationNom;
	}

	public int getFermentationId() {
		return fermentationId;
	}

	public void setFermentationId(int fermentationId) {
		this.fermentationId = fermentationId;
	}

	@Override

	// String toString nous sert à faire des tests comme le Sysout

	public String toString() {
		return "Biere [biereId=" + biereId + ", biereNom=" + biereNom + ", biereDescription=" + biereDescription
				+ ", prix=" + prix + ", dateCommerce=" + dateCommerce + ", pays=" + pays + ", degree=" + degree
				+ ", gout=" + gout + ", couleurId=" + couleurId + ", couleurNom=" + couleurNom + ", fermentationId="
				+ fermentationId + ", fermentationNom=" + fermentationNom + "]";
	}



}