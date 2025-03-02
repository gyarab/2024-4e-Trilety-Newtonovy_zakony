package com.example.rp5;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SecondLawScene {
    private static final double RADIUS = 20;
    private static final double START_X = 50;
    private static final double START_Y = 200;
    private static final double WIDTH = 1000;
    private static final double HEIGHT = 650;
    private static final double SCALE_FACTOR = 10;

    private double mass = 2.0;
    private double force = 4.0;
    private double velocity = 0;
    private double position = START_X;
    private long lastTime = 0;
    private boolean running = false;
    private long startTime = 0;


    private XYChart.Series<Number, Number> velocitySeries;

    public void show(Stage stage) {
        Pane simulationPane = new Pane();


        Rectangle background = new Rectangle(0, 0, WIDTH, HEIGHT);
        background.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.LIGHTBLUE), new Stop(1, Color.POWDERBLUE)));


        Circle ball = new Circle(RADIUS, Color.RED);
        ball.setCenterX(position);
        ball.setCenterY(START_Y);
        ball.setEffect(new javafx.scene.effect.DropShadow(10, Color.BLACK));

        simulationPane.getChildren().addAll(background, ball);


        Label velocityLabel = createLabel("Rychlost: 0.0 m/s");
        Label accelerationLabel = createLabel("Zrychlení: 0.0 m/s²");
        Label timeLabel = createLabel("Čas: 0.0 s");
        Label massValueLabel = createLabel("Hmotnost: 2.0 kg");
        Label forceValueLabel = createLabel("Síla: 4.0 N");


        Slider massSlider = createSlider(0.1,100, 2);
        massSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            mass = newVal.doubleValue();
            massValueLabel.setText(String.format("Hmotnost: %.1f kg", mass));
        });


        Slider forceSlider = createSlider(0,200,  0);
        forceSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            force = newVal.doubleValue();
            forceValueLabel.setText(String.format("Síla: %.1f N", force));
        });


        Button startButton = createButton("Začít simulaci");
        startButton.setOnAction(e -> {
            running = true;
            startTime = System.nanoTime();
        });

        Button resetButton = createButton("Resetovat");
        resetButton.setOnAction(e -> {
            velocity = 0;
            position = START_X;
            mass = 2.0;
            force = 4.0;
            massSlider.setValue(mass);
            forceSlider.setValue(force);
            ball.setCenterX(position);
            running = false;
            lastTime = 0;
            startTime = 0;
            velocityLabel.setText("Rychlost: 0.0 m/s");
            accelerationLabel.setText("Zrychlení: 0.0 m/s²");
            timeLabel.setText("Čas: 0.0 s");
            forceValueLabel.setText("Síla: 4.0 N");
            massValueLabel.setText("Hmotnost: 2.0 kg");


            velocitySeries.getData().clear();
        });

        Button backbutton = createButton("Zpět");
        backbutton.setOnAction(e -> new SecondLawTheory().show(stage));


        LineChart<Number, Number> velocityChart = createChart();
        velocitySeries = new XYChart.Series<>();
        velocityChart.getData().add(velocitySeries);
        velocityChart.setLayoutX(50);
        velocityChart.setLayoutY(450);


        VBox controls = new VBox(15,
                new Label("Hmotnost (kg):"),
                massSlider, massValueLabel,
                new Label("Síla (N):"),
                forceSlider, forceValueLabel,
                velocityLabel, accelerationLabel, timeLabel,
                startButton, resetButton, backbutton
        );
        controls.setLayoutX(750);
        controls.setLayoutY(50);
        controls.setStyle("-fx-background-color: #ffffff; -fx-padding: 15; -fx-border-radius: 10px;");


        Pane root = new Pane();
        root.getChildren().addAll(simulationPane, controls, velocityChart);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Second Law Simulation");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!running) return;
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                double acceleration = force / mass;
                velocity += acceleration * deltaTime;
                position += velocity * deltaTime * SCALE_FACTOR;


                if (position > WIDTH) {
                    position = 0;
                } else if (position < 0) {
                    position = WIDTH;
                }

                ball.setCenterX(position);
                velocityLabel.setText(String.format("Rychlost: %.2f m/s", velocity));
                accelerationLabel.setText(String.format("Zrychlení: %.2f m/s²", acceleration));
                timeLabel.setText(String.format("Čas: %.2f s", (now - startTime) / 1_000_000_000.0));

                velocitySeries.getData().add(new XYChart.Data<>((now - startTime) / 1_000_000_000.0, velocity));

                lastTime = now;
            }
        };
        timer.start();
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", 16));
        label.setStyle("-fx-text-fill: #333333;");
        label.setTextAlignment(TextAlignment.LEFT);
        return label;
    }

    private Slider createSlider(double min, double max, double value) {
        Slider slider = new Slider(min, max, value);
        slider.setStyle("-fx-base: #6495ED;");
        slider.setBlockIncrement(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        return slider;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px;");
        button.setMinWidth(150);
        button.setMinHeight(40);
        return button;
    }


    private LineChart<Number, Number> createChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Čas (s)");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Rychlost (m/s)");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setPrefSize(600, 200);
        lineChart.setLegendVisible(false);

        return lineChart;
    }
}
