package mssmfactory.KafylElYatim.MVC.Controllers.Appareillage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.AddController;
import msjfxuicomponents.others.ICategorizerControllerAdder;
import msjfxuicomponents.uicomponents.MSCategorizer;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Appareillage;
import mssmfactory.KafylElYatim.DomainModel.Others.IAppareillageHolder;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class AffectationAppareillageController extends AddController<Appareillage>
		implements ICategorizerControllerAdder<Appareillage> {

	public static Appareillage LAST_AFFECTED_APPAREILLAGE = null;
	public static IAppareillageHolder APPAREILLAGE_HOLDER = null;

	@FXML
	private MSCategorizer<Appareillage> appareillages;

	@Override
	public void confirm() {
		IAppareillageHolder appareillageHolder = AffectationAppareillageController.APPAREILLAGE_HOLDER;

		if (appareillageHolder != null) {
			Appareillage appareillage = this.appareillages.getEntity();

			if (appareillage != null) {
				(new Thread(() -> {
					appareillageHolder.affectAppareillage(appareillage);
				})).start();

				this.setLastInsertedEntity(appareillage);
				this.getWindow().hide();
			} else
				MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(),
						this.getInsertHeader(), "Erreur... veuillez d'abords selectionner un appareillage",
						this.getWindow());
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getInsertHeader(),
					"Erreur... veuillez d'abords selectionner une personne", this.getWindow());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		this.initCategorizers();

		ConstantsHolder.AFFECTATION_APPAREILLAGE_CONTROLLER = this;
	}

	@Override
	public String getInsertHeader() {
		return "Affectation d'un nouvel appareillage";
	}

	@Override
	public void setLastInsertedEntity(Appareillage entity) {
		AffectationAppareillageController.LAST_AFFECTED_APPAREILLAGE = entity;
	}

	@Override
	public void onInsertCallback(Appareillage entity) {
	}

	@Override
	public Window getWindow() {
		return StagesHolder.AFFECTATION_APPAREILLAGE_STAGE;
	}

	@Override
	public String getNomGestion() {
		return "Gestion d'appareillages";
	}

	private void initCategorizers() {
		this.appareillages.setDAOCategorizerAdder(UtilitiesHolder.APPAREILLAGE_DAO);
		this.appareillages.setEntitiesLoader(UtilitiesHolder.APPAREILLAGE_DAO);
	}

	@Override
	public Stage getStage() {
		return (Stage) this.getWindow();
	}

	@Override
	public Appareillage getConstructedCategorizer() {
		return AffectationAppareillageController.LAST_AFFECTED_APPAREILLAGE;
	}

	@Override
	public Boolean isPrerequisiesAvailable() {
		return AffectationAppareillageController.APPAREILLAGE_HOLDER != null;
	}

	@Override
	public String prerequisiesUnavailableMessage() {
		return "Veuillez d'abords selectionner une personne !";
	}
}
