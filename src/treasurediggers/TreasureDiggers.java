package treasurediggers;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import treasurediggers.domain.Gamescreen;
import treasurediggers.domain.Logic;
import treasurediggers.domain.Utils;

public class TreasureDiggers extends Application {

    private Logic logic;
    private Gamescreen gamescreen;

    @Override
    public void init() {
        List<String> config = Utils.readFile("config.cfg");
        logic = new Logic(config);
        gamescreen = new Gamescreen(logic);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = gamescreen.getScene();

        stage.setTitle("Treasure Diggers");
        stage.setMinHeight((logic.getHeight()+3)*16 + 22);
        stage.setMaxHeight((logic.getHeight()+3)*16 + 38);
        stage.setMinWidth(logic.getWidth()*16);
        stage.setMaxWidth(logic.getWidth()*16 + 16);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(TreasureDiggers.class);
    }

}
