package com.example.Rp5;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FirstLawExamples {

    private Label exampleLabel;
    private ComboBox<String> answerSelector;
    private Label feedbackLabel;
    private Button checkAnswerButton;
    private Label solutionLabel;
    private ComboBox<String> exampleSelector;
    private Button backButton;

    public void show(Stage stage) {

        Label titleLabel = new Label("🌌 První Newtonův zákon 🌌");
        titleLabel.setStyle("-fx-font-size: 60px; -fx-font-weight: bold; -fx-text-fill: #2E4A86; -fx-font-family: 'Arial';");

        Label theoryLabel = new Label(
                "📜 Newtonův první zákon (zákon setrvačnosti) říká:\n" +
                        "Každé těleso setrvává v klidu nebo se pohybuje rovnoměrně přímočaře, " +
                        "pokud na něj nepůsobí žádná síla nebo pokud jsou všechny síly v rovnováze."
        );
        theoryLabel.setWrapText(true);
        theoryLabel.setMaxWidth(1200);
        theoryLabel.setStyle("-fx-font-size: 26px; -fx-font-family: 'Arial'; -fx-text-fill: #333333;");

        Label formulaLabel = new Label("🔢 Newtonův první zákon:");
        formulaLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-font-family: 'Arial'; -fx-text-fill: #2E4A86;");

        Label formulaDescription = new Label(
                "F1 + F2 + F3 + ... = 0 => v= konst. (Tento zákon platí jen v inerciálních soustavách)"
        );
        formulaDescription.setWrapText(true);
        formulaDescription.setMaxWidth(1200);
        formulaDescription.setStyle("-fx-font-size: 28px; -fx-font-family: 'Arial'; -fx-text-fill: #333333;");

        exampleSelector = new ComboBox<>();
        exampleSelector.getItems().addAll(
                "Vlak a míček",
                "Vlak a míček 2"
        );
        exampleSelector.setPromptText("Vyberte příklad");
        exampleSelector.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial'; -fx-background-color: #F0F8FF;");
        exampleLabel = new Label("Vyberte příklad pro zobrazení zadání.");
        exampleLabel.setWrapText(true);
        exampleLabel.setMaxWidth(1200);
        exampleLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial'; -fx-text-fill: #333333;");

        answerSelector = new ComboBox<>();
        answerSelector.setPromptText("Vyberte odpověď");
        answerSelector.setVisible(false);
        answerSelector.setStyle("-fx-font-size: 22px; -fx-background-color: #F0F8FF;");

        checkAnswerButton = new Button("Ověřit odpověď");
        checkAnswerButton.setVisible(false);
        checkAnswerButton.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px;");

        feedbackLabel = new Label();
        feedbackLabel.setVisible(false);
        feedbackLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial';");

        solutionLabel = new Label();
        solutionLabel.setVisible(false);
        solutionLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial';");

        backButton = new Button("Zpět");
        backButton.setStyle("-fx-font-size: 22px; -fx-background-color: #FF6347; -fx-text-fill: white; -fx-border-radius: 5px;");
        backButton.setOnAction(e -> new MainMenuApp().start(stage));

        checkAnswerButton.setOnMouseEntered(e -> checkAnswerButton.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-background-color: #45a049; -fx-text-fill: white; -fx-border-radius: 5px;"));
        checkAnswerButton.setOnMouseExited(e -> checkAnswerButton.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px;"));

        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-font-size: 22px; -fx-background-color: #FF4500; -fx-text-fill: white; -fx-border-radius: 5px;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-font-size: 22px; -fx-background-color: #FF6347; -fx-text-fill: white; -fx-border-radius: 5px;"));

        checkAnswerButton.setOnAction(e -> checkAnswer());

        exampleSelector.setOnAction(e -> {
            String selectedExample = exampleSelector.getValue();
            feedbackLabel.setVisible(false);
            solutionLabel.setVisible(false);
            answerSelector.setVisible(true);
            checkAnswerButton.setVisible(true);
            answerSelector.getItems().clear();

            switch (selectedExample) {
                case "Vlak a míček":
                    exampleLabel.setText("Co se stane s míčem stojícím v zadním vagónu, když se vlak rozjede vpřed?");
                    answerSelector.getItems().addAll("rozjede se směrem od vlaku", "rozjede se směrem k vlaku", "zůstane stát");
                    solutionLabel.setText("✅ Správná odpověď: rozjede se směrem od vlaku");
                    break;
                case "Vlak a míček 2":
                    exampleLabel.setText("Co se stane s míčem stojícím v zadním vagónu, když vlak zatočí doprava a zpomalí?");
                    answerSelector.getItems().addAll("bude se pohybovat šikomo k vlaku", "bude se pohybovat šikomo od vlaku", "nebude se pohybovat");
                    solutionLabel.setText("✅ Správná odpověď: bude se pohybovat šikomo k vlaku");
                    break;
            }
        });

        VBox layout = new VBox(30, titleLabel, theoryLabel, formulaLabel, formulaDescription, exampleSelector, exampleLabel, answerSelector, checkAnswerButton, feedbackLabel, solutionLabel, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 80px; -fx-background-color: #ADD8E6; -fx-background-radius: 15px;");

        Scene scene = new Scene(layout, 1400, 900);
        stage.setTitle("První Newtonův zákon");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void checkAnswer() {
        String userAnswer = answerSelector.getValue();
        String correctAnswer = solutionLabel.getText().replace("✅ Správná odpověď: ", "");

        if (userAnswer != null && userAnswer.equals(correctAnswer)) {
            feedbackLabel.setText("🎉 Správně!");
            feedbackLabel.setStyle("-fx-text-fill: green; -fx-font-size: 24px; -fx-font-family: 'Arial';");
        } else {
            feedbackLabel.setText("❌ Špatně! Správná odpověď: " + correctAnswer);
            feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-size: 24px; -fx-font-family: 'Arial';");
        }
        feedbackLabel.setVisible(true);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), feedbackLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        FadeTransition fadeInSolution = new FadeTransition(Duration.seconds(0.5), solutionLabel);
        fadeInSolution.setFromValue(0);
        fadeInSolution.setToValue(1);
        fadeInSolution.play();
    }
}
