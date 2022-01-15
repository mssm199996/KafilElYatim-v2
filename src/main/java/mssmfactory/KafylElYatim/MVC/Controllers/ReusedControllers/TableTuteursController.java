package mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.cells.WritableValueMSComboBoxTableCell;
import msjfxuicomponents.mvc.SimpleTableController;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.MVC.Controllers.Utils.TableTuteursContainer;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

import java.time.LocalDate;

public class TableTuteursController extends SimpleTableController<TableTuteursContainer, Tuteur> {

    @FXML
    private TableColumn<Tuteur, String> numeroDossierColumn;

    @FXML
    private TableColumn<Tuteur, String> nomColumn;

    @FXML
    private TableColumn<Tuteur, String> prenomColumn;

    @FXML
    private TableColumn<Tuteur, LocalDate> ddnColumn;

    @FXML
    private TableColumn<Tuteur, Region> regionColumn;

    @FXML
    private TableColumn<Tuteur, Integer> nbOrphelinColumn;

    @FXML
    private TableColumn<Tuteur, String> adresseColumn;

    @FXML
    private TableColumn<Tuteur, String> adresseIndicationColumn;

    @FXML
    private TableColumn<Tuteur, String> telephoneFixColumn;

    @FXML
    private TableColumn<Tuteur, String> telephoneMobileColumn;

    @FXML
    private TableColumn<Tuteur, String> telephone1Column;

    @FXML
    private TableColumn<Tuteur, String> telephone2Column;

    @FXML
    private TableColumn<Tuteur, String> nomPereOrphelinsColumn;

    @FXML
    private TableColumn<Tuteur, String> prenomPereOrphelinsColumn;

    @FXML
    public void desarchiver() {
        this.switchArchiveState(false);
    }

    @FXML
    public void archiver() {
        this.switchArchiveState(true);
    }

    public void switchArchiveState(boolean archivePurpose) {
        String header = "Archivage d'un tuteur";
        Tuteur tuteur = this.resultTable.getSelectionModel().getSelectedItem();

        if (tuteur != null) {
            if (archivePurpose && tuteur.getArchived()) {
                MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), header, "Le tuteur " + tuteur.toString() + " est déja archivé(e)", this.getWindow());

                return;
            }

            if (!archivePurpose && !tuteur.getArchived()) {
                MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), header, "Le tuteur " + tuteur.toString() + " est déja active", this.getWindow());

                return;
            }

            boolean confirmed = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(), header, "Voulez vous vraiment archiver le tuteur: " + tuteur.toString() + " ?", this.getWindow());

            if (confirmed) {
                (new Thread(() -> {
                    tuteur.setArchived(archivePurpose);

                    UtilitiesHolder.TUTEUR_DAO.updateArray(tuteur);

                    Platform.runLater(() -> {
                        this.resultTable.getItems().remove(tuteur);
                        this.parent.onTuteurArchiveSwitched(tuteur);
                    });
                })).start();
            }
        } else
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), header, "Veuillez d'abords selectionner un tuteur !", this.getWindow());

    }

    private String getNomGestion() {
        return "Gestion des tuteurs";
    }

    private Stage getWindow() {
        return (Stage) this.resultTable.getScene().getWindow();
    }

    @Override
    public void initTables() {
        super.initTables();

        this.numeroDossierColumn.setCellFactory(this.textCellUpdater);
        this.numeroDossierColumn.setCellValueFactory(call -> {
            return call.getValue().nDossierBureautiqueProperty();
        });

        this.nomColumn.setCellFactory(this.textCellUpdater);
        this.nomColumn.setCellValueFactory(call -> {
            return call.getValue().nomProperty();
        });

        this.prenomColumn.setCellFactory(this.textCellUpdater);
        this.prenomColumn.setCellValueFactory(call -> {
            return call.getValue().prenomProperty();
        });

        this.ddnColumn.setCellValueFactory(call -> {
            return call.getValue().ddnProperty();
        });

        this.regionColumn.setCellValueFactory(call -> {
            return call.getValue().regionProperty();
        });
        this.regionColumn.setCellFactory(call -> {
            return new WritableValueMSComboBoxTableCell<Tuteur, Region>(ConstantsHolder.REGIONS_DATASOURCE) {

                @Override
                public void onElementChanged(Tuteur row, Region newValue) {
                    UtilitiesHolder.TUTEUR_DAO.update(row);
                }
            };
        });

        this.nbOrphelinColumn.setCellValueFactory(call -> {
            return call.getValue().nbOrphelinsProperty().asObject();
        });

        this.adresseColumn.setCellFactory(this.textCellUpdater);
        this.adresseColumn.setCellValueFactory(call -> {
            return call.getValue().adresseProperty();
        });

        this.adresseIndicationColumn.setCellFactory(this.textCellUpdater);
        this.adresseIndicationColumn.setCellValueFactory(call -> {
            return call.getValue().secondeAdresseProperty();
        });

        this.telephoneFixColumn.setCellFactory(this.textCellUpdater);
        this.telephoneFixColumn.setCellValueFactory(call -> {
            return call.getValue().nTelFixProperty();
        });

        this.telephoneMobileColumn.setCellFactory(this.textCellUpdater);
        this.telephoneMobileColumn.setCellValueFactory(call -> {
            return call.getValue().nTelMobProperty();
        });

        this.telephone1Column.setCellFactory(this.textCellUpdater);
        this.telephone1Column.setCellValueFactory(call -> {
            return call.getValue().nTel1Property();
        });

        this.telephone2Column.setCellFactory(this.textCellUpdater);
        this.telephone2Column.setCellValueFactory(call -> {
            return call.getValue().nTel2Property();
        });

        this.nomPereOrphelinsColumn.setCellFactory(this.textCellUpdater);
        this.nomPereOrphelinsColumn.setCellValueFactory(call -> {
            return call.getValue().nomPereOrphelinsProperty();
        });

        this.prenomPereOrphelinsColumn.setCellFactory(this.textCellUpdater);
        this.prenomPereOrphelinsColumn.setCellValueFactory(call -> {
            return call.getValue().prenomPereOrphelinsProperty();
        });
    }

    @Override
    public void onUpdateCallback(Tuteur entity) {
        UtilitiesHolder.TUTEUR_DAO.updateEntity(entity);
    }
}
