package mssmfactory.KafylElYatim;

import DomainModel.ICompte;
import DomainModel.IConfiguration;
import DomainModel.ISoftwareFeatureType;
import DomainModel.Poste;
import MainPackage.CipheringUtilities;
import MainPackage.CopyrightHandler;
import MainPackage.SerialNumberCopyrightHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import msdatabaseutils.ICompteValidator;
import msdatabaseutils.IOperationTypeDAO;
import msdatabaseutils.SessionFactoryHandler;
import msjfxuicomponents.mvc.msintroduction.MSIntroductionStage;
import msjfxuicomponents.mvc.mslogin.MSLoginStage;
import mssmfactory.KafylElYatim.DomainModel.Others.Compte;
import mssmfactory.KafylElYatim.MVC.Stages.Main.MainStage;
import mssmfactory.KafylElYatim.Utilities.ComponentsHandlers.StagesHolder;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ApplicationSessionFactoryHandler;
import mssmfactory.KafylElYatim.Utilities.DataHandlers.ConstantsHolder;
import mssmfactory.KafylElYatim.Utilities.UtilitiesHolder;
import mssoftutils.update.SoftwareVersionDownloader;
import org.hibernate.HibernateException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class MainClass extends Application {
    public static void main(String[] args) {
        LogManager.getLogManager().getLogger("").setLevel(Level.OFF);

        MainClass.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.initLightDatasHandlers();
    }

    private void initLightDatasHandlers() throws MalformedURLException, IOException, URISyntaxException {
        new MSIntroductionStage("Kafil El Yatim");
        new MSLoginStage<MainStage>("Kafil El Yatim") {

            @Override
            public SessionFactoryHandler onFirstLoginInitSessionFactoryHandler()
                    throws HibernateException, MalformedURLException {
                UtilitiesHolder.APPLICATION_SESSION_FACTORY_HANDLER = new ApplicationSessionFactoryHandler();

                return UtilitiesHolder.APPLICATION_SESSION_FACTORY_HANDLER;
            }

            @Override
            public CopyrightHandler onFirstLoginInitCopyrightHandler() {
                UtilitiesHolder.COPYRIGHT_HANDLER = new SerialNumberCopyrightHandler(
                        UtilitiesHolder.APPLICATION_SESSION_FACTORY_HANDLER, new CipheringUtilities(),
                        "LSDMFHLSDFGLDSKFJGKLDSFG4554") {

                    @Override
                    public void onPosteCreation(Poste poste) {
                    }
                };

                return UtilitiesHolder.COPYRIGHT_HANDLER;
            }

            @Override
            public ICompteValidator onFirstLoginInitCompteValidator() {
                return UtilitiesHolder.COMPTE_DAO;
            }

            @Override
            public IConfiguration onFirstLoginInitConfiguration() {
                return ConstantsHolder.LOGGED_IN_ACCOUNT;
            }

            @Override
            public IOperationTypeDAO<?> onFirstLoginInitOperationTypeDAO() {
                return UtilitiesHolder.OPERATION_DAO;
            }

            @Override
            public MainStage onFirstLoginConstructMainStage() {
                StagesHolder.MAIN_STAGE = null; // to make sure that everybody is constructed before the MainStage
                StagesHolder.MAIN_STAGE = new MainStage();

                return StagesHolder.MAIN_STAGE;
            }

            @Override
            public void setMainCompte(ICompte compte) {
                ConstantsHolder.LOGGED_IN_ACCOUNT = (Compte) compte;
            }

            @Override
            public List<? extends ISoftwareFeatureType> softwareFeatureTypes() {
                return new ArrayList<>();
            }

            @Override
            public SoftwareVersionDownloader.Software getSoftware() {
                return SoftwareVersionDownloader.Software.KAFILELYATIM;
            }
        };
    }
}
