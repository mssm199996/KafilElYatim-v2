package mssmfactory.KafylElYatim.MVC.Stages.Donation;

import mssmfactory.KafylElYatim.MVC.Controllers.Donation.NouveauDonateurController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class NouveauDonateurStage extends KafilElYatimApplicationStageType<NouveauDonateurController> {

	public NouveauDonateurStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/Donateur/NouveauDonateur.fxml");

		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
