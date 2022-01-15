package mssmfactory.KafylElYatim.MVC.Controllers.Donation;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.AddController;
import msjfxuicomponents.others.ICategorizerControllerAdder;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donateur;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class NouveauDonateurController extends AddController<Donateur>
		implements ICategorizerControllerAdder<Donateur> {

	public static Donateur LAST_INSERTED_DONATEUR = null;
	
	@FXML
	private JFXTextField nom;

	@FXML
	private JFXTextField prenom;

	@Override
	public void confirm() throws Exception {
		String nom = this.nom.getText();
		String prenom = this.prenom.getText();

		if (nom != null && prenom != null && !nom.equals("") && !prenom.equals("")) {
			Donateur donateur = new Donateur();
			donateur.setNom(nom);
			donateur.setPrenom(prenom);

			(new Thread(() -> {
				this.onInsertCallback(donateur);
			})).start();

			this.setLastInsertedEntity(donateur);
			this.getWindow().hide();
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getInsertHeader(),
					"Erreur... Informations invalides !", this.getWindow());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	
		ConstantsHolder.NOUVEAU_DONATEUR_CONTROLLER = this;
	}

	@Override
	public Window getWindow() {
		return StagesHolder.NOUVEAU_DONATEUR_STAGE;
	}

	@Override
	public String getNomGestion() {
		return "Gestion des donations";
	}

	@Override
	public String getInsertHeader() {
		return "Nouveau donateur";
	}

	@Override
	public void setLastInsertedEntity(Donateur entity) {
		NouveauDonateurController.LAST_INSERTED_DONATEUR = entity;
	}

	@Override
	public void onInsertCallback(Donateur entity) {
		UtilitiesHolder.DONATEUR_DAO.insertEntity(entity);
	}

	@Override
	public Stage getStage() {
		return (Stage) this.getWindow();
	}

	@Override
	public Donateur getConstructedCategorizer() {
		return NouveauDonateurController.LAST_INSERTED_DONATEUR;
	}
}
