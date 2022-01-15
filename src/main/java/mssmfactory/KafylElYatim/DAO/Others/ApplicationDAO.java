package mssmfactory.KafylElYatim.DAO.Others;

import msdatabaseutils.IDAO;
import msdatabaseutils.IOperationTypeDAO;
import msdatabaseutils.OperationType;
import msdatabaseutils.SessionFactoryHandler;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

public abstract class ApplicationDAO<T> implements IDAO<T> {

	@Override
	public SessionFactoryHandler getSessionFactoryHandler() {
		return UtilitiesHolder.APPLICATION_SESSION_FACTORY_HANDLER;
	}

	@Override
	public IOperationTypeDAO<? extends OperationType> getOperationDAO() {
		return UtilitiesHolder.OPERATION_DAO;
	}
}
