import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;

public class DiaryApp extends Application {

    private TextArea editor;
    private ListView<String> entryList;
    private ObservableList<String> entries;
    private TextField searchField;
    private boolean darkMode = false;

    private final Path diaryFile = Paths.get("data/entries.txt");

    @Override
    public void start(Stage stage) {

        // Ensure data folder exists
        try {
            Files.createDirectories(diaryFile.getParent());
            if (!Files.exists(diaryFile)) {
                Files.createFile(diaryFile);
            }
        } catch (IOException e) {
            showError("File Error", e.getMessage());
        }

        BorderPane root = new BorderPane();

        /* ===== LEFT: Entry Browser ===== */
        entries = FXCollections.observableArrayList();
        entryList = new ListView<>(entries);
        entryList.setPrefWidth(200);

        loadEntries();

        entryList.setOnMouseClicked(e -> editor.setText(entryList.getSelectionModel().getSelectedItem()));

        VBox leftPane = new VBox(new Label("Diary Entries"), entryList);
        leftPane.setPadding(new Insets(10));
        leftPane.setSpacing(5);

        /* ===== CENTER: Editor ===== */
        editor = new TextArea();
        editor.setWrapText(true);

        /* Auto-save */
        editor.textProperty().addListener((obs, oldText, newText) -> autoSave(newText));

        /* ===== TOP: Search + Theme ===== */
        searchField = new TextField();
        searchField.setPromptText("Search entries...");
        searchField.textProperty().addListener((obs, oldVal, newVal) -> searchEntries(newVal));

        Button themeBtn = new Button("Toggle Theme");
        themeBtn.setOnAction(e -> toggleTheme(stage));

        HBox topBar = new HBox(10, searchField, themeBtn);
        topBar.setPadding(new Insets(10));

        /* ===== BOTTOM: Actions ===== */
        Button newBtn = new Button("New Entry");
        Button deleteBtn = new Button("Delete Entry");

        newBtn.setOnAction(e -> newEntry());
        deleteBtn.setOnAction(e -> deleteEntry());

        HBox bottomBar = new HBox(10, newBtn, deleteBtn);
        bottomBar.setPadding(new Insets(10));

        root.setTop(topBar);
        root.setLeft(leftPane);
        root.setCenter(editor);
        root.setBottom(bottomBar);

        Scene scene = new Scene(root, 900, 500);
        scene.getStylesheets().add("styles/style.css");

        stage.setTitle("Personal Diary Manager");
        stage.setScene(scene);
        stage.show();
    }

    /* ===== METHODS ===== */

    private void loadEntries() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                entries.clear();
                entries.addAll(Files.readAllLines(diaryFile));
                return null;
            }
        };
        new Thread(task).start();
    }

    private void autoSave(String text) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Files.write(diaryFile, FXCollections.observableArrayList(text), StandardOpenOption.TRUNCATE_EXISTING);
                return null;
            }
        };
        new Thread(task).start();
    }

    private void newEntry() {
        String entry = "Entry - " + LocalDateTime.now() + "\n";
        entries.add(entry);
        editor.setText(entry);
    }

    private void deleteEntry() {
        String selected = entryList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            entries.remove(selected);
            editor.clear();
            saveAll();
        }
    }

    private void saveAll() {
        try {
            Files.write(diaryFile, entries);
        } catch (IOException e) {
            showError("Save Error", e.getMessage());
        }
    }

    private void searchEntries(String keyword) {
        try {
            entries.setAll(Files.readAllLines(diaryFile));
            if (!keyword.isEmpty()) {
                entries.removeIf(e -> !e.toLowerCase().contains(keyword.toLowerCase()));
            }
        } catch (IOException e) {
            showError("Search Error", e.getMessage());
        }
    }

    private void toggleTheme(Stage stage) {
        darkMode = !darkMode;
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(
                darkMode ? "styles/dark.css" : "styles/style.css"
        );
    }

    private void showError(String title, String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(msg);
            alert.showAndWait();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
