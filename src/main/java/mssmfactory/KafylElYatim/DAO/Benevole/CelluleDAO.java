package mssmfactory.KafylElYatim.DAO.Benevole;

import msjfxuicomponents.others.ICategorizerAdder;
import msjfxuicomponents.others.ICategorizerDeleter;
import msjfxuicomponents.others.ICategorizerUpdater;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Benevole;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Cellule;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CelluleDAO extends ApplicationDAO<Cellule>
        implements ICategorizerAdder<Cellule>, Function<String, Collection<Cellule>>, ICategorizerDeleter<Cellule>,
        ICategorizerUpdater<Cellule> {

    @Override
    public void updateCategorizerEntity(Cellule entity) {
        this.updateArray(entity);
    }

    @Override
    public void deleteEntity(Cellule entity) {
        this.deleteArray(entity);
    }

    @Override
    public void insertEntity(Cellule entity) {
        this.insertArray(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Cellule> getSpecifiedCellules(String nom) {
        String request = "FROM Cellule WHERE nom LIKE :nom";

        Map<String, Object> params = new HashMap<>();
        params.put("nom", "%" + nom + "%");

        return (List<Cellule>) (Object) (this.getSessionFactoryHandler().select(request, params));
    }

    @SuppressWarnings("unchecked")
    public List<Cellule> getCellulesByBenevole(Benevole benevole) {
        Benevole celluledBenevole = ((List<Benevole>) (Object) (this.getSessionFactoryHandler()
                .select("FROM Benevole ben LEFT JOIN FETCH ben.cellules WHERE ben = ?", benevole))).get(0);

        if (celluledBenevole != null)
            return celluledBenevole.getCellules();

        return null;
    }

    @Override
    public String getGlobalSelectionQuery() {
        return "FROM Cellule";
    }

    @Override
    public String onInsert(Cellule entity) {
        return "INSERTION D'UNE CELLULE";
    }

    @Override
    public String onDelete(Cellule entity) {
        @SuppressWarnings("unchecked")
        List<Benevole> benevoles = (List<Benevole>) (Object) this.getSessionFactoryHandler()
                .select("FROM Benevole benevole INNER JOIN FETCH benevole.cellules");

        System.out.println("Benevoles: " + benevoles);

        for (Benevole benevole : benevoles) {
            System.out.println("Removing cellule: " + entity + " from benevole: " + benevole);
            benevole.removeCellule(entity);
        }

        System.out.println("Updating Benevoles: " + benevoles);
        UtilitiesHolder.BENEVOLE_DAO.updateCollection(benevoles);

        return "SUPPRESSION D'UNE CELLULE";
    }

    @Override
    public String onUpdate(Cellule entity) {
        return "MISE A JOUR D'UNE CELLULE";
    }

    @Override
    public String getNameOfEntity() {
        return "Cellule";
    }

    @Override
    public Cellule createEntity() {
        return new Cellule();
    }

    @Override
    public Collection<Cellule> apply(String nom) {
        return this.getSpecifiedCellules(nom);
    }
}
