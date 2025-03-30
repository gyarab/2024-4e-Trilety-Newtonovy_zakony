package com.example.rp5;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class SecondLawExamples {

    // Mapy pro ukládání příkladů a správných odpovědí
    private final Map<String, String[]> examples = new HashMap<>();
    private final Map<String, String> correctAnswers = new HashMap<>();

    private Label exampleLabel, feedbackLabel, solutionLabel;
    private ComboBox<String> answerSelector, exampleSelector;

    // Inicializace příkladů a odpovědí
    public SecondLawExamples() {
        examples.put("Auto a síla", new String[]{
                "Jak se změní zrychlení auta, pokud se síla zdvojnásobí a hmotnost zůstane stejná?",
                "Zdvojnásobí se", "Zůstane stejné", "Sníží se na polovinu"
        });
        correctAnswers.put("Auto a síla", "Zdvojnásobí se");

        examples.put("Hmotnost a zrychlení", new String[]{
                "Jak se změní zrychlení, pokud se hmotnost tělesa zdvojnásobí a síla zůstane stejná?",
                "Zdvojnásobí se", "Zůstane stejné", "Sníží se na polovinu"
        });
        correctAnswers.put("Hmotnost a zrychlení", "Sníží se na polovinu");

        examples.put("Kámen a gravitace", new String[]{
                "Jaké zrychlení má kámen padající volným pádem na Zemi?",
                "9,8 m/s²", "5 m/s²", "15 m/s²"
        });
        correctAnswers.put("Kámen a gravitace", "9,8 m/s²");
    }

    // Metoda pro zobrazení okna s příklady
    public void show(Stage stage) {
        Label titleLabel = new Label("⚡ Druhý Newtonův zákon ⚡");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        Label theoryLabel = new Label(
                "📜 Newtonův druhý zákon říká: \n" +
                        "Síla působící na těleso je rovna součinu jeho hmotnosti a zrychlení: F = ma."
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
        backButton.setOnAction(e -> new SecondLawTheory().show(stage));

        VBox layout = new VBox(20, titleLabel, theoryLabel, exampleSelector, exampleLabel, answerSelector,
                checkAnswerButton, feedbackLabel, solutionLabel, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30px; -fx-background-color: linear-gradient(to bottom, #8A2BE2, #4A90E2);");

        Scene scene = new Scene(layout, 900, 600);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) new MainMenuApp().start(stage);
        });

        // Nastavení okna
        stage.setTitle("Druhý Newtonův zákon");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    // Metoda pro načtení vybraného příkladu
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

    // Metoda pro kontrolu odpovědi
    private void checkAnswer() {
        String selectedExample = exampleSelector.getValue();
        String userAnswer = answerSelector.getValue();
        String correctAnswer = correctAnswers.get(selectedExample);

        if (userAnswer == null) {
            feedbackLabel.setText("⚠ Vyberte odpověď!");
            feedbackLabel.setStyle("-fx-text-fill: orange;");
            return;
        }

        // Ověření správnosti odpovědi
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
