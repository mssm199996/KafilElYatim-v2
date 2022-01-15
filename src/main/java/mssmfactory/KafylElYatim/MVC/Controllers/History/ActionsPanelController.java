package mssmfactory.KafylElYatim.MVC.Controllers.History;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import msjfxuicomponents.mvc.compositions.ComposedController;
import msjfxuicomponents.mvc.compositions.SimpleTableComposedController;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.Action;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.Evenement;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.EvenementBon;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.EvenementDecharge;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers.TableTuteursController;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssoftutils.others.SearchController;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ActionsPanelController extends SearchController<Action> implements Initializable, SimpleTableComposedController {

    public static ObservableList<Evenement> HISTORY_EVENTS = FXCollections.observableArrayList();
    private ObservableList<Tuteur> tutors = FXCollections.observableArrayList();

    @FXML
    private JFXComboBox<Evenement> evenementFilter;

    @FXML
    private TableTuteursController tableTuteursController;

    @FXML
    public void printTutors() {
        UtilitiesHolder.PRINTING_HANDLER.printTutorsList(this.tutors);
    }

    @FXML
    public void printTutorsListWithRealOrphelinsNumber() {
        UtilitiesHolder.PRINTING_HANDLER.printTutorsListWithRealOrphelinsNumber(this.tutors);
    }

    @FXML
    public void refresh() {
        List<EvenementDecharge> evenementDecharges = UtilitiesHolder.EVENEMENT_DECHARGE_DAO.getAll();
        List<EvenementBon> evenementBons = UtilitiesHolder.EVENEMENT_BON_DAO.getAll();

        this.evenementFilter.getItems().clear();
        this.evenementFilter.getItems().addAll(evenementDecharges);
        this.evenementFilter.getItems().addAll(evenementBons);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initComboBoxes();
        this.initTables();
    }

    private void initTables() {
        this.tableTuteursController.setItems(this.tutors);
    }

    private void initComboBoxes() {
        this.evenementFilter.setItems(ActionsPanelController.HISTORY_EVENTS);
        this.evenementFilter.getSelectionModel().selectedItemProperty().addListener(e -> {
            this.search();
        });
    }

    @Override
    protected List<Action> selectFromDatabase() throws NullPointerException {
        Evenement evenement = this.evenementFilter.getSelectionModel().getSelectedItem();
        this.tutors.clear();

        if (evenement != null) {
            List<Action> actions = UtilitiesHolder.ACTION_DAO.findByEvent(evenement);

            for (Action action : actions)
                this.tutors.add(action.getTuteur());

            return actions;
        } else return new LinkedList<>();
    }

    @Override
    public void setTableView(TableView tableView) {

    }
}
