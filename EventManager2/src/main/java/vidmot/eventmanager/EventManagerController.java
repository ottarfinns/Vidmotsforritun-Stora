package vidmot.eventmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import vinnsla.EventModel;

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
    private final ObservableList<EventModel> list = FXCollections.observableArrayList();

    /**
     * Upphafsstillir viðmótið með því upphafsstilla gögn og bætir við fyrsta EventView viðmótshlutnum.
     */
    public void initialize() {
        currentView = new EventView();
        fxEventViews.getChildren().add(currentView);
    }


    /**
     * Kallar á selectMedia() aðferðina í EventView til þess að opna nýja myndbandsskrá.
     *
     * @param event atburðurinn sem kemur inn er ónotaður.
     */
    public void opnaSkra(ActionEvent event) {
        currentView.selectMedia();
        spilaButton.setText("Spila");
    }

    /**
     * Bregst við smellum á spila hnappinn og kallar á viðeigandi aðferðir í EventView eftir því hvað stendur á hnappinu.
     *
     * @param event atburður sem kemur inn þegar smellt er á spila hnappinn.
     */
    public void onPlayButton(ActionEvent event) {
        Button playButton = (Button) event.getSource();
        if (playButton.getText().equals("Spila")) {
            //currentView.onSpila(playButton);
            System.out.println("Spila");
        } else {
            //currentView.onPasa(playButton);
            System.out.println("Spila");
        }
    }

    /**
     * Bregst við þegar smellt er á "Aftur að byrjun" hnappinn.
     *
     * @param event atburðurinn sem kemur inn er ónotaður.
     */
    public void onRewindButton(ActionEvent event) {
        currentView.onRewind();
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
        if (!list.contains(currentView.getEventModel())) {
            list.add(currentView.getEventModel());
        }
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
     * Opnar vistaðan viðburð eftir nafni, eða býr til nýjan ef enginn viðburður finnst.
     */
    public void opna() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Opna viðburð");
        dialog.setHeaderText(null);
        dialog.setContentText("Nyr");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String eventName = result.get();

            EventModel eventModel = list.stream()
                    .filter(event -> event.getEventHeiti().equals(eventName))
                    .findFirst()
                    .orElse(null);
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
        //currentView.onPasa();
        switchToLastView();
    }

    /**
     * Eyðir núverandi viðburði bæði úr viðmótinu og vistaða listanum.
     */
    public void eyda() {
        if (currentView != null) {
            fxEventViews.getChildren().remove(currentView);

            list.remove(currentView.getEventModel());

            switchToLastView();
        }
    }
}
