package mssmfactory.KafylElYatim.MVC.Controllers.Donation;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.others.CrudController;
import msjfxuicomponents.uicomponents.MSSearcheableCategorizerCrudListTitledPane;
import msjfxuicomponents.uicomponents.MSSimpleObservationTitledPane;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donateur;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donation;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donation.DonationType;
import mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers.TableDonationsController;
import mssmfactory.KafylElYatim.MVC.Controllers.Utils.TableDonationsContainer;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class DonationsPanelController extends CrudController<Donation> implements TableDonationsContainer {

	@FXML
	private MSSearcheableCategorizerCrudListTitledPane<Donateur> donateursTitledPane;

	@FXML
	private MSSimpleObservationTitledPane<Donation> observationTitledPane;

	@FXML
	private TableDonationsController tableDonationsController;

	@FXML
	private JFXComboBox<DonationType> type;

	@FXML
	private JFXTextField idDonation;

	@FXML
	private JFXDatePicker dateDebut;

	@FXML
	private JFXDatePicker dateFin;

	@FXML
	void printResult(ActionEvent event) {
		// TODO
	}

	@FXML
	void refresh(ActionEvent event) {
		this.dateDebut.setValue(null);
		this.dateFin.setValue(null);
		this.idDonation.setText("");
		this.type.setValue(null);

		this.search();
	}

	// ------------------------------------------------------------------------------------------

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initInitialDatas();
		this.initComboBox();
		this.initDatePickers();
		this.initTableView();
		this.initOthers();

		ConstantsHolder.DONATIONS_PANEL_CONTROLLER = this;
	}

	private void initOthers() {
		this.observationTitledPane.setUpdator(UtilitiesHolder.DONATION_DAO);
		this.observationTitledPane.setEntityFetcher(() -> {
			return this.tableDonationsController.getSelectionModel().getSelectedItem();
		});

		this.donateursTitledPane.setFilterer(UtilitiesHolder.DONATEUR_DAO);
		this.donateursTitledPane.addAll(UtilitiesHolder.DONATEUR_DAO.getAll());
		this.donateursTitledPane.setCategorizerDeleter(UtilitiesHolder.DONATEUR_DAO);
		this.donateursTitledPane.setCategorizerControllerAdder(ConstantsHolder.NOUVEAU_DONATEUR_CONTROLLER);
	}

	private void initComboBox() {
		this.type.getItems().addAll(Donation.DonationType.values());
		this.type.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			this.search();
		});
	}

	private void initDatePickers() {
		this.dateDebut.valueProperty().addListener((obs, oldValue, newValue) -> {
			this.search();
		});

		this.dateFin.valueProperty().addListener((obs, oldValue, newValue) -> {
			this.search();
		});

	}

	private void initTableView() {
		this.tableDonationsController.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldValue, newValue) -> {
					if (newValue != null)
						this.observationTitledPane.setObservationContent(newValue.getObservation());
					else
						this.observationTitledPane.setObservationContent("");
				});
	}

	private void initInitialDatas() {
		this.tableDonationsController.addAll(UtilitiesHolder.DONATION_DAO.getAll());
	}

	@Override
	public Stage getInsertStage() {
		return StagesHolder.NOUVELLE_DONATION_STAGE;
	}

	@Override
	public Donation getLastInsertedEntity() {
		return NouvelleDonationController.LAST_INSERTED_ENTITY;
	}

	@Override
	public String getNomGestion() {
		return "Gestion des donations";
	}

	@Override
	public String getDeleteHeader() {
		return "Suppression d'une donation";
	}

	@Override
	public void onDeleteCallback(Donation entity) {
		UtilitiesHolder.DONATION_DAO.deleteEntity(entity);
	}

	@Override
	public void onBeforeUpdateCallback(Donation entity) {
	}

	@Override
	public void onUpdateCallback(Donation entity) {
	}

	@Override
	protected List<Donation> selectFromDatabase() throws NullPointerException {
		DonationType type = this.type.getSelectionModel().getSelectedItem();

		Integer id = null;

		try {
			id = Integer.parseInt(this.idDonation.getText());
		} catch (NumberFormatException exp) {
		}

		LocalDate startDate = this.dateDebut.getValue();
		LocalDate endDate = this.dateFin.getValue();
		
		return UtilitiesHolder.DONATION_DAO.getSpecifiedDonations(id, type, startDate, endDate, null);
	}

	public Donateur getSelectedDonateur() {
		return this.donateursTitledPane.getSelectedItem();
	}

	@Override
	public boolean isCustomTable() {
		return true;
	}

	@Override
	public boolean onBeforeInsertCallback() {
		Donateur donateur = this.getSelectedDonateur();

		if (donateur != null) {
			NouvelleDonationController.DONATEUR = donateur;

			return super.onBeforeInsertCallback();
		} else {
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Nouvelle donation",
					"Veuillez d'abords selectionner un donateur", this.getWindow());

			return false;
		}
	}
}
