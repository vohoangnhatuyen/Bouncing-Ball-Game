package com.example.bouncingballgamejavafx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Random;

public class BouncingBall extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double BALL_RADIUS = 20;
    private static final double SPEED_INCREMENT = 0.2;
    private double ballSpeedX = 3;
    private double ballSpeedY = 3;
    private double ballX = WIDTH / 2;
    private double ballY = HEIGHT / 2;
    private final Random random = new Random();
    private Color ballColor = Color.RED;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bouncing Ball");
        primaryStage.setResizable(false);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
        speedLabel.setTranslateX(10);
        speedLabel.setTranslateY(40);
        speedLabel.setTextFill(Color.WHITE);
        speedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        root.getChildren().add(speedLabel);
        updateSpeedLabel();
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, WIDTH, HEIGHT);

                gc.setFill(ballColor);
                gc.fillOval(ballX - BALL_RADIUS, ballY - BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2);

                ballX += ballSpeedX;
                ballY += ballSpeedY;

                if (ballX - BALL_RADIUS < 0 || ballX + BALL_RADIUS > WIDTH) {
                    ballSpeedX = -ballSpeedX;
                    ballColor = randomColor();
                }
                if (ballY - BALL_RADIUS < 0 || ballY + BALL_RADIUS > HEIGHT) {
                    ballSpeedY = -ballSpeedY;
                    ballColor = randomColor();
                }
            }
        };

        gameLoop.start();

        primaryStage.show();
    }

    private void handleKeyPress(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.UP) {
            ballSpeedX *= (1 + SPEED_INCREMENT);
            ballSpeedY *= (1 + SPEED_INCREMENT);
        } else if (keyCode == KeyCode.DOWN) {
            ballSpeedX *= (1 - SPEED_INCREMENT);
            ballSpeedY *= (1 - SPEED_INCREMENT);
        }
        updateSpeedLabel();
    }

    private Color randomColor() {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private final Label speedLabel = new Label();
    private double scaledSpeed;

    private void updateSpeedLabel() {
        double maxSpeed = 10;
        double minSpeed = 1;
        double speed = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY * ballSpeedY);
        scaledSpeed = (speed - 3) / 5 * (maxSpeed - minSpeed) + minSpeed;

        speedLabel.setText(String.format("Speed: %.2f", scaledSpeed));
    }

}
