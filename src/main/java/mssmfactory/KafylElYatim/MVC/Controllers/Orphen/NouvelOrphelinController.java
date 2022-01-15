package mssmfactory.KafylElYatim.MVC.Controllers.Orphen;

import java.time.LocalDate;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Window;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.AddController;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.ApparencePhysique;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierFamilial;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierMedical;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierScolaire;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin.Genre;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;

public class NouvelOrphelinController extends AddController<Orphelin> {

	public static Tuteur TUTEUR = null;
	public static Orphelin LAST_INSERTED_ORPHELIN = null;

	@FXML
	private Label nomTuteur;

	@FXML
	private JFXTextField nom;

	@FXML
	private JFXTextField prenom;

	@FXML
	private JFXDatePicker ddn;

	@FXML
	private JFXRadioButton masculin;

	@FXML
	private ToggleGroup sexe;

	@FXML
	private JFXRadioButton feminin;

	@FXML
	private JFXTextField relationTuteur;

	@Override
	public void confirm() {
		Tuteur tuteur = NouvelOrphelinController.TUTEUR;

		String nom = this.nom.getText();
		LocalDate ddn = this.ddn.getValue();
		String prenom = this.prenom.getText();
		String relationTuteur = this.relationTuteur.getText();
		Genre sexe = this.sexe.getSelectedToggle().equals(this.masculin) ? Genre.Masculin : Genre.Feminin;

		if (nom.equals("") || prenom.equals("") || ddn == null || LocalDate.now().compareTo(ddn) < 0) {
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), this.getInsertHeader(),
					"Veuillez revoir le formulaire d'inscription", this.getWindow());
			return;
		}

		DossierScolaire dossierScolaire = new DossierScolaire();
		dossierScolaire.setDdMaj(null);
		dossierScolaire.setNiveauScolaire(null);
		dossierScolaire.setScolarise(false);
		dossierScolaire.setAnneeScolaire(-1);

		DossierFamilial dossierFamilial = new DossierFamilial();
		dossierFamilial.setNomMere("");
		dossierFamilial.setNomPere("");
		dossierFamilial.setPrenomMere("");
		dossierFamilial.setPrenomPere("");
		dossierFamilial.setTypeOrphelinat(null);

		DossierMedical dossierMedical = new DossierMedical();
		dossierMedical.setMedicaments("");
		dossierMedical.setPathologie("");

		ApparencePhysique apparencePhysique = new ApparencePhysique();
		apparencePhysique.setDdMaj(null);
		apparencePhysique.setObservation("");
		apparencePhysique.setPointure("");

		Orphelin orphelin = new Orphelin();
		orphelin.setDdn(ddn);
		orphelin.setNom(nom);
		orphelin.setGenre(sexe);
		orphelin.setObsevation("");
		orphelin.setPrenom(prenom);
		orphelin.setRelationOrphelinTuteur(relationTuteur);

		apparencePhysique.setOrphelin(orphelin);
		dossierMedical.setOrphelin(orphelin);
		dossierFamilial.setOrphelin(orphelin);
		dossierScolaire.setOrphelin(orphelin);

		orphelin.setApparencePhysique(apparencePhysique);
		orphelin.setDossierMedical(dossierMedical);
		orphelin.setDossierFamilial(dossierFamilial);
		orphelin.setDossierScolaire(dossierScolaire);
		orphelin.setTuteur(tuteur);
		orphelin.setAuthorized(false);

		(new Thread(() -> {
			this.onInsertCallback(orphelin);
		})).start();

		this.setLastInsertedEntity(orphelin);
		this.getWindow().hide();
	}

	@Override
	public Window getWindow() {
		return this.ddn.getScene().getWindow();
	}

	@Override
	public String getNomGestion() {
		return "Gestion des orphelins";
	}

	@Override
	public String getInsertHeader() {
		return "Nouvel orphelin";
	}

	@Override
	public void setLastInsertedEntity(Orphelin entity) {
		NouvelOrphelinController.LAST_INSERTED_ORPHELIN = entity;
	}

	@Override
	public void onInsertCallback(Orphelin entity) {
		UtilitiesHolder.ORPHELIN_DAO.insertEntity(entity);
	}

	@Override
	public void onShowingResetResult() {
		super.onShowingResetResult();

		this.nomTuteur
				.setText(NouvelOrphelinController.TUTEUR.getNom() + " " + NouvelOrphelinController.TUTEUR.getPrenom());
	}
}
