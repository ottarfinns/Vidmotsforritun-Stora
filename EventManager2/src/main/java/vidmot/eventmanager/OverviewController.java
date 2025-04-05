package vidmot.eventmanager;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import vinnsla.EventList;
import vinnsla.EventModel;
import vinnsla.Flokkur;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

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

        // Add listener to enable/disable the open button based on selection
        eventTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            opnaButton.setDisable(newSelection == null);
            selectedEvent = newSelection;
        });

        // Set up event handlers for buttons
        tilBakaButton.setOnAction(e -> goBack());
        haettaButton.setOnAction(e -> exitApplication());
        opnaButton.setOnAction(e -> openSelectedEvent());

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
        // TODO: Implement opening the selected event
        EventManagerController controller = goBack();
        controller.finnaEvent(selectedEvent.getEventHeiti());
    }

    /**
     * Fer aftur á aðalsíðu.
     */
    public EventManagerController goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vidmot/eventmanager/eventManager-view.fxml"));
            Parent root = loader.load();

            // Get the controller from the loader
            EventManagerController controller = loader.getController();

            // Set the controller in the application
            EventManagerApplication.setController(controller);

            // Create a new scene and set it
            Stage currentStage = (Stage) eventTableView.getScene().getWindow();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Viðburðarstjórinn");

            // Initialize the view
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
