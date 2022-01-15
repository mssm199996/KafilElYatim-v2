package mssmfactory.KafylElYatim.DAO.Benevole;

import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.*;
import mssmfactory.KafylElYatim.DomainModel.BenevolsAdministration.JourneeDisponible.JourDeSemaine;

import java.util.LinkedList;
import java.util.List;

public class BenevoleDAO extends ApplicationDAO<Benevole> {

    @SuppressWarnings("unchecked")
    public List<Benevole> getSpecifiedBenevoles(String names, JourDeSemaine journee, Vehicule vehicule, Cellule cellule, Profession profession, Statut statut) {
        if ((names == null || names.equals("")) && journee == null && vehicule == null && cellule == null && profession == null && statut == null)
            return this.getAll();

        String request = "SELECT DISTINCT ben FROM Benevole ben ";

        if (journee != null)
            request += ", JourneeDisponible as jd ";

        if (vehicule != null)
            request += ", Vehicule as veh ";

        if (cellule != null)
            request += ", Cellule as cel ";

        if (statut != null)
            request += ", Statut as stat ";

        if (profession != null)
            request += ", Profession as prof ";

        request += " WHERE (";

        List<Object> params = new LinkedList<Object>();

        if (journee != null) {
            request += "(jd.jourSemaine = ? and jd in elements(ben.journeesDisponibles)) and ";

            params.add(journee);
        }

        if (vehicule != null) {
            request += "(veh = ? and veh in elements(ben.vehicules)) and ";

            params.add(vehicule);
        }

        if (cellule != null) {
            request += "(cel = ? and cel in elements(ben.cellules)) and ";

            params.add(cellule);
        }

        if (statut != null) {
            request += "(stat = ? and stat in elements(ben.statuts)) and ";

            params.add(statut);
        }

        if (profession != null) {
            request += "(prof = ? and prof in elements(ben.professions)) and ";

            params.add(profession);
        }

        if (names != null && !names.equals("")) {

            String[] nameParams = this.tokenMePlease(names, " ");

            for (String nameParam : nameParams) {
                request += "(ben.nom like ? or ben.prenom like ?) and ";

                params.add("%" + nameParam + "%");
                params.add("%" + nameParam + "%");
            }
        }

        request = request.substring(0, request.length() - 4);
        request += ")";

        return (List<Benevole>) (Object) this.getSessionFactoryHandler().select(request, params);
    }

    public void affectVehiculeToBenevole(Benevole benevole, Vehicule vehicule) {
        Benevole vehiculedBenevole = (Benevole) this.getSessionFactoryHandler()
                .select("From Benevole ben LEFT JOIN FETCH ben.vehicules WHERE ben = ?", benevole).get(0);

        if (vehiculedBenevole != null) {
            vehiculedBenevole.addVehicule(vehicule);

            this.updateArray(vehiculedBenevole);
        }
    }

    public void detachVehiculeFromBenevole(Benevole benevole, Vehicule vehicule) {
        Benevole vehiculedBenevole = (Benevole) this.getSessionFactoryHandler()
                .select("From Benevole ben LEFT JOIN FETCH ben.vehicules WHERE ben = ?", benevole).get(0);

        if (vehiculedBenevole != null) {
            vehiculedBenevole.removeVehicule(vehicule);

            this.updateArray(vehiculedBenevole);
        }
    }

    public void affectCelluleToBenevole(Benevole benevole, Cellule cellule) {
        Benevole celluledBenevole = (Benevole) this.getSessionFactoryHandler()
                .select("From Benevole ben LEFT JOIN FETCH ben.cellules WHERE ben = ?", benevole).get(0);

        if (celluledBenevole != null) {
            celluledBenevole.addCellule(cellule);

            this.updateArray(celluledBenevole);
        }
    }

    public void detachCelluleFromBenevole(Benevole benevole, Cellule cellule) {
        Benevole celluledBenevole = (Benevole) this.getSessionFactoryHandler()
                .select("From Benevole ben LEFT JOIN FETCH ben.cellules WHERE ben = ?", benevole).get(0);

        if (celluledBenevole != null) {
            celluledBenevole.removeCellule(cellule);

            this.updateArray(celluledBenevole);
        }
    }

    public void affectStatutToBenevole(Benevole benevole, Statut statut) {
        Benevole statutedBenevole = (Benevole) this.getSessionFactoryHandler()
                .select("From Benevole ben LEFT JOIN FETCH ben.statuts WHERE ben = ?", benevole).get(0);

        if (statutedBenevole != null) {
            statutedBenevole.addStatut(statut);

            this.updateArray(statutedBenevole);
        }
    }

    public void detachStatutFromBenevole(Benevole benevole, Statut statut) {
        Benevole statutedBenevole = (Benevole) this.getSessionFactoryHandler()
                .select("From Benevole ben LEFT JOIN FETCH ben.statuts WHERE ben = ?", benevole).get(0);

        if (statutedBenevole != null) {
            statutedBenevole.removeStatut(statut);

            this.updateArray(statutedBenevole);
        }
    }

    // -->

    public void affectProfessionToBenevole(Benevole benevole, Profession profession) {
        Benevole professionedBenevole = (Benevole) this.getSessionFactoryHandler()
                .select("From Benevole ben LEFT JOIN FETCH ben.professions WHERE ben = ?", benevole).get(0);

        if (professionedBenevole != null) {
            professionedBenevole.addProfession(profession);

            this.updateArray(professionedBenevole);
        }
    }

    public void detachProfessionFromBenevole(Benevole benevole, Profession profession) {
        Benevole professionedBenevole = (Benevole) this.getSessionFactoryHandler()
                .select("From Benevole ben LEFT JOIN FETCH ben.professions WHERE ben = ?", benevole).get(0);

        if (professionedBenevole != null) {
            professionedBenevole.removeProfession(profession);

            this.updateArray(professionedBenevole);
        }
    }

    @Override
    public String getGlobalSelectionQuery() {
        return "FROM Benevole";
    }

    @Override
    public String onInsert(Benevole entity) {
        return "INSERTION D'UN BENEVOLE";
    }

    @Override
    public String onDelete(Benevole entity) {
        return "SUPPRESSION D'UN BENEVOLE";
    }

    @Override
    public String onUpdate(Benevole entity) {
        return "MISE A JOUR D'UN BENEVOLE";
    }

    @Override
    public String getNameOfEntity() {
        return "Benevole";
    }
}
