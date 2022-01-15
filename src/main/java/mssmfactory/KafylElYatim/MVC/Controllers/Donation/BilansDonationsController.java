package mssmfactory.KafylElYatim.MVC.Controllers.Donation;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import msjfxuicomponents.mvc.ResetController;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donateur;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donation;
import mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers.TableDonationsController;
import mssmfactory.KafylElYatim.MVC.Controllers.Utils.TableDonationsContainer;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssoftutils.others.SearchController;

public class BilansDonationsController extends SearchController<Donation>
		implements Initializable, TableDonationsContainer, ResetController {

	public static Donateur SELECTED_DONATOR = null;

	@FXML
	private JFXDatePicker dateDebut, dateFin;

	@FXML
	private TableDonationsController tableDonationsController;

	@FXML
	private Label resultCount, resultAmount;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initDatePickers();
	}

	private void initDatePickers() {
		this.dateDebut.valueProperty().addListener((obs, oldValue, newValue) -> {
			this.search();
		});

		this.dateFin.valueProperty().addListener((obs, oldValue, newValue) -> {
			this.search();
		});
	}

	@Override
	protected List<Donation> selectFromDatabase() throws NullPointerException {
		LocalDate dateDebut = this.dateDebut.getValue();
		LocalDate dateFin = this.dateFin.getValue();
		Donateur donateur = BilansDonationsController.SELECTED_DONATOR;

		return UtilitiesHolder.DONATION_DAO.getSpecifiedDonations(null, null, dateDebut, dateFin, donateur);
	}

	@Override
	protected void performAfterSearch() {
		double sum = 0;

		for (Donation donation : this.getDatasource()) {
			try {
				sum += Double.parseDouble(donation.getValeur());
			} catch (NumberFormatException exp) {
			}
		}

		this.resultCount.setText("Nombre de donations: " + this.getDatasource().size());
		this.resultAmount.setText("Total des donations: " + sum);
	}

	@Override
	public void onShowingResetResult() {
		this.search();
	}

	@Override
	public void setTableView(TableView<Donation> tableView) {
		tableView.setItems(this.getDatasource());
	}
}
