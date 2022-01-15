package mssmfactory.KafylElYatim.MVC.Stages.Configuration;

import mssmfactory.KafylElYatim.MVC.Controllers.Configuration.ConfigurationController;
import mssmfactory.KafylElYatim.MVC.Stages.KafilElYatimApplicationStageType;

public class ConfigurationStage extends KafilElYatimApplicationStageType<ConfigurationController> {
	public ConfigurationStage() {
		super("/mssmfactory/KafylElYatim/MVC/FXMLS/Configuration/Configuration.fxml");

		this.setMaximized(true);
	}
}
