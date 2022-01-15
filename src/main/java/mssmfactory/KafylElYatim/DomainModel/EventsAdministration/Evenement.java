package mssmfactory.KafylElYatim.DomainModel.EventsAdministration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import msdatabaseutils.ICategorizer;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Evenement implements ICategorizer {
	private int idEvenement;
	private SimpleStringProperty designationEvenement = new SimpleStringProperty();
	private SimpleStringProperty descriptionEvenement = new SimpleStringProperty();
	private SimpleObjectProperty<LocalDate> dateEvenement = new SimpleObjectProperty<>();
	private List<Action> actions = new ArrayList<Action>();

	@Id
	@Column(name = "ID_EVENEMENT")
	@GeneratedValue(generator = "evenementIdGenerator", strategy = GenerationType.AUTO)
	@GenericGenerator(name = "evenementIdGenerator", strategy = "increment")
	public int getIdEvenement() {
		return idEvenement;
	}

	public void setIdEvenement(int idEvenement) {
		this.idEvenement = idEvenement;
	}

	@Basic
	@Column(name = "DESIGNATION_EVENEMENT")
	public String getDesignationEvenement() {
		return designationEvenement.getValue();
	}

	public void setDesignationEvenement(String designationEvenement) {
		this.designationEvenement.setValue(designationEvenement);
	}

	@Basic
	@Column(name = "DESCRIPTION_EVENEMENT")
	public String getDescriptionEvenement() {
		return descriptionEvenement.getValue();
	}

	public void setDescriptionEvenement(String descriptionEvenement) {
		this.descriptionEvenement.setValue(descriptionEvenement);
	}

	@Basic
	@Column(name = "DATE_EVENEMENT")
	public LocalDate getDateEvenement() {
		return dateEvenement.getValue();
	}

	public void setDateEvenement(LocalDate dateEvenement) {
		this.dateEvenement.setValue(dateEvenement);
	}

	@Basic
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "evenement")
	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void addAction(Action action) {
		this.getActions().add(action);
		action.setEvenement(this);
	}

	public void removeAction(Action action) {
		this.getActions().remove(action);
		action.setEvenement(null);
	}

	@Override
	public String toString() {
		return this.getDesignationEvenement();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Evenement)
			return this.getIdEvenement() == (((Evenement) o).getIdEvenement());
		return false;
	}

	public String getDesignation() {
		return this.getDesignationEvenement();
	}

	public void setDesignation(String designation) {
		this.setDesignationEvenement(designation);
	}

	public SimpleStringProperty designationProperty() {
		return this.designationEvenement;
	}
}
