package mssmfactory.KafylElYatim.DAO.Events;

import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.EvenementBon;

public class EvenementBonDAO extends EvenementDAO<EvenementBon> {

	@Override
	public String getGlobalSelectionQuery() {
		return "FROM EvenementBon";
	}

	@Override
	public String getNameOfEntity() {
		return "EvenementBon";
	}
}
