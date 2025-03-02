package com.example.rp5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class FirstLawScene extends JPanel implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;

    private double x = WIDTH / 2;
    private double y = HEIGHT / 2;
    private double velocityX = 0;
    private double velocityY = 0;

    private final double acceleration = 0.5;
    private double friction = 0.02;
    private boolean frictionEnabled = true;
    private boolean gravityEnabled = false;
    private boolean bouncingEnabled = false;

    private final BufferedImage backgroundImage;

    public FirstLawScene() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);


        backgroundImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = backgroundImage.createGraphics();
        createBackground(g2d);
        g2d.dispose();

        Timer timer = new Timer(1000 / 60, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    velocityX += acceleration;
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    velocityX -= acceleration;
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    velocityX = 0;
                    velocityY = 0;
                } else if (e.getKeyCode() == KeyEvent.VK_T) {
                    frictionEnabled = !frictionEnabled;
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    friction += 0.005;
                } else if (e.getKeyCode() == KeyEvent.VK_M) {
                    friction = Math.max(0, friction - 0.005);
                } else if (e.getKeyCode() == KeyEvent.VK_V) {
                    friction = 0;
                } else if (e.getKeyCode() == KeyEvent.VK_G) {
                    gravityEnabled = !gravityEnabled;
                } else if (e.getKeyCode() == KeyEvent.VK_B) {
                    bouncingEnabled = !bouncingEnabled;
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    velocityY -= 2;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    velocityY += 2;
                }
            }
        });

        setFocusable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        x += velocityX;

        if (gravityEnabled) {
            double gravity = 0.1;
            velocityY += gravity;
        }

        y += velocityY;

        if (frictionEnabled && friction > 0) {
            if (velocityX > 0) {
                velocityX -= friction;
                if (velocityX < 0) velocityX = 0;
            } else if (velocityX < 0) {
                velocityX += friction;
                if (velocityX > 0) velocityX = 0;
            }
        }


        if (x > WIDTH) x = 0;
        if (x < 0) x = WIDTH;


        if (y > HEIGHT - 45) {
            y = HEIGHT - 45;
            velocityY = 0;
            if (bouncingEnabled) {
                velocityY = -5;
            }
        }


        if (y < 0) {
            y = 0;
            velocityY = 0;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.drawImage(backgroundImage, 0, 0, null);


        g.setColor(new Color(30, 144, 255));
        g.fillOval((int) x, (int) y, 40, 40);


        g.setColor(new Color(0, 0, 0, 120));
        g.fillOval((int) (x + 5), (int) (y + 5), 40, 40);


        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Rychlost X: " + String.format("%.2f", velocityX) + " m/s", 20, 20);
        g.drawString("Rychlost Y: " + String.format("%.2f", velocityY) + " m/s", 20, 40);
        g.drawString("Tření: " + String.format("%.3f", friction) + " m/s² (" + (frictionEnabled ? "Zapnuto" : "Vypnuto") + ")" + " (T -> Vypnutí/zapnutí)", 20, 60);
        g.drawString("Gravitace: " + (gravityEnabled ? "Zapnutá (G pro vypnutí)" : "Vypnutá (G pro zapnutí)"), 20, 80);
        g.drawString("Odskočení: " + (bouncingEnabled ? "Zapnuté (B pro vypnutí)" : "Vypnuté (B pro zapnutí)"), 20, 100);
        g.drawString("Šipky -> Přidej rychlost | Space -> Stop", 20, 120);
        g.drawString("(P)lus / (M)ínus -> Změna tření", 20, 140);
        g.drawString("Šipka nahoru -> Skok nahoru | Šipka dolů -> Pohyb dolů", 20, 160);
    }


    private void createBackground(Graphics2D g2d) {

        GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 0, 30), 0, HEIGHT, new Color(0, 0, 60));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        g2d.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) {
            int starX = (int) (Math.random() * WIDTH);
            int starY = (int) (Math.random() * HEIGHT);
            g2d.fillRect(starX, starY, 2, 2);
        }
    }

    public void showWindow() {
        
        JFrame frame = new JFrame("Newtonův první zákon - Interaktivní");
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        frame.setResizable(false);

        frame.setVisible(true);
    }
}
