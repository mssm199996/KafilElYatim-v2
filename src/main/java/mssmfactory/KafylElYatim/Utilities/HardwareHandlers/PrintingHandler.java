package mssmfactory.KafylElYatim.Utilities.HardwareHandlers;

import javafx.application.Platform;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.msjrviewer.MSJRViewerStage.MSJRViewerFxMode;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.Action;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.EvenementBon;
import mssmfactory.KafylElYatim.DomainModel.EventsAdministration.EvenementDecharge;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin;
import mssmfactory.KafylElYatim.DomainModel.Others.OrphelinEvenementBonDTO;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.function.Consumer;

public class PrintingHandler {
    public static Path KY_BON = Paths.get("resources/ky-bon.jasper");
    public static Path KY_DECHARGE = Paths.get("resources/ky-decharge.jasper");
    public static Path KY_TUTORS_LIST = Paths.get("resources/ky-tutors-list.jasper");
    public static Path KY_TUTORS_LIST_REAL_ORPHENS_NUMBER = Paths.get("resources/ky-tutors-list-with-real-orphens-number.jasper");
    public static Path KY_ORPHENS_LIST = Paths.get("resources/ky-orphens-list.jasper");

    public void printTutorsList(Collection<Tuteur> tutors) {
        this.prepareCollectionDocumentAndPrintIt(PrintingHandler.KY_TUTORS_LIST, tutors);
    }

    public void printTutorsListWithRealOrphelinsNumber(Collection<Tuteur> tutors) {
        this.prepareCollectionDocumentAndPrintIt(PrintingHandler.KY_TUTORS_LIST_REAL_ORPHENS_NUMBER, tutors);
    }

    public void printTutorsListWithOrphelins(Collection<Tuteur> tuteurs) {
        List<Orphelin> orphelins = new LinkedList<>();

        for (Tuteur tuteur : tuteurs) {
            List<Orphelin> intermediate = UtilitiesHolder.ORPHELIN_DAO.getByTuteur(tuteur);
            intermediate.forEach(orphelin -> orphelin.setTuteur(tuteur));
            orphelins.addAll(intermediate);
        }

        this.printOrphensList(orphelins);
    }

    public void printTutorsListWithRealOrphelins(Collection<Tuteur> tuteurs) {
        List<Orphelin> orphelins = new LinkedList<>();

        for (Tuteur tuteur : tuteurs) {
            List<Orphelin> intermediate = UtilitiesHolder.ORPHELIN_DAO.getRealOrphelinByTuteur(tuteur);
            intermediate.forEach(orphelin -> orphelin.setTuteur(tuteur));
            orphelins.addAll(intermediate);
        }

        this.printOrphensList(orphelins);
    }

    public void printOrphensList(Collection<Orphelin> orphelins) {
        this.prepareCollectionDocumentAndPrintIt(PrintingHandler.KY_ORPHENS_LIST, orphelins);
    }

    public void printBonsEvenementBon(EvenementBon evenementBon, Map<Tuteur, List<Orphelin>> datasource) {
        Collection<Tuteur> tuteurs = datasource.keySet();

        MSJFXUIComponentsHolder.MS_JR_VIEWER_STAGE.printWithMultipleDocuments(tuteurs, PrintingHandler.KY_BON,
                new Consumer<SimpleEntry<Tuteur, Map<String, Object>>>() {

                    @Override
                    public void accept(SimpleEntry<Tuteur, Map<String, Object>> t) {
                        Tuteur tuteur = t.getKey();
                        Map<String, Object> params = t.getValue();
                        params.put("numeroEvenement", evenementBon.getIdEvenement() + "");
                        params.put("descriptionEvenement", evenementBon.getDescriptionEvenement());
                        params.put("nomPrenomTuteur", tuteur.getNom() + " " + tuteur.getPrenom());
                        params.put("numeroTelephoneTuteur", coalesce(tuteur.getnTelMob()));
                        params.put("adresseTuteur", coalesce(tuteur.getAdresse()));

                        Region region = UtilitiesHolder.REGION_DAO.findRegionByTuteur(tuteur);

                        params.put("nomZone", region.toString());

                        List<Orphelin> orphelins = datasource.get(tuteur);
                        List<OrphelinEvenementBonDTO> orphelinEvenementBonDTOs = new ArrayList<>(orphelins.size());

                        for (Orphelin orphelin : orphelins)
                            orphelinEvenementBonDTOs.add(new OrphelinEvenementBonDTO(evenementBon, orphelin));

                        prepareDocumentForPrinting(params, new JRBeanCollectionDataSource(orphelinEvenementBonDTOs));
                    }
                }, MSJRViewerFxMode.REPORT_VIEW);
    }

