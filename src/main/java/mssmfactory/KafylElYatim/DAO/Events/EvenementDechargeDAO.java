package mssmfactory.KafylElYatim.DAO.Events;

import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.EvenementDecharge;

public class EvenementDechargeDAO extends EvenementDAO<EvenementDecharge> {

	@Override
	public String getGlobalSelectionQuery() {
		return "FROM EvenementDecharge";
	}

	@Override
	public String getNameOfEntity() {
		return "EvenementDecharge";
	}
}