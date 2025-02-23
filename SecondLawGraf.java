package com.example.rp5;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecondLawGraf extends JFrame {
    private XYSeries velocitySeries = new XYSeries("Rychlost (m/s)");
    private XYSeries positionSeries = new XYSeries("Poloha (m)");
    private double mass = 1.0;
    private double force = 10.0;
    private double velocity = 0.0;
    private double position = 0.0;
    private double time = 0.0;
    private Timer timer;

    public SecondLawGraf() {
        setTitle("Simulace 2.Newtonova zákona");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        JLabel massLabel = new JLabel("Hmotnost (kg):");
        JTextField massField = new JTextField("1.0", 5);
        JLabel forceLabel = new JLabel("Síla (N):");
        JTextField forceField = new JTextField("10.0", 5);
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Zastavit");

        controlPanel.add(massLabel);
        controlPanel.add(massField);
        controlPanel.add(forceLabel);
        controlPanel.add(forceField);
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        add(controlPanel, BorderLayout.NORTH);


        JFreeChart velocityChart = ChartFactory.createXYLineChart("Rychlost vs. Čas", "Čas (s)", "Rychlost (m/s)", new XYSeriesCollection(velocitySeries), PlotOrientation.VERTICAL, true, true, false);
        JFreeChart positionChart = ChartFactory.createXYLineChart("Poloha vs. Čas", "Čas (s)", "Poloha (m)", new XYSeriesCollection(positionSeries), PlotOrientation.VERTICAL, true, true, false);

        JPanel chartPanel = new JPanel(new GridLayout(2, 1));
        chartPanel.add(new ChartPanel(velocityChart));
        chartPanel.add(new ChartPanel(positionChart));
        add(chartPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Zpět");

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double acceleration = force / mass;
                velocity += acceleration * 0.1;
                position += velocity * 0.1;
                time += 0.1;
                velocitySeries.add(time, velocity);
                positionSeries.add(time, position);
            }
        });

        startButton.addActionListener(e -> {
            try {
                mass = Double.parseDouble(massField.getText());
                force = Double.parseDouble(forceField.getText());
                velocitySeries.clear();
                positionSeries.clear();
                time = 0.0;
                velocity = 0.0;
                position = 0.0;
                timer.start();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Zadejte platná čísla!");
            }
        });


        stopButton.addActionListener(e -> {
            timer.stop();
        });


        backButton.addActionListener(e -> {
            timer.stop();
            dispose();


            SwingUtilities.invokeLater(() -> new MainMenuApp().showWindow());
        });

        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void showWindow() {
        setVisible(true);
    }
}



