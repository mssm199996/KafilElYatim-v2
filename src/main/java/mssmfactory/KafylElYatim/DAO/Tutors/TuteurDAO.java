package mssmfactory.KafylElYatim.DAO.Tutors;

import msjfxuicomponents.others.ICategorizerDeleter;
import msjfxuicomponents.others.ICategorizerSearcher;
import msjfxuicomponents.others.IDescriptorUpdator;
import mssmfactory.KafylElYatim.DAO.Others.ApplicationDAO;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Appareillage;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierFamilial;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.*;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Habitat.EtatHabitat;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Habitat.TypeBien;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.SituationSociale.NiveauVie;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class TuteurDAO extends ApplicationDAO<Tuteur>
        implements IDescriptorUpdator<Tuteur>, ICategorizerDeleter<Tuteur>, ICategorizerSearcher<Tuteur> {

    @Override
    public void deleteEntity(Tuteur entity) {
        this.deleteArray(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Tuteur> getSpecifiedTuteurs(String names, Region region, NiveauVie ndv, EtatHabitat etatHabitat,
                                            TypeBien typeBien, boolean displayArchived) {
        if ((names == null || names.equals("")) && region == null && ndv == null && etatHabitat == null
                && typeBien == null && !displayArchived)
            return this.getAll();

        String request = "SELECT DISTINCT tuteur FROM Tuteur tuteur " + "LEFT OUTER JOIN FETCH tuteur.region ";

        String condition = "WHERE (";

        List<Object> params = new LinkedList<Object>();

        if (region != null) {
            condition += "(tuteur.region = ?) and ";

            params.add(region);
        }

        if (ndv != null) {
            request += " LEFT OUTER JOIN tuteur.situationSociale ss ";
            condition += "(ss.niveauVie = ?) and ";

            params.add(ndv);
        }

        if (etatHabitat != null || typeBien != null) {
            request += " LEFT OUTER JOIN tuteur.habitat habitat ";
        }

        if (etatHabitat != null) {
            condition += "(habitat.etat = ?) and ";

            params.add(etatHabitat);
        }

        if (typeBien != null) {
            condition += "(habitat.typeBien = ?) and ";

            params.add(typeBien);
        }

        condition += "(archived = ?) and ";
        params.add(displayArchived);

        if (names != null && !names.equals("")) {
            String[] nameParams = this.tokenMePlease(names, " ");

            for (String nameParam : nameParams) {
                condition += "(tuteur.nom like ? or tuteur.prenom like ?) and ";

                params.add("%" + nameParam + "%");
                params.add("%" + nameParam + "%");
            }
        }

        request += condition.substring(0, condition.length() - 4);
        request += ")";

        return (List<Tuteur>) (Object) this.getSessionFactoryHandler().select(request, params);
    }

    @SuppressWarnings("unchecked")
    public DossierFamilial getDossierFamilialTuteur(Tuteur tuteur) {
        DossierFamilial dossierFamilial = new DossierFamilial();

        List<DossierFamilial> result = (List<DossierFamilial>) (Object) this.getSessionFactoryHandler()
                .select("FROM DossierFamilial e WHERE e.tuteur = ?", tuteur);

        if (!result.isEmpty())
            dossierFamilial = result.get(0);

        return dossierFamilial;
    }

    @SuppressWarnings("unchecked")
    public DossierMedical getDossierMedicalTuteur(Tuteur tuteur) {
        DossierMedical dossierFamilial = new DossierMedical();

        List<DossierMedical> result = (List<DossierMedical>) (Object) this.getSessionFactoryHandler()
                .select("FROM DossierMedicalTuteur e WHERE e.tuteur = ?", tuteur);

        if (!result.isEmpty())
            dossierFamilial = result.get(0);

        return dossierFamilial;
    }

    @SuppressWarnings("unchecked")
    public Habitat getHabitatTuteur(Tuteur tuteur) {
        Habitat habitat = new Habitat();

        List<Habitat> result = (List<Habitat>) (Object) this.getSessionFactoryHandler()
                .select("FROM Habitat e WHERE e.tuteur = ?", tuteur);

        if (!result.isEmpty())
            habitat = result.get(0);

        return habitat;
    }

    @SuppressWarnings("unchecked")
    public SituationSociale getSituationSocialeTuteur(Tuteur tuteur) {
        SituationSociale situationSociale = new SituationSociale();

        List<SituationSociale> result = (List<SituationSociale>) (Object) this.getSessionFactoryHandler()
                .select("FROM SituationSociale e LEFT OUTER JOIN FETCH e.enquete WHERE e.tuteur = ?", tuteur);

        if (!result.isEmpty())
            situationSociale = result.get(0);

        return situationSociale;
    }

    public void affectAppareillage(Tuteur tuteur, Appareillage appareillage) {
        DossierMedical dossierMedical = (DossierMedical) this.getSessionFactoryHandler()
                .select("From DossierMedicalTuteur dm LEFT JOIN FETCH dm.appareils WHERE dm.tuteur = ?", tuteur).get(0);
        dossierMedical.addAppareillage(appareillage);

        this.getSessionFactoryHandler().updateArray(dossierMedical);
    }

    public void dettachAppareillage(Tuteur tuteur, Appareillage appareillage) {
        DossierMedical dossierMedical = (DossierMedical) this.getSessionFactoryHandler()
                .select("From DossierMedicalTuteur dm LEFT JOIN FETCH dm.appareils WHERE dm.tuteur = ?", tuteur).get(0);
        dossierMedical.removeAppareillage(appareillage);

        this.getSessionFactoryHandler().updateArray(dossierMedical);
    }

    public void addToNombreOrphelin(Tuteur tuteur, Integer value) {
        String request = "UPDATE Tuteur t SET t.nbOrphelins = t.nbOrphelins + ? WHERE t = ?";

        this.getSessionFactoryHandler().executeHql(request, value, tuteur);
    }

    @Override
    public String getGlobalSelectionQuery() {
        return "FROM Tuteur tuteur LEFT OUTER JOIN FETCH tuteur.region WHERE archived = ?";
    }

    @Override
    public List<Tuteur> getAll() {
        return (List<Tuteur>) (Object) this.getSessionFactoryHandler().select(this.getGlobalSelectionQuery(), new Object[]{false});
    }

    @Override
    public String onInsert(Tuteur entity) {
        return "INSERTION DU TUTEUR " + entity;
    }

    @Override
    public String onDelete(Tuteur entity) {
        return "SUPPRESSION DU TUTEUR " + entity;
    }

    @Override
    public String onUpdate(Tuteur entity) {
        return "MISE A JOUR DU TUTEUR " + entity;
    }

    @Override
    public String getNameOfEntity() {
        return "Tuteur";
    }

    @Override
    public void update(Tuteur descriptor) {
        this.updateEntity(descriptor);
    }

    @Override
    public Collection<Tuteur> search(String value) {
        return this.getSpecifiedTuteurs(value, null, null, null, null, false);
    }
}
