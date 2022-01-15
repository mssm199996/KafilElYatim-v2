package mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import msjfxuicomponents.cells.AutoCommitIntegerTextFieldCell;
import msjfxuicomponents.cells.AutoCommitStringTextFieldCell;
import msjfxuicomponents.mvc.SimpleTableController;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.MVC.Controllers.Utils.TableRegionsContainer;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

public class TableRegionsController extends SimpleTableController<TableRegionsContainer, Region> {

    @FXML
    private TableColumn<Region, Integer> codeColumn;

    @FXML
    private TableColumn<Region, String> designationColumn;

    @Override
    public void initTables() {
        super.initTables();

        this.codeColumn.setCellValueFactory(call -> call.getValue().codeProperty().asObject());
        this.codeColumn.setCellFactory(call -> new AutoCommitIntegerTextFieldCell<Region>() {

            @Override
            public void onEditCommit(Integer integer) {
                Region region = this.getEntity();
                region.setCode(integer);

                (new Thread(() -> {
                    UtilitiesHolder.REGION_DAO.updateEntity(region);
                })).start();
            }
        });

        this.designationColumn.setCellValueFactory(call -> call.getValue().designationProperty());
        this.designationColumn.setCellFactory(call -> new AutoCommitStringTextFieldCell<Region>() {

            @Override
            public void onEditCommit(String s) {
                Region region = this.getEntity();
                region.setDesignation(s);

                (new Thread(() -> {
                    UtilitiesHolder.REGION_DAO.updateEntity(region);
                })).start();
            }
        });
    }

    @Override
    public void onUpdateCallback(Region region) {
        UtilitiesHolder.REGION_DAO.updateEntity(region);
    }
}
