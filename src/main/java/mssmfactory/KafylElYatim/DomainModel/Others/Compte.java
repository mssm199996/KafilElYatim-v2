package mssmfactory.KafylElYatim.DomainModel.Others;

import DomainModel.Droit;
import DomainModel.ICompte;
import DomainModel.IConfiguration;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMPTES")
public class Compte implements ICompte, IConfiguration {

    private int id;
    private String cheminSauvegarde, cheminMysql, username, password;
    private LocalDate dateDerniereConnexion;
    private LocalTime heureDerniereConnexion;
    private Boolean admin;
    private List<Droit> droits = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getCheminSauvegarde() {
        return this.cheminSauvegarde;
    }

    @Override
    public void setCheminSauvegarde(String cheminSauvegarde) {
        this.cheminSauvegarde = cheminSauvegarde;
    }

    @Override
    @Transient
    public Boolean isActivateCamera() {
        return false;
    }

    @Override
    public void setActivateCamera(Boolean activateCamera) {
    }

    @Override
    public String getCheminMysql() {
        return this.cheminMysql;
    }

    @Override
    public void setCheminMysql(String cheminMysql) {
        this.cheminMysql = cheminMysql;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setDateDerniereConnexion(LocalDate date) {
        this.dateDerniereConnexion = date;
    }

    @Override
    public void setHeureDerniereConnexion(LocalTime heure) {
        this.heureDerniereConnexion = heure;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public Boolean isAdmin() {
        return this.admin;
    }

    @Transient
    public List<Droit> getDroits() {
        return this.droits;
    }

    public void setDroit(List<Droit> droits) {
        this.droits = droits;
    }

    public LocalDate getDateDerniereConnexion() {
        return dateDerniereConnexion;
    }

    public LocalTime getHeureDerniereConnexion() {
        return heureDerniereConnexion;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SimpleStringProperty usernameProperty() {
        return new SimpleStringProperty(this.username);
    }

    @Override
    @Transient
    public Boolean isAdvancedPrinting() {
        return true;
    }

    @Override
    public void setAdvancedPrinting(Boolean advancedPrinting) {
    }
}
