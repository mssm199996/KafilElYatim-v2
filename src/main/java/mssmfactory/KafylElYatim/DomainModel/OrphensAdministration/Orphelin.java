package mssmfactory.KafylElYatim.DomainModel.OrphensAdministration;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import msjfxuicomponents.others.IDescriptor;
import mssmfactory.KafylElYatim.DomainModel.Others.IAppareillageHolder;
import mssmfactory.KafylElYatim.DomainModel.Others.IPersonne;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Orphelins")
public class Orphelin implements IAppareillageHolder, IDescriptor, IPersonne {

    private int id;
    private SimpleBooleanProperty authorized = new SimpleBooleanProperty();
    private SimpleStringProperty nom = new SimpleStringProperty(), prenom = new SimpleStringProperty(),
            obsevation = new SimpleStringProperty(), relationOrphelinTuteur = new SimpleStringProperty();
    private SimpleObjectProperty<LocalDate> ddn = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Genre> genre = new SimpleObjectProperty<>();

    private Tuteur tuteur;
    private DossierFamilial dossierFamilial;
    private DossierScolaire dossierScolaire;
    private DossierMedical dossierMedical;
    private ApparencePhysique apparencePhysique;

    @Id
    @GeneratedValue(generator = "orphelinIdGenerator", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "orphelinIdGenerator", strategy = "increment")
    @Column(name = "ID_ORPHELIN")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NOM_ORPHELIN")
    public String getNom() {
        return nom.getValue();
    }

    public void setNom(String nom) {
        this.nom.setValue(nom);
    }

    public SimpleStringProperty nomProperty() {
        return this.nom;
    }

    @Basic
    @Column(name = "PRENOM_ORPHELIN")
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
    @Column(name = "DDN_ORPHELIN")
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
    @Column(name = "GENRE_ORPHELIN")
    public Genre getGenre() {
        return genre.getValue();
    }

    public void setGenre(Genre genre) {
        this.genre.setValue(genre);
    }

    public SimpleObjectProperty<Genre> genreProperty() {
        return this.genre;
    }

    @Basic
    @Column(name = "OBSERVATION_ORPHELIN")
    public String getObsevation() {
        return obsevation.getValue();
    }

    public void setObsevation(String obsevation) {
        this.obsevation.setValue(obsevation);
    }

    @Basic(optional = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TUTEUR")
    public Tuteur getTuteur() {
        return tuteur;
    }

    public void setTuteur(Tuteur tuteur) {
        this.tuteur = tuteur;
    }

    public SimpleStringProperty tuteurProperty() {
        if (this.tuteur != null) {
            return new SimpleStringProperty(this.tuteur.toString());
        }

        return new SimpleStringProperty("");
    }

    @Basic
    @Column(name = "RELATION_TUTEUR")
    public String getRelationOrphelinTuteur() {
        return relationOrphelinTuteur.getValue();
    }

    public void setRelationOrphelinTuteur(String relationOrphelinTuteur) {
        this.relationOrphelinTuteur.setValue(relationOrphelinTuteur);
    }

    public SimpleStringProperty relationOrphelinTuteurProperty() {
        return this.relationOrphelinTuteur;
    }

    @Basic
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOSSIER_FAMILIAL")
    public DossierFamilial getDossierFamilial() {
        return dossierFamilial;
    }

    public void setDossierFamilial(DossierFamilial dossierFamilial) {
        this.dossierFamilial = dossierFamilial;
    }

    @Basic
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOSSIER_SCOLAIRE")
    public DossierScolaire getDossierScolaire() {
        return dossierScolaire;
    }

    public void setDossierScolaire(DossierScolaire dossierScolaire) {
        this.dossierScolaire = dossierScolaire;
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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPARENCE_PHYSIQUE")
    public ApparencePhysique getApparencePhysique() {
        return apparencePhysique;
    }

    public void setApparencePhysique(ApparencePhysique apparencePhysique) {
        this.apparencePhysique = apparencePhysique;
    }

    @Basic
    @Column(name = "AUTORISE")
    public boolean isAuthorized() {
        return authorized.getValue();
    }

    public void setAuthorized(boolean authorized) {
        this.authorized.setValue(authorized);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Orphelin)
            return this.getId() == (((Orphelin) o).getId());

        return false;
    }

    public SimpleIntegerProperty ageProperty() {
        LocalDate intermediaire = ((LocalDate.now().minusYears(this.getDdn().getYear()))
                .minusMonths(this.getDdn().getMonthValue() - 1)).minusDays(this.getDdn().getDayOfMonth() - 1);

        return new SimpleIntegerProperty(intermediaire.getYear());
    }

    @Transient
    public Integer getAge() {
        return this.ageProperty().get();
    }

    @Override
    public String toString() {
        return this.getNom() + " " + this.getPrenom();
    }

    public static enum Genre {
        Masculin, Feminin
    }

    public static enum RelationOrphelinTuteur {
        Pere, Mere
    }

    @Override
    public void affectAppareillage(Appareillage appareillage) {
        UtilitiesHolder.ORPHELIN_DAO.affectAppareillageToOrphelin(this, appareillage);
    }

    @Override
    public void dettachAppareillage(Appareillage appareillage) {
        UtilitiesHolder.ORPHELIN_DAO.dettachAppareillageFromOrphelin(this, appareillage);
    }

    @Override
    public String getDescription() {
        return this.getObsevation();
    }

    @Override
    public void setDescription(String description) {
        this.setObsevation(description);
    }

    @Override
    public String getDesignation() {
        return this.getNom() + " " + this.getPrenom();
    }

    @Override
    public void setDesignation(String designation) {
    }

    @Override
    public SimpleStringProperty designationProperty() {
        return new SimpleStringProperty(this.getDesignation());
    }

    @Transient
    public String getDesignationGenre() {
        return this.getGenre().name().charAt(0) + "";
    }

    @Transient
    public String getDesignationNiveauScolaire() {
        if (this.getDossierScolaire() != null && this.getDossierScolaire().getNiveauScolaire() != null)
            return this.getDossierScolaire().getNiveauScolaire().toString();
        else return "";
    }
}
