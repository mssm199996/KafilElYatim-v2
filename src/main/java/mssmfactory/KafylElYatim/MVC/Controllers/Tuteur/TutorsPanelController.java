package mssmfactory.KafylElYatim.MVC.Controllers.Tuteur;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.*;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.componentsStuffers.DisabilityBinder;
import msjfxuicomponents.others.CrudController;
import msjfxuicomponents.others.ICategorizerDeleter;
import msjfxuicomponents.uicomponents.MSCategorizerCrudListTitledPane;
import msjfxuicomponents.uicomponents.MSSimpleObservationTitledPane;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Appareillage;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.DossierMedical;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Enquete;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Habitat;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Habitat.EtatHabitat;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Habitat.TypeBien;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.SituationSociale;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.SituationSociale.NiveauVie;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.MVC.Controllers.Appareillage.AffectationAppareillageController;
import mssmfactory.KafylElYatim.MVC.Controllers.Main.MainWindowController;
import mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers.TableTuteursController;
import mssmfactory.KafylElYatim.MVC.Controllers.Utils.TableTuteursContainer;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.ComponentsHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class TutorsPanelController extends CrudController<Tuteur> implements TableTuteursContainer {

    public TutorsPanelController() {
        super(ConstantsHolder.TUTORS_DATASOURCE);
    }

    @FXML
    private TableTuteursController tableTuteursController;

    @FXML
    private JFXCheckBox displayArchived;

    @FXML
    private JFXTextField nomPrenomFilter;

    @FXML
    private JFXTextField carteBanquaire;

    @FXML
    private JFXTextField compteCcp;

    @FXML
    private JFXTextField carteNationale;

    @FXML
    private JFXTextField numeroDossier;

    @FXML
    private JFXComboBox<EtatHabitat> etatHabitat;

    @FXML
    private JFXComboBox<TypeBien> typeBien;

    @FXML
    private JFXTextField classementHabitat;

    @FXML
    private JFXTextField salaire;

    @FXML
    private JFXTextField retraire;

    @FXML
    private JFXTextField couvertureSociale;

    @FXML
    private JFXComboBox<NiveauVie> niveauDeVie;

    @FXML
    private JFXTextField fonction;

    @FXML
    private JFXTextField niveauEtudes;

    @FXML
    private JFXTextArea competances;

    @FXML
    private JFXTextField nomEnqueteur;

    @FXML
    private JFXTextField prenomEnqueteur;

    @FXML
    private JFXDatePicker dateEnquete;

    @FXML
    private JFXTextArea pathologies;

    @FXML
    private JFXTextArea medicaments;

    @FXML
    private MSCategorizerCrudListTitledPane<Appareillage> appareillages;

    @FXML
    private MSSimpleObservationTitledPane<Tuteur> observations;

    @FXML
    private JFXComboBox<Region> regionFilter;

    @FXML
    private JFXComboBox<NiveauVie> niveauDeVieFilter;

    @FXML
    private JFXComboBox<EtatHabitat> etatHabitatFilter;

    @FXML
    private JFXComboBox<TypeBien> typeBienFilter;

    @FXML
    private Accordion accordion;

    @FXML
    private Label nombreOrphelonsReelTuteur;

    @FXML
    void printTutors() {
        UtilitiesHolder.PRINTING_HANDLER.printTutorsList(this.getDatasource());
    }

    @FXML
    void printTutorsWithRealOrphelinsNumber() {
        UtilitiesHolder.PRINTING_HANDLER.printTutorsListWithRealOrphelinsNumber(this.getDatasource());
    }

    @FXML
    void printTutorsWithOrphelins() {
        UtilitiesHolder.PRINTING_HANDLER.printTutorsListWithOrphelins(this.getDatasource());
    }

    @FXML
    void printTutorsWithRealOrphelins() {
        UtilitiesHolder.PRINTING_HANDLER.printTutorsListWithRealOrphelins(this.getDatasource());
    }

    @FXML
    void refresh() {
        this.etatHabitatFilter.setValue(null);
        this.niveauDeVieFilter.setValue(null);
        this.nomPrenomFilter.setText("");
        this.regionFilter.setValue(null);
        this.typeBienFilter.setValue(null);
        this.displayArchived.setVisible(false);

        this.search();
    }

    @FXML
    void search(KeyEvent event) {
        this.search();
    }

    @FXML
    void updateAdditionalInformations() {
        Tuteur tuteur = this.tableTuteursController.getSelectionModel().getSelectedItem();

        if (tuteur != null) {
            if (!MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(),
                    "Confirmation de mise à jour", "Veuillez confirmer pour proceder à la mise à jour",
                    this.getWindow()))
                return;

            tuteur.setNccp(this.compteCcp.getText());
            tuteur.setnCarteBanquaine(this.carteBanquaire.getText());
            tuteur.setNcni(this.carteNationale.getText());
            tuteur.setnDossierBureautique(this.numeroDossier.getText());

            (new Thread(() -> {
                this.onUpdateCallback(tuteur);
            })).start();
        } else
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Erreur de mise à jour",
                    "Veuillez d'abords selectionner un tuteur !", this.getWindow());
    }

    @FXML
    void updateDossierMedical() {
        Tuteur tuteur = this.tableTuteursController.getSelectionModel().getSelectedItem();

        if (tuteur != null) {
            if (!MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(),
                    "Confirmation de mise à jour", "Confirmer pour proceder à la mise à jour", this.getWindow()))
                return;

            tuteur.getDossierMedical().setMedicaments(this.medicaments.getText());
            tuteur.getDossierMedical().setPathologie(this.pathologies.getText());

            (new Thread(() -> {
                this.onUpdateCallback(tuteur);
            })).start();
        } else
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Message d'erreur",
                    "Veuillez d'abords selectionner un tuteur !", this.getWindow());
    }

    @FXML
    void updateEnquete() {
        Tuteur tuteur = this.tableTuteursController.getSelectionModel().getSelectedItem();

        if (tuteur != null) {
            if (!MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(),
                    "Confirmation de mise à jour", "Veuillez confirmer pour proceder à la mise à jour",
                    this.getWindow()))
                return;

            tuteur.getSituationSociale().getEnquete().setDateEnquete(this.dateEnquete.getValue());
            tuteur.getSituationSociale().getEnquete().setNomEnqueteur(this.nomEnqueteur.getText());
            tuteur.getSituationSociale().getEnquete().setPrenomEnqueteur(this.prenomEnqueteur.getText());

            (new Thread(() -> {
                this.onUpdateCallback(tuteur);
            })).start();
        } else
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Erreur de mise à jour",
                    "Veuillez d'abords selectionner un tuteur !", this.getWindow());
    }

    @FXML
    void updateHabitat() {
        Tuteur tuteur = this.tableTuteursController.getSelectionModel().getSelectedItem();

        if (tuteur != null) {
            if (!MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(),
                    "Confirmation de mise à jour", "Veuillez confirmer pour proceder à la mise à jour",
                    this.getWindow()))
                return;

            tuteur.getHabitat().setEtat(this.etatHabitat.getValue());
            tuteur.getHabitat().setTypeBien(this.typeBien.getValue());
            tuteur.getHabitat().setClassementHabitat(this.classementHabitat.getText());

            (new Thread(() -> {
                this.onUpdateCallback(tuteur);
            })).start();
        } else
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Erreur de mise à jour",
                    "Veuillez d'abords selectionner un tuteur !", this.getWindow());
    }

    @FXML
    void updateSituationSociale() {
        Tuteur tuteur = this.tableTuteursController.getSelectionModel().getSelectedItem();

        if (tuteur != null) {
            if ((tuteur.getSituationSociale().getNiveauVie() == null)
                    || (tuteur.getSituationSociale().getNiveauVie() != null && this.niveauDeVie.getValue() != null
                    && tuteur.getSituationSociale().getNiveauVie().equals(this.niveauDeVie.getValue()))) {
                if (!MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(),
                        "Confirmation de mise à jour", "Veuillez confirmer pour proceder à la mise à jour",
                        this.getWindow()))
                    return;
            } else if (!MainWindowController.IS_SU) {
                MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(),
                        "Erreur d'authentification", "Veuillez vous connecter en tant que super utilisateur",
                        this.getWindow());
                return;
            }

            tuteur.getSituationSociale().setNiveauVie(this.niveauDeVie.getValue());
            tuteur.getSituationSociale().setFonction(this.fonction.getText());
            tuteur.getSituationSociale().setRetraite(Double.parseDouble(this.retraire.getText()));
            tuteur.getSituationSociale().setConvertureSociale(Double.parseDouble(this.couvertureSociale.getText()));
            tuteur.getSituationSociale().setSalaire(Double.parseDouble(this.salaire.getText()));
            tuteur.getSituationSociale().setNiveauEtude(this.niveauEtudes.getText());
            tuteur.getSituationSociale().setCompetances(this.competances.getText());

            (new Thread(() -> {
                this.onUpdateCallback(tuteur);
            })).start();
        } else
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Erreur de mise à jour",
                    "Veuillez d'abords selectionner un tuteur !", this.getWindow());
    }

    // ------------------------------------------------------------------------------

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.initInitialDatas();
        this.initCheckBoxes();
        this.initComboBox();
        this.initLists();
        this.initTables();
        this.initTextFields();
        this.initConstants();
        this.initOthers();
    }

    private void initCheckBoxes() {
        this.displayArchived.setOnAction(e -> {
            this.search();
        });
    }

    private void initTextFields() {
        this.nomPrenomFilter.textProperty().addListener((__, ___, value) -> {
            this.search();
        });
    }

    private void initLists() {
        this.appareillages.setBeforeAddCallback(() -> {
            AffectationAppareillageController.APPAREILLAGE_HOLDER = this.tableTuteursController.getSelectionModel()
                    .getSelectedItem();
        });

        this.appareillages.setCategorizerControllerAdder(ConstantsHolder.AFFECTATION_APPAREILLAGE_CONTROLLER);
        this.appareillages.setCategorizerDeleter(new ICategorizerDeleter<Appareillage>() {

            @Override
            public void deleteEntity(Appareillage entity) {
                Tuteur tuteur = tableTuteursController.getSelectionModel().getSelectedItem();

                if (tuteur != null) {
                    UtilitiesHolder.TUTEUR_DAO.dettachAppareillage(tuteur, entity);
                } else
                    MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(getNomGestion(),
                            "Affectation d'une appareillage", "Veuillez d'abords selectionner un tuteur", getWindow());
            }
        });
    }

    private void initOthers() {
        this.observations.setUpdator(UtilitiesHolder.TUTEUR_DAO);
        this.observations.setEntityFetcher(() -> {
            return this.tableTuteursController.getSelectionModel().getSelectedItem();
        });
    }

    private void initComboBox() {
        this.regionFilter.setItems(ConstantsHolder.REGIONS_DATASOURCE);
        this.regionFilter.getSelectionModel().selectedItemProperty().addListener(event -> {
            this.search();
        });

        this.typeBien.setItems(FXCollections.observableArrayList(TypeBien.values()));
        this.typeBienFilter.setItems(FXCollections.observableArrayList(TypeBien.values()));
        this.typeBienFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.search();
        });

        this.niveauDeVie.setItems(FXCollections.observableArrayList(NiveauVie.values()));
        this.niveauDeVieFilter.setItems(FXCollections.observableArrayList(NiveauVie.values()));
        this.niveauDeVieFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.search();
        });

        this.etatHabitat.setItems(FXCollections.observableArrayList(EtatHabitat.values()));
        this.etatHabitatFilter.setItems(FXCollections.observableArrayList(EtatHabitat.values()));
        this.etatHabitatFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.search();
        });
    }

    @Override
    public void initTables() {
        super.initTables();

        this.tableTuteursController.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> {
                    this.updateTuteurInformations(newValue);
                });

        DisabilityBinder.linkedTableViewToAccordion(this.tableTuteursController.getResultTable(), this.accordion);
    }

    private void initInitialDatas() {
        this.search();
    }

    private void initConstants() {
        ComponentsHolder.SEARCH_TUTORS = this.nomPrenomFilter;
        ComponentsHolder.SEARCH_REGION_TUTEUR = this.regionFilter;
        ComponentsHolder.SEARCH_NIVEAU_VIE_TUTEUR = this.niveauDeVieFilter;
        ComponentsHolder.SEARCH_TYPE_BIEN = this.typeBienFilter;
        ComponentsHolder.SEARCH_ETAT_HABITAT = this.etatHabitatFilter;
    }

    @Override
    public Stage getInsertStage() {
        return StagesHolder.NOUVEAU_TUTEUR_STAGE;
    }

    @Override
    public Tuteur getLastInsertedEntity() {
        return NouveauTuteurController.LAST_INSERTED_TUTEUR;
    }

    @Override
    public String getNomGestion() {
        return "Gestion des tuteurs";
    }

    @Override
    public String getDeleteHeader() {
        return "Suppression d'un tuteur";
    }

    @Override
    public void onDeleteCallback(Tuteur entity) {
        UtilitiesHolder.TUTEUR_DAO.deleteEntity(entity);
    }

    @Override
    public void onBeforeUpdateCallback(Tuteur entity) {
    }

    @Override
    public void onUpdateCallback(Tuteur entity) {
        UtilitiesHolder.TUTEUR_DAO.updateEntity(entity);
    }

    @Override
    protected List<Tuteur> selectFromDatabase() throws NullPointerException {
        String names = this.nomPrenomFilter.getText();
        Region region = this.regionFilter.getSelectionModel().getSelectedItem();
        NiveauVie ndv = this.niveauDeVieFilter.getSelectionModel().getSelectedItem();
        EtatHabitat etatHabitat = this.etatHabitatFilter.getSelectionModel().getSelectedItem();
        TypeBien typeBien = this.typeBienFilter.getSelectionModel().getSelectedItem();
        boolean displayArchived = this.displayArchived.isSelected();

        return UtilitiesHolder.TUTEUR_DAO.getSpecifiedTuteurs(names, region, ndv, etatHabitat, typeBien, displayArchived);
    }

    public void updateTuteurInformations(Tuteur tuteur) {
        this.appareillages.clear();

        this.pathologies.setText("");
        this.medicaments.setText("");

        this.fonction.setText("");
        this.niveauEtudes.setText("");
        this.salaire.setText("");
        this.retraire.setText("");
        this.couvertureSociale.setText("");
        this.niveauDeVie.setValue(null);
        this.competances.setText("");

        this.dateEnquete.setValue(null);
        this.nomEnqueteur.setText("");
        this.prenomEnqueteur.setText("");

        this.typeBien.setValue(null);
        this.etatHabitat.setValue(null);
        this.classementHabitat.setText("");

        this.carteBanquaire.setText("");
        this.compteCcp.setText("");
        this.carteNationale.setText("");
        this.numeroDossier.setText("");
        this.observations.setObservationContent("");
        this.nombreOrphelonsReelTuteur.setText("Aucun tuteur sélectionné");

        if (tuteur != null) {
            DossierMedical dossierMedical = UtilitiesHolder.TUTEUR_DAO.getDossierMedicalTuteur(tuteur);
            SituationSociale situationSociale = UtilitiesHolder.TUTEUR_DAO.getSituationSocialeTuteur(tuteur);
            Habitat habitat = UtilitiesHolder.TUTEUR_DAO.getHabitatTuteur(tuteur);
            List<Appareillage> appareillages = UtilitiesHolder.APPAREILLAGE_DAO.getAppareillagesTuteur(tuteur);

            tuteur.setDossierMedical(dossierMedical);
            tuteur.setSituationSociale(situationSociale);
            tuteur.setHabitat(habitat);

            this.nombreOrphelonsReelTuteur.setText("Nombre d'orphelins réel: " + tuteur.getNbOrphelinsNotAgedOrAuthorized());
            this.appareillages.addAll(appareillages);

            this.medicaments.setText(dossierMedical.getMedicaments());
            this.pathologies.setText(dossierMedical.getPathologie());

            this.fonction.setText(situationSociale.getFonction());
            this.niveauEtudes.setText(situationSociale.getNiveauEtude());
            this.salaire.setText(Double.toString(situationSociale.getSalaire()));
            this.retraire.setText(Double.toString(situationSociale.getRetraite()));
            this.couvertureSociale.setText(Double.toString(situationSociale.getConvertureSociale()));
            this.niveauDeVie.setValue(situationSociale.getNiveauVie());
            this.competances.setText(situationSociale.getCompetances());

            Enquete enquete = situationSociale.getEnquete();

            if (enquete != null) {
                this.dateEnquete.setValue(enquete.getDateEnquete());
                this.nomEnqueteur.setText(enquete.getNomEnqueteur());
                this.prenomEnqueteur.setText(enquete.getPrenomEnqueteur());
            }

            this.typeBien.setValue(habitat.getTypeBien());
            this.etatHabitat.setValue(habitat.getEtat());
            this.classementHabitat.setText(habitat.getClassementHabitat());

            this.carteBanquaire.setText(tuteur.getnCarteBanquaine());
            this.compteCcp.setText(tuteur.getNccp());
            this.carteNationale.setText(tuteur.getNcni());
            this.numeroDossier.setText(tuteur.getnDossierBureautique());
            this.observations.setObservationContent(tuteur.getObservation());
        }
    }

    @Override
    public boolean isCustomTable() {
        return true;
    }

    @Override
    public void onTuteurArchiveSwitched(Tuteur tuteur) {

    }
}
