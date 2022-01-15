package mssmfactory.KafylElYatim.DomainModel.OrphensAdministration;

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

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "DossiersScolaires")
public class DossierScolaire {

	private int id;
	private SimpleBooleanProperty scolarise = new SimpleBooleanProperty();
	private SimpleIntegerProperty anneeScolaire = new SimpleIntegerProperty();
	private SimpleObjectProperty<NiveauScolaire> niveauScolaire = new SimpleObjectProperty<>();
	private SimpleObjectProperty<LocalDate> ddMaj = new SimpleObjectProperty<>();
	private SimpleStringProperty observation = new SimpleStringProperty();
	private Orphelin orphelin;

	@Id
	@Column(name = "ID_DOSSIER_SCOLAIRE")
	@GeneratedValue(generator = "idDossierScolaireGenerator", strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idDossierScolaireGenerator", strategy = "increment")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "NIVEAU_SCOLAIRE")
	public NiveauScolaire getNiveauScolaire() {
		return niveauScolaire.getValue();
	}

	public void setNiveauScolaire(NiveauScolaire niveauScolaire) {
		this.niveauScolaire.setValue(niveauScolaire);
	}

	@Basic
	@Column(name = "ANNEE_SCOLAIRE")
	public int getAnneeScolaire() {
		return anneeScolaire.getValue();
	}

	public void setAnneeScolaire(int anneeScolaire) {
		this.anneeScolaire.setValue(anneeScolaire);
	}

	@Basic
	@Column(name = "DDMAJ")
	public LocalDate getDdMaj() {
		return ddMaj.getValue();
	}

	public void setDdMaj(LocalDate ddMaj) {
		this.ddMaj.setValue(ddMaj);
	}

	@Basic
	@Column(name = "SCOLARISE")
	public boolean isScolarise() {
		return scolarise.getValue();
	}

	public void setScolarise(boolean scolarise) {
		this.scolarise.setValue(scolarise);
	}

	@Basic
	@Column(name = "OBSERVATION")
	public String getObservation() {
		return observation.getValue();
	}

	public void setObservation(String observation) {
		this.observation.setValue(observation);
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ORPHELIN")
	public Orphelin getOrphelin() {
		return orphelin;
	}

	public void setOrphelin(Orphelin orphelin) {
		this.orphelin = orphelin;
	}

	public static enum NiveauScolaire {
		Sans_scolarite, Pres_scolaire, Primaire, Moyen, Secondaire, Universitaire, Centre_de_formation;

		@Override
		public String toString() {
			return super.toString().replaceAll("_", " ");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DossierScolaire)
			return this.getId() == (((DossierScolaire) o).getId());
		return false;
	}
}
