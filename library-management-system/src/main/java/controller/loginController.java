package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {
    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    public void btnLoginOnAction(ActionEvent actionEvent) {
       if(areFieldsFilled()){
           if(isValidCredentials()){
               try {
                   Stage stage = new Stage();
                   stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form_view.fxml"))));
                   stage.show();

                   // Close the current login stage after loading dashboard
                   Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                   loginStage.close();

               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }else{
               showAlert(Alert.AlertType.ERROR, "Invalid User Name or Password!");
           }
       }
    }

    //--------------------------------Method for check user name and password-----------------
    private Boolean isValidCredentials(){
        return txtUserName.getText().equals("admin") && txtPassword.getText().equals("1234");
    }

    //--------------------------------Method for tigger alerts---------------------------------

    private void showAlert(Alert.AlertType alertType, String content){
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //---------------------------------Method for check text fields are filled or not--------------

    private Boolean areFieldsFilled(){
        if(txtUserName.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, "User name is required!");
            return false;
        }

        if(txtPassword.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Password is required!");
            return false;
        }

        return true;
    }

}
