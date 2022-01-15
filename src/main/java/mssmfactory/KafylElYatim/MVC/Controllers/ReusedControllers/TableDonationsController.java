package mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import msjfxuicomponents.cells.WellFormattedLocalDateCell;
import msjfxuicomponents.mvc.SimpleTableController;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donation;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donation.DonationForm;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donation.DonationType;
import mssmfactory.KafylElYatim.MVC.Controllers.Utils.TableDonationsContainer;

public class TableDonationsController extends SimpleTableController<TableDonationsContainer, Donation> {
	
	@SuppressWarnings("unused")
	private TableDonationsContainer tableDonationsContainer;

	@FXML
	private TableColumn<Donation, DonationType> typeColumn;

	@FXML
	private TableColumn<Donation, DonationForm> formeColumn;

	@FXML
	private TableColumn<Donation, String> valeurColumn;

	@FXML
	private TableColumn<Donation, LocalDate> dateColumn;

	@FXML
	private TableColumn<Donation, String> nomDonateurColumn;

	@Override
	public void initTables() {
		super.initTables();

		this.dateColumn.setCellValueFactory(call -> {
			return call.getValue().dateProperty();
		});
		this.dateColumn.setCellFactory(call -> {
			return new WellFormattedLocalDateCell<>();
		});

		this.formeColumn.setCellValueFactory(call -> {
			return call.getValue().donationFormProperty();
		});

		this.nomDonateurColumn.setCellValueFactory(call -> {
			return call.getValue().nomDonateurProperty();
		});

		this.typeColumn.setCellValueFactory(call -> {
			return call.getValue().donationTypeProperty();
		});

		this.valeurColumn.setCellValueFactory(call -> {
			return call.getValue().valeurProperty();
		});

		this.valeurColumn.setCellFactory(this.textCellUpdater);
	}

	@Override
	public void onUpdateCallback(Donation entity) {
	}
}
