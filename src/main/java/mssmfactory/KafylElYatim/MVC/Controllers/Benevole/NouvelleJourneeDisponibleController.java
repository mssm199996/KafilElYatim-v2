package mssmfactory.KafylElYatim.MVC.Controllers.Benevole;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.AddController;
import msjfxuicomponents.others.ICategorizerControllerAdder;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Benevole;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.JourneeDisponible;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.JourneeDisponible.JourDeSemaine;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class NouvelleJourneeDisponibleController extends AddController<JourneeDisponible>
		implements ICategorizerControllerAdder<JourneeDisponible> {

	public static JourneeDisponible LAST_INSERTED_JOURNEE_DISPONIBLE = null;
	public static Benevole SELECTED_BENEVOLE = null;

	@FXML
	private JFXComboBox<JourDeSemaine> jourSemaine;

	@FXML
	private JFXTimePicker heureDebut;

	@FXML
	private JFXTimePicker heureFin;

	@FXML
	private Label nomBenevole;

	@Override
	public void confirm() throws Exception {
		Benevole benevole = NouvelleJourneeDisponibleController.SELECTED_BENEVOLE;

		if (benevole != null) {
			JourDeSemaine jourDeSemaine = this.jourSemaine.getValue();
			LocalTime heureDebut = this.heureDebut.getValue();
			LocalTime heureFin = this.heureFin.getValue();

			JourneeDisponible journeeDisponible = new JourneeDisponible();
			journeeDisponible.setBenevole(benevole);
			journeeDisponible.setHeureDebut(heureDebut);
			journeeDisponible.setHeureFin(heureFin);
			journeeDisponible.setJourSemaine(jourDeSemaine);

			(new Thread(() -> {
				this.onInsertCallback(journeeDisponible);
			})).start();

			this.setLastInsertedEntity(journeeDisponible);
			this.getWindow().hide();
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(),
					"Nouvelle journée disponible", "Erreur... veuillez d'abords selectionner un bénévole !",
					this.getWindow());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		this.initComboBoxes();

		ConstantsHolder.NOUVELLE_JOURNEE_DISPONIBLE_CONTROLLER = this;
	}

	public void initComboBoxes() {
		this.jourSemaine.setItems(FXCollections.observableArrayList(JourDeSemaine.values()));
	}

	@Override
	public Window getWindow() {
		return StagesHolder.NOUVELLE_JOURNEE_DISPONIBLE_STAGE;
	}

	@Override
	public String getNomGestion() {
		return "Gestion des bénévoles";
	}

	@Override
	public String getInsertHeader() {
		return "Nouvelle journée disponible";
	}

	@Override
	public void setLastInsertedEntity(JourneeDisponible entity) {
		NouvelleJourneeDisponibleController.LAST_INSERTED_JOURNEE_DISPONIBLE = entity;
	}

	@Override
	public void onInsertCallback(JourneeDisponible entity) {
		UtilitiesHolder.JOURNEE_DISPONIBLE_DAO.insertEntity(entity);
	}

	@Override
	public Stage getStage() {
		return (Stage) this.getWindow();
	}

	@Override
	public JourneeDisponible getConstructedCategorizer() {
		return NouvelleJourneeDisponibleController.LAST_INSERTED_JOURNEE_DISPONIBLE;
	}

	@Override
	public void onShowingResetResult() {
		super.onShowingResetResult();

		Benevole benevole = NouvelleJourneeDisponibleController.SELECTED_BENEVOLE;

		if (benevole != null)
			this.nomBenevole.setText(benevole.getNom() + " " + benevole.getPrenom());
	}
}
