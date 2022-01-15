package mssmfactory.KafylElYatim.Utilities;

import MainPackage.CopyrightHandler;
import mssmfactory.KafylElYatim.DAO.Appareillage.AppareillageDAO;
import mssmfactory.KafylElYatim.DAO.Benevole.*;
import mssmfactory.KafylElYatim.DAO.Donations.DonateurDAO;
import mssmfactory.KafylElYatim.DAO.Donations.DonationDAO;
import mssmfactory.KafylElYatim.DAO.Events.ActionDAO;
import mssmfactory.KafylElYatim.DAO.Events.EvenementBonDAO;
import mssmfactory.KafylElYatim.DAO.Events.EvenementDechargeDAO;
import mssmfactory.KafylElYatim.DAO.Orphens.OrphelinDAO;
import mssmfactory.KafylElYatim.DAO.Others.CompteDAO;
import mssmfactory.KafylElYatim.DAO.Others.ConfigurationDAO;
import mssmfactory.KafylElYatim.DAO.Others.OperationDAO;
import mssmfactory.KafylElYatim.DAO.Tutors.RegionDAO;
import mssmfactory.KafylElYatim.DAO.Tutors.TuteurDAO;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ApplicationSessionFactoryHandler;
import mssmfactory.KafylElYatim.Utilities.HardwareHandlers.PrintingHandler;

public abstract class UtilitiesHolder {

    public static ApplicationSessionFactoryHandler APPLICATION_SESSION_FACTORY_HANDLER;
    public static CopyrightHandler COPYRIGHT_HANDLER;

    // -----------------------------------------------------------------------------------
    public static OperationDAO OPERATION_DAO = new OperationDAO();
    // -----------------------------------------------------------------------------------
    public static DonateurDAO DONATEUR_DAO = new DonateurDAO();
    public static DonationDAO DONATION_DAO = new DonationDAO();
    // -----------------------------------------------------------------------------------
    public static AppareillageDAO APPAREILLAGE_DAO = new AppareillageDAO();
    public static ProfessionDAO PROFESSION_DAO = new ProfessionDAO();
    public static StatutDAO STATUT_DAO = new StatutDAO();
    public static CelluleDAO CELLULE_DAO = new CelluleDAO();
    // -----------------------------------------------------------------------------------
    public static ActionDAO ACTION_DAO = new ActionDAO();
    public static EvenementBonDAO EVENEMENT_BON_DAO = new EvenementBonDAO();
    public static EvenementDechargeDAO EVENEMENT_DECHARGE_DAO = new EvenementDechargeDAO();
    // -----------------------------------------------------------------------------------
    public static TuteurDAO TUTEUR_DAO = new TuteurDAO();
    public static RegionDAO REGION_DAO = new RegionDAO();
    // -----------------------------------------------------------------------------------
    public static OrphelinDAO ORPHELIN_DAO = new OrphelinDAO();
    // -----------------------------------------------------------------------------------
    public static BenevoleDAO BENEVOLE_DAO = new BenevoleDAO();
    public static VehiculeDAO VEHICULE_DAO = new VehiculeDAO();
    public static JourneeDisponibleDAO JOURNEE_DISPONIBLE_DAO = new JourneeDisponibleDAO();
    // -----------------------------------------------------------------------------------
    public static ConfigurationDAO CONFIGURATION_DAO = new ConfigurationDAO();
    public static CompteDAO COMPTE_DAO = new CompteDAO();
    // -----------------------------------------------------------------------------------

    public static PrintingHandler PRINTING_HANDLER = new PrintingHandler();
}
