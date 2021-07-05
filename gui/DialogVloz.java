/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sprava.Obec;

/**
 *
 * @author hruby
 */
public class DialogVloz extends Stage {

    Obec obec;
    TextField textFieldNazev;
    TextField textFieldPSC;
    TextField textFieldPocetMuzu;
    TextField textFieldPocetZen;

    public DialogVloz(Obec obec) {
        setTitle("Dialog vlož");
        setWidth(350);
        setHeight(350);
        initModality(Modality.WINDOW_MODAL);
        this.obec = obec;
        setScene(getScena());
    }

    private Scene getScena() {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        HBox hBoxNazev = createHBoxNazev();
        HBox hBoxPSC = createHBoxPSC();
        HBox hBoxPocetMuzu = createHBoxPocetMuzu();
        HBox hBoxPocetZen = createHBoxPocetZen();
        HBox buttons = createHBoxButtons();
        box.getChildren().addAll(hBoxNazev, hBoxPSC, hBoxPocetMuzu, hBoxPocetZen, buttons);
        return new Scene(box);
    }

    private HBox createHBoxPSC() {
        Label label = new Label("PSČ");
        textFieldPSC = new TextField();
        HBox hbox = new HBox(label, textFieldPSC);
        hbox.setSpacing(83);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private HBox createHBoxNazev() {
        Label label = new Label("Název");
        textFieldNazev = new TextField();
        HBox hbox = new HBox(label, textFieldNazev);
        hbox.setSpacing(73);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private HBox createHBoxPocetMuzu() {
        Label label = new Label("Počet mužů");
        textFieldPocetMuzu = new TextField();
        HBox hbox = new HBox(label, textFieldPocetMuzu);
        hbox.setSpacing(45);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private HBox createHBoxPocetZen() {
        Label label = new Label("Počet žen");
        textFieldPocetZen = new TextField();
        HBox hbox = new HBox(label, textFieldPocetZen);
        hbox.setSpacing(53);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private HBox createHBoxButtons() {
        ProgObyvatele.isOkClicked = false;
        HBox hbox = new HBox();
        hbox.setSpacing(40);
        hbox.setAlignment(Pos.CENTER);
        Button btnOk = new Button("OK");
        btnOk.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            if (setObecAttributes()) {
                ProgObyvatele.isOkClicked = true;
                Stage stage = (Stage) btnOk.getScene().getWindow();
                stage.close();
            }
        });
        Button btnCancel = new Button("Cancel");
        btnCancel.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        });
        hbox.getChildren().addAll(btnOk, btnCancel);
        return hbox;
    }

    private boolean setObecAttributes() {
        int psc = 0;
        int pocetMuzu = 0;
        int pocetZen = 0;
        String msg = "";
        String nazevObce = textFieldNazev.getText();
        if (nazevObce.length() == 0) {
            msg += "Název obce není validní!\n";
        }

        try {
            psc = Integer.parseInt(textFieldPSC.getText());
            if (psc <= 0) {
                throw new Exception(); // Přidat aby muselo být čtyř/pěticiferný?
            }
        } catch (Exception e) {
            msg += "PSČ obce není validní!\n";
        }
        try {
            pocetMuzu = Integer.parseInt(textFieldPocetMuzu.getText());
            if (pocetMuzu < 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            msg += "Počet mužů není validní (počet mužů musí být nezáporný)!\n";
        }
        try {
            pocetZen = Integer.parseInt(textFieldPocetZen.getText());
            if (pocetZen < 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            msg += "Počet žen není validní (počet žen musí být nezáporný)!\n";
        }
        if (msg.equals("")) {
            obec.setPsc(psc);
            obec.setObec(nazevObce);
            obec.setPocetMuzu(pocetMuzu);
            obec.setPocetZen(pocetZen);
            obec.setCelkem(pocetZen+pocetMuzu);
            return true;
        } else {
            vypisAlert(msg);
            return false;
        }
    }

    private void vypisAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
