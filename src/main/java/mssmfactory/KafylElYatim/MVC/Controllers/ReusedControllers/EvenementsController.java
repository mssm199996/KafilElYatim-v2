package mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.cells.DeleteListCell;
import msjfxuicomponents.rows.DoubleClickingRow;
import msjfxuicomponents.uicomponents.MSCategorizerCrudListTitledPane;
import msjfxuicomponents.uicomponents.MSSimpleCategorizerListTitledPane;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.Action;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.Evenement;
import mssmfactory.KafylElYatim.DomainModel.Others.IPersonne;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.MVC.Controllers.History.ActionsPanelController;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

public abstract class EvenementsController<E extends Evenement, P extends IPersonne> implements Initializable {

	@FXML
	protected MSCategorizerCrudListTitledPane<E> listeEvenements;

	@FXML
	protected JFXTextField nomPrenomFilter;

	@FXML
	protected MSSimpleCategorizerListTitledPane<P> listePersonnesSelectionnees;

	@FXML
	void confirmAndPrint() {
		E evenement = this.listeEvenements.getSelectedItem();

		if (evenement != null) {
			this.onBeforePrintActions();

			List<P> personnes = this.listePersonnesSelectionnees.getItems();
			Collection<Tuteur> tuteurs = this.constructTuteursFromPersonnes(personnes);
			List<Action> actions = new ArrayList<>(tuteurs.size());

			for (Tuteur tuteur : tuteurs) {
				Integer quantiteeAction = this.getQuantiteeAction(tuteur);

				Action action = new Action();
				action.setDateAction(LocalDate.now());
				action.setEvenement(evenement);
				action.setTuteur(tuteur);
				action.setQuantiteAction(quantiteeAction);

				actions.add(action);
			}

			(new Thread(() -> {
				UtilitiesHolder.ACTION_DAO.insertCollection(actions);
			})).start();

			this.onPrintActions(actions, evenement);
			this.listePersonnesSelectionnees.clear();
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(),
					"Confirmation et impression", "Veuillez d'abords selectionner un évenement !", this.getWindow());
	}

	@FXML
	void addAll(ActionEvent event) {
		List<P> personnes = this.getPersonnesTableView().getItems();

		this.listePersonnesSelectionnees.addAllIfNotContains(personnes);
	}

	@FXML
	void deleteAll(ActionEvent event) {
		List<P> personnes = this.getPersonnesTableView().getItems();

		this.listePersonnesSelectionnees.removeAll(personnes);
	}

	@FXML
	void refresh(ActionEvent event) {
		this.nomPrenomFilter.setText("");

		this.onRefreshRequest();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<E> initialData = this.fetchInitialEvents();

		ActionsPanelController.HISTORY_EVENTS.addAll(initialData);
		this.listeEvenements.addAll(initialData);

		this.listePersonnesSelectionnees.setCellFactory(call -> {
			return new DeleteListCell<P>(this.getNomGestion(), this.getWindow()) {
				@Override
				public void doAfterDelete(P object) {
				}
			};
		});

		this.getPersonnesTableView().setEditable(false);
		this.getPersonnesTableView().setRowFactory(call -> {
			return new DoubleClickingRow<P>() {

				@Override
				public void onMouseDoubleClicked(P object) {
					listePersonnesSelectionnees.addIfNotContains(object);
				}
			};
		});
		this.initList();
		this.initBindings();
	}

	public abstract void initList();

	public abstract void onRefreshRequest();

	public abstract TableView<P> getPersonnesTableView();

	public abstract List<E> fetchInitialEvents();

	public abstract void initBindings();

	public abstract String getNomGestion();

	public abstract Integer getQuantiteeAction(Tuteur tuteur);

	public abstract Collection<Tuteur> constructTuteursFromPersonnes(List<P> personnes);

	public abstract void onPrintActions(List<Action> actions, E evenement);

	public void onBeforePrintActions() {
	}

	public Window getWindow() {
		return this.listeEvenements.getScene().getWindow();
	}
}
