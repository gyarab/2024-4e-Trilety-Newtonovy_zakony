package com.example.rp5;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SecondLawTheory {

    public void show(Stage stage) {
        // Vytvoření scény pro třetí Newtonův zákon
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        // Gradientní pozadí
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, null,
                new Stop(0, Color.LIGHTPINK),
                new Stop(1, Color.DEEPPINK));
        layout.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, null)));

        // Název aplikace
        Text title = new Text("Druhý Newtonův Zákon");
        title.setFont(Font.font("Arial", 36));
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(10, Color.BLACK));

        // Text s vysvětlením třetího zákona
        Text explanation = new Text("Zrychlení tělesa je přímo úměrné výsledné síle působící na těleso" +
                " a nepřímo úměrné jeho hmotnosti, což lze vyjádřit vztahem F = ma.");
        explanation.setFont(Font.font("Arial", 16));
        explanation.setFill(Color.WHITE);

        // Tlačítka pro další akce
        Button examplesButton = createStyledButton("Příklady");
        Button simulationButton = createStyledButton("Simulace");
        Button backButton = createStyledButton("Zpět");
        Button graphButton = createStyledButton("Graf"); // Přidání tlačítka Graf

        // Akce pro tlačítko "Simulace" - přechod na simulaci
        simulationButton.setOnAction(e -> new SecondLawScene().show(stage));

        // Akce pro tlačítko "Příklady"
        examplesButton.setOnAction(e -> new MainMenuApp().start(stage));

        // Akce pro tlačítko "Graf" - přechod na grafy
        graphButton.setOnAction(e -> new SecondLawGraf().showWindow());

        // Akce pro tlačítko "Zpět" - vrací zpět na hlavní menu
        backButton.setOnAction(e -> new MainMenuApp().start(stage));

        // Přidání všech komponent do rozvržení
        layout.getChildren().addAll(title, explanation, examplesButton, simulationButton, graphButton, backButton); // Přidání tlačítka Graf

        // Vytvoření scény pro okno
        Scene scene = new Scene(layout, 800, 600);

        // Nastavení scény do okna
        stage.setTitle("Newton's Laws - Druhý Zákon");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // Pomocná metoda pro vytvoření stylovaného tlačítka
    Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 16));
        button.setStyle("-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;");
        button.setEffect(new DropShadow(5, Color.BLACK));
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: linear-gradient(to bottom, #66BB6A, #388E3C); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;"));
        return button;
    }
}
