package com.example.javafxscheduler.controllers;

import com.example.javafxscheduler.controllers.suggestionDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class suggestionDialogUtil extends Dialog<ButtonType> {

    public suggestionDialogUtil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("suggestionDialog.fxml"));
            Pane suggestionDialogPane = loader.load();

            suggestionDialogController controller = loader.getController();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
