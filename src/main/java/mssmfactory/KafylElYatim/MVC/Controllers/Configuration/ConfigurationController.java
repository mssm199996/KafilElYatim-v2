package mssmfactory.KafylElYatim.MVC.Controllers.Configuration;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.compositions.ComposedController;
import msjfxuicomponents.mvc.compositions.SimpleTableComposedController;
import msjfxuicomponents.mvc.msconfiguration.MSConfigurationController;
import msjfxuicomponents.uicomponents.MSCategorizerCrudListTitledPane;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Cellule;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Profession;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Statut;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Vehicule;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Appareillage;
import mssmfactory.KafylElYatim.DomainModel.Others.Compte;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers.TableRegionsController;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationController extends MSConfigurationController<Compte> implements SimpleTableComposedController<TableRegionsController> {

    @FXML
    private MSCategorizerCrudListTitledPane<Vehicule> vehiculesCrudList;

    @FXML
    private TableRegionsController tableRegionsController;

    @FXML
    private MSCategorizerCrudListTitledPane<Appareillage> appareillageCrudList;

    @FXML
    private MSCategorizerCrudListTitledPane<Profession> professionsCrudList;

    @FXML
    private MSCategorizerCrudListTitledPane<Cellule> cellulesCrudList;

    @FXML
    private MSCategorizerCrudListTitledPane<Statut> statutsCrudList;

    @FXML
    public void addRegion() {
        Region region = new Region();
        region.setDesignation("Nouvelle region");
        region.setCode(0);

        (new Thread(() -> {
            UtilitiesHolder.REGION_DAO.insertEntity(region);

            Platform.runLater(() -> {
                ConstantsHolder.REGIONS_DATASOURCE.add(region);
            });
        })).start();
    }

    @FXML
    private void deleteRegion() {
        Region region = this.tableRegionsController.getSelectionModel().getSelectedItem();

        if (region != null) {
            boolean confirmed = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert("Gestion des regions", "Suppression d'une region", "Voulez vous vraiment supprimer la région: " + region.toString() + " ?", this.getWindow());

            if (confirmed) {
                UtilitiesHolder.REGION_DAO.deleteEntity(region);

                Platform.runLater(() -> {
                    ConstantsHolder.REGIONS_DATASOURCE.remove(region);
                });
            }
        } else
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Gestion des regions", "Suppression d'une région", "Veuillez d'abords selectionner une région", this.getWindow());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.initCategorizerCruds();
    }

    private void initCategorizerCruds() {
        this.tableRegionsController.setItems(ConstantsHolder.REGIONS_DATASOURCE);
        this.tableRegionsController.addAll(UtilitiesHolder.REGION_DAO.getAll());

        this.vehiculesCrudList.setItems(ConstantsHolder.VEHICULES_DATASOURCE);
        this.vehiculesCrudList.addAll(UtilitiesHolder.VEHICULE_DAO.getAll());
        this.vehiculesCrudList.setCategorizerAdder(UtilitiesHolder.VEHICULE_DAO);
        this.vehiculesCrudList.setCategorizerUpdater(UtilitiesHolder.VEHICULE_DAO);
        this.vehiculesCrudList.setCategorizerDeleter(entity -> {
            UtilitiesHolder.VEHICULE_DAO.onDelete(entity);
            UtilitiesHolder.VEHICULE_DAO.deleteEntity(entity);
        });

        this.appareillageCrudList.setItems(ConstantsHolder.APPAREILLAGES_DATASOURCE);
        this.appareillageCrudList.addAll(UtilitiesHolder.APPAREILLAGE_DAO.getAll());
        this.appareillageCrudList.setCategorizerAdder(UtilitiesHolder.APPAREILLAGE_DAO);
        this.appareillageCrudList.setCategorizerUpdater(UtilitiesHolder.APPAREILLAGE_DAO);
        this.appareillageCrudList.setCategorizerDeleter(entity -> {
            UtilitiesHolder.APPAREILLAGE_DAO.onDelete(entity);
            UtilitiesHolder.APPAREILLAGE_DAO.deleteEntity(entity);
        });

        this.professionsCrudList.setItems(ConstantsHolder.PROFESSIONS_DATASOURCE);
        this.professionsCrudList.addAll(UtilitiesHolder.PROFESSION_DAO.getAll());
        this.professionsCrudList.setCategorizerAdder(UtilitiesHolder.PROFESSION_DAO);
        this.professionsCrudList.setCategorizerDeleter(UtilitiesHolder.PROFESSION_DAO);
        this.professionsCrudList.setCategorizerUpdater(UtilitiesHolder.PROFESSION_DAO);
        this.professionsCrudList.setCategorizerDeleter(entity -> {
            UtilitiesHolder.PROFESSION_DAO.onDelete(entity);
            UtilitiesHolder.PROFESSION_DAO.deleteEntity(entity);
        });

        this.statutsCrudList.setItems(ConstantsHolder.STATUTS_DATASOURCE);
        this.statutsCrudList.addAll(UtilitiesHolder.STATUT_DAO.getAll());
        this.statutsCrudList.setCategorizerAdder(UtilitiesHolder.STATUT_DAO);
        this.statutsCrudList.setCategorizerDeleter(UtilitiesHolder.STATUT_DAO);
        this.statutsCrudList.setCategorizerUpdater(UtilitiesHolder.STATUT_DAO);
        this.statutsCrudList.setCategorizerDeleter(entity -> {
            UtilitiesHolder.STATUT_DAO.onDelete(entity);
            UtilitiesHolder.STATUT_DAO.deleteEntity(entity);
        });

        this.cellulesCrudList.setItems(ConstantsHolder.CELLULES_DATASOURCE);
        this.cellulesCrudList.addAll(UtilitiesHolder.CELLULE_DAO.getAll());
        this.cellulesCrudList.setCategorizerAdder(UtilitiesHolder.CELLULE_DAO);
        this.cellulesCrudList.setCategorizerDeleter(UtilitiesHolder.CELLULE_DAO);
        this.cellulesCrudList.setCategorizerUpdater(UtilitiesHolder.CELLULE_DAO);
        this.cellulesCrudList.setCategorizerDeleter(entity -> {
            UtilitiesHolder.CELLULE_DAO.onDelete(entity);
            UtilitiesHolder.CELLULE_DAO.deleteEntity(entity);
        });
    }

    @Override
    public Compte getMainConfiguration() {
        return ConstantsHolder.LOGGED_IN_ACCOUNT;
    }

    @Override
    public void initMainConfiguration(Compte mainConfiguration) {
    }

    @Override
    public void updateMainConfiguration(Compte mainConfiguration) {
        UtilitiesHolder.CONFIGURATION_DAO.updateArray(mainConfiguration);
    }

    @Override
    public void updateCheminSauvegardeInsideSessionFactoryHandler(String cheminSauvegarde) {
        UtilitiesHolder.APPLICATION_SESSION_FACTORY_HANDLER.setSauvegardesPath(cheminSauvegarde);
    }

    @Override
    public void updateMysqlFolderInsideSessionFactoryHandler(String cheminMysql) {
        UtilitiesHolder.APPLICATION_SESSION_FACTORY_HANDLER.setMysqlFolder(cheminMysql);
    }

    @Override
    public void setTableView(TableView<TableRegionsController> tableView) {
    }
}
