package vinnsla;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Vinnsluklasi sem heldur utan um lista af viðburðum.
 * Gerir kleift að bæta við, fjarlægja, leita að og sækja viðburði.
 *
 * @author Óttar Finnsson
 */
public class EventList {
    private final ObservableList<EventModel> events;

    /**
     * Smiður sem býr til nýjan lista af viðburðum.
     */
    public EventList() {
        events = FXCollections.observableArrayList();
    }

    /**
     * Bætir viðburði við listann.
     *
     * @param event Viðburður sem á að bæta við.
     */
    public void addEvent(EventModel event) {
        if (event != null && !events.contains(event)) {
            events.add(event);
        }
    }

    /**
     * Fjarlægir viðburð úr lista.
     *
     * @param event Viðburður sem á að fjarlægja.
     */
    public void removeEvent(EventModel event) {
        if (event != null) {
            events.remove(event);
        }
    }

    /**
     * Finnur viðburð með tiltekið heiti.
     *
     * @param eventName Heiti viðburðar sem á að leita að.
     * @return Viðburður með tiltekið heiti eða null ef enginn fannst.
     */
    public EventModel findEventByName(String eventName) {
        if (eventName != null && !eventName.isEmpty()) {
            for (EventModel event : events) {
                if (eventName.equals(event.getEventHeiti())) {
                    return event;
                }
            }
        }
        return null;
    }

    /**
     * Sækir alla viðburði í lista.
     *
     * @return ObservableList með öllum viðburðum.
     */
    public ObservableList<EventModel> getAllEvents() {
        return events;
    }

    /**
     * Athugar hvort listinn innihaldi tiltekinn viðburð.
     *
     * @param event Viðburður sem á að athuga.
     * @return true ef listinn inniheldur viðburðinn, annars false.
     */
    public boolean containsEvent(EventModel event) {
        return events.contains(event);
    }

    /**
     * Sækir fjölda viðburða í lista.
     *
     * @return Fjöldi viðburða í lista.
     */
    public int getEventCount() {
        return events.size();
    }

    /**
     * Hreinsar alla viðburði úr lista.
     */
    public void clearEvents() {
        events.clear();
    }

}
