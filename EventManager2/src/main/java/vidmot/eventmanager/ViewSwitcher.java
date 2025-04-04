package vidmot.eventmanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Hjálparklasi til að skipta á milli síða með mismunandi view.
 * Geymir hlaðin view í skyndiminni til að bæta frammistöðu forritsins.
 *
 * @author Óttar Finnsson
 */
public class ViewSwitcher {

    private static final Map<View, Parent> cache = new HashMap<>(); // Skyndiminni til að geyma hlaðin view

    private static Scene scene; // Sena

    /**
     * Setur senuna sem á að nota þegar skipt er um viðmót.
     *
     * @param scene senan sem á að skipta yfir í.
     */
    public static void setScene(Scene scene) {
        System.out.println("Setting scene to " + scene);
        ViewSwitcher.scene = scene;
    }

    /**
     * Skiptir yfir í nýtt viðmót.
     * Sækir viðmót úr skyndiminni ef því hefur nú þegar verið hlaðið, annars er því hlaðið úr FXML skrá.
     *
     * @param view viðmót sem á að skipta yfir í.
     */
    public static void switchTo(View view) {
        System.out.println(view);
        System.out.println(scene);
        if (scene == null) {
            System.out.println("No scene set");
            return;
        }

        try {
            Parent root;

            if (cache.containsKey(view)) {
                System.out.println("Loading from cache");
                root = cache.get(view);
            } else {
                System.out.println("Loading from FXML");
                FXMLLoader loader = new FXMLLoader(ViewSwitcher.class.getResource(view.getFileName()));
                root = loader.load();
                cache.put(view, root);
            }

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
