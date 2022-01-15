package mssmfactory.KafylElYatim.Utilities.DataHandlers;

import java.net.MalformedURLException;

import org.hibernate.HibernateException;

import msdatabaseutils.SessionFactoryHandler;
import mssmfactory.KafylElYatim.DomainModel.Others.Compte;

public class ApplicationSessionFactoryHandler extends SessionFactoryHandler {

	public ApplicationSessionFactoryHandler() throws HibernateException, MalformedURLException {
		super("Kafil El Yatim");
	}

	@Override
	public String getFirstCreationCountQuery() {
		return "SELECT COUNT(*) FROM Compte";
	}

	@Override
	public void onFirstCreationCallback() {
		this.initInitialData();
	}

	@Override
	public boolean isCheckIfFirstCreation() {
		return true;
	}

	private void initInitialData() {
		Compte compte = new Compte();
		compte.setAdmin(true);
		compte.setDateDerniereConnexion(null);
		compte.setHeureDerniereConnexion(null);
		compte.setId(1);
		compte.setPassword("admin");
		compte.setUsername("admin");
		compte.setActivateCamera(false);
		compte.setCheminMysql("C:/Program Files/MySQL/MySQL Server 5.7/");
		compte.setCheminSauvegarde("sauvegardes/");

		this.insertArray(compte);
	}
}
