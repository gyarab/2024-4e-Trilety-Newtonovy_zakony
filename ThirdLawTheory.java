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

    public class ThirdLawTheory{


    public void show(Stage stage) {

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");


        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, null,
                new Stop(0, Color.LIGHTBLUE),
                new Stop(1, Color.DARKBLUE));
        layout.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, null)));


        Text title = new Text("Třetí Newtonův Zákon");
        title.setFont(Font.font("Arial", 36));
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(10, Color.BLACK));


        Text explanation = new Text("Každá akce vyvolá stejně velkou opačně orientovanou reakci.");
        explanation.setFont(Font.font("Arial", 16));
        explanation.setFill(Color.WHITE);


        Button examplesButton = createStyledButton("Příklady");
        Button simulationButton = createStyledButton("Simulace");
        Button backButton = createStyledButton("Zpět");


        simulationButton.setOnAction(e -> new ThirdLawScene().show(stage));


        //examplesButton.setOnAction(e -> new FirstLawScene().show(stage));


        backButton.setOnAction(e -> new MainMenuApp().start(stage));


        layout.getChildren().addAll(title, explanation, examplesButton, simulationButton, backButton);


        Scene scene = new Scene(layout, 800, 600);


        stage.setTitle("Newton's Laws - Třetí Zákon");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

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
