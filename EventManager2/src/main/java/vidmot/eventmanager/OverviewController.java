package vidmot.eventmanager;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import vinnsla.Endurtekning;
import vinnsla.EventList;
import vinnsla.EventModel;
import vinnsla.Flokkur;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Stýriklasi fyrir yfirlitssíðu.
 * Bregst við atburðum frá notendaviðmótinu eins og músasmellum og ef ýtt er á takka.
 *
 * @author Óttar Finnsson
 */
public class OverviewController {

    @FXML
    private TableView<EventModel> eventTableView;

    @FXML
    private TableColumn<EventModel, String> heitiColumn;

    @FXML
    private TableColumn<EventModel, String> flokkurColumn;

    @FXML
    private TableColumn<EventModel, String> stadurColumn;

    @FXML
    private TableColumn<EventModel, String> dagsColumn;

    @FXML
    private TableColumn<EventModel, String> timiColumn;

    @FXML
    private ComboBox<Flokkur> siaBox;

    @FXML
    private Button opnaButton;

    @FXML
    private Button tilBakaButton;

    @FXML
    private Button haettaButton;

    @FXML
    private Button eydaButton;

    private final EventList eventList = EventManagerApplication.getEventList();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private EventModel selectedEvent;

    /**
     * Upphafsstillir viðmótið með því upphafsstilla gögn og bætir við fyrsta EventView viðmótshlutnum.
     */
    public void initialize() {
        // Set up the TableView columns
        setupTableColumns();

        // Populate the category filter ComboBox
        siaBox.setItems(FXCollections.observableArrayList(Flokkur.values()));

        // Add listener to enable/disable the open and delete buttons based on selection
        eventTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            opnaButton.setDisable(newSelection == null);
            eydaButton.setDisable(newSelection == null);
            selectedEvent = newSelection;
        });

        // Set up event handlers for buttons
        tilBakaButton.setOnAction(e -> goBack());
        haettaButton.setOnAction(e -> exitApplication());
        opnaButton.setOnAction(e -> openSelectedEvent());
        eydaButton.setOnAction(e -> deleteSelectedEvent());

        // Add listener to filter events based on selected category
        siaBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            filterEventsByCategory(newValue);
        });

        // Load events into the TableView
        loadEvents();
    }

    /**
     * Setur upp dálka fyrir töfluna.
     */
    private void setupTableColumns() {
        // Heiti column
        heitiColumn.setCellValueFactory(new PropertyValueFactory<>("eventHeiti"));

        // Flokkur column
        flokkurColumn.setCellValueFactory(cellData -> {
            Flokkur flokkur = cellData.getValue().getFlokkurProperty().get();
            return new SimpleStringProperty(
                flokkur != null ? flokkur.toString() : "Enginn flokkur"
            );
        });

        // Staður column
        stadurColumn.setCellValueFactory(cellData -> {
            String stadur = cellData.getValue().getStadssetningProperty().get();
            return new SimpleStringProperty(
                stadur != null && !stadur.isEmpty() ? stadur : "Engin staðsetning"
            );
        });

        // Dagsetning column
        dagsColumn.setCellValueFactory(cellData -> {
            var dags = cellData.getValue().getDagsProperty().get();
            return new SimpleStringProperty(
                dags != null ? dags.format(dateFormatter) : "Engin dagsetning"
            );
        });

        // Tími column
        timiColumn.setCellValueFactory(cellData -> {
            var timi = cellData.getValue().getTimiProperty().get();
            return new SimpleStringProperty(
                timi != null ? timi.format(timeFormatter) : "Enginn tími"
            );
        });
    }

    /**
     * Hleður viðburðum í TableView.
     */
    private void loadEvents() {
        eventTableView.setItems(eventList.getAllEvents());
    }

    /**
     * Sía viðburði eftir flokki.
     *
     * @param category flokkur sem á að sía eftir.
     */
    private void filterEventsByCategory(Flokkur category) {
        if (category == null) {
            loadEvents();
            return;
        }

        ObservableList<EventModel> filteredEvents = FXCollections.observableArrayList();
        for (EventModel event : eventList.getAllEvents()) {
            if (event.getFlokkurProperty().get() == category) {
                filteredEvents.add(event);
            }
        }
        eventTableView.setItems(filteredEvents);
    }

    /**
     * Opnar valinn viðburð.
     */
    private void openSelectedEvent() {
        EventManagerController controller = goBack();
        controller.finnaEvent(selectedEvent.getEventHeiti());
    }

    /**
     * Athugar hvort að viðburður sé endurtekinn og eyðir viðburðum eftir svari notanda.
     */
    private void deleteSelectedEvent() {
        if (selectedEvent != null) {
            if (selectedEvent.getEndurtekning() != Endurtekning.EKKI) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eyða endurteknum viðburði");
                alert.setHeaderText(null);
                alert.setContentText("Þetta er endurtekinn viðburður. Viltu eyða öllum framtíðarviðburðum eða bara þessum viðburði?");

                ButtonType allFutureButton = new ButtonType("Eyða öllum framtíðarviðburðum");
                ButtonType singleButton = new ButtonType("Eyða einungis þessum viðburði");
                ButtonType cancelButton = new ButtonType("Hætta við", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(allFutureButton, singleButton, cancelButton);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == allFutureButton) {
                        String[] parts = selectedEvent.getEventHeiti().split(" - ");
                        if (parts.length > 1) {
                            int currentNumber = Integer.parseInt(parts[1]);
                            String baseName = parts[0];

                            eventList.getAllEvents().removeIf(event -> {
                                if (event.getEventHeiti().startsWith(baseName + " - ")) {
                                    String[] eventParts = event.getEventHeiti().split(" - ");
                                    if (eventParts.length > 1) {
                                        int eventNumber = Integer.parseInt(eventParts[1]);
                                        return eventNumber >= currentNumber;
                                    }
                                }
                                return false;
                            });
                        }
                    } else if (result.get() == singleButton) {
                        eventList.removeEvent(selectedEvent);
                    }
                }
            } else {
                eventList.removeEvent(selectedEvent);
            }
            loadEvents();
        }
    }

    /**
     * Fer aftur á aðalsíðu.
     */
    public EventManagerController goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vidmot/eventmanager/eventManager-view.fxml"));
            Parent root = loader.load();

            EventManagerController controller = loader.getController();

            EventManagerApplication.setController(controller);

            Stage currentStage = (Stage) eventTableView.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Viðburðarstjórinn");

            controller.initialize();

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Hættir forritinu.
     */
    private void exitApplication() {
        Platform.exit();
    }
}
