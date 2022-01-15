package mssmfactory.KafylElYatim.MVC.Stages.Orphen;

import mssmfactory.KafylElYatim.MVC.Controllers.Orphen.NouvelOrphelinController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class NouvelOrphelinStage extends KafilElYatimApplicationStageType<NouvelOrphelinController> {

	public NouvelOrphelinStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/Orphen/NouvelOrphelin.fxml");

		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
