package mssmfactory.KafylElYatim.DomainModel.TutorsAdministration;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import msjfxuicomponents.mvc.IPrintableDocument;
import msjfxuicomponents.others.IDescriptor;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Appareillage;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin;
import mssmfactory.KafylElYatim.DomainModel.Others.IAppareillageHolder;
import mssmfactory.KafylElYatim.DomainModel.Others.IPersonne;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tuteurs")
public class Tuteur implements IAppareillageHolder, IDescriptor, IPersonne, IPrintableDocument {

    private int id;
    private SimpleIntegerProperty nbOrphelins = new SimpleIntegerProperty();
    private SimpleBooleanProperty archived = new SimpleBooleanProperty();
    private SimpleIntegerProperty nbOrphelinsNotAgedOrAuthorized = null;
    private SimpleStringProperty nom = new SimpleStringProperty(), prenom = new SimpleStringProperty(),
            nomPereOrphelins = new SimpleStringProperty(), prenomPereOrphelins = new SimpleStringProperty(),
            adresse = new SimpleStringProperty(), secondeAdresse = new SimpleStringProperty(),
            ncni = new SimpleStringProperty(), nDossierBureautique = new SimpleStringProperty(""),
            nTelFix = new SimpleStringProperty(), nTelMob = new SimpleStringProperty(),
            nTel1 = new SimpleStringProperty(), nTel2 = new SimpleStringProperty(),
            observation = new SimpleStringProperty(), nccp = new SimpleStringProperty(),
            nCarteBanquaine = new SimpleStringProperty();

    private SimpleObjectProperty<LocalDate> ddn = new SimpleObjectProperty<>();

    private SimpleObjectProperty<Region> region = new SimpleObjectProperty<Region>();
    private Habitat habitat;
    private SituationSociale situationSociale;
    private DossierMedical dossierMedical;

    private List<Orphelin> orphelins = new ArrayList<Orphelin>();

