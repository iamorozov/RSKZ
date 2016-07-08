package ru.fors.gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Main stage class (main window).
 */
public class MainStage extends Application {
    /**
     * Window width.
     */
    private static final int STAGE_WIDTH = 450;
    /**
     * Window height.
     */
    private static final int STAGE_HEIGHT = 240;
    /**
     * Window min width.
     */
    private static final int STAGE_MIN_WIDTH = 450;
    /**
     * Window min height.
     */
    private static final int STAGE_MIN_HEIGTH = 240;

    /**
     * Entry point to the program.
     * @param args arguments.
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * Method for launching application.
     * @param primaryStage main window.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane mainPane = new MainPane();
        Scene mainScene = new Scene(mainPane, STAGE_WIDTH, STAGE_HEIGHT);


        primaryStage.setScene(mainScene);

        applyDefaultStageSettings(primaryStage);
        primaryStage.show();

    }

    /**
     * Method for centering stage (window).
     * @param stage stage for centering.
     */
    private static void centerStagePosition(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - STAGE_WIDTH) / 2);
        stage.setY((screenBounds.getHeight() - STAGE_HEIGHT) / 2);
    }

    /**
     * Method for applying default stage settings.
     * @param stage stage for applying settings.
     */
    private static void applyDefaultStageSettings(Stage stage) {
        stage.setTitle("Clicker");
        centerStagePosition(stage);
        stage.setMinWidth(STAGE_MIN_WIDTH);
        stage.setMinHeight(STAGE_MIN_HEIGTH);
    }
}