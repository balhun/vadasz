package com.example.vadasz;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class HelloController {

    public Pane pane;
    public Label lbLoves;
    public Label lbRoka;

    public String[] iconNev = { "dark", "dead", "fox", "home", "tree" };
    public Image[] icons = new Image[5];

    public final int DARK = 0;
    public final int ROKA = 2;
    public final int TREE = 4;

    public ImageView[][] it = new ImageView[16][32];
    public int[][] t = new int[16][32];

    public int roka;
    public int rokaMax;

    public void initialize() {
        for (int i = 0; i < 5; i++) icons[i] = new Image(getClass().getResourceAsStream(iconNev[i] + ".png"));
        for (int y = 0; y < 16; y++) for (int x = 0; x < 32; x++) {
            it[y][x] = new ImageView(icons[DARK]);
            it[y][x].setLayoutX(10+x*48);
            it[y][x].setLayoutY(10+y*48);
            pane.getChildren().addAll(it[y][x]);
        }
        generalErdo();
    }

    public void generalErdo() {
        roka = 0;
        for (int y = 0; y < 16; y++) for (int x = 0; x < 32; x++) {
            if (Math.random() < 0.1) { t[y][x] = ROKA; roka++;} else t[y][x] = TREE;
        }
        rokaMax = roka;
        lbRoka.setText(roka + " / " + rokaMax + " róka");
    }
}