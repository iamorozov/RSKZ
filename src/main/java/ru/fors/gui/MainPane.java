import com.thoughtworks.selenium.webdriven.commands.Check;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.SyncFailedException;
import java.util.Arrays;

public class MainPane extends GridPane {
    private final TextField usernameTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final TextField chromeDriverPath = new TextField();
    private final TextField representationTextField = new TextField();
    private final TextArea activityTextArea = new TextArea();
    private final Label activityLabel = new LabelWithStyle("Активность:");
    private final CheckBox inWaitCheckBox = new CheckBox("Перевод в ожидание");
    private final CheckBox changeActivityCheckBox = new CheckBox("Изменение активности");

    private final String RED_BORDER = "-fx-border-color: red";
    private final String INHERIT_BORDER = "-fx-border-color: inherit";

    private final int MAX_TEXT_LENGTH = 60;

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

        add(representationTextField, col, row++);

//        ChoiceBox<String> debugChoice = new ChoiceBox<>();
//        debugChoice.getItems().addAll("false", "true");
//        debugChoice.getSelectionModel().selectFirst();
//        debugChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue.equals("true"))
//            {
//                activityLabel.setVisible(true);
//                activityLabel.setManaged(true);
//                activityTextArea.setVisible(true);
//                activityTextArea.setManaged(true);
//            } else {
//                activityLabel.setVisible(false);
//                activityLabel.setManaged(false);
//                activityTextArea.setVisible(false);
//                activityTextArea.setManaged(false);
//            }
//        });
//        add(debugChoice, col, row++);

        changeActivityCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
            {
                activityLabel.setVisible(true);
                activityLabel.setManaged(true);
                activityTextArea.setVisible(true);
                activityTextArea.setManaged(true);
            } else {
                activityLabel.setVisible(false);
                activityLabel.setManaged(false);
                activityTextArea.setVisible(false);
                activityTextArea.setManaged(false);
            }
        });
        add(changeActivityCheckBox, col, row++);

        activityTextArea.setPrefRowCount(3);
        activityTextArea.setVisible(false);
        activityTextArea.setManaged(false);
        activityTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (activityTextArea.getText().length() > MAX_TEXT_LENGTH)
                activityTextArea.setText(activityTextArea.getText(0, MAX_TEXT_LENGTH));
        });
        add(activityTextArea, col, row);
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

//        final Label debugLabel = new LabelWithStyle("Режим отладки:");
//        add(debugLabel, col, row++);

        add(inWaitCheckBox, col, row++);

        activityLabel.setVisible(false);
        activityLabel.setManaged(false);
        add(activityLabel, col, row++);

        final Button runButton = new Button("Запуск");
        add(runButton, col, row);
        setColumnSpan(runButton, 2);
        setHalignment(runButton, HPos.CENTER);
        runButton.setOnAction(e -> {
            checkField(usernameTextField);
            checkField(passwordField);
            checkField(chromeDriverPath);
            checkField(representationTextField);
            checkCheckBoxes();
        });
    }

    private void checkField(TextField field) {
        if (field.getText().isEmpty())
            field.setStyle(RED_BORDER);
        else
            field.setStyle(INHERIT_BORDER);
    }

    private void checkCheckBoxes() {
        if (!inWaitCheckBox.isSelected() && !changeActivityCheckBox.isSelected())
        {
            inWaitCheckBox.setStyle(RED_BORDER);
            changeActivityCheckBox.setStyle(RED_BORDER);
        } else {
            inWaitCheckBox.setStyle(INHERIT_BORDER);
            changeActivityCheckBox.setStyle(INHERIT_BORDER);
        }
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