package treasurediggers;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import treasurediggers.domain.Gamescreen;
import treasurediggers.domain.Logic;
import treasurediggers.domain.Player;

public class TreasureDiggers extends Application {

    private Logic logic;
    private Gamescreen gamescreen;

    @Override
    public void init() {
        int width = 80;
        int height = 42;
        List<Player> players = new ArrayList<>();
        players.add(new Player("Iivo", 1, 1));
        players.add(new Player("Jooa", 78, 40));
        logic = new Logic(players, width, height);
        gamescreen = new Gamescreen(logic);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = gamescreen.getScene();

        stage.setTitle("Treasure Diggers");
        stage.setMinHeight(742);
        stage.setMaxHeight(758);
        stage.setMinWidth(1280);
        stage.setMaxWidth(1296);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(TreasureDiggers.class);

    }

}
