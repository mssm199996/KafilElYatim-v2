package mssmfactory.KafylElYatim.MVC.Stages.Appareillage;

import mssmfactory.KafylElYatim.MVC.Controllers.Appareillage.AffectationAppareillageController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class AffectationAppareillageStage extends KafilElYatimApplicationStageType<AffectationAppareillageController> {

	public AffectationAppareillageStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/Appareillage/AffectationAppareillage.fxml");

		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
