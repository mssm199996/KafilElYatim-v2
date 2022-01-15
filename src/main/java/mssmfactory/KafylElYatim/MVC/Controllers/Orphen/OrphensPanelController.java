package mssmfactory.KafylElYatim.MVC.Controllers.Orphen;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.componentsStuffers.DisabilityBinder;
import msjfxuicomponents.others.CrudController;
import msjfxuicomponents.others.ICategorizerDeleter;
import msjfxuicomponents.uicomponents.MSCategorizerCrudListTitledPane;
import msjfxuicomponents.uicomponents.MSSearcheableCategorizerCrudListTitledPane;
import msjfxuicomponents.uicomponents.MSSimpleObservationTitledPane;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Appareillage;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.ApparencePhysique;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierFamilial;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierFamilial.TypeOrphelinat;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierMedical;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierScolaire;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.DossierScolaire.NiveauScolaire;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin;
import mssmfactory.KafylElYatim.DomainModel.OrphensAdministration.Orphelin.Genre;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Region;
import mssmfactory.KafylElYatim.DomainModel.TutorsAdministration.Tuteur;
import mssmfactory.KafylElYatim.MVC.Controllers.Appareillage.AffectationAppareillageController;
import mssmfactory.KafylElYatim.MVC.Controllers.ReusedControllers.TableOrphelinsController;
import mssmfactory.KafylElYatim.MVC.Controllers.Utils.TableOrphelinsContainer;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.ComponentsHolder;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

public class OrphensPanelController extends CrudController<Orphelin> implements TableOrphelinsContainer {

	public OrphensPanelController() {
		super(ConstantsHolder.ORPHELINS_DATASOURCE);
	}

	@FXML
	private MSSearcheableCategorizerCrudListTitledPane<Tuteur> tuteursListView;

	@FXML
	private TableOrphelinsController tableOrphelinsController;

	@FXML
	private JFXTextField nomFilter;

	@FXML
	private JFXCheckBox displayAll;

	@FXML
	private JFXComboBox<TypeOrphelinat> typeOrphelinat;

	@FXML
	private JFXTextField nomPere;

	@FXML
	private JFXTextField prenomPere;

	@FXML
	private JFXTextField nomMere;

	@FXML
	private JFXTextField prenomMere;

	@FXML
	private JFXTextField situationFamilliale;

	@FXML
	private JFXComboBox<NiveauScolaire> niveauScolaire;

	@FXML
	private JFXComboBox<Integer> anneeScolaire;

	@FXML
	private JFXDatePicker dateDossierScolaire;

	@FXML
	private JFXTextArea observationsDossierScolaire;

	@FXML
	private JFXTextField pointure;

	@FXML
	private JFXDatePicker dateApparencePhysique;

	@FXML
	private JFXTextArea observationApparencePhysique;

	@FXML
	private JFXTextArea pathologies;

	@FXML
	private JFXTextArea medicaments;

	@FXML
	private MSCategorizerCrudListTitledPane<Appareillage> appareillages;

	@FXML
	private MSSimpleObservationTitledPane<Orphelin> observations;

	@FXML
	private JFXDatePicker dateMinFilter;

	@FXML
	private JFXDatePicker dateMaxFilter;

	@FXML
	private JFXTextField ageMinFilter;

	@FXML
	private JFXTextField ageMaxFilter;

	@FXML
	private JFXComboBox<Genre> genreFilter;

	@FXML
	private JFXComboBox<NiveauScolaire> niveauScolaireFilter;

	@FXML
	private JFXComboBox<Integer> anneeScolaireFilter;
	
	@FXML
	private JFXComboBox<Region> regionFilter;

	@FXML
	private Accordion accordion;

	@FXML
	void printOrphelins(ActionEvent event) {
		UtilitiesHolder.PRINTING_HANDLER.printOrphensList(this.getDatasource());
	}

	@FXML
	void refresh(ActionEvent event) {
		this.ageMaxFilter.setText("");
		this.ageMinFilter.setText("");
		this.anneeScolaireFilter.setValue(null);
		this.regionFilter.setValue(null);
		this.dateMaxFilter.setValue(null);
		this.dateMinFilter.setValue(null);
		this.genreFilter.setValue(null);
		this.niveauScolaireFilter.setValue(null);
		this.nomFilter.setText("");
		this.tuteursListView.clearSelection();

		this.search();
	}

	@FXML
	void search(KeyEvent event) {
		this.search();
	}

