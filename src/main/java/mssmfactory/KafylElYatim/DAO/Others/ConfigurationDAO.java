package mssmfactory.KafylElYatim.DAO.Others;

import mssmfactory.KafylElYatim.DomainModel.Others.Compte;

public class ConfigurationDAO extends ApplicationDAO<Compte> {

	@Override
	public String getGlobalSelectionQuery() {
		return null;
	}

	@Override
	public String onInsert(Compte entity) {
		return null;
	}

	@Override
	public String onDelete(Compte entity) {
		return null;
	}

	@Override
	public String onUpdate(Compte entity) {
		return "MISE A JOUR DE LA CONFIGURATION";
	}

	@Override
	public String getNameOfEntity() {
		return "Configuration";
	}
}
