package mssmfactory.KafylElYatim.MVC.Stages.Tuteur;

import mssmfactory.KafylElYatim.MVC.Controllers.Tuteur.NouveauTuteurController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class NouveauTuteurStage extends KafilElYatimApplicationStageType<NouveauTuteurController> {

	public NouveauTuteurStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/Tuteur/NouveauTuteur.fxml");

		this.setAlwaysOnTop(true);
		this.setResizable(false);
	}
}
