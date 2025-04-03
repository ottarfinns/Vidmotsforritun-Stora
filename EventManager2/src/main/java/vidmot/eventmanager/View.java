package vidmot.eventmanager;

/**
 * Enum sem heldur utan um mismunandi view fyrir viðmót.
 *
 * @author Óttar Finnsson
 */
public enum View {
    UPPHAF("eventManager-view.fxml"),
    OVERVIEW("event-overview.fxml");

    private final String fileName;

    /**
     * Býr til view með viðegandi FXML skrá.
     * @param fileName skráarheiti FXML skráarinnar fyrir viðmótið.
     */
    View(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Sækir skráarheiti fyrir view og skilar sem streng.
     * @return fileName skráarheiti FXML skráarinnar fyrir View.
     */
    public String getFileName() {
        return fileName;
    }
}
