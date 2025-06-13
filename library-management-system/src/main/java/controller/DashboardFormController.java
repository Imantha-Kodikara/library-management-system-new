package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardFormController {

    @FXML
    private Label lblAdmin;

    @FXML
    private Label lblFineCollected;

    @FXML
    private Label lblIssuedBooks;

    @FXML
    private Label lblRemainingBooks;

    @FXML
    private Label lblTotalBooks;

    @FXML
    private Label lblTotalMembers;

    @FXML
    private AnchorPane windowsPane;

    @FXML
    void btnAddBookOnClick(ActionEvent event) {

    }

    @FXML
    void btnAddMemberOnClick(ActionEvent event) {
        loadWindow("/view/add_member_form_view.fxml");
    }

    @FXML
    void btnDeleteBookOnClick(ActionEvent event) {

    }

    @FXML
    void btnDeleteMemberOnClick(ActionEvent event) {

    }

    @FXML
    void btnIssueBookOnClick(ActionEvent event) {

    }

    @FXML
    void btnReturnBookOnClick(ActionEvent event) {

    }

    @FXML
    void btnSearchBookOnClick(ActionEvent event) {

    }

    @FXML
    void btnSearchMemberOnClick(ActionEvent event) {

    }

    @FXML
    void btnUpdateBookOnClick(ActionEvent event) {

    }

    @FXML
    void btnUpdateMemberOnClick(ActionEvent event) {
        loadWindow("/view/update_member_form_view.fxml");
    }


    //-----------------------To load window when button pressed---------------------------------
    private void loadWindow(String fxmlPath){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxmlPath));
            windowsPane.getChildren().setAll(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
