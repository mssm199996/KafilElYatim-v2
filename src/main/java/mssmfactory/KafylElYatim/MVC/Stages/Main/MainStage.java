package mssmfactory.KafylElYatim.MVC.Stages.Main;

import msjfxuicomponents.mvc.MainApplicationStage;
import mssmfactory.KafylElYatim.MVC.Controllers.Main.MainWindowController;

public class MainStage extends MainApplicationStage<MainWindowController> {

	public MainStage() {
		super("Kafil El Yatim", "/mssmfactory/KafylElYatim/MVC/FXMLS/Main/Main.fxml");

		this.setMaximized(true);
	}
}
