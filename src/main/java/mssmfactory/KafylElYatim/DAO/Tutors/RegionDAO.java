package mssmfactory.KafylElYatim.DAO.Tutors;

import msjfxuicomponents.others.ICategorizerAdder;
import msjfxuicomponents.others.ICategorizerDeleter;
import msjfxuicomponents.others.ICategorizerUpdater;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;

import java.util.List;

public class RegionDAO extends ApplicationDAO<Region>
        implements ICategorizerAdder<Region>, ICategorizerDeleter<Region>, ICategorizerUpdater<Region> {

    @Override
    public void deleteEntity(Region entity) {
        this.deleteArray(entity);
    }

    @Override
    public void updateCategorizerEntity(Region entity) {
        this.updateArray(entity);
    }

    @Override
    public void insertEntity(Region entity) {
        this.insertArray(entity);
    }

    public Region findRegionByTuteur(Tuteur tuteur) {
        @SuppressWarnings("unchecked")
        List<Tuteur> result = (List<Tuteur>) (Object) this.getSessionFactoryHandler()
                .select("FROM Tuteur tuteur INNER JOIN FETCH tuteur.region WHERE tuteur = ?", tuteur);

        if (!result.isEmpty()) {
            Tuteur fullTuteur = result.get(0);

            return fullTuteur.getRegion();
        }

        return null;
    }

    @Override
    public String getGlobalSelectionQuery() {
        return "FROM Region";
    }

    @Override
    public String onInsert(Region entity) {
        return "INSERTION DE LA REGION " + entity;
    }

    @Override
    public String onDelete(Region entity) {
        @SuppressWarnings("unchecked")
        List<Tuteur> tuteurs = (List<Tuteur>) (Object) this.getSessionFactoryHandler()
                .select("FROM Tuteur tuteur WHERE tuteur.region = ?", entity);

        for (Tuteur tuteur : tuteurs)
            tuteur.setRegion(null);

        this.getSessionFactoryHandler().update(tuteurs);

        return "SUPPRESSION DE LA REGION " + entity;
    }

    @Override
    public String onUpdate(Region entity) {
        return "MISE A JOUR DE LA REGION " + entity;
    }

    @Override
    public String getNameOfEntity() {
        return "Region";
    }

    @Override
    public Region createEntity() {
        return new Region();
    }
}
