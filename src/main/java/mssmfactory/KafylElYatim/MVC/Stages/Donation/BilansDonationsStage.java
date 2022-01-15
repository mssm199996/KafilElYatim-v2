package mssmfactory.KafylElYatim.MVC.Stages.Donation;

import mssmfactory.KafylElYatim.MVC.Controllers.Donation.BilansDonationsController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class BilansDonationsStage extends KafilElYatimApplicationStageType<BilansDonationsController> {

	public BilansDonationsStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/Donations/BilansDonations.fxml");

		this.setMaximized(true);
	}
}
