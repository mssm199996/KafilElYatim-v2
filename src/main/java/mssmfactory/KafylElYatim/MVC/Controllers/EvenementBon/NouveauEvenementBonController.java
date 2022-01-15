package mssmfactory.KafylElYatim.MVC.Controllers.EvenementBon;

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
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.EvenementBon;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class NouveauEvenementBonController extends AddController<EvenementBon>
		implements ICategorizerControllerAdder<EvenementBon> {

	public static EvenementBon EVENEMENT_BON = null;

	@FXML
	private JFXTextField designation;

	@FXML
	private JFXTextArea description;

	@FXML
	private JFXDatePicker date;

	@FXML
	private JFXTextField coutBon;

	@FXML
	private JFXTextField etablissementValidation;

	@Override
	public void confirm() throws Exception {
		String designation = this.designation.getText();
		String description = this.description.getText();
		LocalDate date = this.date.getValue();
		String coutBon = this.coutBon.getText();
		String etablissementValidation = this.etablissementValidation.getText();

		try {
			if (designation != null && !designation.equals("") && date != null && coutBon != null) {
				EvenementBon evenementBon = new EvenementBon();
				evenementBon.setCoutBon(Double.parseDouble(coutBon));
				evenementBon.setDateEvenement(date);
				evenementBon.setDescriptionEvenement(description);
				evenementBon.setDesignationEvenement(designation);
				evenementBon.setEtablissementValidation(etablissementValidation);

				(new Thread(() -> {
					this.onInsertCallback(evenementBon);
				})).start();

				this.setLastInsertedEntity(evenementBon);
				this.getWindow().hide();
			} else
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();

			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getInsertHeader(),
					"Erreur... Informations invalides !", this.getWindow());
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		ConstantsHolder.NOUVEAU_EVENEMENT_BON_CONTROLLER = this;
	}

	@Override
	public Window getWindow() {
		return StagesHolder.NOUVEAU_EVENEMENT_BON_STAGE;
	}

	@Override
	public String getNomGestion() {
		return "Gestion des évenements de bons";
	}

	@Override
	public String getInsertHeader() {
		return "Nouvel évenement bon";
	}

	@Override
	public void setLastInsertedEntity(EvenementBon entity) {
		NouveauEvenementBonController.EVENEMENT_BON = entity;
	}

	@Override
	public void onInsertCallback(EvenementBon entity) {
		UtilitiesHolder.EVENEMENT_BON_DAO.insertEntity(entity);
	}

	@Override
	public Stage getStage() {
		return StagesHolder.NOUVEAU_EVENEMENT_BON_STAGE;
	}

	@Override
	public EvenementBon getConstructedCategorizer() {
		return NouveauEvenementBonController.EVENEMENT_BON;
	}
}
