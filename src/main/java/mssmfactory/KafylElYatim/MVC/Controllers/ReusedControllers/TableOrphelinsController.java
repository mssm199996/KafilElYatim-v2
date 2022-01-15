package mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.cells.DatePickerCell;
import msjfxuicomponents.mvc.SimpleTableController;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin.Genre;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.MVC.Controllers.Utils.TableOrphelinsContainer;
import mssmfactory.KafylElYatim.Utilities.Components.OrphelinTableRow;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

import java.time.LocalDate;

public class TableOrphelinsController extends SimpleTableController<TableOrphelinsContainer, Orphelin> {

    @FXML
    private TableColumn<Orphelin, String> nomColumn;

    @FXML
    private TableColumn<Orphelin, String> prenomColumn;

    @FXML
    private TableColumn<Orphelin, LocalDate> ddnColumn;

    @FXML
    private TableColumn<Orphelin, Integer> ageColumn;

    @FXML
    private TableColumn<Orphelin, Genre> genreColumn;

    @FXML
    private TableColumn<Orphelin, String> tuteurColumn;

    @FXML
    private TableColumn<Orphelin, Region> regionColumn;

    @FXML
    private TableColumn<Orphelin, String> relationTuteurColumn;

    @FXML
    private MenuItem authorizeMenuItem;

    @FXML
    private MenuItem dismissMenuItem;

    @Override
    public void initTables() {
        super.initTables();

        this.resultTable.setRowFactory(call -> {
            return new OrphelinTableRow();
        });

        this.resultTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            this.authorizeMenuItem.setDisable(true);
            this.dismissMenuItem.setDisable(true);

            if (newValue != null) {
                this.authorizeMenuItem.setDisable(newValue.isAuthorized());
                this.dismissMenuItem.setDisable(!newValue.isAuthorized());
            }
        });

        this.nomColumn.setCellValueFactory(call -> {
            return call.getValue().nomProperty();
        });
        this.nomColumn.setCellFactory(this.textCellUpdater);

        this.prenomColumn.setCellValueFactory(call -> {
            return call.getValue().prenomProperty();
        });
        this.prenomColumn.setCellFactory(this.textCellUpdater);

        this.ageColumn.setCellValueFactory(call -> {
            return call.getValue().ageProperty().asObject();
        });

        this.ddnColumn.setCellValueFactory(call -> {
            return call.getValue().ddnProperty();
        });
        this.ddnColumn.setCellFactory(call -> {
            return new DatePickerCell<Orphelin>() {

                @Override
                public void onUpdateRequest(Orphelin entity) {
                    updateEntityRow(entity);
                }
            };
        });

        this.genreColumn.setCellValueFactory(call -> {
            return call.getValue().genreProperty();
        });

        this.regionColumn.setCellValueFactory(call -> {
            if (call.getValue().getTuteur() == null)
                return new SimpleObjectProperty<>();

            return call.getValue().getTuteur().regionProperty();
        });

        this.relationTuteurColumn.setCellValueFactory(call -> {
            return call.getValue().relationOrphelinTuteurProperty();
        });
        this.relationTuteurColumn.setCellFactory(this.textCellUpdater);

        this.tuteurColumn.setCellValueFactory(call -> {
            return call.getValue().tuteurProperty();
        });
    }

    @FXML
    void authorizeOrphen(ActionEvent event) {
        Orphelin orphelin = this.resultTable.getSelectionModel().getSelectedItem();

        if (orphelin != null) {
            Integer age = orphelin.getAge();
            Genre genre = orphelin.getGenre();

            Integer ageLimit = genre == Genre.Feminin ? ConstantsHolder.FEMALE_AGE_LIMIT
                    : ConstantsHolder.MALE_AGE_LIMIT;

            int dAge = age - ageLimit;

            if (dAge >= 0) {
                orphelin.setAuthorized(true);

                (new Thread(() -> {
                    this.onUpdateCallback(orphelin);
                })).start();
            } else
                MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Gestion des orphelins",
                        "Autorisation d'un orphelin", "L'orphelin selectionné(e) n'est pas encore suffisement agé(e)",
                        this.getWindow());
        } else
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Gestion des orphelins",
                    "Autorisation d'un orphelin agée", "Veuillez d'abors selectionner un orphelin", this.getWindow());
    }

    @FXML
    void dismissOrphen(ActionEvent event) {
        Orphelin orphelin = this.resultTable.getSelectionModel().getSelectedItem();

        if (orphelin != null) {
            orphelin.setAuthorized(false);

            (new Thread(() -> {
                this.onUpdateCallback(orphelin);
            })).start();
        } else
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Gestion des orphelins",
                    "Autorisation d'un orphelin agée", "Veuillez d'abors selectionner un orphelin", this.getWindow());

    }

    @Override
    public void onUpdateCallback(Orphelin entity) {
        UtilitiesHolder.ORPHELIN_DAO.updateEntity(entity);
    }

    private Window getWindow() {
        return this.resultTable.getScene().getWindow();
    }
}
