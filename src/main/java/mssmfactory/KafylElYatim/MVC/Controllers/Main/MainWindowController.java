package mssmfactory.KafylElYatim.MVC.Controllers.Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import msjfxuicomponents.MSJFXUIComponentsHolder;
import msjfxuicomponents.mvc.BorderPaneTypeLoader;
import mssmfactory.KafylElYatim.DomainModel.DonationsAdministration.Donateur;
import mssmfactory.KafylElYatim.MVC.Controllers.Donation.BilansDonationsController;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public static boolean IS_SU = false;
    public static String SU_PASSWORD = "ky-superuser";

    @FXML
    private Tab donationsPanel, tutorsPanel, orphensPanel, benevolsPanel, evenementsBonPanel, evenementsDechargePanel,
            historiqueActionsPanel;

    @FXML
    void authorizedOrphens() {
        StagesHolder.AUTHORIZED_ORPHENS_STAGE.show();
    }

    @FXML
    void journalAudit() {
        MSJFXUIComponentsHolder.MS_JOURNAL_AUDIT_STAGE.show();
    }

    @FXML
    void createSauvegarde() {
        MSJFXUIComponentsHolder.MS_SAUVEGARDE_BUILDER_STAGE.show();
    }

    @FXML
    void loadSauvegarde() {
        MSJFXUIComponentsHolder.MS_SAUVEGARDE_LOADER_STAGE.show();
    }

    @FXML
    private void updateSettings() {
        StagesHolder.CONFIGURATION_STAGE.show();
    }

    @FXML
    private void deconnexion() {
        this.getWindow().fireEvent(new WindowEvent(this.getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    private void activation() {
        MSJFXUIComponentsHolder.MS_ACTIVATION_STAGE.show();
    }

    @FXML
    private void shutdown() {
        System.exit(0);
    }

    @FXML
    private void bilanDonations() {
        if (MainWindowController.IS_SU) {
            Donateur donateur = ConstantsHolder.DONATIONS_PANEL_CONTROLLER.getSelectedDonateur();

            if (donateur != null) {
                BilansDonationsController.SELECTED_DONATOR = donateur;

                StagesHolder.BILANS_DONATIONS_STAGE.show();
            } else
                MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Gestion des donations",
                        "Bilan des donations",
                        "Veuillez d'abords sélectionner un donateur dans le panneau des donations", this.getWindow());

        } else
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Gestion des donations",
                    "Erreur d'authentification", "Veuillez vous connecter en tant qu'administrateur", this.getWindow());
    }

    @FXML
    private void authentificteSuperUser() {
        String password = MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayPasswordAlert("Gestion des donations",
                "Confirmation d'authentification", "Veuillez saisir le mot de passe de suppression", "");

        if (password != null && password.equals(MainWindowController.SU_PASSWORD)) {
            IS_SU = true;

            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayInformationAlert("Authentification",
                    "Authentification établie avec succés",
                    "Vous êtes maintenant connecté(e) en tant que super utilisateur", this.getWindow());
        } else if (password != null)
            MSJFXUIComponentsHolder.MS_ALERT_DISPLAYER.displayErrorAlert("Authentification",
                    "Erreur d'authentification", "Mot de passe invalide", this.getWindow());
    }

    private Window getWindow() {
        return StagesHolder.MAIN_STAGE;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        BorderPane donationsPane = BorderPaneTypeLoader
                .loadBorderPane("/mssmfactory/KafylElYatim/MVC/FXMLS/Donations/DonationsPanel.fxml");

        BorderPane tutorsPane = BorderPaneTypeLoader
                .loadBorderPane("/mssmfactory/KafylElYatim/MVC/FXMLS/Tuteur/TutorsPanel.fxml");

        BorderPane orphensPane = BorderPaneTypeLoader
                .loadBorderPane("/mssmfactory/KafylElYatim/MVC/FXMLS/Orphen/OrphensPanel.fxml");

        BorderPane benevolsPane = BorderPaneTypeLoader
                .loadBorderPane("/mssmfactory/KafylElYatim/MVC/FXMLS/Benevole/BenevolsPanel.fxml");

        BorderPane eventsBonPane = BorderPaneTypeLoader
                .loadBorderPane("/mssmfactory/KafylElYatim/MVC/FXMLS/EvenementBon/EvenementsBonsPanel.fxml");

        BorderPane eventsDechargePane = BorderPaneTypeLoader
                .loadBorderPane("/mssmfactory/KafylElYatim/MVC/FXMLS/EvenementDecharge/EvenementsDechargesPanel.fxml");

        BorderPane actionsPane = BorderPaneTypeLoader
                .loadBorderPane("/mssmfactory/KafylElYatim/MVC/FXMLS/Action/ActionsPanel.fxml");

        this.donationsPanel.setContent(donationsPane);
        this.tutorsPanel.setContent(tutorsPane);
        this.orphensPanel.setContent(orphensPane);
        this.benevolsPanel.setContent(benevolsPane);
        this.evenementsDechargePanel.setContent(eventsDechargePane);
        this.evenementsBonPanel.setContent(eventsBonPane);
        this.historiqueActionsPanel.setContent(actionsPane);
    }
}
