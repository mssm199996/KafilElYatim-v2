package mssmfactory.KafylElYatim.DomainModel.TutorsAdministration;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "SituationsSociales")
public class SituationSociale {
	private int id;
	private SimpleObjectProperty<NiveauVie> niveauVie = new SimpleObjectProperty<>();
	private SimpleStringProperty fonction = new SimpleStringProperty(), niveauEtude = new SimpleStringProperty(),
			competances = new SimpleStringProperty();
	private SimpleDoubleProperty retraite = new SimpleDoubleProperty(), convertureSociale = new SimpleDoubleProperty(),
			salaire = new SimpleDoubleProperty();
	private Enquete enquete;
	private Tuteur tuteur;

	@Id
	@GeneratedValue(generator = "situationSocialeIdGenerator", strategy = GenerationType.AUTO)
	@GenericGenerator(name = "situationSocialeIdGenerator", strategy = "increment")
	@Column(name = "ID_SITUATION_SOCIALE")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "NIVEAU_VIE")
	public NiveauVie getNiveauVie() {
		return niveauVie.getValue();
	}

	public void setNiveauVie(NiveauVie niveauVie) {
		this.niveauVie.setValue(niveauVie);
	}

	@Basic
	@Column(name = "FONCTION")
	public String getFonction() {
		return fonction.getValue();
	}

	public void setFonction(String fonction) {
		this.fonction.setValue(fonction);
	}

	@Basic
	@Column(name = "RETRAIRE")
	public double getRetraite() {
		return retraite.getValue();
	}

	public void setRetraite(double retraite) {
		this.retraite.setValue(retraite);
	}

	@Basic
	@Column(name = "CONVERTURE_SOCIALE")
	public double getConvertureSociale() {
		return convertureSociale.getValue();
	}

	public void setConvertureSociale(double convertureSociale) {
		this.convertureSociale.setValue(convertureSociale);
	}

	@Basic
	@Column(name = "SALAIRE")
	public double getSalaire() {
		return salaire.getValue();
	}

	public void setSalaire(double salaire) {
		this.salaire.setValue(salaire);
	}

	@Basic
	@Column(name = "NIVEAU_ETUDE")
	public String getNiveauEtude() {
		return niveauEtude.getValue();
	}

	public void setNiveauEtude(String niveauEtude) {
		this.niveauEtude.setValue(niveauEtude);
	}

	@Basic
	@Column(name = "COMPETANCES")
	public String getCompetances() {
		return competances.getValue();
	}

	public void setCompetances(String competances) {
		this.competances.setValue(competances);
	}

	@Basic
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ENQUETE")
	public Enquete getEnquete() {
		return enquete;
	}

	public void setEnquete(Enquete enquete) {
		this.enquete = enquete;
	}

	@Basic
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TUTEUR")
	public Tuteur getTuteur() {
		return tuteur;
	}

	public void setTuteur(Tuteur tuteur) {
		this.tuteur = tuteur;
	}

	public static enum NiveauVie {
		Bon, Moyen, Critique
	}
}
