package mssmfactory.KafylElYatim.DomainModel.OrphensAdministration;

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
@Table(name = "DossiersFamilliaux")
public class DossierFamilial {

	private int id;
	private SimpleObjectProperty<TypeOrphelinat> typeOrphelinat = new SimpleObjectProperty<>();
	private SimpleStringProperty nomPere = new SimpleStringProperty(), prenomPere = new SimpleStringProperty(),
			nomMere = new SimpleStringProperty(), prenomMere = new SimpleStringProperty(),
			situationFamilliale = new SimpleStringProperty();
	private Orphelin orphelin;

	@Id
	@Column(name = "ID_DOSSIER_FAMILIAL")
	@GeneratedValue(generator = "idDossierFamilialGenerator", strategy = GenerationType.AUTO)
	@GenericGenerator(name = "idDossierFamilialGenerator", strategy = "increment")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "TYPE_ORPHELINAT")
	public TypeOrphelinat getTypeOrphelinat() {
		return typeOrphelinat.getValue();
	}

	public void setTypeOrphelinat(TypeOrphelinat typeOrphelinat) {
		this.typeOrphelinat.setValue(typeOrphelinat);
	}

	@Basic
	@Column(name = "NOM_PERE")
	public String getNomPere() {
		return nomPere.getValue();
	}

	public void setNomPere(String nomPere) {
		this.nomPere.setValue(nomPere);
	}

	@Basic
	@Column(name = "PRENOM_PERE")
	public String getPrenomPere() {
		return prenomPere.getValue();
	}

	public void setPrenomPere(String prenomPere) {
		this.prenomPere.setValue(prenomPere);
	}

	@Basic
	@Column(name = "NOM_MERE")
	public String getNomMere() {
		return nomMere.getValue();
	}

	public void setNomMere(String nomMere) {
		this.nomMere.setValue(nomMere);
	}

	@Basic
	@Column(name = "PRENOM_MERE")
	public String getPrenomMere() {
		return prenomMere.getValue();
	}

	public void setPrenomMere(String prenomMere) {
		this.prenomMere.setValue(prenomMere);
	}

	@Basic
	@Column(name = "SITUATION_FAMILLIALE")
	public String getSituationFamilliale() {
		return this.situationFamilliale.getValue();
	}

	public void setSituationFamilliale(String situationFamilliale) {
		this.situationFamilliale.set(situationFamilliale);
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ORPHELIN")
	public Orphelin getOrphelin() {
		return orphelin;
	}

	public void setOrphelin(Orphelin orphelin) {
		this.orphelin = orphelin;
	}

	public static enum TypeOrphelinat {
		Pere, Mere, Pere_Mere;
		
		@Override
		public String toString() {
			return super.toString().replaceAll("_", " ");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DossierFamilial)
			return this.getId() == (((DossierFamilial) o).getId());
		return false;
	}
}
