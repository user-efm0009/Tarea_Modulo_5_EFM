package org.example.halloween_terror;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Random;

public class Ruleta {

    @FXML
    private Canvas ruletaCanvas;

    @FXML
    private Label textoRuleta;

    private double currentAngle = 0;  // Ángulo actual de la ruleta
    private final Random random = new Random();

    @FXML
    public void initialize() {
        textoRuleta.setText("Bienvenido a la Ruleta del Terror");
        dibujarRuleta(0);
    }

    @FXML
    public void girarRuleta() {
        int vueltas = 3 + random.nextInt(3); // 3 a 5 vueltas
        int extra = random.nextInt(360); // ángulo adicional
        double anguloTotal = vueltas * 360 + extra;

        RotateTransition rt = new RotateTransition(Duration.seconds(3), ruletaCanvas);
        rt.setByAngle(anguloTotal);
        rt.setCycleCount(1);
        rt.setOnFinished(e -> {
            currentAngle = (currentAngle + anguloTotal) % 360;
            mostrarResultado(currentAngle);
        });
        rt.play();
    }

    private void mostrarResultado(double anguloFinal) {
        // Consideramos 0°–179° como "Truco", 180°–359° como "Trato"
        String resultado = (anguloFinal % 360 < 180) ? "¡Truco!" : "¡Trato!";
        textoRuleta.setText("Resultado: " + resultado);
    }

    private void dibujarRuleta(double rotacion) {
        GraphicsContext gc = ruletaCanvas.getGraphicsContext2D();
        double w = ruletaCanvas.getWidth();
        double h = ruletaCanvas.getHeight();

        gc.save(); // Guardar estado
        gc.clearRect(0, 0, w, h);

        // Centro de rotación
        gc.translate(w / 2, h / 2);
        gc.rotate(rotacion);

        // Arco 1 - Truco
        gc.setFill(Color.ORANGERED);
        gc.fillArc(-w / 2, -h / 2, w, h, 0, 180, javafx.scene.shape.ArcType.ROUND);

        // Arco 2 - Trato
        gc.setFill(Color.DARKORANGE);
        gc.fillArc(-w / 2, -h / 2, w, h, 180, 180, javafx.scene.shape.ArcType.ROUND);

        // Textos
        gc.setFill(Color.WHITE);
        gc.fillText("Truco", -30, -60);
        gc.fillText("Trato", -30, 80);

        gc.restore(); // Restaurar estado
    }
}
