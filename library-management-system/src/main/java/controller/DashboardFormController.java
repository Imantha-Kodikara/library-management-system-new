package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import service.ServiceFactory;
import service.custom.BookService;
import service.custom.IssuedBooksService;
import service.custom.MemberService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshDashboard();
    }

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
    void btnIssueBookOnAction(ActionEvent event) {loadWindow("/view/issue_book_form_view.fxml");}

    @FXML
    void btnReturnBookOnAction(ActionEvent event) {
        loadWindow("/view/return_book_form_view.fxml");}

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

    @FXML
    void btnRefreshDashboardOnAction(ActionEvent actionEvent) {
        refreshDashboard();
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

    BookService bookService = ServiceFactory.getInstance().getServiceType(ServiceType.BOOK);
    IssuedBooksService issuedBooksService = ServiceFactory.getInstance().getServiceType(ServiceType.ISSUEDBOOK);
    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER);

    //----------------------------Refresh Dashboard method(To add the values to lables)------------------
    private void refreshDashboard(){
        try {
            Integer totalBooks = bookService.getTotalBooks(); //Get total books count from db
            lblTotalBooks.setText(String.valueOf(totalBooks));//Set total books count to the total books label

            Integer totalIssuedBooks = issuedBooksService.getTotalIssuedBooks(); //Get total issued books count from db
            lblIssuedBooks.setText(String.valueOf(totalIssuedBooks));//set total issued books count to the total issued books label

            Integer totalRemainingBooks = totalBooks - totalIssuedBooks;
            lblRemainingBooks.setText(String.valueOf(totalRemainingBooks));

            Integer totalMembers = memberService.getTotalMembers();
            lblTotalMembers.setText(String.valueOf(totalMembers));

            Integer totalFine = issuedBooksService.getTotalFine();
            lblFineCollected.setText(String.valueOf(totalFine));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
