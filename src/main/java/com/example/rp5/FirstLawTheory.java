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

public class FirstLawTheory{


    public void show(Stage stage) {
        // Vytvoření scény pro třetí Newtonův zákon
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        // Gradientní pozadí
        LinearGradient backgroundGradient = new LinearGradient(0, 0, 1, 0, true, null,
                new Stop(0, Color.LIGHTYELLOW),
                new Stop(1, Color.ORANGE));
        layout.setBackground(new Background(new BackgroundFill(backgroundGradient, CornerRadii.EMPTY, null)));

        // Název aplikace
        Text title = new Text("První Newtonův Zákon");
        title.setFont(Font.font("Arial", 36));
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(10, Color.BLACK));

        // Text s vysvětlením třetího zákona
        Text explanation = new Text("Těleso setrvává v klidu nebo v rovnoměrném přímočarém pohybu," +
                " pokud na něj nepůsobí vnější síly nebo pokud jsou výsledné síly nulové.");
        explanation.setFont(Font.font("Arial", 16));
        explanation.setFill(Color.BLUE);

        // Tlačítka pro další akce
        Button examplesButton = createStyledButton("Příklady");
        Button simulationButton = createStyledButton("Simulace");
        Button backButton = createStyledButton("Zpět");

        // Akce pro tlačítko "Simulace" - přechod na simulaci
        simulationButton.setOnAction(e -> new FirstLawScene().showWindow());
       //simulationButton.setOnAction(e -> new FirstLawScene().show(stage));

        // Akce pro tlačítko "Příklady"
       examplesButton.setOnAction(e -> new MainMenuApp().start(stage));

        // Akce pro tlačítko "Zpět" - vrací zpět na hlavní menu
        backButton.setOnAction(e -> new MainMenuApp().start(stage));

        // Přidání všech komponent do rozvržení
        layout.getChildren().addAll(title, explanation, examplesButton, simulationButton, backButton);

        // Vytvoření scény pro okno
        Scene scene = new Scene(layout, 800, 600);

        // Nastavení scény do okna
        stage.setTitle("Newton's Laws - Třetí Zákon");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // Pomocná metoda pro vytvoření stylovaného tlačítka
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 16));
        button.setStyle("-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;");
        button.setEffect(new DropShadow(5, Color.BLACK));
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: linear-gradient(to bottom, #66BB6A, #388E3C); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;"));
        return button;
    }

}
