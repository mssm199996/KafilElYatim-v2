package mssmfactory.KafylElYatim.DomainModel.Others;

import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Appareillage;

public interface IAppareillageHolder {

	public void affectAppareillage(Appareillage appareillage);

	public void dettachAppareillage(Appareillage appareillage);
}
