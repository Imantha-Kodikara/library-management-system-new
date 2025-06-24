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
    void btnAddBookOnAction(ActionEvent event) {
        loadWindow("/view/add_book_form_view.fxml");
    }

    @FXML
    void btnAddMemberOnAction(ActionEvent event) {
        loadWindow("/view/add_member_form_view.fxml");
    }

    @FXML
    void btnDeleteBookOnAction(ActionEvent event) {
        loadWindow("/view/delete_book_form_view.fxml");
    }

    @FXML
    void btnDeleteMemberOnAction(ActionEvent event) {
        loadWindow("/view/delete_member_form_view.fxml");
    }

    @FXML
    void btnIssueBookOnAction(ActionEvent event) {
        loadWindow("/view/issue_book_form_view.fxml");
    }

    @FXML
    void btnReturnBookOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchBookOnAction(ActionEvent event) {
        loadWindow("/view/search_book_form_view.fxml");
    }

    @FXML
    void btnSearchMemberOnAction(ActionEvent event) {
        loadWindow("/view/search_member_form_view.fxml");
    }

    @FXML
    void btnUpdateBookOnAction(ActionEvent event) {
        loadWindow("/view/update_book_form_view.fxml");
    }

    @FXML
    void btnUpdateMemberOnAction(ActionEvent event) {
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
