package mssmfactory.KafylElYatim.DomainModel.DonationsAdministration;

import java.time.LocalDate;

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

import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import msjfxuicomponents.others.IDescriptor;

@Entity
@Table(name = "Donations")
public class Donation implements IDescriptor {
	private int id;
	private SimpleObjectProperty<DonationType> type = new SimpleObjectProperty<>();
	private SimpleObjectProperty<DonationForm> forme = new SimpleObjectProperty<>();
	private SimpleStringProperty valeur = new SimpleStringProperty();
	private SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
	private SimpleStringProperty observation = new SimpleStringProperty();
	private Donateur donateur;

	@Id
	@Column(name = "ID_DONATION")
	@GenericGenerator(name = "donationIdGenerator", strategy = "increment")
	@GeneratedValue(generator = "donationIdGenerator", strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "TYPE_DONATION")
	public DonationType getType() {
		return type.getValue();
	}

	public void setType(DonationType type) {
		this.type.setValue(type);
	}

	public SimpleObjectProperty<DonationType> donationTypeProperty() {
		return this.type;
	}

	@Basic
	@Column(name = "VALEUR_DONATION")
	public String getValeur() {
		return valeur.getValue();
	}

	public void setValeur(String valeur) {
		this.valeur.setValue(valeur);
	}

	public SimpleStringProperty valeurProperty() {
		return this.valeur;
	}

	@Basic
	@Column(name = "DATE_DONATION")
	public LocalDate getDate() {
		return date.getValue();
	}

	public void setDate(LocalDate date) {
		this.date.setValue(date);
	}

	public SimpleObjectProperty<LocalDate> dateProperty() {
		return this.date;
	}

	@Basic
	@Column(name = "FORME_DONATION")
	public DonationForm getForme() {
		return forme.getValue();
	}

	public void setForme(DonationForm forme) {
		this.forme.setValue(forme);
	}

	public SimpleObjectProperty<DonationForm> donationFormProperty() {
		return this.forme;
	}

	@Basic
	@Column(name = "OBSERVATION_DONATION")
	public String getObservation() {
		return observation.getValue();
	}

	public void setObservation(String observation) {
		this.observation.setValue(observation);
	}

	@Basic
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DONATEUR")
	public Donateur getDonator() {
		return donateur;
	}

	public void setDonator(Donateur donateur) {
		this.donateur = donateur;
	}

	public StringExpression nomDonateurProperty() {
		if (this.donateur != null)
			return this.donateur.nomProperty().concat(this.donateur.prenomProperty());

		return new SimpleStringProperty("");
	}

	public static enum DonationType {
		Zakat, Sadaka,
	}

	public static enum DonationForm {
		Espece, Cheque, En_nature, Virement, Versement_bancaire;

		@Override
		public String toString() {
			return super.toString().replaceAll("_", " ");
		}
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Donation && (this.getId() == ((Donation) o).getId());
	}

	@Override
	public String getDescription() {
		return this.getObservation();
	}

	@Override
	public void setDescription(String description) {
		this.setObservation(description);
	}
}
