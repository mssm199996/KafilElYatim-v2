package mssmfactory.KafylElYatim.DAO.Benevole;

import msjfxuicomponents.others.ICategorizerAdder;
import msjfxuicomponents.others.ICategorizerDeleter;
import msjfxuicomponents.others.ICategorizerUpdater;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Benevole;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Statut;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StatutDAO extends ApplicationDAO<Statut>
        implements ICategorizerAdder<Statut>, Function<String, Collection<Statut>>, ICategorizerDeleter<Statut>,
        ICategorizerUpdater<Statut> {

    @Override
    public void updateCategorizerEntity(Statut entity) {
        this.updateArray(entity);
    }

    @Override
    public void deleteEntity(Statut entity) {
        this.deleteArray(entity);
    }

    @Override
    public void insertEntity(Statut entity) {
        this.insertArray(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Statut> getSpecifiedStatuts(String nom) {
        String request = "FROM Statut WHERE nom LIKE :nom";

        Map<String, Object> params = new HashMap<>();
        params.put("type", "%" + nom + "%");

        return (List<Statut>) (Object) (this.getSessionFactoryHandler().select(request, params));
    }

    @SuppressWarnings("unchecked")
    public List<Statut> getStatutsByBenevole(Benevole benevole) {
        Benevole vehiculedBenevole = ((List<Benevole>) (Object) (this.getSessionFactoryHandler()
                .select("FROM Benevole ben LEFT JOIN FETCH ben.statuts WHERE ben = ?", benevole))).get(0);

        if (vehiculedBenevole != null)
            return vehiculedBenevole.getStatuts();

        return null;
    }

    @Override
    public String getGlobalSelectionQuery() {
        return "FROM Statut";
    }

    @Override
    public String onInsert(Statut entity) {
        return "INSERTION D'UN STATUT";
    }

    @Override
    public String onDelete(Statut entity) {
        @SuppressWarnings("unchecked")
        List<Benevole> benevoles = (List<Benevole>) (Object) this.getSessionFactoryHandler()
                .select("FROM Benevole benevole INNER JOIN FETCH benevole.statuts");

        for (Benevole benevole : benevoles)
            benevole.removeStatut(entity);

        UtilitiesHolder.BENEVOLE_DAO.updateCollection(benevoles);

        return "SUPPRESSION D'UN STATUT";
    }

    @Override
    public String onUpdate(Statut entity) {
        return "MISE A JOUR D'UN STATUT";
    }

    @Override
    public String getNameOfEntity() {
        return "Statut";
    }

    @Override
    public Statut createEntity() {
        return new Statut();
    }

    @Override
    public Collection<Statut> apply(String nom) {
        return this.getSpecifiedStatuts(nom);
    }
}
