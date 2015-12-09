package edu.wpi.off.by.one.errors.code.controller.menupanes;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import edu.wpi.off.by.one.errors.code.controller.MainPane;
import edu.wpi.off.by.one.errors.code.controller.customcontrols.ClearableTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * Created by jules on 11/28/2015.
 */
public class SettingsMenuPane extends BorderPane {
    //region Attributes

    //region Constants for Identify each line when save/loading user info from textfile.
    private final String NAME_STRING = "USER_NAME:";
    private final String PHONE_STRING = "USER_PHONE:";
    private final String EMAIL_STRING = "USER_EMAIL:";
    //endregion

    //region FXML attributes
    @FXML private ClearableTextField nameTextField;
    @FXML private ClearableTextField phoneTextField;
    @FXML private ClearableTextField emailTextField;
    //endregion

    //region Other attributes
    private final String FILE_LOCATION = "src/edu/wpi/off/by/one/errors/code/resources/UserSettings.txt";
    private FileWriter fileWriter;
    private PrintWriter printWriter;
    //endregion

    //endregion

    //region Constructors
    /**
     * Constructor
     * Will load the fxml file and initialize necessary controls and layouts
     */
    public SettingsMenuPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/menupanes/SettingsMenuPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
            fileWriter = new FileWriter(FILE_LOCATION);
            printWriter = new PrintWriter(fileWriter);
        }catch(IOException excpt){
            throw new RuntimeException(excpt);
        }
    }
    //endregion

    @FXML private void onSaveButtonClick(){
        printWriter.println(NAME_STRING + nameTextField.getText());
        printWriter.println(PHONE_STRING + nameTextField.getText());
        printWriter.println(EMAIL_STRING + nameTextField.getText());

        printWriter.close();
    }

}
