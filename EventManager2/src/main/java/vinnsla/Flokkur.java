package vinnsla;

/**
 * Enum sem heldur utan um mismunandi flokka fyrir viðburði.
 *
 * @author Óttar Finnsson
 */
public enum Flokkur {
    FLOKKUR1("Fræðsla"),
    FLOKKUR2("Skemmtun"),
    FLOKKUR3("Fjölskylda"),
    Flokkur4("Annað");

    private final String name;

    /**
     * Býr til nýjan flokk með viðeigandi nafni.
     * @param name Nafn flokksins.
     */
    Flokkur(String name) {
        this.name = name;
    }

    /**
     * Skilar nafni flokksins.
     * @return Nafn flokksins.
     */
    @Override
    public String toString() {
        return name;
    }
}