    public void printBonsEvenementDecharge(EvenementDecharge evenementDecharge, List<Action> datasource) {
        MSJFXUIComponentsHolder.MS_JR_VIEWER_STAGE.printWithMultipleDocuments(datasource,
                PrintingHandler.KY_DECHARGE, new Consumer<SimpleEntry<Action, Map<String, Object>>>() {

                    @Override
                    public void accept(SimpleEntry<Action, Map<String, Object>> t) {
                        Tuteur tuteur = t.getKey().getTuteur();
                        List<Orphelin> orphelins = UtilitiesHolder.ORPHELIN_DAO.getRealOrphelinWithDossierScolaireByTuteur(tuteur);

                        Map<String, Object> params = t.getValue();
                        params.put("numeroEvenement", evenementDecharge.getIdEvenement() + "");
                        params.put("descriptionEvenement", evenementDecharge.getDescriptionEvenement());
                        params.put("designationEvenement", evenementDecharge.getDesignationEvenement());
                        params.put("nomPrenomTuteur", tuteur.getNom() + " " + tuteur.getPrenom());
                        params.put("nomPereOrphelins", coalesce(tuteur.getNomPereOrphelins()));
                        params.put("adresse", coalesce(tuteur.getAdresse()));
                        params.put("adresseIndication", coalesce(tuteur.getSecondeAdresse()));
                        params.put("ncni", coalesce(tuteur.getNcni()));
                        params.put("numeroMobile", coalesce(tuteur.getnTelMob()));
                        params.put("numeroFixe", coalesce(tuteur.getnTelFix()));
                        params.put("zone", coalesce(tuteur.getRegion().toString()));
                        params.put("orphelinsDatasource", new JRBeanCollectionDataSource(orphelins));

                        params.put("quantitee", t.getKey().getQuantiteAction());

                        Region region = UtilitiesHolder.REGION_DAO.findRegionByTuteur(tuteur);

                        params.put("nomZone", region.toString());

                        prepareDocumentForPrinting(params, new JRBeanCollectionDataSource(new LinkedList<>()));
                    }
                }, MSJRViewerFxMode.REPORT_VIEW);
    }

    // --------------------------------------------------------------------------------------------------------------------

    private <T> void prepareCollectionDocumentAndPrintIt(Path documentPath, Collection<T> datasource) {
        HashMap<String, Object> params = new HashMap<>();

        this.prepareDocumentForPrinting(params, new JRBeanCollectionDataSource(datasource));
        this.printDocument(documentPath, params, new JREmptyDataSource());
    }

    private void prepareDocumentForPrinting(Map<String, Object> params, JRDataSource mainDataSource) {
        String entete = "resources/entete.png";

        if (!Files.exists(Paths.get(entete)))
            entete = null;

        params.put("entete", entete);
        params.put("lieuDate", "Tlemcen le, " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        params.put("datasource", mainDataSource);
    }

    private void printDocument(Path documentPath, HashMap<String, Object> params, JRDataSource mainDataSource) {
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(documentPath.toAbsolutePath().toString(), params,
                    mainDataSource);

            Platform.runLater(() -> {
                MSJFXUIComponentsHolder.MS_JR_VIEWER_STAGE.previewDocument(jasperPrint, MSJRViewerFxMode.REPORT_VIEW);
            });
        } catch (JRException e) {
            e.printStackTrace();

            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Kafil El Yatim", "Erreur d'impression",
                    "Le fichiers d'impression a été supprimé / endommagé, ou les paramètres d'impressions ont été mal configurées",
                    null);
        }
    }

    public Object coalesce(Object... items) {
        for (Object o : items)
            if (o != null)
                return o;

        return "";
    }
}
