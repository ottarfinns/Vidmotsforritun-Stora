package vidmot.eventmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import vinnsla.EventModel;
import vinnsla.Flokkur;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;


/**
 * Viðmótsklasi fyrir viðburð.
 * Heldur utan um gögn fyrir einstakan viðburð og veitir notandanum möguleika á að breyta þeim.
 *
 * @author Óttar Finnsson
 */
public class EventView extends VBox {

    // Vinnsla
    private final EventModel eventModel;

    private MediaPlayer mediaPlayer;

    // Viðmótshlutir
    @FXML
    private TextField heitiField;

    @FXML
    private ComboBox<Flokkur> flokkurComboBox;

    @FXML
    private DatePicker dagsPicker;

    @FXML
    private Spinner<LocalTime> timiSpinner;

    @FXML
    private MediaView mediaView;


    /**
     * Smiður sem býr til nýtt EventView og hleður inn viðmótinu.
     */
    public EventView() {
        this.eventModel = new EventModel();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("event-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Smiður sem býr til nýtt EventView með tilbúnu EventModel og hleður inn viðmótinu.
     */
    public EventView(EventModel eventModel) {
        this.eventModel = eventModel;
        System.out.println("Opnum gamla sem var lokað: " + this.eventModel.getEventHeiti());
        System.out.println("Tíminn: " + this.eventModel.getTimi());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("event-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Upphafsstillir viðmótið og bindur gögn frá EventModel við viðmótshluti.
     */
    public void initialize() {

        heitiField.textProperty().bindBidirectional(eventModel.getEventHeitiProperty());

        flokkurComboBox.getItems().setAll(Flokkur.values());

        flokkurComboBox.valueProperty().bindBidirectional(eventModel.getFlokkurProperty());

        dagsPicker.valueProperty().bindBidirectional(eventModel.getDagsProperty());

        timiSpinner.setValueFactory(new SpinnerValueFactory<>() {
            {
                setValue(LocalTime.of(12, 0));
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps * 60L));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps * 60L));
            }
        });

        if (eventModel.getTimiProperty().get() != null) {
            timiSpinner.getValueFactory().setValue(eventModel.getTimiProperty().get());
        }

        timiSpinner.getValueFactory().valueProperty().addListener((obs, oldValue, newValue) -> {
            eventModel.getTimiProperty().set(newValue);
        });

        eventModel.getTimiProperty().addListener((obs, oldValue, newValue) -> {
            if (!timiSpinner.getValue().equals(newValue)) {
                timiSpinner.getValueFactory().setValue(newValue);
            }
        });

        eventModel.getMyndbandProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mediaPlayer = new MediaPlayer(newValue);
                mediaView.setMediaPlayer(mediaPlayer);
            } else {
                mediaView.setMediaPlayer(null);
            }
        });

        if (eventModel.getMyndbandProperty().get() != null) {
            System.out.println("Myndband: " + eventModel.getMyndbandProperty().get());
            mediaPlayer = new MediaPlayer(eventModel.getMyndbandProperty().get());
            mediaView.setMediaPlayer(mediaPlayer);
        }

    }

    /**
     * Opnar skráarval fyrir myndband og skilar myndbandi í EventModel.
     */
    public void selectMedia() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Opna myndbandsskrá");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String url = file.toURI().toString();

            Media media = new Media(url);

            eventModel.setMyndband(media);
        }
    }

    /**
     * Pásar myndbandið og breytir stöðu hnappsins.
     *
     * @param playButton hnappurinn sem ýtt var á til að pása myndandið.
     */
    public void onPasa(Button playButton) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            playButton.setText("Spila");
        }
    }

    /**
     * Spilar myndbandið og breytir stöðu hnappsins.
     *
     * @param playButton hnappurinn sem ýtt var á til að spila myndandið.
     */
    public void onSpila(Button playButton) {
        if (mediaPlayer != null) {
            mediaPlayer.play();
            playButton.setText("Pása");
        }
    }

    /**
     * Spólar myndbandið til baka í upphafsstöðu
     */
    public void onRewind() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.ZERO);
        }
    }


    /**
     * Skilar EventModel hlut viðburðarins.
     *
     * @return EventModel hlutur viðburðarins.
     */
    public EventModel getEventModel() {
        return eventModel;
    }

}