	@FXML
	void updateApparencePhysique(ActionEvent event) {
		Orphelin orphelin = this.tableOrphelinsController.getSelectionModel().getSelectedItem();

		if (orphelin != null) {
			if (!MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(),
					"Confirmation de mise à jour", "Confirmer pour proceder à la mise à jour", this.getWindow()))
				return;

			orphelin.getApparencePhysique().setDdMaj(this.dateApparencePhysique.getValue());
			orphelin.getApparencePhysique().setObservation(this.observationApparencePhysique.getText());
			orphelin.getApparencePhysique().setPointure(this.pointure.getText());

			(new Thread(() -> {
				UtilitiesHolder.ORPHELIN_DAO.updateEntity(orphelin);
			})).start();
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Message d'erreur",
					"Veuillez d'abords selectionner un orphelin !", this.getWindow());
	}

	@FXML
	void updateDossierFamillial(ActionEvent event) {
		Orphelin orphelin = this.tableOrphelinsController.getSelectionModel().getSelectedItem();

		if (orphelin != null) {
			if (!MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(),
					"Confirmation de mise à jour", "Confirmer pour proceder à la mise à jour", this.getWindow()))
				return;

			orphelin.getDossierFamilial().setNomMere(this.nomMere.getText());
			orphelin.getDossierFamilial().setNomPere(this.nomPere.getText());
			orphelin.getDossierFamilial().setPrenomMere(this.prenomMere.getText());
			orphelin.getDossierFamilial().setPrenomPere(this.prenomPere.getText());
			orphelin.getDossierFamilial().setSituationFamilliale(this.situationFamilliale.getText());
			orphelin.getDossierFamilial().setTypeOrphelinat(this.typeOrphelinat.getValue());

			(new Thread(() -> {
				UtilitiesHolder.ORPHELIN_DAO.updateEntity(orphelin);
			})).start();
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Message d'erreur",
					"Veuillez d'abords selectionner un orphelin !", this.getWindow());
	}

	@FXML
	void updateDossierMedical(ActionEvent event) {
		Orphelin orphelin = this.tableOrphelinsController.getSelectionModel().getSelectedItem();

		if (orphelin != null) {
			if (!MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(),
					"Confirmation de mise à jour", "Confirmer pour proceder à la mise à jour", this.getWindow()))
				return;

			orphelin.getDossierMedical().setMedicaments(this.medicaments.getText());
			orphelin.getDossierMedical().setPathologie(this.pathologies.getText());

			(new Thread(() -> {
				UtilitiesHolder.ORPHELIN_DAO.updateEntity(orphelin);
			})).start();
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Message d'erreur",
					"Veuillez d'abords selectionner un orphelin !", this.getWindow());
	}

	@FXML
	void updateDossierScolaire(ActionEvent event) {
		Orphelin orphelin = this.tableOrphelinsController.getSelectionModel().getSelectedItem();

		if (orphelin != null) {
			if (!MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayConfirmationAlert(this.getNomGestion(),
					"Confirmation de mise à jour", "Confirmer pour proceder à la mise à jour", this.getWindow()))
				return;

			orphelin.getDossierScolaire().setDdMaj(this.dateDossierScolaire.getValue());
			orphelin.getDossierScolaire().setNiveauScolaire(this.niveauScolaire.getValue());
			orphelin.getDossierScolaire().setScolarise(this.niveauScolaire.getValue() != NiveauScolaire.Sans_scolarite);
			orphelin.getDossierScolaire().setAnneeScolaire(this.anneeScolaire.getValue());
			orphelin.getDossierScolaire().setObservation(this.observationsDossierScolaire.getText());

			(new Thread(() -> {
				UtilitiesHolder.ORPHELIN_DAO.updateEntity(orphelin);
			})).start();
		} else
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Message d'erreur",
					"Veuillez d'abords selectionner un orphelin !", this.getWindow());
	}

	// ---------------------------------------------------------------------------------------------

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initComponents();
		this.initLists();
		this.initDatePickers();
		this.initComboBoxes();
		this.initTables();
		this.initCheckBox();
		this.initTextFields();
		this.initObservationTitledPane();
		this.initInitialDatas();
	}

	private void initObservationTitledPane() {
		this.observations.setUpdator(UtilitiesHolder.ORPHELIN_DAO);
		this.observations.setEntityFetcher(() -> {
			return this.tableOrphelinsController.getSelectionModel().getSelectedItem();
		});
	}

	private void initTextFields() {
		this.nomFilter.textProperty().addListener((__, ___, value) -> {
			this.search();
		});
	}

	private void initCheckBox() {
		this.displayAll.setOnMouseClicked(event -> {
			this.search();
		});
	}

	private void initDatePickers() {
		this.dateMinFilter.valueProperty().addListener((obs, oldValue, newValue) -> {
			this.search();
		});

		this.dateMaxFilter.valueProperty().addListener((obs, oldValue, newValue) -> {
			this.search();
		});
	}

	private void initComboBoxes() {
		this.typeOrphelinat.setItems(FXCollections.observableArrayList(TypeOrphelinat.values()));

		this.regionFilter.setItems(ConstantsHolder.REGIONS_DATASOURCE);
		this.regionFilter.getSelectionModel().selectedItemProperty().addListener(event -> {
			this.search();
		});
		
		this.genreFilter.setItems(FXCollections.observableArrayList(Genre.values()));
		this.genreFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			this.search();
		});

		this.anneeScolaire.setItems(FXCollections.observableArrayList(new Integer[] { 1, 2, 3, 4, 5, 6 }));
		this.anneeScolaireFilter.setItems(FXCollections.observableArrayList(new Integer[] { 1, 2, 3, 4, 5, 6 }));
		this.anneeScolaireFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			this.search();
		});

		this.niveauScolaire.setItems(FXCollections.observableArrayList(NiveauScolaire.values()));
		this.niveauScolaireFilter.setItems(FXCollections.observableArrayList(NiveauScolaire.values()));
		this.niveauScolaireFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			this.search();
		});
	}

	@Override
	public void initTables() {
		super.initTables();

		this.tableOrphelinsController.getSelectionModel().selectedItemProperty()
				.addListener((obs, oldValue, newValue) -> {
					this.updateEtiquetteOrphelin(newValue);
				});

		DisabilityBinder.linkedTableViewToAccordion(this.tableOrphelinsController.getResultTable(), this.accordion);

	}

	private void initLists() {
		this.tuteursListView.setFilterer(UtilitiesHolder.TUTEUR_DAO);
		this.tuteursListView.setItems(ConstantsHolder.TUTORS_DATASOURCE);
		this.tuteursListView.setCategorizerControllerAdder(ConstantsHolder.NOUVEAU_TUTEUR_CONTROLLER);
		this.tuteursListView.setCategorizerDeleter(UtilitiesHolder.TUTEUR_DAO);
		this.tuteursListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			this.search();
		});

		this.appareillages.setBeforeAddCallback(() -> {
			AffectationAppareillageController.APPAREILLAGE_HOLDER = this.tableOrphelinsController.getSelectionModel()
					.getSelectedItem();
		});

		this.appareillages.setCategorizerControllerAdder(ConstantsHolder.AFFECTATION_APPAREILLAGE_CONTROLLER);
		this.appareillages.setCategorizerDeleter(new ICategorizerDeleter<Appareillage>() {

			@Override
			public void deleteEntity(Appareillage entity) {
				Orphelin orphelin = tableOrphelinsController.getSelectionModel().getSelectedItem();

				if (orphelin != null) {
					UtilitiesHolder.ORPHELIN_DAO.affectAppareillageToOrphelin(orphelin, entity);
				} else
					MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Gestion des orphelins",
							"Affectation d'un appareillage à un orphelin", "Veuillez d'abords selectionner un orphelin",
							getWindow());
			}
		});
	}

	private void initInitialDatas() {
		this.search();
	}

	private void initComponents() {
		ComponentsHolder.SEARCH_NOM_ORPHELIN = this.nomFilter;
		ComponentsHolder.SEARCH_AGE_MIN_ORPHELIN = this.ageMinFilter;
		ComponentsHolder.SEARCH_AGE_MAX_ORPHELIN = this.ageMaxFilter;
		ComponentsHolder.SEARCH_GENRE_ORPHELIN = this.genreFilter;
		ComponentsHolder.SEARCH_ANNEE_SCOLAIRE = this.anneeScolaireFilter;
		ComponentsHolder.SEARCH_NIVEAU_SCOLAIRE = this.niveauScolaireFilter;
		ComponentsHolder.SEARCH_DDN_MAX = this.dateMinFilter;
		ComponentsHolder.SEARCH_DDN_MIN = this.dateMaxFilter;
	}

	@Override
	public Stage getInsertStage() {
		return StagesHolder.NOUVEL_ORPHELIN_STAGE;
	}

	@Override
	public Orphelin getLastInsertedEntity() {
		return NouvelOrphelinController.LAST_INSERTED_ORPHELIN;
	}

	@Override
	public String getNomGestion() {
		return "Gestion des orphelins";
	}

	@Override
	public String getDeleteHeader() {
		return "Suppression d'un orphelin";
	}

	@Override
	public void onDeleteCallback(Orphelin entity) {
		UtilitiesHolder.ORPHELIN_DAO.deleteEntity(entity);
	}

	@Override
	public void onBeforeUpdateCallback(Orphelin entity) {
	}

	@Override
	public void onUpdateCallback(Orphelin entity) {
		UtilitiesHolder.ORPHELIN_DAO.updateEntity(entity);
	}

	@Override
	protected List<Orphelin> selectFromDatabase() throws NullPointerException {
		String names = this.nomFilter.getText();
		Tuteur tuteur = this.tuteursListView.getSelectedItem();
		Genre gender = this.genreFilter.getSelectionModel().getSelectedItem();
		NiveauScolaire niveauScolaire = this.niveauScolaireFilter.getValue();
		Integer anneeScolaire = this.anneeScolaireFilter.getValue();
		Region region = this.regionFilter.getValue();
		
		LocalDate dateMin = this.dateMinFilter.getValue();
		LocalDate dateMax = this.dateMaxFilter.getValue();

		int ageMin = -1;
		int ageMax = -1;

		try {
			ageMin = Integer.parseInt(this.ageMinFilter.getText());
		} catch (NumberFormatException exp) {
		}
		try {
			ageMax = Integer.parseInt(this.ageMaxFilter.getText());
		} catch (NumberFormatException exp) {
		}

		final int ageMinFin = ageMin;
		final int ageMaxFin = ageMax;

		boolean includeAged = this.displayAll.isSelected();

		return UtilitiesHolder.ORPHELIN_DAO.getSpecifiedOrphelins(tuteur, region, names, gender, ageMinFin, ageMaxFin,
				niveauScolaire, anneeScolaire, dateMin, dateMax, includeAged);
	}

	public void updateEtiquetteOrphelin(Orphelin orphelin) {
		this.typeOrphelinat.setValue(null);
		this.nomPere.setText("");
		this.prenomPere.setText("");
		this.nomMere.setText("");

		this.prenomMere.setText("");
		this.situationFamilliale.setText("");

		this.niveauScolaire.setValue(null);
		this.anneeScolaire.setValue(null);
		this.dateDossierScolaire.setValue(null);
		this.observationsDossierScolaire.setText("");

		this.pointure.setText("");
		this.dateApparencePhysique.setValue(null);
		this.observationApparencePhysique.setText("");

		this.pathologies.setText("");
		this.medicaments.setText("");

		this.appareillages.clear();

		this.observations.setObservationContent("");

		if (orphelin != null) {
			List<Appareillage> appareillages = UtilitiesHolder.APPAREILLAGE_DAO.getAppareillagesOrphelin(orphelin);
			DossierFamilial dossierFamilial = UtilitiesHolder.ORPHELIN_DAO.getDossierFamilial(orphelin);
			DossierMedical dossierMedical = UtilitiesHolder.ORPHELIN_DAO.getDossierMedical(orphelin);
			DossierScolaire dossierScolaire = UtilitiesHolder.ORPHELIN_DAO.getDossierScolaireOrphelin(orphelin);
			ApparencePhysique apparencePhysique = UtilitiesHolder.ORPHELIN_DAO.getApparencePhysiqueOrphelin(orphelin);

			orphelin.setApparencePhysique(apparencePhysique);
			orphelin.setDossierFamilial(dossierFamilial);
			orphelin.setDossierMedical(dossierMedical);
			orphelin.setDossierScolaire(dossierScolaire);

			this.typeOrphelinat.setValue(dossierFamilial.getTypeOrphelinat());
			this.nomPere.setText(dossierFamilial.getNomPere());
			this.prenomPere.setText(dossierFamilial.getPrenomPere());
			this.nomMere.setText(dossierFamilial.getNomMere());
			this.prenomMere.setText(dossierFamilial.getPrenomMere());
			this.situationFamilliale.setText(dossierFamilial.getSituationFamilliale());

			this.niveauScolaire.setValue(dossierScolaire.getNiveauScolaire());
			this.anneeScolaire.setValue(dossierScolaire.getAnneeScolaire());
			this.dateDossierScolaire.setValue(dossierScolaire.getDdMaj());
			this.observationsDossierScolaire.setText(dossierScolaire.getObservation());

			this.pointure.setText(apparencePhysique.getPointure());
			this.dateApparencePhysique.setValue(apparencePhysique.getDdMaj());
			this.observationApparencePhysique.setText(apparencePhysique.getObservation());

			this.pathologies.setText(dossierMedical.getPathologie());
			this.medicaments.setText(dossierMedical.getMedicaments());

			this.appareillages.addAll(appareillages);

			this.observations.setObservationContent(orphelin.getObsevation());
		}
	}

	@Override
	public boolean isCustomTable() {
		return true;
	}

	@Override
	public boolean onBeforeInsertCallback() {
		Tuteur tuteur = this.tuteursListView.getSelectedItem();

		if (tuteur != null) {
			NouvelOrphelinController.TUTEUR = tuteur;

			return super.onBeforeInsertCallback();
		} else {
			MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert(this.getNomGestion(), "Nouvel orphelin",
					"Veuillez d'abords selectionner un tuteur", this.getWindow());

			return false;
		}
	}
}
