package ru.fors.gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import ru.fors.pages.LoginException;
import ru.fors.pages.User;

import java.io.File;
import java.io.FileNotFoundException;

public class MainPane extends GridPane {
    private final TextField usernameTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final TextField chromeDriverPath = new TextField();
    private final TextField representationTextField = new TextField();
    private final TextArea activityTextArea = new TextArea();
    private final Label activityLabel = new LabelWithStyle("Активность:");
    private final CheckBox inWaitCheckBox = new CheckBox("Перевод в ожидание");
    private final CheckBox changeActivityCheckBox = new CheckBox("Изменение активности");
    private final RadioButton closeIncidentRadioButton = new RadioButton("Закрытие инцидентов");
    private final RadioButton workingWithActivitiesRadioButton = new RadioButton("Работа с инцидентами");
    private final TextArea solutionTextArea = new TextArea();
    private final Label closeLabel = new LabelWithStyle("Текст решения: ");
    private Text errorMessage;

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

    public MainPane(String path) {
        this();
        try {
            User.decode(path).startWork();
        } catch (LoginException e) {
            failToLoginMessage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void failToLoginMessage() {
        showError("Ошибка аутентификации");
    }

    private void driverNotFound() {
        showError("Не удалось запустить Chrome");
    }

    private void showError(String text){
        errorMessage = new Text(text);
        errorMessage.setFill(Color.RED);
        add(errorMessage, 0, (this.getChildren().size() - 1) / 2);
        setColumnSpan(errorMessage, 2);
        setHalignment(errorMessage, HPos.CENTER);
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
        //add(closeIncidentRadioButton, col-1, row++);
        add(workingWithActivitiesRadioButton, col, row++);
        workingWithActivitiesRadioButton.setOnAction(e -> {
            closeLabel.setVisible(false);
            solutionTextArea.setVisible(false);
            changeActivityCheckBox.setVisible(true);
            inWaitCheckBox.setVisible(true);
        });

        changeActivityCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
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
        changeActivityCheckBox.setVisible(false);
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

        inWaitCheckBox.setVisible(false);
        closeLabel.setVisible(false);
        add(inWaitCheckBox, col, ++row);
        add(closeIncidentRadioButton, col, row - 1);
        add(solutionTextArea, col + 1, row);
        add(closeLabel, col, row++);
        solutionTextArea.setVisible(false);
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(workingWithActivitiesRadioButton, closeIncidentRadioButton);


        closeIncidentRadioButton.setOnAction(e -> {
            activityLabel.setVisible(false);
            activityTextArea.setVisible(false);
            activityTextArea.setManaged(false);
            changeActivityCheckBox.setVisible(false);
            inWaitCheckBox.setVisible(false);
            inWaitCheckBox.setSelected(false);
            changeActivityCheckBox.setSelected(false);
            solutionTextArea.setVisible(true);
            closeLabel.setVisible(true);
        });

        activityLabel.setVisible(false);
        activityLabel.setManaged(false);
        add(activityLabel, col, row++);

        final Button runButton = new Button("Запуск");
        add(runButton, col + 1, row);
        setColumnSpan(runButton, 2);
        setHalignment(runButton, HPos.RIGHT);
        runButton.setOnAction(e -> startButton());

        final Button saveButton = new Button("Сохранить");
        add(saveButton, col, row);
        setHalignment(saveButton, HPos.CENTER);
        saveButton.setOnAction(e -> saveButton());

        final Button openButton = new Button("Открыть");
        add(openButton, col, row);
        setHalignment(openButton, HPos.LEFT);
        openButton.setOnAction(e -> openButton());
    }

    private void openButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите путь файла конфигурации");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("Все файлы", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());
        if (selectedFile != null)
            try {
                User user = User.decode(selectedFile.getPath());
                fillFields(user);
                user.startWork();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (LoginException e) {
                failToLoginMessage();
            } catch (IllegalStateException e){
                driverNotFound();
            }
    }

    private void fillFields(User user) {
        usernameTextField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        chromeDriverPath.setText(user.getDriverPath());
        representationTextField.setText(user.getRepresentation());
        if (user.isDoChangeActivity()) {
            changeActivityCheckBox.setSelected(true);
            activityTextArea.setText(user.getActivity());
            workingWithActivitiesRadioButton.fire();
        }
        if (user.isDoChangeStatus()) {
            inWaitCheckBox.setSelected(true);
            workingWithActivitiesRadioButton.fire();
        }
        if (user.isDoChangeStatusToSolve()) {
            solutionTextArea.setText(user.getSolution());
            closeIncidentRadioButton.fire();
        }
    }

    private void saveButton() {
        User user = getUser();
        if (user != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите путь для сохранения файла конфигурации");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XML", "*.xml"),
                    new FileChooser.ExtensionFilter("Все файлы", "*.*")
            );
            File selectedFile = fileChooser.showSaveDialog(getScene().getWindow());
            if (selectedFile != null)
                User.save(getUser(), selectedFile.getPath());
        }
    }

    private void startButton() {
        User user = getUser();
        if (user != null) {
            try {
                if(errorMessage != null){
                    errorMessage.setText("");
                }
                user.startWork();
            } catch (LoginException e) {
                failToLoginMessage();
            }
        }
    }

    private User getUser() {
        if (checkInputData()) {
            if (workingWithActivitiesRadioButton.isSelected())
                return startManagingIncidents();
            else if (closeIncidentRadioButton.isSelected())
                return startClosingIncidents();
        }

        return null;
    }

    private User startClosingIncidents() {
        if (checkField(solutionTextArea))
            return new User(usernameTextField.getText(), passwordField.getText(), chromeDriverPath.getText(),
                    representationTextField.getText(), solutionTextArea.getText());
        return null;
    }

    private User startManagingIncidents() {
        if (checkCheckBoxes())
            if (changeActivityCheckBox.isSelected())
                return new User(usernameTextField.getText(), passwordField.getText(), chromeDriverPath.getText(),
                        representationTextField.getText(), activityTextArea.getText(),
                        inWaitCheckBox.isSelected());
            else
                return new User(usernameTextField.getText(), passwordField.getText(), chromeDriverPath.getText(),
                        representationTextField.getText());
        return null;
    }

    private boolean checkInputData() {
        return checkField(usernameTextField) &&
                checkField(passwordField) &&
                checkField(chromeDriverPath) &&
                checkField(representationTextField);
    }

    private boolean checkField(TextInputControl field) {
        if (field.getText().isEmpty()) {
            field.setStyle(RED_BORDER);
            return false;
        } else {
            field.setStyle(INHERIT_BORDER);
            return true;
        }
    }

    private boolean checkCheckBoxes() {
        if (!inWaitCheckBox.isSelected() && !changeActivityCheckBox.isSelected()) {
            inWaitCheckBox.setStyle(RED_BORDER);
            changeActivityCheckBox.setStyle(RED_BORDER);
            return false;
        } else {
            inWaitCheckBox.setStyle(INHERIT_BORDER);
            changeActivityCheckBox.setStyle(INHERIT_BORDER);
            return true;
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