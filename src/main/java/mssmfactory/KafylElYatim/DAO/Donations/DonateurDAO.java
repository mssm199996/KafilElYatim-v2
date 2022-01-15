package mssmfactory.KafylElYatim.DAO.Donations;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import msjfxuicomponents.others.ICategorizerDeleter;
import msjfxuicomponents.others.ICategorizerSearcher;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.JourneeDisponible;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donateur;

public class DonateurDAO extends ApplicationDAO<Donateur>
		implements ICategorizerDeleter<Donateur>, ICategorizerSearcher<Donateur> {

	@Override
	public void deleteEntity(Donateur entity) {
		this.deleteArray(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Donateur> getSpecifiedDonators(String names) {
		if (names != null && !names.equals("")) {
			String request = "From Donateur donateur WHERE (";
			List<Object> params = new LinkedList<Object>();
			
			String[] nameParams = this.tokenMePlease(names, " ");
			
			for (String nameParam : nameParams) {
				request += "(donateur.nom like ? or donateur.prenom like ?) and ";

				params.add("%" + nameParam + "%");
				params.add("%" + nameParam + "%");
			}

			request = request.substring(0, request.length() - 4);
			request += ")";
			
			return (List<Donateur>) (Object) (this.getSessionFactoryHandler().select(request, params));
		} else
			return this.getAll();
	}

	@Override
	public String getGlobalSelectionQuery() {
		return "FROM Donateur";
	}

	@Override
	public String onInsert(Donateur entity) {
		return "INSERTION D'UN DONATEUR";
	}

	@Override
	public String onDelete(Donateur entity) {
		return "SUPPRESSION D'UN DONATEUR";
	}

	@Override
	public String onUpdate(Donateur entity) {
		return "MISE A JOUR D'UN DONATEUR";
	}

	@Override
	public String getNameOfEntity() {
		return "Donateur";
	}

	@Override
	public Collection<Donateur> search(String value) {
		return this.getSpecifiedDonators(value);
	}
}
