package com.example.rp5;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ThirdLawScene {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;

    private Circle ball1, ball2;
    private double v1 = 2;
    private double v2 = -2;
    private double m1 = 1;
    private double m2 = 5;

    private boolean isElastic = true;
    private boolean isFrictionEnabled = false;
    private double friction = 0.99;
    private double restitutionCoefficient = 0.8;

    private final Label infoLabel;

    private final Line frictionForceArrow1;
    private final Line frictionForceArrow2;

    private double totalKineticEnergyBefore;
    private double totalKineticEnergyAfter;

    public ThirdLawScene() {
        infoLabel = new Label();
        frictionForceArrow1 = new Line();
        frictionForceArrow2 = new Line();
    }

    void show(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        Rectangle background = new Rectangle(0, 0, WIDTH, HEIGHT);
        background.setFill(new LinearGradient(0, 0, 1, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.DEEPSKYBLUE),
                new Stop(1, Color.MIDNIGHTBLUE)));
        root.getChildren().add(background);

        ball1 = new Circle(50, HEIGHT / 2 + 50, 20, Color.BLUE);
        ball1.setStroke(Color.BLACK);
        ball1.setStrokeWidth(2);

        ball2 = new Circle(300, HEIGHT / 2 + 50, 30, Color.RED);
        ball2.setStroke(Color.BLACK);
        ball2.setStrokeWidth(2);

        infoLabel.setTranslateX(WIDTH - 350);
        infoLabel.setTranslateY(10);
        infoLabel.setFont(Font.font("Arial", 14));
        updateInfoLabel();

        Label controlsLabel = new Label(""" 
        Ovládání:
        ← → - Změna rychlosti modré koule
        ↑ ↓ - Změna rychlosti červené koule
        A/D - Změna hmotnosti modré koule
        W/S - Změna hmotnosti červené koule
        R - Reset simulace
        P - Pozastavení/pokračování
        E - Elastická/Neelastická srážka
        T - Aktivace/Vypnutí tření
        (V)íce / (M)éně - Zvýšení / Snížení tření
        L - Zvýšení koeficientu odrazu
        O - Snížení koeficientu odrazu
        B - Zpět do Menu
        """
        );
        controlsLabel.setTranslateX(10);
        controlsLabel.setTranslateY(10);
        controlsLabel.setFont(Font.font("Arial", 14));

        root.getChildren().addAll(ball1, ball2, infoLabel, controlsLabel, frictionForceArrow1, frictionForceArrow2);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT -> v1 -= 1;
                case RIGHT -> v1 += 1;
                case UP -> v2 += 1;
                case DOWN -> v2 -= 1;
                case A -> m1 = Math.max(0.5, m1 - 0.5);
                case D -> m1 += 0.5;
                case W -> m2 += 0.5;
                case S -> m2 = Math.max(0.5, m2 - 0.5);
                case R -> resetSimulation();
                case P -> {
                    if (timeline.getStatus() == Timeline.Status.RUNNING) timeline.pause();
                    else timeline.play();
                }
                case E -> isElastic = !isElastic;
                case T -> isFrictionEnabled = !isFrictionEnabled;
                case V -> friction = Math.min(0.999, friction + 0.01);
                case M -> friction = Math.max(0.9, friction - 0.01);
                case B -> new ThirdLawTheory().show(stage);
                case L -> restitutionCoefficient = Math.min(1.0, restitutionCoefficient + 0.05);
                case O -> restitutionCoefficient = Math.max(0.05, restitutionCoefficient - 0.05);
            }
            updateInfoLabel();
        });

        stage.setTitle("Newtonova Srážka");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }


    private void update() {
        ball1.setCenterX(ball1.getCenterX() + v1);
        ball2.setCenterX(ball2.getCenterX() + v2);

        if (isFrictionEnabled) {
            v1 *= friction;
            v2 *= friction;
        }

        double distance = Math.abs(ball1.getCenterX() - ball2.getCenterX());
        double combinedRadius = ball1.getRadius() + ball2.getRadius();

        if (distance < combinedRadius) {
            double overlap = combinedRadius - distance;
            double nx = (ball2.getCenterX() - ball1.getCenterX()) / distance;
            ball1.setCenterX(ball1.getCenterX() - nx * overlap / 2);
            ball2.setCenterX(ball2.getCenterX() + nx * overlap / 2);

            double newV1 = ((m1 - m2) * v1 + 2 * m2 * v2) / (m1 + m2);
            double newV2 = ((m2 - m1) * v2 + 2 * m1 * v1) / (m1 + m2);

            if (isElastic) {
                v1 = newV1;
                v2 = newV2;
            } else {
                v1 = newV1 * restitutionCoefficient;
                v2 = newV2 * restitutionCoefficient;
            }

            if (ball1.getCenterX() < ball2.getCenterX()) {
                ball1.setCenterX(ball2.getCenterX() - combinedRadius);
            } else {
                ball1.setCenterX(ball2.getCenterX() + combinedRadius);
            }

            if (totalKineticEnergyBefore < totalKineticEnergyAfter) {
                totalKineticEnergyBefore = calculateKineticEnergy(m1, v1) + calculateKineticEnergy(m2, v2);
            } else {
                totalKineticEnergyBefore = totalKineticEnergyAfter;
            }

            totalKineticEnergyAfter = calculateKineticEnergy(m1, v1) + calculateKineticEnergy(m2, v2);
        }

        if (ball1.getCenterX() - ball1.getRadius() <= 0 || ball1.getCenterX() + ball1.getRadius() >= WIDTH) v1 = -v1;
        if (ball2.getCenterX() - ball2.getRadius() <= 0 || ball2.getCenterX() + ball2.getRadius() >= WIDTH) v2 = -v2;

        updateFrictionForceArrows();
        updateInfoLabel();
    }

    private double calculateKineticEnergy(double mass, double velocity) {
        return 0.5 * mass * velocity * velocity;
    }


    private void updateFrictionForceArrows() {
        if (isFrictionEnabled && friction > 0) {
            updateArrow(frictionForceArrow1, ball1.getCenterX(), ball1.getCenterY(), v1);
            updateArrow(frictionForceArrow2, ball2.getCenterX(), ball2.getCenterY(), v2);
        } else {
            frictionForceArrow1.setVisible(false);
            frictionForceArrow2.setVisible(false);
        }
    }

    private void updateArrow(Line arrow, double x, double y, double velocity) {
        double arrowLength = 50;
        arrow.setStartX(x);
        arrow.setStartY(y);
        arrow.setEndX(x + (velocity > 0 ? -arrowLength : arrowLength));
        arrow.setEndY(y);
        arrow.setStroke(Color.DARKGREEN);
        arrow.setStrokeWidth(3);
        arrow.setVisible(true);
    }


    private void resetSimulation() {
        ball1.setCenterX(50);
        ball2.setCenterX(300);
        v1 = 2;
        v2 = -2;
        m1 = 2;
        m2 = 1;
        isElastic = true;
        isFrictionEnabled = false;
        friction = 0.99;
        updateInfoLabel();
    }

    private void updateInfoLabel() {
        infoLabel.setText(String.format(""" 
            Modrá koule - Hmotnost: %.1f kg, Rychlost: %.1f m/s
            Červená koule - Hmotnost: %.1f kg, Rychlost: %.1f m/s
            Srážka: %s
            Tření: %s (%.2f)
            Koeficient odrazu: %.2f
            Kinetická energie před srážkou: %.2f J
            Kinetická energie po srážce: %.2f J""",
                m1, v1, m2, v2, isElastic ? "Elastická" : "Neelastická", isFrictionEnabled ? "Zapnuto" : "Vypnuto", friction,
                restitutionCoefficient, totalKineticEnergyBefore, totalKineticEnergyAfter));
    }
}


