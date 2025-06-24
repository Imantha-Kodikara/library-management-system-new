package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import service.ServiceFactory;
import service.custom.BookService;
import service.custom.MemberService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class IssueBookFormController implements Initializable {


    @FXML
    private JFXButton btnIssueBook;

    @FXML
    private JFXComboBox cmbSelectBookTitle;

    @FXML
    private JFXComboBox cmbSelectMemberId;

    @FXML
    private DatePicker dateOfBookIssuing;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblAvailableCopies;

    @FXML
    private Label lblBookId;

    @FXML
    private Label lblBorowedBooksCount;

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblIsbn;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblMembershipDate;

    @FXML
    private Label lblNic;

    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER);
    BookService bookService = ServiceFactory.getInstance().getServiceType(ServiceType.BOOK);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnIssueBook.setDisable(true);
        try {
            cmbSelectBookTitle.setItems(bookService.getAllBookTitles());
            cmbSelectMemberId.setItems(memberService.getMembersId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnIssueBookOnAction(ActionEvent event) {

    }

    @FXML
    void btnShowDetailsOnAction(ActionEvent event) {

    }
}
