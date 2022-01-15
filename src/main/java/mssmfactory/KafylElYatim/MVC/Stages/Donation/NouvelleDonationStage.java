package mssmfactory.KafylElYatim.MVC.Stages.Donation;

import mssmfactory.KafylElYatim.MVC.Controllers.Donation.NouvelleDonationController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class NouvelleDonationStage extends KafilElYatimApplicationStageType<NouvelleDonationController> {

	public NouvelleDonationStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/Donations/NouvelleDonation.fxml");

		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
