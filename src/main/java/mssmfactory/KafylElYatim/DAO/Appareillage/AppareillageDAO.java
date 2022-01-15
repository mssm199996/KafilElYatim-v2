package mssmfactory.KafylElYatim.DAO.Appareillage;

import msjfxuicomponents.others.ICategorizerAdder;
import msjfxuicomponents.others.ICategorizerDeleter;
import msjfxuicomponents.others.ICategorizerUpdater;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Appareillage;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AppareillageDAO extends ApplicationDAO<Appareillage> implements ICategorizerAdder<Appareillage>,
        Function<String, Collection<Appareillage>>, ICategorizerDeleter<Appareillage>, ICategorizerUpdater<Appareillage> {

    @Override
    public void updateCategorizerEntity(Appareillage entity) {
        this.updateArray(entity);
    }

    @Override
    public void deleteEntity(Appareillage entity) {
        this.deleteArray(entity);
    }

    @Override
    public void insertEntity(Appareillage entity) {
        this.insertArray(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Appareillage> getSpecifiedAppareillages(String type) {
        String request = "FROM Appareillage WHERE type LIKE :type";

        Map<String, Object> params = new HashMap<>();
        params.put("type", "%" + type + "%");

        return (List<Appareillage>) (Object) (this.getSessionFactoryHandler().select(request, params));
    }

    @SuppressWarnings("unchecked")
    public List<Appareillage> getAppareillagesTuteur(Tuteur tuteur) {
        return ((List<mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.DossierMedical>) (Object) (this
                .getSessionFactoryHandler()
                .select("FROM DossierMedicalTuteur dm LEFT JOIN FETCH dm.appareils WHERE dm.tuteur = ?", tuteur)))
                .get(0).getAppareils();
    }

    @SuppressWarnings("unchecked")
    public List<Appareillage> getAppareillagesOrphelin(Orphelin orphelin) {
        return ((List<mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierMedical>) (Object) (this
                .getSessionFactoryHandler()
                .select("FROM DossierMedical dm LEFT JOIN FETCH dm.appareils WHERE dm.orphelin = ?", orphelin))).get(0)
                .getAppareils();
    }

    @Override
    public String getGlobalSelectionQuery() {
        return "FROM Appareillage";
    }

    @Override
    public String onInsert(Appareillage entity) {
        return "INSERTION DE L'APPAREILLAGE " + entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String onDelete(Appareillage entity) {
        List<Orphelin> orphelins = (List<Orphelin>) (Object) this.getSessionFactoryHandler()
                .select("FROM Orphelin orphelin LEFT JOIN FETCH orphelin.dossierMedical.appareils");

        List<Tuteur> tuteurs = (List<Tuteur>) (Object) this.getSessionFactoryHandler()
                .select("FROM Tuteur tuteur LEFT JOIN FETCH tuteur.dossierMedical.appareils");

        for (Orphelin orphelin : orphelins)
            orphelin.getDossierMedical().removeAppareillage(entity);

        for (Tuteur tuteur : tuteurs)
            tuteur.getDossierMedical().removeAppareillage(entity);

        this.getSessionFactoryHandler().update(orphelins);
        this.getSessionFactoryHandler().update(tuteurs);

        return "SUPPRESSION DE L'APPAREILLAGE " + entity;
    }

    @Override
    public String onUpdate(Appareillage entity) {
        return "MISE A JOUR DE L'APPAREILLAGE " + entity;
    }

    @Override
    public String getNameOfEntity() {
        return "Appareillage";
    }

    @Override
    public Collection<Appareillage> apply(String type) {
        return this.getSpecifiedAppareillages(type);
    }

    @Override
    public Appareillage createEntity() {
        return new Appareillage();
    }
}
