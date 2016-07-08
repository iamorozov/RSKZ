package ru.fors.gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

import java.io.File;

public class MainPane extends GridPane {
    private final TextField usernameTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final TextField chromeDriverPath = new TextField();
    private final TextField representationTextField = new TextField();

    public MainPane() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        setVgap(10);
        setHgap(10);
        setColumns();

        fillFirstColumn();
        fillSecondColumn();
    }

    private void fillSecondColumn() {
        final int col = 1;
        int row = 0;

        add(usernameTextField, col, row++);

        add(passwordField, col, row++);

        HBox.setHgrow(chromeDriverPath, Priority.ALWAYS);
        chromeDriverPath.setDisable(true);

        final Button fileChooseButton = new Button("...");
        fileChooseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите путь к драйверу браузера Chrome");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Исполняемые файлы", "*.exe"),
                    new FileChooser.ExtensionFilter("Все файлы", "*.*")
            );
            File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());
            if (selectedFile != null)
                chromeDriverPath.setText(selectedFile.getPath());
        });

        final HBox pathHBox = new HBox(chromeDriverPath, fileChooseButton);
        pathHBox.setSpacing(5);
        add(pathHBox, col, row++);

        add(representationTextField, col, row);
    }

    private void fillFirstColumn() {
        final int col = 0;
        int row = 0;

        final Label usernameLabel = new LabelWithStyle("Логин:");
        add(usernameLabel, col, row++);

        final Label passwordLabel = new LabelWithStyle("Пароль:");
        add(passwordLabel, col, row++);

        final Label pathLabel = new LabelWithStyle("Путь к драйверу браузера Chrome:");
        add(pathLabel, col, row++);

        final Label representationLabel = new LabelWithStyle("Имя представления:");
        add(representationLabel, col, row++);

        final Button runButton = new Button("Запуск");
        add(runButton, col, row);
        setColumnSpan(runButton, 2);
        setHalignment(runButton, HPos.CENTER);
        runButton.setOnAction(e -> {
            checkField(usernameTextField);
            checkField(passwordField);
            checkField(chromeDriverPath);
            checkField(representationTextField);
        });
    }

    private void checkField(TextField field) {
        if (field.getText().isEmpty())
            field.setStyle("-fx-border-color: red");
        else
            field.setStyle("-fx-border-color: inherit");
    }

    private void setColumns() {
        ColumnConstraints labelsColumn = new ColumnConstraints();
        labelsColumn.setHalignment(HPos.RIGHT);
        labelsColumn.setPercentWidth(50);
        ColumnConstraints fieldsColumn = new ColumnConstraints();
        fieldsColumn.setHalignment(HPos.LEFT);
        fieldsColumn.setPercentWidth(50);
        getColumnConstraints().addAll(labelsColumn, fieldsColumn);
    }
}

class LabelWithStyle extends Label {
    LabelWithStyle(String text) {
        super(text);
        setWrapText(true);
        setTextAlignment(TextAlignment.RIGHT);
        setStyle("-fx-font-size: 15px");
    }
}