package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.SQLException;

public class AddMemberFormController {

    @FXML
    private DatePicker dateOfMembership;

    @FXML
    private Label lblMembershipId;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContactNumber;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtNic;

    @FXML
    void btnAddMemberOnClick(ActionEvent event) {

    }

    @FXML
    void btnClearFormOnClick(ActionEvent event) {

    }

}
