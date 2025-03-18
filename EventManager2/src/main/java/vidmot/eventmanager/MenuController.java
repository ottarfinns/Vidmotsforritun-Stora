package vidmot.eventmanager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

/**
 * Stýriklasi fyrir valmyndina MenuBar í notendaviðmótinu.
 * Bregst við atburðum frá notendaviðmótinu eins og músasmellum og ef ýtt er á takka.
 *
 * @author Óttar Finnsson
 */
public class MenuController {

    /**
     * Býr til nýjan viðburð.
     * Kallar á aðferðina nyr() í aðalstýringu forritsins.
     *
     * @param event atburðurinn sem kemur inn er ónotaður.
     */
    public void onNyr(ActionEvent event) {
        EventManagerApplication.getController().nyr();
    }

    /**
     * Opnar skráðan viðburð.
     * Kallar á aðferðina opna() í aðalstýringu forritsins.
     *
     * @param event atburðurinn sem kemur inn er ónotaður.
     */
    public void onOpna(ActionEvent event) {
        EventManagerApplication.getController().opna();
    }

    /**
     * Vistar viðburðinn sem er í vinnslu.
     * Kallar á aðferðina vista() í aðalstýringu forritsins.
     *
     * @param event atburðurinn sem kemur inn er ónotaður.
     */
    public void onVista(ActionEvent event) {
        EventManagerApplication.getController().vista();
    }

    /**
     * Lokar forritinu.
     * Kallar á Platform.exit() til að loka forritinu.
     *
     * @param event atburðurinn sem kemur inn er ónotaður.
     */
    public void onHaetta(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Eyðir núverandi viðburði.
     * Kallar á aðferðina eyda() í aðalstýringu forritsins.
     *
     * @param event atburðurinn sem kemur inn er ónotaður.
     */
    public void onEyda(ActionEvent event) {
        EventManagerApplication.getController().eyda();
    }

    /**
     * Lokar núverandi viðburði.
     * Kallar á aðferðina loka() í aðalstýringu forritsins.
     *
     * @param event atburðurinn sem kemur inn er ónotaður.
     */
    public void onLoka(ActionEvent event) {
        EventManagerApplication.getController().loka();
    }

    /**
     * Birtir upplýsingar um forritið með information dialog glugga.
     *
     * @param event atburðurinn sem kemur inn er ónotaður.
     */
    public void onUm(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upplýsingar um forrit");
        alert.setHeaderText(null);
        alert.setContentText("Þetta er viðburðarstjóri sem gerir þér kleift að skrá viðburð, heiti hans, " +
                "tíma og dagsetningu, flokk og kynningarmyndband ef við á. Hægt er að stofna nýjan viðburð, " +
                "opna viðburð, loka viðburði, breyta þeim, vista og eyða.");
        alert.showAndWait();
    }
}
