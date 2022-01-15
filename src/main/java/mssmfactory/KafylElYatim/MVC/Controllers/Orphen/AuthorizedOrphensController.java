package mssmfactory.KafylElYatim.MVC.Controllers.Orphen;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import msjfxuicomponents.mvc.ResetController;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin;
import mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers.TableOrphelinsController;
import mssmfactory.KafylElYatim.MVC.Controllers.Utils.TableOrphelinsContainer;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssoftutils.others.SearchController;

public class AuthorizedOrphensController extends SearchController<Orphelin>
		implements ResetController, Initializable, TableOrphelinsContainer {

	@FXML
	private TableOrphelinsController tableOrphelinsController;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initTableOrphelins();
	}

	private void initTableOrphelins() {
		this.tableOrphelinsController.setItems(this.getDatasource());
	}

	@Override
	protected List<Orphelin> selectFromDatabase() throws NullPointerException {
		return UtilitiesHolder.ORPHELIN_DAO.getAuthorizedOrphelins();
	}

	@Override
	public void onShowingResetResult() {
		this.search();
	}

	@Override
	public void setTableView(TableView<Orphelin> tableView) {
	}
}
