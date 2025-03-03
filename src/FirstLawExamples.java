package com.example.rp5;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FirstLawExamples {

    private Label exampleLabel;
    private ComboBox<String> answerSelector;
    private Label feedbackLabel;
    private Button checkAnswerButton;
    private Label solutionLabel;
    private ComboBox<String> exampleSelector;

    public void show(Stage stage) {

        Label titleLabel = new Label("🌌 První Newtonův zákon 🌌");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #1E3A8A; -fx-font-family: 'Arial';");

        Label theoryLabel = new Label(
                "📜 Newtonův první zákon (zákon setrvačnosti) říká:\n" +
                        "Každé těleso setrvává v klidu nebo se pohybuje rovnoměrně přímočaře, " +
                        "pokud na něj nepůsobí žádná síla nebo pokud jsou všechny síly v rovnováze."
        );
        theoryLabel.setWrapText(true);
        theoryLabel.setMaxWidth(600);
        theoryLabel.setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial'; -fx-text-fill: #333333;");

        Label formulaLabel = new Label("🔢 Newtonův první zákon:");
        formulaLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-font-family: 'Arial'; -fx-text-fill: #1E3A8A;");

        Label formulaDescription = new Label("F1 + F2 + F3 + ... = 0 => v= konst.");
        formulaDescription.setStyle("-fx-font-size: 20px; -fx-font-family: 'Arial'; -fx-text-fill: #333333;");

        exampleSelector = new ComboBox<>();
        exampleSelector.getItems().addAll("Vlak a míček", "Vlak a míček 2");
        exampleSelector.setPromptText("Vyberte příklad");
        exampleSelector.setStyle("-fx-font-size: 16px;");

        exampleLabel = new Label("Vyberte příklad pro zobrazení zadání.");
        exampleLabel.setWrapText(true);
        exampleLabel.setMaxWidth(600);
        exampleLabel.setStyle("-fx-font-size: 16px;");

        answerSelector = new ComboBox<>();
        answerSelector.setPromptText("Vyberte odpověď");
        answerSelector.setVisible(false);

        checkAnswerButton = new Button("Ověřit odpověď");
        checkAnswerButton.setVisible(false);
        checkAnswerButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px;");

        feedbackLabel = new Label();
        feedbackLabel.setVisible(false);
        feedbackLabel.setStyle("-fx-font-size: 16px;");

        solutionLabel = new Label();
        solutionLabel.setVisible(false);
        solutionLabel.setStyle("-fx-font-size: 16px;");

        Button backButton = new Button("Zpět");
        backButton.setStyle("-fx-font-size: 16px; -fx-background-color: #FF6347; -fx-text-fill: white; -fx-padding: 10px 20px;");
        backButton.setOnAction(e -> new MainMenuApp().start(stage));

        checkAnswerButton.setOnAction(e -> checkAnswer());

        exampleSelector.setOnAction(e -> {
            String selectedExample = exampleSelector.getValue();
            feedbackLabel.setVisible(false);
            solutionLabel.setVisible(false);
            answerSelector.setVisible(true);
            checkAnswerButton.setVisible(true);
            answerSelector.getItems().clear();

            switch (selectedExample) {
                case "Vlak a míček" -> {
                    exampleLabel.setText("Co se stane s míčem stojícím v zadním vagónu, když se vlak rozjede vpřed?");
                    answerSelector.getItems().addAll("rozjede se směrem od vlaku", "rozjede se směrem k vlaku", "zůstane stát");
                    solutionLabel.setText("✅ Správná odpověď: rozjede se směrem od vlaku");
                }
                case "Vlak a míček 2" -> {
                    exampleLabel.setText("Co se stane s míčem stojícím v zadním vagónu, když vlak zatočí doprava a zpomalí?");
                    answerSelector.getItems().addAll("bude se pohybovat šikomo k vlaku", "bude se pohybovat šikomo od vlaku", "nebude se pohybovat");
                    solutionLabel.setText("✅ Správná odpověď: bude se pohybovat šikomo k vlaku");
                }
            }
        });

        VBox layout = new VBox(15, titleLabel, theoryLabel, formulaLabel, formulaDescription, exampleSelector, exampleLabel, answerSelector, checkAnswerButton, feedbackLabel, solutionLabel, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20px; -fx-background-color: #D4E6F1; -fx-background-radius: 10px;");

        Scene scene = new Scene(layout, 1000, 700);
        stage.setTitle("První Newtonův zákon");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void checkAnswer() {
        String userAnswer = answerSelector.getValue();
        String correctAnswer = solutionLabel.getText().replace("✅ Správná odpověď: ", "");

        if (userAnswer != null && userAnswer.equals(correctAnswer)) {
            feedbackLabel.setText("🎉 Správně!");
            feedbackLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        } else {
            feedbackLabel.setText("❌ Špatně! Správná odpověď: " + correctAnswer);
            feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        }
        feedbackLabel.setVisible(true);
    }
}

