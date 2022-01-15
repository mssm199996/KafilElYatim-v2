package mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration;

import java.beans.Transient;
import java.time.LocalTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import msdatabaseutils.ICategorizer;

@Entity
@Table(name = "JourneesDisponibles")
public class JourneeDisponible implements ICategorizer {
	private int id;
	private SimpleObjectProperty<JourDeSemaine> jourSemaine = new SimpleObjectProperty<>();
	private SimpleObjectProperty<LocalTime> heureDebut = new SimpleObjectProperty<>();
	private SimpleObjectProperty<LocalTime> heureFin = new SimpleObjectProperty<>();
	private Benevole benevole;

	@Id
	@Column(name = "ID_JOURNEE_DISPONIBLE")
	@GenericGenerator(name = "journeeDisponibleIdGenerator", strategy = "increment")
	@GeneratedValue(generator = "journeeDisponibleIdGenerator", strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "JOUR_SEMAINE")
	public JourDeSemaine getJourSemaine() {
		return jourSemaine.getValue();
	}

	public void setJourSemaine(JourDeSemaine jourSemaine) {
		this.jourSemaine.setValue(jourSemaine);
	}

	@Basic
	@Column(name = "HEURE_DEBUT")
	public LocalTime getHeureDebut() {
		return heureDebut.getValue();
	}

	public void setHeureDebut(LocalTime heureDebut) {
		this.heureDebut.setValue(heureDebut);
	}

	@Basic
	@Column(name = "HEURE_FIN")
	public LocalTime getHeureFin() {
		return heureFin.getValue();
	}

	public void setHeureFin(LocalTime heureFin) {
		this.heureFin.setValue(heureFin);
	}

	@Basic
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BENEVOLE")
	public Benevole getBenevole() {
		return benevole;
	}

	public void setBenevole(Benevole benevole) {
		this.benevole = benevole;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof JourneeDisponible)
			return this.getId() == (((JourneeDisponible) o).getId());
		return false;
	}

	@Override
	public String toString() {
		if (this.getHeureDebut() != null && this.getHeureFin() != null)
			return "Le " + this.getJourSemaine() + ", de: " + this.getHeureDebut() + " à " + this.getHeureFin();
		else if (this.getHeureDebut() != null)
			return "Le " + this.getJourSemaine() + " à partir de: " + this.getHeureDebut();
		else if (this.getHeureFin() != null)
			return "Le " + this.getJourSemaine() + ", jusqu'à: " + this.getHeureFin();
		else
			return "Le " + this.getJourSemaine();
	}

	public static enum JourDeSemaine {
		Samedi, Dimanche, Lundi, Mardi, Mercredi, Jeudi, Vendredi
	}

	@Override
	@Transient
	public String getDesignation() {
		return this.toString();
	}

	@Override
	public void setDesignation(String designation) {
	}

	@Override
	public SimpleStringProperty designationProperty() {
		return new SimpleStringProperty(this.toString());
	}
}
