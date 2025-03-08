package com.example.rp5;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ThirdLawExamples {

    private final Map<String, String[]> examples = new HashMap<>();
    private final Map<String, String> correctAnswers = new HashMap<>();

    private Label exampleLabel, feedbackLabel, solutionLabel;
    private ComboBox<String> answerSelector, exampleSelector;

    public ThirdLawExamples() {
        examples.put("Pohyb auta a zdi", new String[]{
                "Co se stane, když auto narazí do zdi?",
                "Zeď působí na auto stejnou silou, kterou auto působí na zeď",
                "Auto působí na zeď větší silou",
                "Zeď působí na auto menší silou"
        });
        correctAnswers.put("Pohyb auta a zdi", "Zeď působí na auto stejnou silou, kterou auto působí na zeď");

        examples.put("Ruka a míč", new String[]{
                "Co se stane, když ruka udeří do míče?",
                "Míč působí na ruku stejnou silou, kterou ruka působí na míč",
                "Ruka působí na míč větší silou",
                "Míč působí na ruku menší silou"
        });
        correctAnswers.put("Ruka a míč", "Míč působí na ruku stejnou silou, kterou ruka působí na míč");

        examples.put("Gravitace a planeta", new String[]{
                "Jaký vztah má gravitační síla mezi Zemí a Měsícem?",
                "Země působí na Měsíc stejnou silou, jakou Měsíc působí na Zemi",
                "Země působí na Měsíc větší silou",
                "Měsíc působí na Zemi menší silou"
        });
        correctAnswers.put("Gravitace a planeta", "Země působí na Měsíc stejnou silou, jakou Měsíc působí na Zemi");
    }

    public void show(Stage stage) {
        Label titleLabel = new Label("⚡ Třetí Newtonův zákon ⚡");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        Label theoryLabel = new Label(
                "📜 Newtonův třetí zákon říká: \n" +
                        "Pro každou akci je stejná a opačná reakce."
        );
        theoryLabel.setWrapText(true);
        theoryLabel.setMaxWidth(600);
        theoryLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffffff;");

        exampleSelector = new ComboBox<>();
        exampleSelector.getItems().addAll(examples.keySet());
        exampleSelector.setPromptText("Vyberte příklad");

        exampleLabel = new Label("Zde se zobrazí zadání příkladu.");
        exampleLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18px; -fx-font-weight: bold;");

        answerSelector = new ComboBox<>();
        answerSelector.setPromptText("Vyberte odpověď");

        Button checkAnswerButton = new Button("✅ Ověřit odpověď");
        checkAnswerButton.setOnAction(e -> checkAnswer());

        feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-font-size: 18px;");

        solutionLabel = new Label();
        solutionLabel.setStyle("-fx-text-fill: yellow; -fx-font-size: 16px;");

        exampleSelector.setOnAction(e -> loadExample());

        Button backButton = new Button("🔙 Zpět");
        backButton.setOnAction(e -> new ThirdLawTheory().show(stage));

        VBox layout = new VBox(20, titleLabel, theoryLabel, exampleSelector, exampleLabel, answerSelector,
                checkAnswerButton, feedbackLabel, solutionLabel, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: linear-gradient(to bottom, #8A2BE2, #4A90E2);");

        Scene scene = new Scene(layout, 900, 600);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) new MainMenuApp().start(stage);
        });

        stage.setTitle("Třetí Newtonův zákon");
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
