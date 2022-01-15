package mssmfactory.KafylElYatim.DomainModel.EventsAdministration;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "EVENEMENTS_BONS")
public class EvenementBon extends Evenement {
	private SimpleDoubleProperty coutBon = new SimpleDoubleProperty();
	private SimpleStringProperty etablissementValidation = new SimpleStringProperty();

	@Basic
	@Column(name = "ETABLISSEMENT_VALIDATION")
	public String getEtablissementValidation() {
		return etablissementValidation.getValue();
	}

	public void setEtablissementValidation(String etablissementValidation) {
		this.etablissementValidation.setValue(etablissementValidation);
	}

	@Basic
	@Column(name = "COUT_BON")
	public double getCoutBon() {
		return coutBon.getValue();
	}

	public void setCoutBon(double coutBon) {
		this.coutBon.setValue(coutBon);
	}
}
