package mssmfactory.KafylElYatim.MVC.Stages.Benevole;

import mssmfactory.KafylElYatim.MVC.Controllers.Benevole.NouvelleJourneeDisponibleController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class NouvelleJourneeDisponibleStage extends KafilElYatimApplicationStageType<NouvelleJourneeDisponibleController> {

	public NouvelleJourneeDisponibleStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/Benevole/NouvelleJourneeDisponible.fxml");

		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
