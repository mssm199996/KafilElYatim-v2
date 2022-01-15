package mssmfactory.KafylElYatim.DAO.Benevole;

import msjfxuicomponents.others.ICategorizerAdder;
import msjfxuicomponents.others.ICategorizerDeleter;
import msjfxuicomponents.others.ICategorizerUpdater;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Benevole;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Profession;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Vehicule;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ProfessionDAO extends ApplicationDAO<Profession>
        implements ICategorizerAdder<Profession>, Function<String, Collection<Profession>>, ICategorizerDeleter<Profession>,
        ICategorizerUpdater<Profession> {

    @Override
    public void updateCategorizerEntity(Profession entity) {
        this.updateArray(entity);
    }

    @Override
    public void deleteEntity(Profession entity) {
        this.deleteArray(entity);
    }

    @Override
    public void insertEntity(Profession entity) {
        this.insertArray(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Profession> getSpecifiedProfessions(String nom) {
        String request = "FROM Profession WHERE nom LIKE :nom";

        Map<String, Object> params = new HashMap<>();
        params.put("nom", "%" + nom + "%");

        return (List<Profession>) (Object) (this.getSessionFactoryHandler().select(request, params));
    }

    @SuppressWarnings("unchecked")
    public List<Profession> getProfessionsByBenevole(Benevole benevole) {
        Benevole vehiculedBenevole = ((List<Benevole>) (Object) (this.getSessionFactoryHandler()
                .select("FROM Benevole ben LEFT JOIN FETCH ben.professions WHERE ben = ?", benevole))).get(0);

        if (vehiculedBenevole != null)
            return vehiculedBenevole.getProfessions();

        return null;
    }

    @Override
    public String getGlobalSelectionQuery() {
        return "FROM Profession";
    }

    @Override
    public String onInsert(Profession entity) {
        return "INSERTION D'UNE PROFESSION";
    }

    @Override
    public String onDelete(Profession entity) {
        @SuppressWarnings("unchecked")
        List<Benevole> benevoles = (List<Benevole>) (Object) this.getSessionFactoryHandler()
                .select("FROM Benevole benevole INNER JOIN FETCH benevole.professions");

        for (Benevole benevole : benevoles)
            benevole.removeProfession(entity);

        UtilitiesHolder.BENEVOLE_DAO.updateCollection(benevoles);

        return "SUPPRESSION D'UNE PROFESSION";
    }

    @Override
    public String onUpdate(Profession entity) {
        return "MISE A JOUR D'UNE PROFESSION";
    }

    @Override
    public String getNameOfEntity() {
        return "Profession";
    }

    @Override
    public Profession createEntity() {
        return new Profession();
    }

    @Override
    public Collection<Profession> apply(String nom) {
        return this.getSpecifiedProfessions(nom);
    }
}
