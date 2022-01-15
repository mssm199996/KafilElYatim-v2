package mssmfactory.KafylElYatim.DomainModel.Others;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javafx.beans.property.SimpleStringProperty;
import msdatabaseutils.OperationType;

@Entity
@Table(name = "OPERATIONS")
public class ApplicationOperation extends OperationType {

	private Compte operateur;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_OPERATEUR")
	public Compte getOperateur() {
		return operateur;
	}

	public void setOperateur(Compte operateur) {
		this.operateur = operateur;
	}

	@Override
	public SimpleStringProperty designationOperateurProperty() {
		if (this.operateur == null || this.operateur.getUsername() == null)
			return new SimpleStringProperty("");

		return this.operateur.usernameProperty();
	}
}
