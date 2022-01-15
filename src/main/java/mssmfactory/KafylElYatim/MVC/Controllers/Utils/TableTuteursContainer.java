package mssmfactory.KafylElYatim.MVC.Controllers.Utils;

import msjfxuicomponents.mvc.compositions.SimpleTableComposedController;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;

public interface TableTuteursContainer extends SimpleTableComposedController<Tuteur> {

    public void onTuteurArchiveSwitched(Tuteur tuteur);
}
