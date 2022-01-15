package mssmfactory.KafylElYatim.MVC.Stages.Benevole;

import mssmfactory.KafylElYatim.MVC.Controllers.Benevole.NouveauBenevoleController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class NouveauBenevoleStage extends KafilElYatimApplicationStageType<NouveauBenevoleController> {

	public NouveauBenevoleStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/Benevole/NouveauBenevole.fxml");

		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}