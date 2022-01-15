package mssmfactory.KafylElYatim.MVC.Stages;

import javafx.fxml.Initializable;
import msjfxuicomponents.mvc.MainApplicationStage;

public class KafilElYatimApplicationStageType<I extends Initializable> extends MainApplicationStage<I> {

	public KafilElYatimApplicationStageType(String fxmlPath) {
		super("Kafil El Yatim", fxmlPath);
	}
}
