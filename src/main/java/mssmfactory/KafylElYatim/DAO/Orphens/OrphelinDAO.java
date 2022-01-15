package mssmfactory.KafylElYatim.DAO.Orphens;

import msjfxuicomponents.others.IDescriptorUpdator;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.*;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierScolaire.NiveauScolaire;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin.Genre;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class OrphelinDAO extends ApplicationDAO<Orphelin> implements IDescriptorUpdator<Orphelin> {

    @SuppressWarnings("unchecked")
    public List<Orphelin> getAuthorizedOrphelins() {
        return (List<Orphelin>) (Object) this.getSessionFactoryHandler().select("FROM Orphelin orphelin "
                + "LEFT JOIN FETCH orphelin.tuteur as tuteur LEFT OUTER JOIN FETCH tuteur.region WHERE (orphelin.authorized = ?)", true);
    }

    public List<Orphelin> getRealOrphelinByTuteur(Tuteur tuteur) {
        String request = "FROM Orphelin orphelin WHERE " +
                "(" +
                "(orphelin.tuteur = ?) " +
                "AND " +
                "(" +
                "(orphelin.authorized) = ? " +
                "OR " +
                "(" +
                "(orphelin.ddn >= ? and orphelin.genre = ?) " +
                "OR " +
                "(orphelin.ddn >= ? and orphelin.genre = ?) " +
                ")" +
                ")" +
                ")";

        LocalDate femaleMaxDate = LocalDate.now().minusYears(ConstantsHolder.FEMALE_AGE_LIMIT);
        LocalDate maleMaxDate = LocalDate.now().minusYears(ConstantsHolder.MALE_AGE_LIMIT);

        LinkedList<Object> params = new LinkedList<Object>();
        params.add(tuteur);
        params.add(true);
        params.add(femaleMaxDate);
        params.add(Genre.Feminin);
        params.add(maleMaxDate);
        params.add(Genre.Masculin);

        return (List<Orphelin>) (Object) this.getSessionFactoryHandler().select(request, params);
    }

    public List<Orphelin> getRealOrphelinWithDossierScolaireByTuteur(Tuteur tuteur) {
        String request = "FROM Orphelin orphelin LEFT OUTER JOIN FETCH orphelin.dossierScolaire WHERE " +
                "(" +
                "(orphelin.tuteur = ?) " +
                "AND " +
                "(" +
                "(orphelin.authorized) = ? " +
                "OR " +
                "(" +
                "(orphelin.ddn >= ? and orphelin.genre = ?) " +
                "OR " +
                "(orphelin.ddn >= ? and orphelin.genre = ?) " +
                ")" +
                ")" +
                ")";

        LocalDate femaleMaxDate = LocalDate.now().minusYears(ConstantsHolder.FEMALE_AGE_LIMIT);
        LocalDate maleMaxDate = LocalDate.now().minusYears(ConstantsHolder.MALE_AGE_LIMIT);

        LinkedList<Object> params = new LinkedList<Object>();
        params.add(tuteur);
        params.add(true);
        params.add(femaleMaxDate);
        params.add(Genre.Feminin);
        params.add(maleMaxDate);
        params.add(Genre.Masculin);

        return (List<Orphelin>) (Object) this.getSessionFactoryHandler().select(request, params);
    }

    public List<Orphelin> getByTuteur(Tuteur tuteur) {
        String request = "FROM Orphelin orphelin WHERE orphelin.tuteur = ? ORDER BY orphelin.nom ASC, orphelin.prenom ASC";
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(tuteur);

        return (List<Orphelin>) (Object) this.getSessionFactoryHandler().select(request, params);
    }

    @SuppressWarnings("unchecked")
    public List<Orphelin> getAgedOrphelins(int ageMin, Genre genre) {
        String request = "FROM Orphelin orphelin " + "LEFT OUTER JOIN FETCH orphelin.tuteur "
                + "WHERE (orphelin.authorized = ? and orphelin.ddn <= ? and orphelin.genre = ?) "
                + "ORDER BY orphelin.nom ASC, orphelin.prenom ASC";

        LocalDate minDate = LocalDate.now().minusYears(ageMin);

        LinkedList<Object> params = new LinkedList<Object>();
        params.add(false);
        params.add(minDate);
        params.add(genre);

        return (List<Orphelin>) (Object) this.getSessionFactoryHandler().select(request, params);
    }

    public Long countNoneAgedOrAutorizedOrphelinsByTuteur(Tuteur tuteur) {
        String request = "SELECT COUNT(*) FROM Orphelin orphelin WHERE " +
                "(" +
                "(orphelin.tuteur = ?) " +
                "AND " +
                "(" +
                "(orphelin.authorized) = ? " +
                "OR " +
                "(" +
                "(orphelin.ddn >= ? and orphelin.genre = ?) " +
                "OR " +
                "(orphelin.ddn >= ? and orphelin.genre = ?) " +
                ")" +
                ")" +
                ")";

        LocalDate femaleMaxDate = LocalDate.now().minusYears(ConstantsHolder.FEMALE_AGE_LIMIT);
        LocalDate maleMaxDate = LocalDate.now().minusYears(ConstantsHolder.MALE_AGE_LIMIT);

        LinkedList<Object> params = new LinkedList<Object>();
        params.add(tuteur);
        params.add(true);
        params.add(femaleMaxDate);
        params.add(Genre.Feminin);
        params.add(maleMaxDate);
        params.add(Genre.Masculin);

        List<Long> result = (List<Long>) (Object) this.getSessionFactoryHandler().select(request, params);

        if (result.isEmpty())
            return 0L;
        else return result.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Orphelin> getSpecifiedOrphelins(Tuteur tuteur, Region region, String names, Genre gender, int ageMin, int ageMax,
                                                NiveauScolaire niveauScolaire, Integer anneeScolaire, LocalDate dateMin, LocalDate dateMax,
                                                boolean includeAged) {
        if (tuteur == null && region == null && gender == null && ageMin == -1 && ageMax == -1 && (names == null || names.equals(""))
                && niveauScolaire == null && anneeScolaire == null && dateMin == null && dateMax == null && includeAged)
            return this.getAll();

        String request = "SELECT DISTINCT orphelin FROM Orphelin orphelin " + "LEFT JOIN FETCH orphelin.tuteur tuteur LEFT OUTER JOIN FETCH tuteur.region ";
        String condition = "WHERE (";

        LinkedList<Object> params = new LinkedList<Object>();

        if (niveauScolaire != null || anneeScolaire != null)
            request += "INNER JOIN orphelin.dossierScolaire dossierScolaire ";

        if (niveauScolaire != null) {
            condition += "(dossierScolaire.niveauScolaire = ?) and ";

            params.add(niveauScolaire);
        }

        if (anneeScolaire != null) {
            condition += "(dossierScolaire.anneeScolaire = ?) and ";

            params.add(anneeScolaire);
        }

        if (region != null) {
            condition += "(tuteur.region = ?) and ";
            params.add(region);
        }

        if (tuteur != null) {
            condition += "(tuteur = ?) and ";
            params.add(tuteur);
        }

        if (gender != null) {
            condition += "(orphelin.genre = ?) and ";
            params.add(gender);
        }

        condition += "(tuteur.archived = ?) and ";
        params.add(false);

        if (ageMin != -1 && ageMax != -1) {
            LocalDate minDate = LocalDate.now().minusYears(ageMin);
            LocalDate maxDate = LocalDate.now().minusYears(ageMax + 1);

            condition += "((orphelin.ddn between ? and ?) or (orphelin.ddn between ? and ?)) and ";
            params.add(minDate);
            params.add(maxDate);
            params.add(maxDate);
            params.add(minDate);
        } else if (ageMin != -1) {
            LocalDate minDate = LocalDate.now().minusYears(ageMin);

            condition += "(orphelin.ddn <= ?) and ";
            params.add(minDate);
        } else if (ageMax != -1) {
            LocalDate maxDate = LocalDate.now().minusYears(ageMax + 1);

            condition += "(orphelin.ddn >= ?) and ";
            params.add(maxDate);
        }

        if (dateMin != null && dateMax != null) {
            condition += "((orphelin.ddn between ? and ?) or (orphelin.ddn between ? and ?)) and ";
            params.add(dateMin);
            params.add(dateMax);
            params.add(dateMax);
            params.add(dateMin);
        } else if (dateMin != null) {
            condition += "(orphelin.ddn >= ?) and ";
            params.add(dateMin);
        } else if (dateMax != null) {
            condition += "(orphelin.ddn <= ?) and ";
            params.add(dateMax);
        }

        if (names != null && !names.equals("")) {
            String[] nameParams = this.tokenMePlease(names, " ");

            for (String nameParam : nameParams) {
                condition += "(orphelin.nom like ? or orphelin.prenom like ?) and ";

                params.add("%" + nameParam + "%");
                params.add("%" + nameParam + "%");
            }
        }

        if (!includeAged) {
            condition += "(orphelin.authorized = ? OR (orphelin.ddn >= ? AND orphelin.genre = ?) "
                    + "OR (orphelin.ddn >= ? AND orphelin.genre = ?)) and ";

            params.add(true);
            params.add(LocalDate.now().minusYears(ConstantsHolder.MALE_AGE_LIMIT));
            params.add(Genre.Masculin);
            params.add(LocalDate.now().minusYears(ConstantsHolder.FEMALE_AGE_LIMIT));
            params.add(Genre.Feminin);
        }

        request += condition.substring(0, condition.length() - 4);
        request += ")";

        return (List<Orphelin>) (Object) this.getSessionFactoryHandler().select(request, params);
    }

    public void affectAppareillageToOrphelin(Orphelin orphelin, Appareillage appareillage) {
        DossierMedical dm = (DossierMedical) this.getSessionFactoryHandler()
                .select("From DossierMedical dm LEFT JOIN FETCH dm.appareils WHERE dm.orphelin = ?", orphelin).get(0);
        dm.addAppareillage(appareillage);

        this.getSessionFactoryHandler().updateArray(dm);
    }

    public void dettachAppareillageFromOrphelin(Orphelin orphelin, Appareillage appareillage) {
        DossierMedical dm = (DossierMedical) this.getSessionFactoryHandler()
                .select("From DossierMedical dm LEFT JOIN FETCH dm.appareils WHERE dm.orphelin = ?", orphelin).get(0);
        dm.removeAppareillage(appareillage);

        this.getSessionFactoryHandler().updateArray(dm);
    }

    public DossierFamilial getDossierFamilial(Orphelin orphelin) {
        return this.getChild("DossierFamilial", orphelin);
    }

    public DossierMedical getDossierMedical(Orphelin orphelin) {
        return this.getChild("DossierMedical", orphelin);
    }

    public DossierScolaire getDossierScolaireOrphelin(Orphelin orphelin) {
        return this.getChild("DossierScolaire", orphelin);
    }

    public ApparencePhysique getApparencePhysiqueOrphelin(Orphelin orphelin) {
        return this.getChild("ApparencePhysique", orphelin);
    }

    @SuppressWarnings("unchecked")
    private <T> T getChild(String entityName, Orphelin orphelin) {
        List<T> result = (List<T>) (Object) this.getSessionFactoryHandler()
                .select("FROM " + entityName + " WHERE orphelin = ?", orphelin);

        if (!result.isEmpty())
            return result.get(0);

        return null;
    }

    @Override
    public String getGlobalSelectionQuery() {
        return "FROM Orphelin orphelin LEFT JOIN FETCH orphelin.tuteur tuteur LEFT OUTER JOIN FETCH tuteur.region WHERE(tuteur.archived = ?)";
    }

    @Override
    public List<Orphelin> getAll() {
        return (List<Orphelin>) (Object) this.getSessionFactoryHandler().select(this.getGlobalSelectionQuery(), new Object[]{false});
    }

    @Override
    public String onInsert(Orphelin entity) {
        UtilitiesHolder.TUTEUR_DAO.addToNombreOrphelin(entity.getTuteur(), 1);

        return "INSERTION DE L'ORPHELIN " + entity;
    }

    @Override
    public String onDelete(Orphelin entity) {
        UtilitiesHolder.TUTEUR_DAO.addToNombreOrphelin(entity.getTuteur(), -1);

        return "SUPPRESSION DE L'ORPHELIN " + entity;
    }

    @Override
    public String onUpdate(Orphelin entity) {
        return "MISE A JOUR DE L'ORPHELIN " + entity;
    }

    @Override
    public String getNameOfEntity() {
        return "Orphelin";
    }

    @Override
    public void update(Orphelin descriptor) {
        this.updateEntity(descriptor);
    }
}
