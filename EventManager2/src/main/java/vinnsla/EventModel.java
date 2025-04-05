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
    private final SimpleStringProperty lysing;
    private final SimpleStringProperty stadssetning;
    private final SimpleObjectProperty<Flokkur> flokkur;
    private final SimpleObjectProperty<LocalDate> dags;
    private final SimpleObjectProperty<LocalTime> timi;
    private final SimpleObjectProperty<Media> myndband;
    private final SimpleObjectProperty<Endurtekning> endurtekning;
    private final SimpleObjectProperty<LocalDate> endurtekningLokadagur;

    /**
     * Smiður fyrir klasann sem upphafsstillir eigindin.
     */
    public EventModel() {
        this.eventHeiti = new SimpleStringProperty("");
        this.lysing = new SimpleStringProperty("");
        this.stadssetning = new SimpleStringProperty("");
        this.flokkur = new SimpleObjectProperty<>(null);
        this.dags = new SimpleObjectProperty<>(LocalDate.now());
        this.timi = new SimpleObjectProperty<>(LocalTime.of(12, 0));
        this.myndband = new SimpleObjectProperty<>(null);
        this.endurtekning = new SimpleObjectProperty<>(Endurtekning.EKKI);
        this.endurtekningLokadagur = new SimpleObjectProperty<>(null);
    }

    public EventModel(EventModel other) {
        this.eventHeiti = new SimpleStringProperty(other.getEventHeiti());
        this.lysing = new SimpleStringProperty(other.getLysing());
        this.stadssetning = new SimpleStringProperty(other.getStadssetning());
        this.flokkur = new SimpleObjectProperty<>(other.getFlokkur());
        this.dags = new SimpleObjectProperty<>(other.getDags());
        this.timi = new SimpleObjectProperty<>(other.getTimi());
        this.myndband = new SimpleObjectProperty<>(other.getMyndband());
        this.endurtekning = new SimpleObjectProperty<>(other.getEndurtekning());
        this.endurtekningLokadagur = new SimpleObjectProperty<>(other.getEndurtekningLokadagur());
    }


    /**
     * Getter fyrir StringProperty viðburðarheitisins.
     * @return StringProperty fyrir viðburðarheiti.
     */
    public StringProperty getEventHeitiProperty() {
        return eventHeiti;
    }

    /**
     * Getter fyrir StringProperty lýsingar viðburðar.
     * @return StringProperty fyrir lýsingu viðburðar.
     */
    public StringProperty getLysingProperty() {
        return lysing;
    }

    /**
     * Getter fyrir StringProperty staðsetningar viðburðar.
     * @return StringProperty fyrir staðsetningu viðburðar.
     */
    public StringProperty getStadssetningProperty() {
        return stadssetning;
    }

    /**
     * Getter fyrir heiti viðburðar.
     * @return heiti viðburðar.
     */
    public String getEventHeiti() {
        return eventHeiti.get();
    }

    /**
     * Getter fyrir lýsingu viðburðar.
     * @return lýsing viðburðar.
     */
    public String getLysing() {
        return lysing.get();
    }

    /**
     * Getter fyrir staðsetningu viðburðar.
     * @return staðsetning viðburðar.
     */
    public String getStadssetning() {
        return stadssetning.get();
    }

    /**
     * Getter fyrir dagsetningu viðburðar.
     * @return dagsetning viðburðar.
     */ 
    public LocalDate getDags() {
        return dags.get();
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
     * Getter fyrir myndband viðburðar.
     * @return myndband viðburðar.
     */
    public Media getMyndband() {
        return myndband.get();
    }

    /**
     * Setter fyrir myndband viðburðarins.
     * @param myndband Myndband sem á að setja sem eigindi.
     */
    public void setMyndband(Media myndband) {
        this.myndband.set(myndband);
    }

    /**
     * Getter fyrir endurtekningarmynstur.
     * @return endurtekningarmynstur.
     */
    public Endurtekning getEndurtekning() {
        return endurtekning.get();
    }

    /**
     * Getter fyrir ObjectProperty endurtekningar.
     * @return ObjectProperty fyrir endurtekningu.
     */
    public ObjectProperty<Endurtekning> getEndurtekningProperty() {
        return endurtekning;
    }

    /**
     * Getter fyrir lokadag endurtekningar.
     * @return lokadagur endurtekningar.
     */
    public LocalDate getEndurtekningLokadagur() {
        return endurtekningLokadagur.get();
    }

    /**
     * Getter fyrir ObjectProperty lokadags endurtekningar.
     * @return ObjectProperty fyrir lokadag endurtekningar.
     */
    public ObjectProperty<LocalDate> getEndurtekningLokadagurProperty() {
        return endurtekningLokadagur;
    }

    /**
     * Setter fyrir eventHeiti.
     * @param eventHeiti Heiti sem á að setja sem eigindi.
     */
    public void setEventHeiti(String eventHeiti) {
        this.eventHeiti.set(eventHeiti);
    }

    /**
     * Getter fyrir flokk viðburðar.
     * @return flokk viðburðar.
     */
    public Flokkur getFlokkur() {
        return flokkur.get();
    }

    /**
     * Setter fyrir dagsetningu viðburðar.
     * @param dags Dagsetning sem á að setja sem eigindi.
     */
    public void setDags(LocalDate dags) {
        this.dags.set(dags);
    }
}
