package mssmfactory.KafylElYatim.MVC.Controllers.Benevole;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.componentsStuffers.DisabilityBinder;
import msjfxuicomponents.others.CrudController;
import msjfxuicomponents.uicomponents.MSCategorizerCrudListTitledPane;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.*;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Benevole.Genre;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.JourneeDisponible.JourDeSemaine;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class BenevolsPanelController extends CrudController<Benevole> {

    @FXML
    private TableColumn<Benevole, String> nomColumn;

    @FXML
    private TableColumn<Benevole, String> prenomColumn;

    @FXML
    private TableColumn<Benevole, Genre> genreColumn;

    @FXML
    private TableColumn<Benevole, String> telephoneColumn;

    @FXML
    private TableColumn<Benevole, String> emailColumn;

    @FXML
    private TableColumn<Benevole, String> facebookColumn;

    @FXML
    private TableColumn<Benevole, String> adresseColumn;

    @FXML
    private TableColumn<Benevole, Integer> nombreSollicitationColumn;

    @FXML
    private TableColumn<Benevole, LocalDate> dateDerniereSollicitationColumn;

    @FXML
    private JFXTextField nomPrenom;

    @FXML
    private JFXComboBox<JourneeDisponible.JourDeSemaine> journeeDisponible;

    @FXML
    private JFXComboBox<Vehicule> vehicule;

    @FXML
    private JFXComboBox<Cellule> cellule;

    @FXML
    private JFXComboBox<Profession> profession;

    @FXML
    private JFXComboBox<Statut> statut;

    @FXML
    private MSCategorizerCrudListTitledPane<JourneeDisponible> journeesDisponiblesListView;

    @FXML
    private MSCategorizerCrudListTitledPane<Vehicule> vehiculesListView;

    @FXML
    private MSCategorizerCrudListTitledPane<Cellule> cellulesListView;

    @FXML
    private MSCategorizerCrudListTitledPane<Statut> statutsListView;

    @FXML
    private MSCategorizerCrudListTitledPane<Profession> professionsListView;

    @FXML
    private Accordion accordion;

    @FXML
    void refresh(ActionEvent event) {
        this.nomPrenom.setText("");
        this.journeeDisponible.setValue(null);
        this.vehicule.setValue(null);
        this.cellule.setValue(null);
        this.profession.setValue(null);
        this.statut.setValue(null);

        this.search();
    }

    @FXML
    private void solliciter() {
        Benevole benevole = this.tablePrincipale.getSelectionModel().getSelectedItem();

        if (benevole != null) {
            boolean confirmation = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(), "Sollicitation d'un benevole", "Voulez vous vraiment solliciter le benevole ? Ceci va mettre à jour le nombre de sollicitation ainsi que la dernière date de sollicitation !", this.getWindow());

            if (confirmation) {
                benevole.setDateDerniereSollicitation(LocalDate.now());
                benevole.setNombreSollicitation(benevole.getNombreSollicitation() + 1);

                (new Thread(() -> {
                    UtilitiesHolder.BENEVOLE_DAO.updateArray(benevole);
                })).start();
            }
        } else
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Sollicitation d'un benevole", "Veuillez d'abords selectionner un benevole !", this.getWindow());
    }

    @FXML
    void search(KeyEvent event) {
        super.search();
    }

    // ---------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        this.initComboBoxes();
        this.initListViews();
        this.initInitialData();
    }

    private void initComboBoxes() {
        this.journeeDisponible.getItems().addAll(JourDeSemaine.values());
        this.journeeDisponible.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.search();
        });

        this.vehicule.setItems(ConstantsHolder.VEHICULES_DATASOURCE);
        this.vehicule.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.search();
        });

        this.statut.setItems(ConstantsHolder.STATUTS_DATASOURCE);
        this.statut.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.search();
        });

        this.profession.setItems(ConstantsHolder.PROFESSIONS_DATASOURCE);
        this.profession.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.search();
        });

        this.cellule.setItems(ConstantsHolder.CELLULES_DATASOURCE);
        this.cellule.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.search();
        });
    }

    @Override
    public void initTables() {
        super.initTables();

        this.tablePrincipale.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.vehiculesListView.clear();
            this.journeesDisponiblesListView.clear();
            this.cellulesListView.clear();
            this.statutsListView.clear();
            this.professionsListView.clear();

            if (newValue != null) {
                this.vehiculesListView.addAll(UtilitiesHolder.VEHICULE_DAO.getVehiculesBenevole(newValue));
                this.cellulesListView.addAll(UtilitiesHolder.CELLULE_DAO.getCellulesByBenevole(newValue));
                this.statutsListView.addAll(UtilitiesHolder.STATUT_DAO.getStatutsByBenevole(newValue));
                this.professionsListView.addAll(UtilitiesHolder.PROFESSION_DAO.getProfessionsByBenevole(newValue));
                this.journeesDisponiblesListView.addAll(UtilitiesHolder.JOURNEE_DISPONIBLE_DAO.getJourneesDisponiblesBenevole(newValue));
            }
        });

        DisabilityBinder.linkedTableViewToAccordion(this.tablePrincipale, this.accordion);

        this.nomColumn.setCellFactory(this.textCellUpdater);
        this.nomColumn.setCellValueFactory(call -> {
            return call.getValue().nomProperty();
        });

        this.prenomColumn.setCellFactory(this.textCellUpdater);
        this.prenomColumn.setCellValueFactory(call -> {
            return call.getValue().prenomProperty();
        });

        this.genreColumn.setCellValueFactory(call -> {
            return call.getValue().genreProperty();
        });

        this.telephoneColumn.setCellFactory(this.textCellUpdater);
        this.telephoneColumn.setCellValueFactory(call -> {
            return call.getValue().telephoneProperyt();
        });

        this.emailColumn.setCellFactory(this.textCellUpdater);
        this.emailColumn.setCellValueFactory(call -> {
            return call.getValue().emailProperty();
        });

        this.facebookColumn.setCellFactory(this.textCellUpdater);
        this.facebookColumn.setCellValueFactory(call -> {
            return call.getValue().facebookProperty();
        });

        this.adresseColumn.setCellFactory(this.textCellUpdater);
        this.adresseColumn.setCellValueFactory(call -> {
            return call.getValue().adresseProperty();
        });

        this.nombreSollicitationColumn.setCellValueFactory(call -> call.getValue().nombreSollicitationProperty().asObject());
        this.dateDerniereSollicitationColumn.setCellValueFactory(call -> call.getValue().dateDerniereSollicitationProperty());
    }

    private void initListViews() {
        this.cellulesListView.setOnCustomAdd(() -> {
            Benevole benevole = this.tablePrincipale.getSelectionModel().getSelectedItem();
            String header = "Affectation d'un benevole à une céllule";

            if (benevole != null) {
                Cellule cellule = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayChoiceAlert(this.getNomGestion(), header, ConstantsHolder.CELLULES_DATASOURCE, null, this.getWindow());

                if (cellule != null) {
                    (new Thread(() -> {
                        UtilitiesHolder.BENEVOLE_DAO.affectCelluleToBenevole(benevole, cellule);

                        Platform.runLater(() -> {
                            this.cellulesListView.addAll(Arrays.asList(cellule));
                        });
                    })).start();
                }
            } else
                MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), header, "Veuillez d'abords selectionner un bénévole", this.getWindow());
        });
        this.cellulesListView.setCategorizerDeleter(entity -> {
            Benevole benevole = tablePrincipale.getSelectionModel().getSelectedItem();

            if (benevole != null) {
                UtilitiesHolder.BENEVOLE_DAO.detachCelluleFromBenevole(benevole, entity);
            }
        });

        // --->

        this.professionsListView.setOnCustomAdd(() -> {
            Benevole benevole = this.tablePrincipale.getSelectionModel().getSelectedItem();
            String header = "Affectation d'un benevole à une profession";

            if (benevole != null) {
                Profession profession = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayChoiceAlert(this.getNomGestion(), header, ConstantsHolder.PROFESSIONS_DATASOURCE, null, this.getWindow());

                if (profession != null) {
                    (new Thread(() -> {
                        UtilitiesHolder.BENEVOLE_DAO.affectProfessionToBenevole(benevole, profession);

                        Platform.runLater(() -> {
                            this.professionsListView.addAll(Arrays.asList(profession));
                        });
                    })).start();
                }
            } else
                MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), header, "Veuillez d'abords selectionner un bénévole", this.getWindow());
        });
        this.professionsListView.setCategorizerDeleter(entity -> {
            Benevole benevole = tablePrincipale.getSelectionModel().getSelectedItem();

            if (benevole != null) {
                UtilitiesHolder.BENEVOLE_DAO.detachProfessionFromBenevole(benevole, entity);
            }
        });

        // --->

        this.statutsListView.setOnCustomAdd(() -> {
            Benevole benevole = this.tablePrincipale.getSelectionModel().getSelectedItem();
            String header = "Affectation d'un statut à un bénévole";

            if (benevole != null) {
                Statut statut = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayChoiceAlert(this.getNomGestion(), header, ConstantsHolder.STATUTS_DATASOURCE, null, this.getWindow());

                if (statut != null) {
                    (new Thread(() -> {
                        UtilitiesHolder.BENEVOLE_DAO.affectStatutToBenevole(benevole, statut);

                        Platform.runLater(() -> {
                            this.statutsListView.addAll(Arrays.asList(statut));
                        });
                    })).start();
                }
            } else
                MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), header, "Veuillez d'abords selectionner un bénévole", this.getWindow());
        });
        this.statutsListView.setCategorizerDeleter(entity -> {
            Benevole benevole = tablePrincipale.getSelectionModel().getSelectedItem();

            if (benevole != null) {
                UtilitiesHolder.BENEVOLE_DAO.detachStatutFromBenevole(benevole, entity);
            }
        });

        // --->

        this.vehiculesListView.setBeforeAddCallback(() -> AffectationVehiculeBenevoleController.BENEVOLE = this.tablePrincipale.getSelectionModel().getSelectedItem());
        this.vehiculesListView.setCategorizerControllerAdder(ConstantsHolder.AFFECTATION_VEHICULE_BENEVOLE_CONTROLLER);
        this.vehiculesListView.setCategorizerDeleter(entity -> {
            Benevole benevole = tablePrincipale.getSelectionModel().getSelectedItem();

            if (benevole != null) {
                UtilitiesHolder.BENEVOLE_DAO.detachVehiculeFromBenevole(benevole, entity);
            }
        });

        this.journeesDisponiblesListView.setBeforeAddCallback(() -> NouvelleJourneeDisponibleController.SELECTED_BENEVOLE = this.tablePrincipale.getSelectionModel().getSelectedItem());
        this.journeesDisponiblesListView.setCategorizerControllerAdder(ConstantsHolder.NOUVELLE_JOURNEE_DISPONIBLE_CONTROLLER);
        this.journeesDisponiblesListView.setCategorizerDeleter(UtilitiesHolder.JOURNEE_DISPONIBLE_DAO);
    }

    private void initInitialData() {
        this.search();
    }

    @Override
    public Stage getInsertStage() {
        return StagesHolder.NOUVEAU_BENEVOLE_STAGE;
    }

    @Override
    public Benevole getLastInsertedEntity() {
        return NouveauBenevoleController.LAST_INSERTED_ENTITY;
    }

    @Override
    public String getNomGestion() {
        return "Gestion des bénévoles";
    }

    @Override
    public String getDeleteHeader() {
        return "Suppression d'un bénévole";
    }

    @Override
    public void onDeleteCallback(Benevole entity) {
        UtilitiesHolder.BENEVOLE_DAO.deleteEntity(entity);
    }

    @Override
    public void onBeforeUpdateCallback(Benevole entity) {
    }

    @Override
    public void onUpdateCallback(Benevole entity) {
        UtilitiesHolder.BENEVOLE_DAO.updateEntity(entity);
    }

    @Override
    protected List<Benevole> selectFromDatabase() throws NullPointerException {
        String names = this.nomPrenom.getText();
        JourDeSemaine journee = this.journeeDisponible.getSelectionModel().getSelectedItem();
        Vehicule vehicule = this.vehicule.getSelectionModel().getSelectedItem();
        Cellule cellule = this.cellule.getSelectionModel().getSelectedItem();
        Profession profession = this.profession.getSelectionModel().getSelectedItem();
        Statut statut = this.statut.getSelectionModel().getSelectedItem();

        return UtilitiesHolder.BENEVOLE_DAO.getSpecifiedBenevoles(names, journee, vehicule, cellule, profession, statut);
    }
}
