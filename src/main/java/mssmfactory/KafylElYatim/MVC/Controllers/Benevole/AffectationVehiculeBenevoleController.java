package mssmfactory.KafylElYatim.MVC.Controllers.Benevole;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.AddController;
import msjfxuicomponents.others.ICategorizerControllerAdder;
import msjfxuicomponents.uicomponents.MSCategorizer;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Benevole;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Vehicule;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class AffectationVehiculeBenevoleController extends AddController<Vehicule>
		implements ICategorizerControllerAdder<Vehicule> {

	public static Vehicule LAST_AFFECTED_VEHICULE = null;
	public static Benevole BENEVOLE = null;

	@FXML
	private MSCategorizer<Vehicule> vehicules;

	@FXML
	private Label nomBenevole;

	@Override
	public void confirm() throws Exception {
		Benevole benevole = AffectationVehiculeBenevoleController.BENEVOLE;

		if (benevole != null) {
			Vehicule vehicule = this.vehicules.getEntity();

			if (vehicule != null) {
				(new Thread(() -> {
					UtilitiesHolder.BENEVOLE_DAO.affectVehiculeToBenevole(benevole, vehicule);
				})).start();

				this.setLastInsertedEntity(vehicule);
				this.getWindow().hide();
			} else
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(),
						this.getInsertHeader(), "Erreur... veuillez d'abords choisir un véhicule !", this.getWindow());
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getInsertHeader(),
					"Erreur...  veuillez d'abords selectionner un bénévole !", this.getWindow());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		this.initCategorizers();
		ConstantsHolder.AFFECTATION_VEHICULE_BENEVOLE_CONTROLLER = this;
	}

	@Override
	public Window getWindow() {
		return StagesHolder.AFFECTATION_VEHICULE_BENEVOLE_STAGE;
	}

	@Override
	public String getNomGestion() {
		return "Gestion des bénévoles";
	}

	@Override
	public String getInsertHeader() {
		return "Nouvelle affectation d'un vehicule";
	}

	@Override
	public void setLastInsertedEntity(Vehicule entity) {
		AffectationVehiculeBenevoleController.LAST_AFFECTED_VEHICULE = entity;
	}

	@Override
	public void onInsertCallback(Vehicule entity) {
	}

	private void initCategorizers() {
		this.vehicules.setDAOCategorizerAdder(UtilitiesHolder.VEHICULE_DAO);
		this.vehicules.setEntitiesLoader(UtilitiesHolder.VEHICULE_DAO);
	}

	@Override
	public Stage getStage() {
		return (Stage) this.getWindow();
	}

	@Override
	public Vehicule getConstructedCategorizer() {
		return AffectationVehiculeBenevoleController.LAST_AFFECTED_VEHICULE;
	}

	@Override
	public void onShowingResetResult() {
		super.onShowingResetResult();

		Benevole benevole = NouvelleJourneeDisponibleController.SELECTED_BENEVOLE;

		if (benevole != null)
			this.nomBenevole.setText(benevole.getNom() + " " + benevole.getPrenom());
	}
}
