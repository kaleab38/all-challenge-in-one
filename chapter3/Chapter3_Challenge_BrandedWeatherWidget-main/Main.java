import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        /* ================= ROOT ================= */
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");

        /* ================= TOP ================= */
        Label cityLabel = new Label("Addis Ababa");
        cityLabel.getStyleClass().add("city-label");
        root.setTop(cityLabel);
        BorderPane.setAlignment(cityLabel, Pos.CENTER);

        /* ================= CENTER ================= */
        VBox centerBox = new VBox(12);
        centerBox.setAlignment(Pos.CENTER);

        Label temperature = new Label("23°C");
        temperature.getStyleClass().add("temperature");

        Label condition = new Label("Sunny");
        condition.getStyleClass().add("condition");

        // Eco Shape (Leaf Symbol)
        Circle leaf = new Circle(35);
        leaf.getStyleClass().add("leaf-shape");

        // Leaf Rotation Animation
        RotateTransition rotate = new RotateTransition(Duration.seconds(8), leaf);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.play();

        Label airQuality = new Label("Air Quality Index: Good 🌱");
        airQuality.getStyleClass().add("air-quality");

        Label gardeningTip = new Label(
                "Gardening Tip: Water plants early morning to reduce evaporation."
        );
        gardeningTip.getStyleClass().add("tip");

        centerBox.getChildren().addAll(
                temperature,
                condition,
                leaf,
                airQuality,
                gardeningTip
        );

        root.setCenter(centerBox);

        /* ================= BOTTOM ================= */
        HBox forecastBox = new HBox(20);
        forecastBox.setPadding(new Insets(10));
        forecastBox.setAlignment(Pos.CENTER);
        forecastBox.getStyleClass().add("forecast-box");

        forecastBox.getChildren().addAll(
                createDayCard("Mon", "22°C"),
                createDayCard("Tue", "24°C"),
                createDayCard("Wed", "21°C")
        );

        root.setBottom(forecastBox);

        /* ================= RIGHT ================= */
        VBox rightBox = new VBox(10);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(10));

        TextField cityField = new TextField();
        cityField.setPromptText("Enter city");

        Button refreshBtn = new Button("Refresh");
        refreshBtn.getStyleClass().add("refresh-btn");

        // Property Binding (REQUIRED)
        refreshBtn.disableProperty().bind(
                Bindings.isEmpty(cityField.textProperty())
        );

        // Refresh animation
        refreshBtn.setOnAction(e -> {
            cityLabel.setText(cityField.getText());

            FadeTransition fade = new FadeTransition(Duration.seconds(1), centerBox);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();
        });

        rightBox.getChildren().addAll(cityField, refreshBtn);
        root.setRight(rightBox);

        /* ================= SCENE ================= */
        Scene scene = new Scene(root, 700, 420);
        scene.getStylesheets().add("style.css");

        stage.setTitle("EcoLife Solutions – Weather Widget");
        stage.setScene(scene);
        stage.show();
    }

    /* ================= FORECAST CARD ================= */
    private VBox createDayCard(String day, String temp) {
        Label d = new Label(day);
        Label t = new Label(temp);

        VBox box = new VBox(5, d, t);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("forecast-card");

        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
