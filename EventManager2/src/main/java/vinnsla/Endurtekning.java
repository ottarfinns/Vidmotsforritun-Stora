package vinnsla;

/**
 * Enum sem heldur utan um endurtekningarmynstur fyrir viðburði.
 */
public enum Endurtekning {
    EKKI("Engin endurtekning"),
    DAGLEGA("Daglega"),
    VIKULEGA("Vikulega"),
    MANADARLEGA("Mánaðarlega"),
    ARLEGA("Árlega");

    private final String lysing;

    Endurtekning(String lysing) {
        this.lysing = lysing;
    }

    @Override
    public String toString() {
        return lysing;
    }
}
