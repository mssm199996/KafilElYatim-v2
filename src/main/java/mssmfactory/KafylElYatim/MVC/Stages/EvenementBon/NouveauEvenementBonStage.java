package mssmfactory.KafylElYatim.MVC.Stages.EvenementBon;

import mssmfactory.KafylElYatim.MVC.Controllers.EvenementBon.NouveauEvenementBonController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class NouveauEvenementBonStage extends KafilElYatimApplicationStageType<NouveauEvenementBonController> {

	public NouveauEvenementBonStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/EvenementBon/NouveauEvenementBon.fxml");

		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
