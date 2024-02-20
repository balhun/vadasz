package com.example.vadasz;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    public final int DEAD = 1;
    public final int ROKA = 2;
    public final int HOME = 3;
    public final int TREE = 4;

    public ImageView[][] it = new ImageView[16][32];
    public int[][] t = new int[16][32];

    public int ey = -2, ex = -1;

    public int roka = 0;
    public int rokaMax = 0;

    public int loves = 0;
    public int talalt = 0;

    public AnimationTimer timer = null;
    public long tt = 0;
    public long most = 0;

    public void initialize() {
        for (int i = 0; i < 5; i++) icons[i] = new Image(getClass().getResourceAsStream(iconNev[i] + ".png"));
        for (int y = 0; y < 16; y++) for (int x = 0; x < 32; x++) {
            int yy = y, xx = x;
            it[y][x] = new ImageView(icons[DARK]);
            it[y][x].setLayoutX(10+x*48);
            it[y][x].setLayoutY(10+y*48);
            it[y][x].setOnMouseEntered(e -> vilagit(yy, xx));
            it[y][x].setOnMousePressed(e -> loves(yy, xx));
            pane.getChildren().addAll(it[y][x]);
        }


        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                most = now;
                if (now > tt) {
                    elbujik();
                } if (roka == 0) {
                    timer.stop();
                    vege();
                }
            }
        };
        generalErdo();
    }

    public void generalErdo() {
        roka = 0; loves = 0; talalt = 0;
        for (int y = 0; y < 16; y++) for (int x = 0; x < 32; x++) {
            if (Math.random() < 0.1) { t[y][x] = ROKA; roka++;} else t[y][x] = TREE;
            it[y][x].setImage(icons[DARK]);
        }
        rokaMax = roka;
        lbRoka.setText(roka + " / " + rokaMax + " róka");
        timer.start();
    }

    public void vilagit(int y, int x) {
        if (y != ey || x != ex) {
            for (int dY = -2; dY <= 2; dY++) for (int dX = -2; dX <= 2; dX++) {
                int yy = ey + dY, xx = ex + dX;
                if (yy >= 0 && yy <= 15 &&xx >= 0 && xx <= 31 && !(Math.abs(dY) == 2 && Math.abs(dX) == 2)) {
                    it[yy][xx].setImage(icons[DARK]);
                }
            }
            for (int dY = -2; dY <= 2; dY++) for (int dX = -2; dX <= 2; dX++) {
                int yy = y + dY, xx = x + dX;
                if (yy >= 0 && yy <= 15 &&xx >= 0 && xx <= 31 && !(Math.abs(dY) == 2 && Math.abs(dX) == 2)) {
                    it[y + dY][x + dX].setImage(icons[t[y + dY][x + dX]]);
                }
            }
            ey = y; ex = x;
            tt = most + 500_000_000;
        }
    }

    public void elbujik() {
        for (int dY = -2; dY <= 2; dY++) for (int dX = -2; dX <= 2; dX++) {
            int yy = ey + dY, xx = ex + dX;
            if (yy >= 0 && yy <= 15 && xx >= 0 && xx <= 31 && !(Math.abs(dY) == 2 && Math.abs(dX) == 2) && t[yy][xx] == ROKA) {
                it[yy][xx].setImage(icons[HOME]); t[yy][xx] = HOME; roka--;
            }
        }
        lbRoka.setText(roka + " / " + rokaMax + " róka");
    }

    public void loves(int y, int x) {
        loves++;
        if (t[y][x] == ROKA) {
            it[y][x].setImage(icons[DEAD]);
            t[y][x] = DEAD;
            roka--;
            lbRoka.setText(roka + " / " + rokaMax + " róka");
            talalt++;
        }
        lbLoves.setText(loves + " lövés / " + talalt + " találat");
    }



    public void vege() {
        for (int y = 0; y < 16; y++) for (int x = 0; x < 32; x++) it[y][x].setImage(icons[t[y][x]]);
        Alert uzenet = new Alert(Alert.AlertType.NONE);
        uzenet.setTitle("Game over!");
        uzenet.setHeaderText(null);
        String txt = String.format("%d lövésből %d talált, ami %d%%\n", loves, talalt, talalt*100/loves);
        txt += String.format("%d rókából %d lett lelőve, ami %d%%", rokaMax, talalt, talalt*100/rokaMax);
        uzenet.setContentText(txt);
        uzenet.getButtonTypes().add(new ButtonType("Újra"));
        uzenet.setOnCloseRequest(e -> generalErdo());
        uzenet.show();
    }
}