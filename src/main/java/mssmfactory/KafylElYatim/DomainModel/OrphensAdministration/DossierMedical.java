package mssmfactory.KafylElYatim.DomainModel.OrphensAdministration;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "DossiersMedicauxOrphelins")
public class DossierMedical {
	private int id;
	private SimpleStringProperty pathologie = new SimpleStringProperty(), medicaments = new SimpleStringProperty();

	private List<Appareillage> appareils = new ArrayList<Appareillage>();
	private Orphelin orphelin;

	@Id
	@Column(name = "ID_DOSSIER_MEDICAL")
	@GeneratedValue(generator = "idDossierMedicalGenerator", strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idDossierMedicalGenerator", strategy = "increment")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "PATHOLOGIE")
	public String getPathologie() {
		return pathologie.getValue();
	}

	public void setPathologie(String pathologie) {
		this.pathologie.setValue(pathologie);
	}

	@Basic
	@Column(name = "MEDICAMENTS")
	public String getMedicaments() {
		return medicaments.getValue();
	}

	public void setMedicaments(String medicaments) {
		this.medicaments.setValue(medicaments);
	}

	@Basic
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Orphelins_Appareillages", joinColumns = @JoinColumn(name = "ID_DOSSIER_MEDICAL"), inverseJoinColumns = @JoinColumn(name = "ID_APPAREILLAGE"))
	public List<Appareillage> getAppareils() {
		return appareils;
	}

	public void setAppareils(List<Appareillage> appareils) {
		this.appareils = appareils;
	}

	public void addAppareillage(Appareillage appareillage) {
		this.getAppareils().add(appareillage);
	}

	public void removeAppareillage(Appareillage appareillage) {
		this.getAppareils().remove(appareillage);
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ORPHELIN")
	public Orphelin getOrphelin() {
		return orphelin;
	}

	public void setOrphelin(Orphelin orphelin) {
		this.orphelin = orphelin;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DossierMedical)
			return this.getId() == (((DossierMedical) o).getId());
		return false;
	}
}
