package mssmfactory.KafylElYatim.DomainModel.TutorsAdministration;

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
@Table(name = "Habitats")
public class Habitat {

	private int id;
	private SimpleObjectProperty<EtatHabitat> etat = new SimpleObjectProperty<>();
	private SimpleObjectProperty<TypeBien> typeBien = new SimpleObjectProperty<>();
	private SimpleStringProperty classementHabitat = new SimpleStringProperty();
	private Tuteur tuteur;

	@Id
	@GeneratedValue(generator = "habitatIdGenerator", strategy = GenerationType.AUTO)
	@GenericGenerator(name = "habitatIdGenerator", strategy = "increment")
	@Column(name = "ID_HABITAT")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "ETAT_HABITAT")
	public EtatHabitat getEtat() {
		return etat.getValue();
	}

	public void setEtat(EtatHabitat etat) {
		this.etat.setValue(etat);
	}

	@Basic
	@Column(name = "TYPE_BIEN")
	public TypeBien getTypeBien() {
		return typeBien.get();
	}

	public void setTypeBien(TypeBien typeBien) {
		this.typeBien.setValue(typeBien);
	}

	@Basic
	@Column(name = "CLASSEMENT_HABITAT")
	public String getClassementHabitat() {
		return classementHabitat.getValue();
	}

	public void setClassementHabitat(String classementHabitat) {
		this.classementHabitat.setValue(classementHabitat);
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

	public static enum EtatHabitat {
		Bon, Moyen, Mauvais
	}

	public static enum TypeBien {
		Proprietaire, Locataire, Heberge, Heritage, Illicite
	}
}
