package mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import javafx.beans.property.SimpleStringProperty;
import msdatabaseutils.ICategorizer;

@Entity
@Table(name = "Vehicules")
public class Vehicule implements ICategorizer {
	private int id;
	private SimpleStringProperty type = new SimpleStringProperty();

	@Id
	@Column(name = "ID_VEHICULE")
	@GeneratedValue(generator = "idVehiculeGenerator", strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idVehiculeGenerator", strategy = "increment")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "TYPE_VEHICULE")
	public String getType() {
		return type.getValue();
	}

	public void setType(String type) {
		this.type.setValue(type);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vehicule)
			return this.getId() == (((Vehicule) o).getId());

		return false;
	}

	@Override
	public String toString() {
		return this.getType();
	}

	@Override
	public String getDesignation() {
		return this.getType();
	}

	@Override
	public void setDesignation(String designation) {
		this.setType(designation);
	}

	@Override
	public SimpleStringProperty designationProperty() {
		return this.type;
	}
}
