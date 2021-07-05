/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author hruby
 */
class ControlPanelVBox extends VBox {

    ControlPanelVBox() {
        setPadding(new Insets(15));
        setSpacing(20);
        setAlignment(Pos.TOP_CENTER);
    }

    void addButton(String text, EventHandler<ActionEvent> event) {
        Button button = new Button();
        button.setText(text);
        button.addEventHandler(ActionEvent.ACTION, event);
        button.setMinWidth(180);
        getChildren().add(button);
    }

    ComboBox generateCb(Enum[] enumField) {
        ObservableList ol = FXCollections.observableArrayList(enumField);
        ComboBox cb = new ComboBox();
        cb.setMinWidth(180);
        cb.setItems(ol);
        cb.getSelectionModel().selectFirst();
        return cb;
    }

    void addLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Times New Roman", 15));
        label.setPadding(new Insets(40, 0, 0, 0));
        getChildren().add(label);
    }

    void addComboBox(ComboBox cb) {
        getChildren().add(cb);
    }
}
