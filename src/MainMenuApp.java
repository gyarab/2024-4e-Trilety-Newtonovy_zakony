package com.example.rp5;

import javafx.application.Application;
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

public class MainMenuApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showWindow();
    }

    public void showWindow() {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setStyle("-fx-padding: 20;");

        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, null,
                new Stop(0, Color.LIGHTBLUE),
                new Stop(1, Color.DARKBLUE));
        menu.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, null)));

        Text title = new Text("RP-simulace Newtonových zákonů");
        title.setFont(Font.font("Arial", 36));
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(10, Color.BLACK));

        Button firstLawButton = createStyledButton("První zákon - Setrvačnost");
        Button secondLawButton = createStyledButton("Druhý zákon - F = ma");
        Button thirdLawButton = createStyledButton("Třetí zákon - Akce a reakce");

        // Otevře příslušné simulace
        firstLawButton.setOnAction(e -> new FirstLawTheory().show(primaryStage));
        secondLawButton.setOnAction(e -> new SecondLawTheory().show(primaryStage));
        thirdLawButton.setOnAction(e -> new ThirdLawTheory().show(primaryStage));

        menu.getChildren().addAll(title, firstLawButton, secondLawButton, thirdLawButton);

        Scene mainMenuScene = new Scene(menu, 800, 600);
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Newtonovy zákony");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 16));
        button.setStyle("-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;");
        button.setEffect(new DropShadow(5, Color.BLACK));
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: linear-gradient(to bottom, #66BB6A, #388E3C); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 5;"));
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
