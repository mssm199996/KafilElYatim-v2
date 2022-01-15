package mssmfactory.KafylElYatim.DAO.Others;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DomainModel.ICompte;
import msdatabaseutils.ICompteValidator;
import mssmfactory.KafylElYatim.DomainModel.Others.Compte;

public class CompteDAO extends ApplicationDAO<Compte> implements ICompteValidator {

	@Override
	public String getGlobalSelectionQuery() {
		return "FROM Compte";
	}

	@Override
	public String onInsert(Compte entity) {
		return "INSERTION DU COMPTE " + entity.getUsername();
	}

	@Override
	public String onDelete(Compte entity) {
		return "SUPPRESSION DU COMPTE " + entity.getUsername();
	}

	@Override
	public String onUpdate(Compte entity) {
		return "MISE A JOUR DU COMPTE " + entity.getUsername();
	}

	@Override
	public String getNameOfEntity() {
		return "Compte";
	}

	@SuppressWarnings("unchecked")
	public Compte findByUsernameAndPassword(String username, String password) {
		String request = "FROM Compte WHERE username = :username AND password = :password";
		Map<String, Object> params = new HashMap<>();
		params.put("username", username);
		params.put("password", password);

		List<Compte> result = (List<Compte>) (Object) this.getSessionFactoryHandler().select(request, params);

		if (!result.isEmpty())
			return result.get(0);

		return null;
	}

	@Override
	public ICompte isAccountValid(String username, String password) {
		return this.findByUsernameAndPassword(username, password);
	}

	@Override
	public void onAttemptingToConnect(String username, String password) {
		this.getOperationDAO().insertOperation(
				"TENTATIVE DE CONNEXION AVEC LES INFORMATIONS SUIVANTES: (" + username + " ; " + password + ")");
	}
}
