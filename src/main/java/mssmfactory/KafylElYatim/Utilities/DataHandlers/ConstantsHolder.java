package mssmfactory.KafylElYatim.Utilities.DataHandlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Cellule;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Profession;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Statut;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Vehicule;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Appareillage;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin;
import mssmfactory.KafylElYatim.DomainModel.Others.Compte;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.MVC.Controllers.Appareillage.AffectationAppareillageController;
import mssmfactory.KafylElYatim.MVC.Controllers.Benevole.AffectationVehiculeBenevoleController;
import mssmfactory.KafylElYatim.MVC.Controllers.Benevole.NouvelleJourneeDisponibleController;
import mssmfactory.KafylElYatim.MVC.Controllers.Donation.DonationsPanelController;
import mssmfactory.KafylElYatim.MVC.Controllers.Donation.NouveauDonateurController;
import mssmfactory.KafylElYatim.MVC.Controllers.EvenementBon.NouveauEvenementBonController;
import mssmfactory.KafylElYatim.MVC.Controllers.EvenementDecharge.NouveauEvenementDechargeController;
import mssmfactory.KafylElYatim.MVC.Controllers.Tuteur.NouveauTuteurController;

import java.util.LinkedList;

public class ConstantsHolder {

    public static Integer MALE_AGE_LIMIT = 19;
    public static Integer FEMALE_AGE_LIMIT = 21;

    public static Compte LOGGED_IN_ACCOUNT = null;

    public static NouveauEvenementDechargeController NOUVEAU_EVENEMENT_DECHARGE_CONTROLLER = null;
    public static NouveauEvenementBonController NOUVEAU_EVENEMENT_BON_CONTROLLER = null;

    public static DonationsPanelController DONATIONS_PANEL_CONTROLLER = null;
    public static NouveauDonateurController NOUVEAU_DONATEUR_CONTROLLER = null;

    public static AffectationVehiculeBenevoleController AFFECTATION_VEHICULE_BENEVOLE_CONTROLLER = null;
    public static NouvelleJourneeDisponibleController NOUVELLE_JOURNEE_DISPONIBLE_CONTROLLER = null;

    public static AffectationAppareillageController AFFECTATION_APPAREILLAGE_CONTROLLER = null;

    public static NouveauTuteurController NOUVEAU_TUTEUR_CONTROLLER = null;

    public static ObservableList<Tuteur> TUTORS_DATASOURCE = FXCollections.observableList(new LinkedList<>());
    public static ObservableList<Orphelin> ORPHELINS_DATASOURCE = FXCollections.observableList(new LinkedList<>());

    public static ObservableList<Vehicule> VEHICULES_DATASOURCE = FXCollections.observableArrayList();
    public static ObservableList<Region> REGIONS_DATASOURCE = FXCollections.observableArrayList();
    public static ObservableList<Appareillage> APPAREILLAGES_DATASOURCE = FXCollections.observableArrayList();
    public static ObservableList<Profession> PROFESSIONS_DATASOURCE = FXCollections.observableArrayList();
    public static ObservableList<Cellule> CELLULES_DATASOURCE = FXCollections.observableArrayList();
    public static ObservableList<Statut> STATUTS_DATASOURCE = FXCollections.observableArrayList();
}
