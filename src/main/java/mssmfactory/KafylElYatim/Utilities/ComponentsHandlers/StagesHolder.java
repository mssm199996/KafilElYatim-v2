package mssmfactory.KafylElYatim.Utilities.ComponentsHandlers;

import mssmfactory.KafylElYatim.MVC.Stages.Appareillage.AffectationAppareillageStage;
import mssmfactory.KafylElYatim.MVC.Stages.Benevole.AffectationVehiculeBenevoleStage;
import mssmfactory.KafylElYatim.MVC.Stages.Benevole.NouveauBenevoleStage;
import mssmfactory.KafylElYatim.MVC.Stages.Benevole.NouvelleJourneeDisponibleStage;
import mssmfactory.KafylElYatim.MVC.Stages.Configuration.ConfigurationStage;
import mssmfactory.KafylElYatim.MVC.Stages.Donation.BilansDonationsStage;
import mssmfactory.KafylElYatim.MVC.Stages.Donation.NouveauDonateurStage;
import mssmfactory.KafylElYatim.MVC.Stages.Donation.NouvelleDonationStage;
import mssmfactory.KafylElYatim.MVC.Stages.EvenementBon.NouveauEvenementBonStage;
import mssmfactory.KafylElYatim.MVC.Stages.EvenementDecharge.NouveauEvenementDechargeStage;
import mssmfactory.KafylElYatim.MVC.Stages.Main.MainStage;
import mssmfactory.KafylElYatim.MVC.Stages.Orphen.AuthorizedOrphensStage;
import mssmfactory.KafylElYatim.MVC.Stages.Orphen.NouvelOrphelinStage;
import mssmfactory.KafylElYatim.MVC.Stages.Tuteur.NouveauTuteurStage;

public class StagesHolder {

	public static NouveauDonateurStage NOUVEAU_DONATEUR_STAGE = new NouveauDonateurStage();
	public static NouvelleDonationStage NOUVELLE_DONATION_STAGE = new NouvelleDonationStage();
	public static BilansDonationsStage BILANS_DONATIONS_STAGE = new BilansDonationsStage();

	public static NouveauTuteurStage NOUVEAU_TUTEUR_STAGE = new NouveauTuteurStage();

	public static NouvelOrphelinStage NOUVEL_ORPHELIN_STAGE = new NouvelOrphelinStage();
	public static AuthorizedOrphensStage AUTHORIZED_ORPHENS_STAGE = new AuthorizedOrphensStage();

	public static NouveauBenevoleStage NOUVEAU_BENEVOLE_STAGE = new NouveauBenevoleStage();
	public static NouvelleJourneeDisponibleStage NOUVELLE_JOURNEE_DISPONIBLE_STAGE = new NouvelleJourneeDisponibleStage();
	public static AffectationVehiculeBenevoleStage AFFECTATION_VEHICULE_BENEVOLE_STAGE = new AffectationVehiculeBenevoleStage();

	public static NouveauEvenementBonStage NOUVEAU_EVENEMENT_BON_STAGE = new NouveauEvenementBonStage();
	public static NouveauEvenementDechargeStage NOUVEAU_EVENEMENT_DECHARGE_STAGE = new NouveauEvenementDechargeStage();

	public static ConfigurationStage CONFIGURATION_STAGE = new ConfigurationStage();
	public static AffectationAppareillageStage AFFECTATION_APPAREILLAGE_STAGE = new AffectationAppareillageStage();

	/* ------------------------------------------------------------------------- */

	public static MainStage MAIN_STAGE = null;
}
