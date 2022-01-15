package mssmfactory.KafylElYatim.MVC.Controllers.EvenementDecharge;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.mspavenumerique.MSSimplePaveNumeriqueStage;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.Action;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.EvenementDecharge;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Habitat.EtatHabitat;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Habitat.TypeBien;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.SituationSociale.NiveauVie;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers.EvenementsController;
import mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers.TableTuteursController;
import mssmfactory.KafylElYatim.MVC.Controllers.Utils.TableTuteursContainer;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.ComponentsHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class EvenementsDechargesPanelController extends EvenementsController<EvenementDecharge, Tuteur>
        implements TableTuteursContainer {

    @FXML
    private JFXComboBox<Region> regionFilter;

    @FXML
    private JFXComboBox<NiveauVie> niveauDeVieFilter;

    @FXML
    private JFXComboBox<EtatHabitat> etatHabitatFilter;

    @FXML
    private JFXComboBox<TypeBien> typeBienFilter;

    @FXML
    private TableTuteursController tableTuteursController;

    private Integer buffer = -1;
    private MSSimplePaveNumeriqueStage msSimplePaveNumeriqueStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        this.initComboBoxes();
    }

    @Override
    public void initList() {
        this.listeEvenements.setCategorizerControllerAdder(ConstantsHolder.NOUVEAU_EVENEMENT_DECHARGE_CONTROLLER);
        this.listeEvenements.setCategorizerDeleter(UtilitiesHolder.EVENEMENT_DECHARGE_DAO);
    }

    private void initComboBoxes() {
        this.regionFilter.setItems(ConstantsHolder.REGIONS_DATASOURCE);
        this.niveauDeVieFilter.setItems(FXCollections.observableArrayList(NiveauVie.values()));
        this.typeBienFilter.setItems(FXCollections.observableArrayList(TypeBien.values()));
        this.etatHabitatFilter.setItems(FXCollections.observableArrayList(EtatHabitat.values()));
    }

    @Override
    public void onRefreshRequest() {
        this.regionFilter.setValue(null);
        this.niveauDeVieFilter.setValue(null);
        this.typeBienFilter.setValue(null);
        this.etatHabitatFilter.setValue(null);
    }

    @Override
    public TableView<Tuteur> getPersonnesTableView() {
        return this.tableTuteursController.getResultTable();
    }

    @Override
    public List<EvenementDecharge> fetchInitialEvents() {
        return UtilitiesHolder.EVENEMENT_DECHARGE_DAO.getAll();
    }

    @Override
    public void initBindings() {
        ComponentsHolder.SEARCH_REGION_TUTEUR.valueProperty().bindBidirectional(this.regionFilter.valueProperty());
        ComponentsHolder.SEARCH_TUTORS.textProperty().bindBidirectional(this.nomPrenomFilter.textProperty());
        ComponentsHolder.SEARCH_ETAT_HABITAT.valueProperty().bindBidirectional(this.etatHabitatFilter.valueProperty());
        ComponentsHolder.SEARCH_TYPE_BIEN.valueProperty().bindBidirectional(this.typeBienFilter.valueProperty());
        ComponentsHolder.SEARCH_NIVEAU_VIE_TUTEUR.valueProperty()
                .bindBidirectional(this.niveauDeVieFilter.valueProperty());
    }

    @Override
    public String getNomGestion() {
        return "Gestion des décharges";
    }

    @Override
    public Integer getQuantiteeAction(Tuteur tuteur) {
        return this.buffer;
    }

    @Override
    public Collection<Tuteur> constructTuteursFromPersonnes(List<Tuteur> personnes) {
        return personnes;
    }

    @Override
    public void onPrintActions(List<Action> actions, EvenementDecharge evenementDecharge) {
        UtilitiesHolder.PRINTING_HANDLER.printBonsEvenementDecharge(evenementDecharge, actions);
    }

    public void onBeforePrintActions() {
        if (this.msSimplePaveNumeriqueStage == null)
            this.msSimplePaveNumeriqueStage = new MSSimplePaveNumeriqueStage(this.getWindow());

        this.buffer = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayIntegerAlert(this.msSimplePaveNumeriqueStage, this.getNomGestion(),
                "Quantitée du lot", "Veuillez saisir la quantitée de chaque lot", "1", this.getWindow());
    }

    @Override
    public void setTableView(TableView<Tuteur> tableView) {
        tableView.setItems(ConstantsHolder.TUTORS_DATASOURCE);
    }

    @Override
    public void onTuteurArchiveSwitched(Tuteur tuteur) {
    }
}
