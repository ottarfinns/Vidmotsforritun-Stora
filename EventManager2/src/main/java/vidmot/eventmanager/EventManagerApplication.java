package vidmot.eventmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vinnsla.EventList;

import java.io.IOException;

/**
 * Application klasi JavaFX forritsins
 * Ræsir forritið og upphafsstillir það
 *
 * @author Óttar Finnsson
 */
public class EventManagerApplication extends Application {
    private static EventManagerController controller;
    private static final EventList eventList = new EventList();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventManagerApplication.class.getResource("eventManager-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        controller = fxmlLoader.getController();

        // Set up ViewSwitcher with the scene
        ViewSwitcher.setScene(scene);

        stage.setTitle("Viðburðarstjórinn");
        stage.setScene(scene);

        // Set window size to 700x950
        stage.setWidth(700);
        stage.setHeight(950);

        stage.show();
    }

    /**
     * Skilar aðalstýringu forritsins.
     *
     * @return Tilvísun í EventManagerController hlutinn.
     */
    public static EventManagerController getController() {
        return controller;
    }

    /**
     * Skilar lista af viðburðum.
     *
     * @return Tilvísun í EventList hlutinn.
     */
    public static EventList getEventList() {
        return eventList;
    }

    public static void main(String[] args) {
        launch();
    }
}
