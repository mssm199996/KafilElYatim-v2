package mssmfactory.KafylElYatim.MVC.Stages.Benevole;

import mssmfactory.KafylElYatim.MVC.Controllers.Benevole.AffectationVehiculeBenevoleController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class AffectationVehiculeBenevoleStage extends KafilElYatimApplicationStageType<AffectationVehiculeBenevoleController> {

	public AffectationVehiculeBenevoleStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/Benevole/AffectationVehiculeBenevole.fxml");

		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
