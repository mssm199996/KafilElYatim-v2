package mssmfactory.KafylElYatim.DAO.Others;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import msdatabaseutils.IOperationTypeDAO;
import msdatabaseutils.SessionFactoryHandler;
import msjfxuicomponents.mvc.mslogin.MSLoginController;
import mssmfactory.KafylElYatim.DomainModel.Others.ApplicationOperation;
import mssmfactory.KafylElYatim.DomainModel.Others.Compte;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

public class OperationDAO implements IOperationTypeDAO<ApplicationOperation> {

	@Override
	public SessionFactoryHandler getSessionFactoryHandler() {
		return UtilitiesHolder.APPLICATION_SESSION_FACTORY_HANDLER;
	}

	@Override
	public ApplicationOperation constructOperation() {
		return new ApplicationOperation();
	}

	@Override
	public void linkWithOperateur(ApplicationOperation operation) {
		operation.setOperateur((Compte) MSLoginController.LOGGED_IN_COMPTE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationOperation> fetchFromDatabase(String descriptionOperation, LocalDate dateOperation) {
		Map<String, Object> params = new HashMap<>();
		params.put("description", "%" + descriptionOperation + "%");

		String request = 
				"FROM ApplicationOperation o "
				+ "LEFT OUTER JOIN FETCH o.operateur "
				+ "WHERE (o.description LIKE :description ";

		if (dateOperation != null) {
			request += "AND o.dateOperation = :date";
			params.put("date", dateOperation);
		}

		request += ")";

		return (List<ApplicationOperation>) (Object) this.getSessionFactoryHandler().select(request, params);
	}
}
