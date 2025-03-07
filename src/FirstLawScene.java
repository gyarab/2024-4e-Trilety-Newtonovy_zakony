package com.example.rp5;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Random;

public class FirstLawScene {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;

    private double x = WIDTH / 2, y = HEIGHT / 2;
    private double velocityX = 0, velocityY = 0;
    private double friction = 0.02;
    private boolean frictionEnabled = true;
    private boolean gravityEnabled = false;
    private boolean bouncingEnabled = false;
    private final int numStars = 150;
    private final double[] starX = new double[numStars];
    private final double[] starY = new double[numStars];
    private final double[] starSize = new double[numStars];
    private Stage stage;

    public FirstLawScene() {
        Random random = new Random();
        for (int i = 0; i < numStars; i++) {
            starX[i] = random.nextDouble() * WIDTH;
            starY[i] = random.nextDouble() * HEIGHT;
            starSize[i] = 1 + random.nextDouble() * 2.5;
        }
    }

    public void show(Stage stage) {
        this.stage = stage;
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updatePhysics();
                draw(gc);
            }
        }.start();

        stage.setTitle("1. Newtonův zákon");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void handleKeyPress(KeyCode keyCode) {
        double acceleration = 0.7;
        switch (keyCode) {
            case RIGHT -> velocityX += acceleration;
            case LEFT -> velocityX -= acceleration;
            case SPACE -> { velocityX = 0; velocityY = 0; }
            case T -> frictionEnabled = !frictionEnabled;
            case P -> friction = friction + 0.005;
            case M -> friction = friction - 0.005;
            case V -> friction = 0;
            case G -> gravityEnabled = !gravityEnabled;
            case B -> bouncingEnabled = !bouncingEnabled;
            case UP -> velocityY -= 3;
            case DOWN -> velocityY += 3;
            case J -> new FirstLawTheory().show(stage);
        }
    }

    private void updatePhysics() {
        x += velocityX;
        if (gravityEnabled) velocityY += 0.15;
        y += velocityY;

        if (frictionEnabled && friction > 0) {
            velocityX *= (1 - friction);
            if (Math.abs(velocityX) < 0.01) velocityX = 0;
        }

        if (x > WIDTH) x = 0;
        if (x < 0) x = WIDTH;

        if (y > HEIGHT - 45) {
            y = HEIGHT - 45;
            if (bouncingEnabled) velocityY = -Math.abs(velocityY) * 0.7;
            else velocityY = 0;
        }
        if (y < 0) {
            y = 0;
            velocityY = 0;
        }
    }

    private void draw(GraphicsContext gc) {
        gc.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.DARKBLUE), new Stop(1, Color.BLACK)));
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.WHITE);
        for (int i = 0; i < numStars; i++) {
            gc.fillOval(starX[i], starY[i], starSize[i], starSize[i]);
        }

        gc.setFill(Color.ORANGE);
        gc.fillOval(x, y, 40, 40);
        gc.setFill(new Color(0, 0, 0, 0.3));
        gc.fillOval(x + 6, y + 6, 40, 40);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 14));
        gc.fillText("Rychlost X: " + String.format("%.2f", velocityX) + " m/s", 20, 20);
        gc.fillText("Rychlost Y: " + String.format("%.2f", velocityY) + " m/s", 20, 40);
        gc.fillText("Tření: " + String.format("%.3f", friction) + " (" + (frictionEnabled ? "Zapnuto" : "Vypnuto") + ")", 20, 60);
        gc.fillText("Gravitace: " + (gravityEnabled ? "Zapnutá (G pro vypnutí)" : "Vypnutá (G pro zapnutí)"), 20, 80);
        gc.fillText("Odskočení: " + (bouncingEnabled ? "Zapnuté (B pro vypnutí)" : "Vypnuté (B pro zapnutí)"), 20, 100);
        gc.fillText("Šipky -> Přidej rychlost | Space -> Stop", 20, 120);
        gc.fillText("(P)lus / (M)ínus -> Změna tření", 20, 140);
        gc.fillText("Šipka nahoru -> Skok nahoru | Šipka dolů -> Pohyb dolů", 20, 160);
        gc.fillText("J -> Zpět", 20, 180);
    }
}
