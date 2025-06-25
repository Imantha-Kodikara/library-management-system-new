package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class ReturnBookFormController {

    @FXML
    private JFXButton btnReturnBook;

    @FXML
    private JFXComboBox<?> cmbSelectBookTitle;

    @FXML
    private JFXComboBox<?> cmbSelectMemberId;

    @FXML
    private DatePicker dateOfReturning;

    @FXML
    private Label lblBookIssuedate;

    @FXML
    private Label lblFine;

    @FXML
    private Label lblMember;

    @FXML
    void btnReturnBookOnAction(ActionEvent event) {

    }

    @FXML
    void btnShowDetailsOnAction(ActionEvent event) {

    }

}
