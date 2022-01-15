package mssmfactory.KafylElYatim.MVC.Stages.Orphen;

import mssmfactory.KafylElYatim.MVC.Controllers.Orphen.AuthorizedOrphensController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class AuthorizedOrphensStage extends KafilElYatimApplicationStageType<AuthorizedOrphensController> {

	public AuthorizedOrphensStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/Orphen/AuthorizedOrphens.fxml");

		this.setMaximized(true);
	}
}
