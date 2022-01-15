package mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Benevoles")
public class Benevole {

    private int id;
    private SimpleStringProperty nom = new SimpleStringProperty();
    private SimpleStringProperty prenom = new SimpleStringProperty();
    private SimpleObjectProperty<Genre> genre = new SimpleObjectProperty<>();
    private SimpleStringProperty telephone = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private SimpleStringProperty facebook = new SimpleStringProperty();
    private SimpleStringProperty adresse = new SimpleStringProperty();

    private SimpleObjectProperty<LocalDate> dateDerniereSollicitation = new SimpleObjectProperty<>();
    private SimpleIntegerProperty nombreSollicitation = new SimpleIntegerProperty();

    private List<JourneeDisponible> journeesDisponibles = new ArrayList<JourneeDisponible>();
    private List<Vehicule> vehicules = new ArrayList<Vehicule>();
    private List<Profession> professions = new ArrayList<>();
    private List<Cellule> cellules = new ArrayList<>();
    private List<Statut> statuts = new ArrayList<>();

    @Id
    @Column(name = "ID_BENEVOLE")
    @GenericGenerator(name = "benevoleIdGenerator", strategy = "increment")
    @GeneratedValue(generator = "benevoleIdGenerator", strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NOM_BENEVOLE")
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
    @Column(name = "PRENOM_BENEVOLE")
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
    @Column(name = "GENRE")
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
    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone.getValue();
    }

    public void setTelephone(String telephone) {
        this.telephone.setValue(telephone);
    }

    public SimpleStringProperty telephoneProperyt() {
        return this.telephone;
    }

    @Basic
    @Column(name = "ADRESSE")
    public String getAdresse() {
        return adresse.getValue();
    }

    public void setAdresse(String adresse) {
        if (adresse != null)
            this.adresse.setValue(adresse);
        else this.adresse.setValue("");
    }

    public SimpleStringProperty adresseProperty() {
        return this.adresse;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return this.email.getValue();
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public SimpleStringProperty emailProperty() {
        return this.email;
    }

    @Basic
    @Column(name = "FACEBOOK")
    public String getFacebook() {
        return facebook.getValue();
    }

    public void setFacebook(String facebook) {
        this.facebook.setValue(facebook);
    }

    public SimpleStringProperty facebookProperty() {
        return this.facebook;
    }

    @Basic
    @OneToMany(mappedBy = "benevole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<JourneeDisponible> getJourneesDisponibles() {
        return journeesDisponibles;
    }

    public void setJourneesDisponibles(List<JourneeDisponible> journeesDisponibles) {
        this.journeesDisponibles = journeesDisponibles;
    }

    public void addJourneeDisponible(JourneeDisponible journeeDisponible) {
        this.getJourneesDisponibles().add(journeeDisponible);
        journeeDisponible.setBenevole(this);
    }

    public void removeJourneeDisponible(JourneeDisponible journeeDisponible) {
        this.getJourneesDisponibles().remove(journeeDisponible);
        journeeDisponible.setBenevole(null);
    }

    @Basic
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Benevoles_Vehicules", joinColumns = @JoinColumn(name = "ID_BENEVOLE"), inverseJoinColumns = @JoinColumn(name = "ID_VEHICULE"))
    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public void addVehicule(Vehicule vehicule) {
        this.getVehicules().add(vehicule);
    }

    public void removeVehicule(Vehicule vehicule) {
        this.getVehicules().remove(vehicule);
    }

    @Basic
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Benevoles_Professions", joinColumns = @JoinColumn(name = "ID_BENEVOLE"), inverseJoinColumns = @JoinColumn(name = "ID_PROFESSION"))
    public List<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(List<Profession> professions) {
        this.professions = professions;
    }

    public void addProfession(Profession profession) {
        this.getProfessions().add(profession);
    }

    public void removeProfession(Profession profession) {
        this.getProfessions().remove(profession);
    }

    // --->

    @Basic
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Benevoles_Statuts", joinColumns = @JoinColumn(name = "ID_BENEVOLE"), inverseJoinColumns = @JoinColumn(name = "ID_STATUT"))
    public List<Statut> getStatuts() {
        return statuts;
    }

    public void setStatuts(List<Statut> statuts) {
        this.statuts = statuts;
    }

    public void addStatut(Statut statut) {
        this.getStatuts().add(statut);
    }

    public void removeStatut(Statut statut) {
        this.getStatuts().remove(statut);
    }

    // --->

    @Basic
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Benevoles_Cellules", joinColumns = @JoinColumn(name = "ID_BENEVOLE"), inverseJoinColumns = @JoinColumn(name = "ID_CELLULE"))
    public List<Cellule> getCellules() {
        return cellules;
    }

    public void setCellules(List<Cellule> cellules) {
        this.cellules = cellules;
    }

    public void addCellule(Cellule cellule) {
        this.getCellules().add(cellule);
    }

    public void removeCellule(Cellule cellule) {
        this.getCellules().remove(cellule);
    }

    public LocalDate getDateDerniereSollicitation() {
        return this.dateDerniereSollicitation.getValue();
    }

    public void setDateDerniereSollicitation(LocalDate dateDerniereSollicitation) {
        this.dateDerniereSollicitation.setValue(dateDerniereSollicitation);
    }

    public SimpleObjectProperty<LocalDate> dateDerniereSollicitationProperty() {
        return this.dateDerniereSollicitation;
    }

    public Integer getNombreSollicitation() {
        return this.nombreSollicitation.getValue();
    }

    public void setNombreSollicitation(Integer nombreSollicitation) {
        this.nombreSollicitation.setValue(nombreSollicitation);
    }

    public SimpleIntegerProperty nombreSollicitationProperty() {
        return this.nombreSollicitation;
    }

    public static enum Genre {
        Masculin, Feminin
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Benevole)
            return this.getId() == (((Benevole) o).getId());
        return false;
    }

    @Override
    public String toString() {
        return this.getNom() + " " + this.getPrenom();
    }
}
