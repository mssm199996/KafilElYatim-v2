package mssmfactory.KafylElYatim.MVC.Controllers.EvenementBon;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import msjfxuicomponents.uicomponents.MSCategorizerCrudListTitledPane;
import msjfxuicomponents.uicomponents.MSSimpleCategorizerListTitledPane;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.Action;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.EvenementBon;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierScolaire.NiveauScolaire;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin.Genre;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers.EvenementsController;
import mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers.TableOrphelinsController;
import mssmfactory.KafylElYatim.MVC.Controllers.Utils.TableOrphelinsContainer;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.ComponentsHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class EventsBonsPanelController extends EvenementsController<EvenementBon, Orphelin>
		implements TableOrphelinsContainer {

	@FXML
	private JFXDatePicker dateMinFilter;

	@FXML
	private JFXDatePicker dateMaxFilter;

	@FXML
	private JFXTextField ageMinFilter;

	@FXML
	private JFXTextField ageMaxFilter;

	@FXML
	private JFXComboBox<Genre> genreFilter;

	@FXML
	private JFXComboBox<NiveauScolaire> niveauScolaireFilter;

	@FXML
	private JFXComboBox<Integer> anneeScolaireFilter;

	@FXML
	private MSCategorizerCrudListTitledPane<EvenementBon> listeEvenements;

	@FXML
	private TableOrphelinsController tableOrphelinsController;

	@FXML
	private JFXTextField nomPrenomFilter;

	@FXML
	private MSSimpleCategorizerListTitledPane<Orphelin> listePersonnesSelectionnees;

	private Map<Tuteur, List<Orphelin>> buffer = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		this.initComboBoxes();
	}

	private void initComboBoxes() {
		this.genreFilter.setItems(FXCollections.observableArrayList(Genre.values()));
		this.anneeScolaireFilter.setItems(FXCollections.observableArrayList(new Integer[] { 1, 2, 3, 4, 5, 6 }));
		this.niveauScolaireFilter.setItems(FXCollections.observableArrayList(NiveauScolaire.values()));
	}

	@Override
	public void initList() {
		this.listeEvenements.setCategorizerControllerAdder(ConstantsHolder.NOUVEAU_EVENEMENT_BON_CONTROLLER);
		this.listeEvenements.setCategorizerDeleter(UtilitiesHolder.EVENEMENT_BON_DAO);
	}

	@Override
	public void onRefreshRequest() {
		this.ageMaxFilter.setText("");
		this.ageMinFilter.setText("");
		this.anneeScolaireFilter.setValue(null);
		this.dateMaxFilter.setValue(null);
		this.dateMinFilter.setValue(null);
		this.genreFilter.setValue(null);
		this.niveauScolaireFilter.setValue(null);
	}

	@Override
	public TableView<Orphelin> getPersonnesTableView() {
		return this.tableOrphelinsController.getResultTable();
	}

	@Override
	public List<EvenementBon> fetchInitialEvents() {
		return UtilitiesHolder.EVENEMENT_BON_DAO.getAll();
	}

	@Override
	public void initBindings() {
		ComponentsHolder.SEARCH_NOM_ORPHELIN.textProperty().bindBidirectional(this.nomPrenomFilter.textProperty());
		ComponentsHolder.SEARCH_AGE_MIN_ORPHELIN.textProperty().bindBidirectional(this.ageMinFilter.textProperty());
		ComponentsHolder.SEARCH_AGE_MAX_ORPHELIN.textProperty().bindBidirectional(this.ageMaxFilter.textProperty());
		ComponentsHolder.SEARCH_GENRE_ORPHELIN.valueProperty().bindBidirectional(this.genreFilter.valueProperty());
		ComponentsHolder.SEARCH_DDN_MAX.valueProperty().bindBidirectional(this.dateMaxFilter.valueProperty());
		ComponentsHolder.SEARCH_DDN_MIN.valueProperty().bindBidirectional(this.dateMinFilter.valueProperty());
		ComponentsHolder.SEARCH_ANNEE_SCOLAIRE.valueProperty()
				.bindBidirectional(this.anneeScolaireFilter.valueProperty());
		ComponentsHolder.SEARCH_NIVEAU_SCOLAIRE.valueProperty()
				.bindBidirectional(this.niveauScolaireFilter.valueProperty());
	}

	@Override
	public String getNomGestion() {
		return "Gestion des bon d'achats";
	}

	@Override
	public Integer getQuantiteeAction(Tuteur tuteur) {
		return this.buffer.get(tuteur).size();
	}

	@Override
	public Collection<Tuteur> constructTuteursFromPersonnes(List<Orphelin> personnes) {
		return this.buffer.keySet();
	}

	@Override
	public void onPrintActions(List<Action> actions, EvenementBon evenementBon) {
		UtilitiesHolder.PRINTING_HANDLER.printBonsEvenementBon(evenementBon, this.buffer);
	}

	@Override
	public void onBeforePrintActions() {
		List<Orphelin> orphelins = this.listePersonnesSelectionnees.getItems();

		this.buffer = this.fromListToMap(orphelins);
	}

	private Map<Tuteur, List<Orphelin>> fromListToMap(List<Orphelin> orphelins) {
		Map<Tuteur, List<Orphelin>> result = new HashMap<Tuteur, List<Orphelin>>();

		for (Orphelin orphelin : orphelins) {
			try {
				List<Orphelin> subOrphelins = result.get(orphelin.getTuteur());

				subOrphelins.add(orphelin);
			} catch (NullPointerException exp) {
				List<Orphelin> subOrphelins = new LinkedList<Orphelin>();
				subOrphelins.add(orphelin);

				result.put(orphelin.getTuteur(), subOrphelins);
			}
		}

		return result;
	}

	@Override
	public void setTableView(TableView<Orphelin> tableView) {
		tableView.setItems(ConstantsHolder.ORPHELINS_DATASOURCE);
	}
}
