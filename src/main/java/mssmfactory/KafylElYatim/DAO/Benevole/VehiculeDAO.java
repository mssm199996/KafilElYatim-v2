package mssmfactory.KafylElYatim.DAO.Benevole;

import msjfxuicomponents.others.ICategorizerAdder;
import msjfxuicomponents.others.ICategorizerDeleter;
import msjfxuicomponents.others.ICategorizerUpdater;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Benevole;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.Vehicule;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class VehiculeDAO extends ApplicationDAO<Vehicule>
        implements ICategorizerAdder<Vehicule>, Function<String, Collection<Vehicule>>, ICategorizerDeleter<Vehicule>,
        ICategorizerUpdater<Vehicule> {

    @Override
    public void updateCategorizerEntity(Vehicule entity) {
        this.updateArray(entity);
    }

    @Override
    public void deleteEntity(Vehicule entity) {
        this.deleteArray(entity);
    }

    @Override
    public void insertEntity(Vehicule entity) {
        this.insertArray(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Vehicule> getSpecifiedVehicules(String type) {
        String request = "FROM Vehicule WHERE type LIKE :type";

        Map<String, Object> params = new HashMap<>();
        params.put("type", "%" + type + "%");

        return (List<Vehicule>) (Object) (this.getSessionFactoryHandler().select(request, params));
    }

    @SuppressWarnings("unchecked")
    public List<Vehicule> getVehiculesBenevole(Benevole benevole) {
        Benevole vehiculedBenevole = ((List<Benevole>) (Object) (this.getSessionFactoryHandler()
                .select("FROM Benevole ben LEFT JOIN FETCH ben.vehicules WHERE ben = ?", benevole))).get(0);

        if (vehiculedBenevole != null)
            return vehiculedBenevole.getVehicules();

        return null;
    }

    @Override
    public String getGlobalSelectionQuery() {
        return "FROM Vehicule";
    }

    @Override
    public String onInsert(Vehicule entity) {
        return "INSERTION D'UN VEHICULE";
    }

    @Override
    public String onDelete(Vehicule entity) {
        @SuppressWarnings("unchecked")
        List<Benevole> benevoles = (List<Benevole>) (Object) this.getSessionFactoryHandler()
                .select("FROM Benevole benevole INNER JOIN FETCH benevole.vehicules");

        for (Benevole benevole : benevoles)
            benevole.removeVehicule(entity);

        UtilitiesHolder.BENEVOLE_DAO.updateCollection(benevoles);

        return "SUPPRESSION D'UN VEHICULE";
    }

    @Override
    public String onUpdate(Vehicule entity) {
        return "MISE A JOUR D'UN VEHICULE";
    }

    @Override
    public String getNameOfEntity() {
        return "Vehicule";
    }

    @Override
    public Vehicule createEntity() {
        return new Vehicule();
    }

    @Override
    public Collection<Vehicule> apply(String type) {
        return this.getSpecifiedVehicules(type);
    }
}
