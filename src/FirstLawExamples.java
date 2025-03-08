package com.example.rp5;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class FirstLawExamples {

    private final Map<String, String[]> examples = new HashMap<>();
    private final Map<String, String> correctAnswers = new HashMap<>();

    private Label exampleLabel, feedbackLabel, solutionLabel;
    private ComboBox<String> answerSelector, exampleSelector;

    public FirstLawExamples() {
        examples.put("Vlak a míček", new String[]{
                "Co se stane s míčem v zadním vagónu, když se vlak rozjede vpřed?",
                "rozjede se směrem od vlaku", "rozjede se směrem k vlaku", "zůstane stát"
        });
        correctAnswers.put("Vlak a míček", "rozjede se směrem od vlaku");

        examples.put("Vlak a míček 2", new String[]{
                "Co se stane s míčem v zadním vagónu, když vlak zatočí doprava a zpomalí?",
                "bude se pohybovat šikmo k vlaku", "bude se pohybovat šikmo od vlaku", "nebude se pohybovat"
        });
        correctAnswers.put("Vlak a míček 2", "bude se pohybovat šikmo k vlaku");

        examples.put("Auto a led", new String[]{
                "Auto jede po silnici a vjede na led. Jak se bude pohybovat?",
                "zpomalí a zatočí", "bude se pohybovat rovnoměrně", "zastaví okamžitě"
        });
        correctAnswers.put("Auto a led", "bude se pohybovat rovnoměrně");
    }

    public void show(Stage stage) {
        Label titleLabel = new Label("🌌 První Newtonův zákon 🌌");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-font-family: 'Arial Black';");

        Label theoryLabel = new Label(
                "📜 Newtonův první zákon říká: \n" +
                        "Těleso setrvává v klidu nebo v rovnoměrném pohybu, pokud na něj nepůsobí síly nebo jsou síly v rovnováze."
        );
        theoryLabel.setWrapText(true);
        theoryLabel.setMaxWidth(600);
        theoryLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffffff; -fx-font-family: 'Verdana';");

        exampleSelector = new ComboBox<>();
        exampleSelector.getItems().addAll(examples.keySet());
        exampleSelector.setPromptText("Vyberte příklad");
        exampleSelector.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-radius: 5px;");

        exampleLabel = new Label("Zde se zobrazí zadání příkladu.");
        exampleLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18px; -fx-font-weight: bold;");

        answerSelector = new ComboBox<>();
        answerSelector.setPromptText("Vyberte odpověď");
        answerSelector.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-radius: 5px;");

        Button checkAnswerButton = new Button("✅ Ověřit odpověď");
        checkAnswerButton.setStyle("-fx-font-size: 18px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px; -fx-border-radius: 10px;");

        feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-font-size: 18px;");

        solutionLabel = new Label();
        solutionLabel.setStyle("-fx-text-fill: yellow; -fx-font-size: 16px; -fx-font-weight: bold;");

        checkAnswerButton.setOnAction(e -> checkAnswer());
        exampleSelector.setOnAction(e -> loadExample());

        Button backButton = new Button("🔙 Zpět");
        backButton.setStyle("-fx-font-size: 18px; -fx-background-color: #FF5733; -fx-text-fill: white; -fx-padding: 10px; -fx-border-radius: 10px;");
        backButton.setOnAction(e -> new FirstLawTheory().show(stage));

        VBox layout = new VBox(20, titleLabel, theoryLabel, exampleSelector, exampleLabel, answerSelector,
                checkAnswerButton, feedbackLabel, solutionLabel, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: linear-gradient(to bottom, #1E3A8A, #4A90E2);");

        Scene scene = new Scene(layout, 900, 600);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) new MainMenuApp().start(stage);
        });

        stage.setTitle("První Newtonův zákon");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void loadExample() {
        String selectedExample = exampleSelector.getValue();
        if (selectedExample != null && examples.containsKey(selectedExample)) {
            String[] data = examples.get(selectedExample);
            exampleLabel.setText(data[0]);
            answerSelector.getItems().setAll(data[1], data[2], data[3]);
            answerSelector.setValue(null);
            feedbackLabel.setText("");
            solutionLabel.setText("");
        }
    }

    private void checkAnswer() {
        String selectedExample = exampleSelector.getValue();
        String userAnswer = answerSelector.getValue();
        String correctAnswer = correctAnswers.get(selectedExample);

        if (userAnswer == null) {
            feedbackLabel.setText("⚠ Vyberte odpověď!");
            feedbackLabel.setStyle("-fx-text-fill: orange;");
            return;
        }

        if (userAnswer.equals(correctAnswer)) {
            feedbackLabel.setText("🎉 Správně!");
            feedbackLabel.setStyle("-fx-text-fill: green;");
        } else {
            feedbackLabel.setText("❌ Špatně! Správná odpověď: " + correctAnswer);
            feedbackLabel.setStyle("-fx-text-fill: red;");
        }
        solutionLabel.setText("Správná odpověď: " + correctAnswer);
    }
}
