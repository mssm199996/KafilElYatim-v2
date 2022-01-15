package mssmfactory.KafylElYatim.MVC.Stages.EvenementDecharge;

import mssmfactory.KafylElYatim.MVC.Controllers.EvenementDecharge.NouveauEvenementDechargeController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class NouveauEvenementDechargeStage extends KafilElYatimApplicationStageType<NouveauEvenementDechargeController> {

	public NouveauEvenementDechargeStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/EvenementDecharge/NouveauEvenementDecharge.fxml");

		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
