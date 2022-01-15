package mssmfactory.KafylElYatim.MVC.Controllers.EvenementDecharge;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.AddController;
import msjfxuicomponents.others.ICategorizerControllerAdder;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.EvenementDecharge;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class NouveauEvenementDechargeController extends AddController<EvenementDecharge>
		implements ICategorizerControllerAdder<EvenementDecharge> {

	public static EvenementDecharge LAST_INSERTED_EVENEMENT_DECHARGE = null;

	@FXML
	private JFXTextField designation;

	@FXML
	private JFXTextArea description;

	@FXML
	private JFXDatePicker date;

	@Override
	public void confirm() throws Exception {
		String designation = this.designation.getText();
		String description = this.description.getText();
		LocalDate date = this.date.getValue();

		if (designation != null && !designation.equals("") && date != null) {
			EvenementDecharge evenementDecharge = new EvenementDecharge();
			evenementDecharge.setDateEvenement(date);
			evenementDecharge.setDescriptionEvenement(description);
			evenementDecharge.setDesignationEvenement(designation);

			(new Thread(() -> {
				this.onInsertCallback(evenementDecharge);
			})).start();

			this.setLastInsertedEntity(evenementDecharge);
			this.getWindow().hide();
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getInsertHeader(),
					"Erreur... Informations invalides !", this.getWindow());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		ConstantsHolder.NOUVEAU_EVENEMENT_DECHARGE_CONTROLLER = this;
	}

	@Override
	public Window getWindow() {
		return StagesHolder.NOUVEAU_EVENEMENT_DECHARGE_STAGE;
	}

	@Override
	public String getNomGestion() {
		return "Gestion des évenements des décharges";
	}

	@Override
	public String getInsertHeader() {
		return "Nouvel évenement décharge";
	}

	@Override
	public void setLastInsertedEntity(EvenementDecharge entity) {
		NouveauEvenementDechargeController.LAST_INSERTED_EVENEMENT_DECHARGE = entity;
	}

	@Override
	public void onInsertCallback(EvenementDecharge entity) {
		UtilitiesHolder.EVENEMENT_DECHARGE_DAO.insertEntity(entity);
	}

	@Override
	public Stage getStage() {
		return StagesHolder.NOUVEAU_EVENEMENT_DECHARGE_STAGE;
	}

	@Override
	public EvenementDecharge getConstructedCategorizer() {
		return NouveauEvenementDechargeController.LAST_INSERTED_EVENEMENT_DECHARGE;
	}
}
