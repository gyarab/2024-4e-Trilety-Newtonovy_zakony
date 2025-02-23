package com.example.rp5;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class SecondLawScene {
    private double mass = 1.0;
    private double force = 0.0;
    private double velocity = 0.0;
    private double radius = 200;
    private double angle = 0;
    private AnimationTimer animation;
    private long startTime;

    public void show(Stage stage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.DARKBLUE),
                new Stop(1, Color.BLACK));
        layout.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        Text text = new Text("Druhý Newtonův zákon: F = ma");
        text.setFont(Font.font("Arial", 26));
        text.setFill(Color.WHITE);

        Text massLabel = new Text("Hmotnost objektu (kg): 1.00");
        massLabel.setFont(Font.font("Arial", 16));
        massLabel.setFill(Color.LIGHTGRAY);

        Text forceLabel = new Text("Působící síla (N): 0.00");
        forceLabel.setFont(Font.font("Arial", 16));
        forceLabel.setFill(Color.LIGHTGRAY);

        Text accelerationLabel = new Text("Zrychlení (m/s²): 0.00");
        accelerationLabel.setFont(Font.font("Arial", 16));
        accelerationLabel.setFill(Color.LIGHTGRAY);

        Slider massSlider = new Slider(1, 500, 1);
        massSlider.setStyle("-fx-control-inner-background: darkgray;");

        Slider forceSlider = new Slider(0, 100, 0);
        forceSlider.setStyle("-fx-control-inner-background: darkgray;");

        massSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            mass = newVal.doubleValue();
            massLabel.setText(String.format("Hmotnost objektu (kg): %.2f", mass));
            accelerationLabel.setText(String.format("Zrychlení (m/s²): %.2f", force / mass));
        });

        forceSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            force = newVal.doubleValue();
            forceLabel.setText(String.format("Působící síla (N): %.2f", force));
            accelerationLabel.setText(String.format("Zrychlení (m/s²): %.2f", force / mass));
        });

        Canvas canvas = new Canvas(800, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - startTime > 5_000_000_000L) {
                    stop();
                    velocity = 0.0;
                    return;
                }
                double acceleration = force / mass;
                velocity += acceleration * 0.1;
                angle += velocity * 0.01;
                double x = 400 + radius * Math.cos(angle);
                double y = 200 + radius * Math.sin(angle);

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.RED);
                gc.fillOval(x, y, 30, 30);
            }
        };

        Button simulateButton = new Button("Simulovat");
        simulateButton.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;");
        simulateButton.setOnAction(e -> {
            startTime = System.nanoTime();
            velocity = 0.0;
            animation.start();
        });

        Button backButton = new MainMenuApp().createStyledButton("Zpět do menu");
        backButton.setStyle("-fx-background-color: #FF4500; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;");
        backButton.setOnAction(e -> new MainMenuApp().start(stage));

        layout.getChildren().addAll(text, massLabel, massSlider, forceLabel, forceSlider, accelerationLabel, simulateButton, canvas, backButton);
        Scene scene = new Scene(layout, 1000, 1000);
        stage.setScene(scene);
    }
}
