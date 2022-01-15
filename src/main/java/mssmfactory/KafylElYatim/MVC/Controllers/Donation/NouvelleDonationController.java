package mssmfactory.KafylElYatim.MVC.Controllers.Donation;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.AddController;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donateur;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donation;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donation.DonationForm;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donation.DonationType;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

public class NouvelleDonationController extends AddController<Donation> {
	
	public static Donation LAST_INSERTED_ENTITY = null;
	public static Donateur DONATEUR = null;

	@FXML
	private Label nomDonateur;

	@FXML
	private JFXComboBox<DonationType> type;

	@FXML
	private JFXComboBox<DonationForm> forme;

	@FXML
	private JFXTextField valeur;

	@FXML
	private JFXDatePicker date;

	@Override
	public void confirm() throws Exception {
		Donateur donateur = NouvelleDonationController.DONATEUR;
		LocalDate date = this.date.getValue();
		DonationType type = this.type.getSelectionModel().getSelectedItem();
		DonationForm forme = this.forme.getSelectionModel().getSelectedItem();
		String valeur = this.valeur.getText();

		if (donateur == null) {
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getInsertHeader(),
					"Veuillez d'abords selectionner un donateur", this.getWindow());
			return;
		}

		if (date == null || LocalDate.now().compareTo(date) < 0 || type == null || forme == null) {
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getInsertHeader(),
					"Date ou type invalides !", this.getWindow());
			return;
		}

		if (!MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(),
				this.getInsertHeader(), "Donation (" + type + ")" + " du " + date + " d'une valeur de: " + valeur
						+ "\nConfirmer pour continuer.",
				this.getWindow()))
			return;

		Donation donation = new Donation();
		donation.setDate(date);
		donation.setValeur(valeur);
		donation.setDonator(donateur);
		donation.setType(type);
		donation.setForme(forme);
		donation.setObservation("Rien à signaler");
		
		(new Thread(() -> {
			(new Thread(() -> {
				this.onInsertCallback(donation);
			})).start();
		})).start();
		
		this.setLastInsertedEntity(donation);
		this.getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		this.initComboBoxes();
	}

	private void initComboBoxes() {
		this.type.getItems().addAll(DonationType.values());
		this.forme.getItems().addAll(DonationForm.values());
	}

	@Override
	public Window getWindow() {
		return this.date.getScene().getWindow();
	}

	@Override
	public String getNomGestion() {
		return "Gestion des donations";
	}

	@Override
	public String getInsertHeader() {
		return "Nouvelle donation";
	}

	@Override
	public void setLastInsertedEntity(Donation entity) {
		NouvelleDonationController.LAST_INSERTED_ENTITY = entity;
	}

	@Override
	public void onInsertCallback(Donation entity) {
		UtilitiesHolder.DONATION_DAO.insertEntity(entity);
	}

	public void onShowingResetResult() {
		super.onShowingResetResult();

		Donateur donateur = NouvelleDonationController.DONATEUR;

		if (donateur != null)
			this.nomDonateur.setText(donateur.getNom() + " " + donateur.getPrenom());
		else
			this.nomDonateur.setText("");
	}
}
