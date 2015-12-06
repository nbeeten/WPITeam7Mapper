package edu.wpi.off.by.one.errors.code.controller.customcontrols;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Created by jules on 12/5/2015.
 */
public class ClearableTextField extends BorderPane {

    //region FXML attributes
    @FXML private TextField textField;
    @FXML private Button clearButton;
    //endregion

    //region constructor/s
    public ClearableTextField(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/customcontrols/ClearableTextField.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try{
            loader.load();
            this.getStylesheets().add(getClass().getResource("../../resources/stylesheets/customcontrols/ClearableTextFieldStyleSheet.css").toExternalForm());
        }
        catch(IOException excpt){
            throw new RuntimeException(excpt);
        }
        this.getStyleClass().add("clearable-text-field");

        addListeners();
    }
    //endregion

    //region Getters, Setters
    public StringProperty textProperty() {
        return textField.textProperty();
    }

    public String getText(){
        return textProperty().get();
    }

    public void setText(String text){
        textProperty().set(text);
    }

    public StringProperty promptTextProperty() {
        return textField.promptTextProperty();
    }

    public String getPromptText(){
        return promptTextProperty().get();
    }

    public void setPromptText(String text){
        promptTextProperty().set(text);
    }
    //endregion

    //region listener Methods
    @FXML private void onClearTextBoxButtonClick(){
        textField.clear();
    }
    //endregion

    //region helper Methods

    //endregion

    private void addListeners(){
        clearButton.disableProperty().bind(textField.textProperty().isEmpty());
    }

}
