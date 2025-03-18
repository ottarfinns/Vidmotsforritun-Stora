package vinnsla;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.media.Media;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Vinnsluklasi fyrir viðburð.
 * Heldur utan um heiti, flokk, dagssetningu, tíma og myndband fyrir viðburðinn.
 * @author Óttar Finnsson
 */
public class EventModel {
    // Eigindi
    private final SimpleStringProperty eventHeiti;
    private final SimpleObjectProperty<Flokkur> flokkur;
    private final SimpleObjectProperty<LocalDate> dags;
    private final SimpleObjectProperty<LocalTime> timi;
    private final SimpleObjectProperty<Media> myndband;

    /**
     * Smiður fyrir klasann sem upphafsstillir eigindin.
     */
    public EventModel() {
        this.eventHeiti = new SimpleStringProperty("");
        this.flokkur = new SimpleObjectProperty<>(null);
        this.dags = new SimpleObjectProperty<>(LocalDate.now());
        this.timi = new SimpleObjectProperty<>(null);
        this.myndband = new SimpleObjectProperty<>(null);
    }


    /**
     * Getter fyrir StringProperty viðburðarheitisins.
     * @return StringProperty fyrir viðburðarheiti.
     */
    public StringProperty getEventHeitiProperty() {
        return eventHeiti;
    }

    /**
     * Getter fyrir heiti viðburðar.
     * @return heiti viðburðar.
     */
    public String getEventHeiti() {
        return eventHeiti.get();
    }

    /**
     * Getter fyrir ObjectProperty flokks viðburðarins.
     * @return ObjectProperty fyrir flokk viðburðarins.
     */
    public ObjectProperty<Flokkur> getFlokkurProperty() {
        return flokkur;
    }

    /**
     * Getter fyrir ObjectProperty dagssetningu viðburðarins.
     * @return ObjectProperty fyrir dagssetningu viðburðarins.
     */
    public ObjectProperty<LocalDate> getDagsProperty() {
        return dags;
    }

    /**
     * Getter fyrir ObjectProperty tímasetningu viðburðarins.
     * @return ObjectProperty fyrir tímasetningu viðburðarins.
     */
    public ObjectProperty<LocalTime> getTimiProperty() {
        return timi;
    }

    /**
     * Getter fyrir tímasetningu viðburðarins.
     * @return tímasetning viðburðarins.
     */
    public LocalTime getTimi() {
        return timi.get();
    }

    /**
     * Getter fyrir ObjectProperty myndbands viðburðarins.
     * @return ObjectProperty fyrir myndband viðburðarins.
     */
    public ObjectProperty<Media> getMyndbandProperty() {
        return myndband;
    }

    /**
     * Setter fyrir myndband viðburðarins.
     * @param myndband Myndband sem á að setja sem eigindi.
     */
    public void setMyndband(Media myndband) {
        this.myndband.set(myndband);
    }
}
