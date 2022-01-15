package mssmfactory.KafylElYatim.DAO.Benevole;

import java.util.List;

import msjfxuicomponents.others.ICategorizerDeleter;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Benevole;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.JourneeDisponible;

public class JourneeDisponibleDAO extends ApplicationDAO<JourneeDisponible>
		implements ICategorizerDeleter<JourneeDisponible> {

	@SuppressWarnings("unchecked")
	public List<JourneeDisponible> getJourneesDisponiblesBenevole(Benevole benevole) {
		return (List<JourneeDisponible>) (Object) (this.getSessionFactoryHandler()
				.select("FROM JourneeDisponible jd WHERE jd.benevole = ?", benevole));
	}

	@Override
	public void deleteEntity(JourneeDisponible entity) {
		this.deleteArray(entity);
	}

	@Override
	public String getGlobalSelectionQuery() {
		return "FROM JourneeDisponible";
	}

	@Override
	public String onInsert(JourneeDisponible entity) {
		return "INSERTION D'UNE NOUVELLE JOURNEE";
	}

	@Override
	public String onDelete(JourneeDisponible entity) {
		return "SUPPRESSION D'UNE NOUVELLE JOURNEE";
	}

	@Override
	public String onUpdate(JourneeDisponible entity) {
		return "MISE A JOUR D'UNE NOUVELLE JOURNEE";
	}

	@Override
	public String getNameOfEntity() {
		return "JourneeDisponible";
	}
}
