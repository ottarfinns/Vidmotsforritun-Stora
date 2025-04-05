package vidmot.eventmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import vinnsla.EventList;
import vinnsla.EventModel;

import java.io.IOException;
import java.util.Optional;

/**
 * Stýriklasi fyrir aðal viðmótið.
 * Bregst við atburðum frá notendaviðmótinu eins og músasmellum og ef ýtt er á takka.
 *
 * @author Óttar Finnsson
 */
public class EventManagerController {

    // Viðmótshlutir
    @FXML
    private StackPane fxEventViews;

    @FXML
    private Button spilaButton;

    private EventView currentView;

    // Listi sem heldur utan um EventModel hluti sem hafa verið vistaðir
    private final EventList eventList = EventManagerApplication.getEventList();

    /**
     * Upphafsstillir viðmótið með því upphafsstilla gögn og bætir við fyrsta EventView viðmótshlutnum.
     */
    public void initialize() {
        currentView = new EventView();
        fxEventViews.getChildren().add(currentView);
    }

    /**
     * Býr til nýjan viðburð og setur sem barn af StackPane.
     * Kallar á switchView hjálparfallið til að gera nýja viðburðinn sýnilegann.
     */
    public void nyr() {
        EventView newView = new EventView();
        fxEventViews.getChildren().add(newView);
        currentView = newView;
        switchView(currentView);
    }

    /**
     * Vistar viðburðin með því að bæta EventModel viðburðarins í lista ef hann er ekki þegar til.
     */
    public void vista() {
        eventList.addEvent(currentView.getEventModel());
    }

    /**
     * Hjálparfall sem gerir viðburðinn sem fallið fær sem inntak að sýnilega viðburðinum.
     *
     * @param targetView viðburður sem á að gera sýnilegann.
     */
    private void switchView(EventView targetView) {
        for (Node node : fxEventViews.getChildren()) {
            node.setVisible(false);
        }
        targetView.setVisible(true);
        targetView.setFocusTraversable(true);
    }

    /**
     * Finnur viðburð með sama EventModel og fallið fær sem inntak.
     *
     * @param eventModel EventModel hlutur sem á að leita að.
     * @return EventView sem samsvarar EventModel hlutnum sem fallið fær sem inntak.
     */
    private EventView findEventView(EventModel eventModel) {
        for (Node node : fxEventViews.getChildren()) {
            if (node instanceof EventView eView) {
                if (eView.getEventModel().getEventHeiti().equals(eventModel.getEventHeiti())) {
                    return eView;
                }
            }
        }
        return null;
    }

    /**
     * Opnar dialog til að fá nafn á vistuðum viðburði og opnar hann ef viðburðurinn finnst.
     */
    public void opna() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Opna viðburð");
        dialog.setHeaderText(null);
        dialog.setContentText("Nyr");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String eventName = result.get();
            finnaEvent(eventName);
        }
    }

    /**
     * Finnur viðburð eftir nafni og opnar hann ef hann finnst
     * @param eventName nafn á viðburði
     */
    public void finnaEvent(String eventName) {

        EventModel eventModel = eventList.findEventByName(eventName);
        if (eventModel != null) {
            currentView = findEventView(eventModel);

            if (currentView == null) {
                currentView = new EventView(eventModel);
                fxEventViews.getChildren().add(currentView);
            }

        } else {
            System.out.println("Enginn viðburður með nafnið " + eventName + " fannst.");
            System.out.println("Opnum nýjan viðburð.");
            EventView newView = new EventView();
            fxEventViews.getChildren().add(newView);
            currentView = newView;
        }
        switchView(currentView);

    }

    /**
     * Skiptir yfir á síðasta viðburð ef einhver er eftir.
     */
    private void switchToLastView() {
        if (!fxEventViews.getChildren().isEmpty()) {
            currentView = (EventView) fxEventViews.getChildren().getLast();
            switchView(currentView);
        }
    }

    /**
     * Lokar núverandi viðburði og fer til baka í síðasta opna viðburð.
     */
    public void loka() {
        fxEventViews.getChildren().remove(currentView);
        switchToLastView();
    }

    /**
     * Eyðir núverandi viðburði bæði úr viðmótinu og vistaða listanum.
     */
    public void eyda() {
        if (currentView != null) {
            fxEventViews.getChildren().remove(currentView);

            switchToLastView();
        }
    }

    /**
     * Skiptir yfir í yfirlitssíðu.
     */
    public void goToOverview() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vidmot/eventmanager/event-overview.fxml"));

            // Búum til nýjan controller
            OverviewController overviewController = new OverviewController();
            loader.setController(overviewController);

            Parent root = loader.load();
            Scene overviewScene = new Scene(root);

            Stage currentStage = (Stage) fxEventViews.getScene().getWindow();
            currentStage.setScene(overviewScene);
            currentStage.setTitle("Yfirlit");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