    @Id
    @GeneratedValue(generator = "tuteurIdGenerator", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "tuteurIdGenerator", strategy = "increment")
    @Column(name = "ID_TUTEUR")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NOM_TUTEUR")
    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.setValue(nom);
    }

    public SimpleStringProperty nomProperty() {
        return this.nom;
    }

    @Basic
    @Column(name = "PRENOM_TUTEUR")
    public String getPrenom() {
        return prenom.getValue();
    }

    public void setPrenom(String prenom) {
        this.prenom.setValue(prenom);
    }

    public SimpleStringProperty prenomProperty() {
        return this.prenom;
    }

    @Basic
    @Column(name = "NOM_PERE_ORPHELINS")
    public String getNomPereOrphelins() {
        return nomPereOrphelins.getValue();
    }

    public void setNomPereOrphelins(String nomPereOrphelins) {
        this.nomPereOrphelins.setValue(nomPereOrphelins);
    }

    public SimpleStringProperty nomPereOrphelinsProperty() {
        return this.nomPereOrphelins;
    }

    @Basic
    @Column(name = "PRENOM_PERE_ORPHELINS")
    public String getPrenomPereOrphelins() {
        return prenomPereOrphelins.getValue();
    }

    public void setPrenomPereOrphelins(String prenomPereOrphelins) {
        this.prenomPereOrphelins.setValue(prenomPereOrphelins);
    }

    public SimpleStringProperty prenomPereOrphelinsProperty() {
        return this.prenomPereOrphelins;
    }

    @Basic
    @Column(name = "DDN_TUTEUR")
    public LocalDate getDdn() {
        return ddn.getValue();
    }

    public void setDdn(LocalDate ddn) {
        this.ddn.setValue(ddn);
    }

    public SimpleObjectProperty<LocalDate> ddnProperty() {
        return this.ddn;
    }

    @Basic
    @Column(name = "ADRESSE_TUTEUR")
    public String getAdresse() {
        return adresse.get();
    }

    public void setAdresse(String adresse) {
        this.adresse.setValue(adresse);
    }

    public SimpleStringProperty adresseProperty() {
        return this.adresse;
    }

    @Basic
    @Column(name = "SECONDE_ADRESSE_TUTEUR")
    public String getSecondeAdresse() {
        return secondeAdresse.getValue();
    }

    public void setSecondeAdresse(String secondeAdresse) {
        this.secondeAdresse.setValue(secondeAdresse);
    }

    public SimpleStringProperty secondeAdresseProperty() {
        return this.secondeAdresse;
    }

    @Basic
    @Column(name = "NCNI_TUTEUR")
    public String getNcni() {
        return ncni.getValue();
    }

    public void setNcni(String ncni) {
        this.ncni.setValue(ncni);
    }

    public SimpleStringProperty ncniProperty() {
        return this.ncni;
    }

    @Basic
    @Column(name = "NUMERO_TELEPHONE_FIXE_TUTEUR")
    public String getnTelFix() {
        return nTelFix.getValue();
    }

    public void setnTelFix(String nTelFix) {
        this.nTelFix.setValue(nTelFix);
    }

    public SimpleStringProperty nTelFixProperty() {
        return this.nTelFix;
    }

    @Basic
    @Column(name = "NUMERO_TELEPHONE_MOBILE_TUTEUR")
    public String getnTelMob() {
        return nTelMob.getValue();
    }

    public void setnTelMob(String nTelMob) {
        this.nTelMob.setValue(nTelMob);
    }

    public SimpleStringProperty nTelMobProperty() {
        return this.nTelMob;
    }

    @Basic
    @Column(name = "AUTRE_NUMERO_TELEPHONE_1")
    public String getnTel1() {
        return nTel1.getValue();
    }

    public void setnTel1(String nTel1) {
        this.nTel1.setValue(nTel1);
    }

    public SimpleStringProperty nTel1Property() {
        return this.nTel1;
    }

    @Basic
    @Column(name = "AUTRE_NUMERO_TELEPHONE_2")
    public String getnTel2() {
        return nTel2.getValue();
    }

    public void setnTel2(String nTel2) {
        this.nTel2.setValue(nTel2);
    }

    public SimpleStringProperty nTel2Property() {
        return this.nTel2;
    }

    @Basic
    @Column(name = "NB_ORPHELIN", updatable = false)
    public int getNbOrphelins() {
        return nbOrphelins.getValue();
    }

    public void setNbOrphelins(int nbOrphelins) {
        this.nbOrphelins.setValue(nbOrphelins);
    }

    public SimpleIntegerProperty nbOrphelinsProperty() {
        return this.nbOrphelins;
    }

    @Transient
    public int getNbOrphelinsNotAgedOrAuthorized() {
        if (this.nbOrphelinsNotAgedOrAuthorized == null) {
            long number = UtilitiesHolder.ORPHELIN_DAO.countNoneAgedOrAutorizedOrphelinsByTuteur(this);
            this.nbOrphelinsNotAgedOrAuthorized = new SimpleIntegerProperty((int) number);
        }

        return nbOrphelinsNotAgedOrAuthorized.getValue();
    }

    public void setNbOrphelinsNotAgedOrAuthorized(int nbOrphelinsNotAgedOrAuthorized) {
        this.nbOrphelinsNotAgedOrAuthorized.setValue(nbOrphelinsNotAgedOrAuthorized);
    }

    public SimpleIntegerProperty nbOrphelinsNotAgedOrAuthorized() {
        return this.nbOrphelinsNotAgedOrAuthorized;
    }

    @Basic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REGION_TUTEUR")
    public Region getRegion() {
        return region.getValue();
    }

    public void setRegion(Region region) {
        this.region.setValue(region);
    }

    public SimpleObjectProperty<Region> regionProperty() {
        return this.region;
    }

    @Basic
    @Column(name = "NCCP_TUTEUR")
    public String getNccp() {
        return nccp.getValue();
    }

    public void setNccp(String nccp) {
        this.nccp.setValue(nccp);
    }

    public SimpleStringProperty nccpProperty() {
        return this.nccp;
    }

    @Basic
    @Column(name = "NCARTE_BANQUAIRE")
    public String getnCarteBanquaine() {
        return nCarteBanquaine.getValue();
    }

    public void setnCarteBanquaine(String nCarteBanquaine) {
        this.nCarteBanquaine.setValue(nCarteBanquaine);
    }

    public SimpleStringProperty nCarteBanquaireProperty() {
        return this.nCarteBanquaine;
    }

    @Basic
    @Column(name = "NDOSSIER_BUREAUTIQUE")
    public String getnDossierBureautique() {
        return nDossierBureautique.getValue();
    }

    public void setnDossierBureautique(String nDossierBureautique) {
        this.nDossierBureautique.setValue(nDossierBureautique);
    }

    public SimpleStringProperty nDossierBureautiqueProperty() {
        return this.nDossierBureautique;
    }

    @Basic
    @Column(name = "OBSERVATION")
    public String getObservation() {
        return observation.getValue();
    }

    public void setObservation(String observation) {
        this.observation.setValue(observation);
    }

    @Basic
    @OneToMany(mappedBy = "tuteur", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Orphelin> getOrphelins() {
        return orphelins;
    }

    public void setOrphelins(List<Orphelin> orphelins) {
        this.orphelins = orphelins;
    }

    public void addOrphelin(Orphelin orphelin) {
        this.getOrphelins().add(orphelin);
        orphelin.setTuteur(this);
    }

    public void removeOrphelin(Orphelin orphelin) {
        this.getOrphelins().remove(orphelin);
        orphelin.setTuteur(null);
    }

    @Basic
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_HABITAT")
    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    @Basic
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SITUATION_SOCIALE")
    public SituationSociale getSituationSociale() {
        return situationSociale;
    }

    public void setSituationSociale(SituationSociale situationSociale) {
        this.situationSociale = situationSociale;
    }

    @Basic
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOSSIER_MEDICAL")
    public DossierMedical getDossierMedical() {
        return dossierMedical;
    }

    public void setDossierMedical(DossierMedical dossierMedical) {
        this.dossierMedical = dossierMedical;
    }

    @Basic
    public boolean getArchived() {
        return this.archived.getValue();
    }

    public void setArchived(boolean archived) {
        this.archived.setValue(archived);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tuteur)
            return this.getId() == (((Tuteur) o).getId());
        return false;
    }

    @Override
    public int hashCode() {
        return this.getId();
    }

    @Override
    public String toString() {
        return this.getNom() + " " + this.getPrenom();
    }

    @Override
    public void affectAppareillage(Appareillage appareillage) {
        UtilitiesHolder.TUTEUR_DAO.affectAppareillage(this, appareillage);
    }

    @Override
    public void dettachAppareillage(Appareillage appareillage) {
        UtilitiesHolder.TUTEUR_DAO.dettachAppareillage(this, appareillage);
    }

    @Override
    public String getDescription() {
        return this.getObservation();
    }

    @Override
    public void setDescription(String description) {
        this.setObservation(description);
    }

    @Override
    public String getDesignation() {
        return this.getNom() + " " + this.getPrenom();
    }

    @Override
    public void setDesignation(String designation) {
    }

    @Transient
    public String getNumeroTelephone() {
        if (this.getnTelMob() != null && !this.getnTelMob().equals(""))
            return this.getnTelMob();
        else if (this.getnTelFix() != null && !this.getnTelFix().equals(""))
            return this.getnTelFix();

        return this.getnTel1();
    }

    @Override
    public SimpleStringProperty designationProperty() {
        return new SimpleStringProperty(this.getDesignation());
    }
}
