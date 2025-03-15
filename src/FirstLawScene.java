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

import java.util.LinkedList;
import java.util.List;
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
    private boolean ResistanceEnabled = false;
    private double ResistanceCoefficient = 0.05;

    private final int numStars = 150;
    private final double[] starX = new double[numStars];
    private final double[] starY = new double[numStars];
    private final double[] starSize = new double[numStars];

    private final List<double[]> trail = new LinkedList<>();
    private static final int TRAIL_LENGTH = 100;

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
            case SPACE -> {
                velocityX = 0;
                velocityY = 0;
            }
            case T -> frictionEnabled = !frictionEnabled;
            case P -> friction += 0.005;
            case M -> friction -= 0.005;
            case G -> gravityEnabled = !gravityEnabled;
            case B -> bouncingEnabled = !bouncingEnabled;
            case R -> ResistanceEnabled = !ResistanceEnabled;
            case UP -> velocityY -= 3;
            case DOWN -> velocityY += 3;
            case J -> new FirstLawTheory().show(stage);
            case V -> ResistanceCoefficient += 0.01;
            case C -> ResistanceCoefficient = Math.max(ResistanceCoefficient - 0.01, 0);

        }
    }

    private void updatePhysics() {
        x += velocityX;

        if (gravityEnabled) {
            velocityY += 0.15;
        }

        y += velocityY;

        if (ResistanceEnabled) {
            double airResistanceX = -ResistanceCoefficient * velocityX;
            velocityX += airResistanceX;
        }

        if (y > HEIGHT - 45) {
            y = HEIGHT - 45;
            if (bouncingEnabled) {
                velocityY = -Math.abs(velocityY) * 0.7;
            } else {
                velocityY = 0;
            }
        }

        if (y < 0) {
            y = 0;
            if (bouncingEnabled) {
                velocityY = Math.abs(velocityY) * 0.7;
            } else {
                velocityY = 0;
            }
        }

        if (y >= HEIGHT - 45 && frictionEnabled && friction > 0) {
            double frictionForce = friction * velocityX;
            velocityX -= Math.signum(velocityX) * Math.min(Math.abs(frictionForce), Math.abs(velocityX));
            if (Math.abs(velocityX) < 0.01) velocityX = 0;
        }

        if (x > WIDTH) x = 0;
        if (x < 0) x = WIDTH;


        trail.add(new double[]{x + 20, y + 20});
        if (trail.size() > TRAIL_LENGTH) {
            trail.remove(0);
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

        for (int i = 0; i < trail.size(); i++) {
            double[] point = trail.get(i);
            double opacity = (double) i / trail.size();
            gc.setFill(new Color(1, 0, 0, opacity));
            gc.fillOval(point[0], point[1], 4, 4);
        }

        gc.setFill(Color.ORANGE);
        gc.fillOval(x, y, 40, 40);
        gc.setFill(new Color(0, 0, 0, 0.3));
        gc.fillOval(x + 6, y + 6, 40, 40);

        if (gravityEnabled) {
            gc.setStroke(Color.YELLOW);
            gc.setLineWidth(2);
            gc.strokeLine(x + 20, y + 20, x + 20, y + 60);
            gc.setFill(Color.YELLOW);
            gc.fillText("Gravitace", x + 30, y + 50);
        }

        if (ResistanceEnabled) {
            double airResistanceX = -ResistanceCoefficient * velocityX;
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeLine(x + 20, y + 20, x + 20 + airResistanceX * 30, y + 20);
            gc.setFill(Color.RED);
            gc.fillText("Odpor prostředí", x + 30, y + 30);
        }

        if (y >= HEIGHT - 45 && frictionEnabled && friction > 0) {
            double frictionDirection = Math.signum(velocityX) == 0 ? 0 : -Math.signum(velocityX);
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(2);
            gc.strokeLine(x + 20, y + 20, x + 20 + frictionDirection * 30, y + 20);
            gc.setFill(Color.GREEN);
            gc.fillText("Tření", x + 30, y + 10);
        }
        
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 14));
        gc.fillText("Ovládání:", 20, 20);
        gc.fillText("Šipky -> Přidej rychlost", 20, 40);
        gc.fillText("Space -> Stop", 20, 60);
        gc.fillText("(P)lus / (M)ínus -> Změna tření", 20, 80);
        gc.fillText("(V)ětší / (C)menší odpor prostředí", 20, 100);
        gc.fillText("G -> Přepnout gravitaci", 20, 120);
        gc.fillText("B -> Přepnout odraz", 20, 140);
        gc.fillText("R -> Přepnout odpor prostředí", 20, 160);
        gc.fillText("J -> Zpět", 20, 180);

        gc.fillText("Rychlost X: " + String.format("%.2f", velocityX) + " m/s", WIDTH - 200, 20);
        gc.fillText("Rychlost Y: " + String.format("%.2f", velocityY) + " m/s", WIDTH - 200, 40);
        gc.fillText("Tření: " + String.format("%.3f", friction) + " (" + (frictionEnabled ? "Zapnuto" : "Vypnuto") + ")", WIDTH - 200, 60);
        gc.fillText("Odpor prostředí: " + String.format("%.2f", ResistanceCoefficient)+ " (" + (ResistanceEnabled ? "Zapnuto" : "Vypnuto") + ")", WIDTH - 200, 80);
        gc.fillText("Gravitace: " + (gravityEnabled ? "Zapnutá" : "Vypnutá"), WIDTH - 200, 100);
        gc.fillText("Odskočení: " + (bouncingEnabled ? "Zapnuté" : "Vypnuté"), WIDTH - 200, 120);
    }

}
