package mssmfactory.KafylElYatim.DomainModel.Others;

import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.EvenementBon;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin;

public class OrphelinEvenementBonDTO {

	private String nom, prenom, genre;
	private Integer age;
	private Double cout;

	public OrphelinEvenementBonDTO() {
	}

	public OrphelinEvenementBonDTO(EvenementBon evenementBon, Orphelin orphelin) {
		this.nom = orphelin.getNom();
		this.prenom = orphelin.getPrenom();
		this.genre = orphelin.getGenre().name().charAt(0) + "";
		this.age = orphelin.getAge();
		this.cout = evenementBon.getCoutBon();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Double getCout() {
		return cout;
	}

	public void setCout(Double cout) {
		this.cout = cout;
	}
}
