package mssmfactory.KafylElYatim.MVC.Controllers.Tuteur;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.AddController;
import msjfxuicomponents.others.ICategorizerControllerAdder;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.DossierMedical;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Enquete;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Habitat;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.SituationSociale;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class NouveauTuteurController extends AddController<Tuteur> implements ICategorizerControllerAdder<Tuteur> {

	public static Tuteur LAST_INSERTED_TUTEUR = null;

	@FXML
	private JFXTextField nom;

	@FXML
	private JFXTextField prenom;

	@FXML
	private JFXDatePicker ddn;

	@FXML
	private JFXComboBox<Region> region;

	@FXML
	private JFXTextArea adresse;

	@FXML
	private JFXTextField telephoneFix;

	@FXML
	private JFXTextField telephoneMobile;

	@FXML
	private JFXTextField cni;

	@FXML
	private JFXTextField ccp;

	@FXML
	void confirm(ActionEvent event) {
		String nom = this.nom.getText();
		String prenom = this.prenom.getText();
		String telephoneFix = this.telephoneFix.getText();
		String telephoneMobile = this.telephoneMobile.getText();
		String cni = this.cni.getText();
		String ccp = this.ccp.getText();
		LocalDate ddn = this.ddn.getValue();
		Region region = this.region.getValue();
		String adresse = this.adresse.getText();

		if (ddn == null || LocalDate.now().compareTo(ddn) < 0 || nom == null || nom.equals("") || prenom == null
				|| prenom.equals("") || region == null) {
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getInsertHeader(),
					"Veuillez verifier les informations", this.getWindow());
			return;
		}

		Habitat habitat = new Habitat();
		habitat.setEtat(null);
		habitat.setTypeBien(null);
		habitat.setClassementHabitat("");

		Enquete enquete = new Enquete();
		enquete.setDateEnquete(null);
		enquete.setNomEnqueteur("");
		enquete.setPrenomEnqueteur("");

		SituationSociale situationSociale = new SituationSociale();
		situationSociale.setCompetances("");
		situationSociale.setConvertureSociale(0.0);
		situationSociale.setFonction("");
		situationSociale.setNiveauEtude("");
		situationSociale.setNiveauVie(null);
		situationSociale.setRetraite(0.0);
		situationSociale.setSalaire(0.0);

		DossierMedical dossierMedical = new DossierMedical();
		dossierMedical.setMedicaments("");
		dossierMedical.setPathologie("");

		Tuteur tuteur = new Tuteur();
		tuteur.setAdresse(adresse);
		tuteur.setDdn(ddn);
		tuteur.setNccp(ccp);
		tuteur.setNcni(cni);
		tuteur.setNom(nom);
		tuteur.setnTelFix(telephoneFix);
		tuteur.setnTelMob(telephoneMobile);
		tuteur.setPrenom(prenom);
		tuteur.setRegion(region);
		tuteur.setNbOrphelins(0);
		tuteur.setnDossierBureautique("");
		tuteur.setnTel1("");
		tuteur.setnTel2("");
		tuteur.setNomPereOrphelins("");
		tuteur.setPrenomPereOrphelins("");
		tuteur.setnCarteBanquaine("");

		situationSociale.setTuteur(tuteur);
		situationSociale.setEnquete(enquete);

		tuteur.setSituationSociale(situationSociale);
		tuteur.setHabitat(habitat);
		tuteur.setDossierMedical(dossierMedical);

		habitat.setTuteur(tuteur);

		enquete.setSituationSociale(situationSociale);

		dossierMedical.setTuteur(tuteur);

		(new Thread(() -> {
			this.onInsertCallback(tuteur);
		})).start();

		this.setLastInsertedEntity(tuteur);
		this.getWindow().hide();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ConstantsHolder.NOUVEAU_TUTEUR_CONTROLLER = this;

		super.initialize(location, resources);

		this.initComboBoxes();
	}

	public void initComboBoxes() {
		this.region.setItems(ConstantsHolder.REGIONS_DATASOURCE);
	}

	@Override
	public Window getWindow() {
		return this.adresse.getScene().getWindow();
	}

	@Override
	public String getNomGestion() {
		return "Gestion des tuteurs";
	}

	@Override
	public String getInsertHeader() {
		return "Nouveau tuteur";
	}

	@Override
	public void setLastInsertedEntity(Tuteur entity) {
		NouveauTuteurController.LAST_INSERTED_TUTEUR = entity;
	}

	@Override
	public void onInsertCallback(Tuteur entity) {
		UtilitiesHolder.TUTEUR_DAO.insertEntity(entity);
	}

	@Override
	public Stage getStage() {
		return (Stage) this.getWindow();
	}

	@Override
	public Tuteur getConstructedCategorizer() {
		return NouveauTuteurController.LAST_INSERTED_TUTEUR;
	}
}
