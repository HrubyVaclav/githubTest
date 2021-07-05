/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author hruby
 */
class ControlPanelHBox extends HBox {

    public ControlPanelHBox() {
        setPadding(new Insets(15, 15, 15, 10));
        setSpacing(10);
    }

    public void addButton(String text, EventHandler<ActionEvent> event) {
        Button button = new Button(text);
        button.addEventHandler(ActionEvent.ACTION, event);
        button.setMinWidth(80);
        getChildren().add(button);
    }

}
