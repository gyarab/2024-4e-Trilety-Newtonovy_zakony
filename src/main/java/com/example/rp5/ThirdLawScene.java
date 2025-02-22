package com.example.rp5;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ThirdLawScene {

    // Konstanty
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;

    // Koule
    private Circle ball1, ball2;
    private double v1 = 2; // Rychlost první koule (m/s)
    private double v2 = -2; // Rychlost druhé koule (m/s)
    private double m1 = 2; // Hmotnost první koule (kg)
    private double m2 = 1; // Hmotnost druhé koule (kg)

    private boolean isElastic = true; // Typ srážky: elastická nebo neelastická
    private boolean isFrictionEnabled = false; // Tření zapnuto nebo vypnuto

    // Informace o objektech
    private Label infoLabel;

    public ThirdLawScene() {
        // Konstruktor pro inicializaci potřebných proměnných
        infoLabel = new Label();
    }

    public void show(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // Vytvoření vícero gradientů pro pozadí
        LinearGradient gradient1 = new LinearGradient(0, 0, 1, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.DEEPSKYBLUE),
                new Stop(0.5, Color.CORNFLOWERBLUE),
                new Stop(1, Color.MIDNIGHTBLUE));

        LinearGradient gradient2 = new LinearGradient(0, 0, 1, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.SKYBLUE),
                new Stop(0.5, Color.DODGERBLUE),
                new Stop(1, Color.BLACK));

        // Vytvoření dynamického pozadí s různými efekty
        Rectangle background1 = new Rectangle(0, 0, WIDTH, HEIGHT);
        background1.setFill(gradient1);

        Rectangle background2 = new Rectangle(0, 0, WIDTH, HEIGHT);
        background2.setFill(gradient2);
        background2.setOpacity(0.3); // Jemný přechod s nízkou opacitou

        root.getChildren().addAll(background1, background2);

        // Vytvoření koulí s efektem stínu
        ball1 = new Circle(50, HEIGHT / 2, 20, Color.BLUE);
        ball2 = new Circle(300, HEIGHT / 2, 30, Color.RED);
        ball1.setEffect(new javafx.scene.effect.DropShadow(10, Color.BLACK));
        ball2.setEffect(new javafx.scene.effect.DropShadow(10, Color.BLACK));

        // Label s informacemi
        infoLabel.setTranslateX(10);
        infoLabel.setTranslateY(10);
        infoLabel.setFont(Font.font("Arial", 14));
        updateInfoLabel();

        // Label s ovládáním
        Label controlsLabel = new Label(""" 
                Ovládání:
                ← → - Změna rychlosti modré koule
                ↑ ↓ - Změna rychlosti červené koule
                A/D - Změna hmotnosti modré koule
                W/S - Změna hmotnosti červené koule
                R - Reset simulace
                P - Pozastavení/pokračování
                E - Elastická/Neelastická srážka
                T - Aktivace/Vypnutí tření""");
        controlsLabel.setTranslateX(10);
        controlsLabel.setTranslateY(80);
        controlsLabel.setFont(Font.font("Arial", 14));

        root.getChildren().addAll(ball1, ball2, infoLabel, controlsLabel);

        // Tlačítko Zpět
        Button backButton = new Button("Zpět");
        backButton.setTranslateX(10);
        backButton.setTranslateY(HEIGHT - 100); // Tlačítko bude ve spodní části
        backButton.setOnAction(e -> new MainMenuApp().start(stage)); // Akce tlačítka
        root.getChildren().add(backButton);

        // Animace pohybu s hladkým přechodem
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.LEFT) {
                v1 -= 1;
            } else if (event.getCode() == javafx.scene.input.KeyCode.RIGHT) {
                v1 += 1;
            } else if (event.getCode() == javafx.scene.input.KeyCode.UP) {
                v2 += 1;
            } else if (event.getCode() == javafx.scene.input.KeyCode.DOWN) {
                v2 -= 1;
            } else if (event.getCode() == javafx.scene.input.KeyCode.A) {
                m1 -= 0.5; // Snížení hmotnosti modré koule
                if (m1 < 0.5) m1 = 0.5; // Minimální hmotnost
            } else if (event.getCode() == javafx.scene.input.KeyCode.D) {
                m1 += 0.5; // Zvýšení hmotnosti modré koule
            } else if (event.getCode() == javafx.scene.input.KeyCode.W) {
                m2 += 0.5; // Zvýšení hmotnosti červené koule
            } else if (event.getCode() == javafx.scene.input.KeyCode.S) {
                m2 -= 0.5; // Snížení hmotnosti červené koule
                if (m2 < 0.5) m2 = 0.5; // Minimální hmotnost
            } else if (event.getCode() == javafx.scene.input.KeyCode.R) {
                resetSimulation();
            } else if (event.getCode() == javafx.scene.input.KeyCode.P) {
                if (timeline.getStatus() == Timeline.Status.RUNNING) {
                    timeline.pause();
                } else {
                    timeline.play();
                }
            } else if (event.getCode() == javafx.scene.input.KeyCode.E) {
                isElastic = !isElastic; // Přepnutí mezi elastickou a neelastickou srážkou
            } else if (event.getCode() == javafx.scene.input.KeyCode.T) {
                isFrictionEnabled = !isFrictionEnabled; // Aktivace/Vypnutí tření
            }
            updateInfoLabel();
        });

        stage.setTitle("Newtonova Srážka");
        stage.setScene(scene);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setResizable(false);
        stage.show();
    }

    private void openThirdLawTheory(Stage stage) {
        // Otevření nové scény s teorií
        ThirdLawTheory theoryScene = new ThirdLawTheory();
        theoryScene.show(stage);
    }

    private void update() {
        // Pohyb koulí
        ball1.setCenterX(ball1.getCenterX() + v1);
        ball2.setCenterX(ball2.getCenterX() + v2);

        // Tření (volitelné)
        if (isFrictionEnabled) {
            v1 *= 0.99; // Snížení rychlosti modré koule
            v2 *= 0.99; // Snížení rychlosti červené koule
        }

        // Detekce srážky mezi koulemi
        double distance = Math.abs(ball1.getCenterX() - ball2.getCenterX());
        double combinedRadius = ball1.getRadius() + ball2.getRadius();

        if (distance < combinedRadius) {
            // Před elastickou srážkou se musí koule dotýkat, nebo se mírně překrývat
            double overlap = combinedRadius - distance;

            // Zajistíme, že se koule "odpíchnou" od sebe, pokud jsou příliš blízko
            if (overlap > 0) {
                // Posuneme koule tak, aby se dotýkaly
                double moveDistance = overlap / 2;
                ball1.setCenterX(ball1.getCenterX() - moveDistance);
                ball2.setCenterX(ball2.getCenterX() + moveDistance);
            }

            // Výpočet rychlostí pro elastickou srážku
            double newV1 = ((m1 - m2) * v1 + 2 * m2 * v2) / (m1 + m2);
            double newV2 = ((m2 - m1) * v2 + 2 * m1 * v1) / (m1 + m2);

            // Aplikujeme nové rychlosti
            v1 = newV1;
            v2 = newV2;

            updateInfoLabel();  // Aktualizace informací
        }

        // Odrážení od okrajů obrazovky
        if (ball1.getCenterX() - ball1.getRadius() <= 0 || ball1.getCenterX() + ball1.getRadius() >= WIDTH) {
            v1 = -v1;
            updateInfoLabel();
        }

        if (ball2.getCenterX() - ball2.getRadius() <= 0 || ball2.getCenterX() + ball2.getRadius() >= WIDTH) {
            v2 = -v2;
            updateInfoLabel();
        }
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
        updateInfoLabel();
    }

    private void updateInfoLabel() {
        infoLabel.setText(String.format("Modrá koule - Hmotnost: %.1f kg, Rychlost: %.1f m/s\nČervená koule - Hmotnost: %.1f kg, Rychlost: %.1f m/s\nSrážka: %s\nTření: %s",
                m1, v1, m2, v2, isElastic ? "Elastická" : "Neelastická", isFrictionEnabled ? "Zapnuto" : "Vypnuto"));
    }
}
