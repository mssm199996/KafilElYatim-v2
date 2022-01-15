package mssmfactory.KafylElYatim.MVC.Controllers.Benevole;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.AddController;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Benevole;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Benevole.Genre;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;

public class NouveauBenevoleController extends AddController<Benevole> {

	public static Benevole LAST_INSERTED_ENTITY = null;
	
	@FXML
	private JFXTextField nom;

	@FXML
	private JFXTextField prenom;

	@FXML
	private JFXRadioButton masculin;

	@FXML
	private ToggleGroup sexe;

	@FXML
	private JFXRadioButton feminin;

	@FXML
	private JFXTextField telephone;

	@FXML
	private JFXTextField email;

	@FXML
	private JFXTextField facebook;

	@Override
	public void confirm() {
		String nom = this.nom.getText();
		String prenom = this.prenom.getText();
		String telephone = this.telephone.getText();
		String email = this.email.getText();
		String facebook = this.facebook.getText();
		Genre sexe = this.sexe.getSelectedToggle().equals(this.masculin) ? Genre.Masculin : Genre.Feminin;

		if (nom.equals("") || nom.equals("")) {
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Gestion des Benevoles", "Erreur de saisi",
					"Veuillez verifier le nom et le prenom", this.getWindow());
			return;
		}

		Benevole benevole = new Benevole();
		benevole.setEmail(email);
		benevole.setFacebook(facebook);
		benevole.setGenre(sexe);
		benevole.setNom(nom);
		benevole.setPrenom(prenom);
		benevole.setTelephone(telephone);

		(new Thread(() -> {
			this.onInsertCallback(benevole);
		})).start();

		this.setLastInsertedEntity(benevole);
		this.getWindow().hide();
	}

	@Override
	public Window getWindow() {
		return StagesHolder.NOUVEAU_BENEVOLE_STAGE;
	}

	@Override
	public String getNomGestion() {
		return "Gestion des bénévoles";
	}

	@Override
	public String getInsertHeader() {
		return "Nouveau bénévole";
	}

	@Override
	public void setLastInsertedEntity(Benevole entity) {
		NouveauBenevoleController.LAST_INSERTED_ENTITY = entity;
	}

	@Override
	public void onInsertCallback(Benevole entity) {
		UtilitiesHolder.BENEVOLE_DAO.insertEntity(entity);
	}
}
