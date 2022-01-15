package mssmfactory.KafylElYatim.DomainModel.TutorsAdministration;

import java.time.LocalDate;

import javax.persistence.Basic;
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

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "Enquetes")
public class Enquete {

	private int id;
	private SimpleStringProperty nomEnqueteur = new SimpleStringProperty(),
			prenomEnqueteur = new SimpleStringProperty();
	private SimpleObjectProperty<LocalDate> dateEnquete = new SimpleObjectProperty<>();
	private SituationSociale situationSociale;

	@Id
	@GeneratedValue(generator = "enqueteIdGenerator", strategy = GenerationType.AUTO)
	@GenericGenerator(name = "enqueteIdGenerator", strategy = "increment")
	@Column(name = "ID_ENQUETE")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "NOM_ENQUETEUR")
	public String getNomEnqueteur() {
		return nomEnqueteur.getValue();
	}

	public void setNomEnqueteur(String nomEnqueteur) {
		this.nomEnqueteur.setValue(nomEnqueteur);
	}

	@Basic
	@Column(name = "PRENOM_ENQUETEUR")
	public String getPrenomEnqueteur() {
		return prenomEnqueteur.getValue();
	}

	public void setPrenomEnqueteur(String prenomEnqueteur) {
		this.prenomEnqueteur.setValue(prenomEnqueteur);
	}

	@Basic
	@Column(name = "DATE_ENQUETE")
	public LocalDate getDateEnquete() {
		return dateEnquete.getValue();
	}

	public void setDateEnquete(LocalDate dateEnquete) {
		this.dateEnquete.setValue(dateEnquete);
	}

	@Basic
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SITUATION_SOCIALE")
	public SituationSociale getSituationSociale() {
		return situationSociale;
	}

	public void setSituationSociale(SituationSociale situationSociale) {
		this.situationSociale = situationSociale;
	}
}
