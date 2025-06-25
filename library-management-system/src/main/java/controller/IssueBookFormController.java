package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.dto.BookDTO;
import model.dto.IssuedBookDTO;
import model.dto.MemberDTO;
import service.ServiceFactory;
import service.custom.BookService;
import service.custom.IssuedBooksService;
import service.custom.MemberService;
import util.ServiceType;

import java.net.Socket;
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
    private Label lblAuthor;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblMembershipDate;

    @FXML
    private Label lblNic;

    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER);
    BookService bookService = ServiceFactory.getInstance().getServiceType(ServiceType.BOOK);
    IssuedBooksService issuedBooksService = ServiceFactory.getInstance().getServiceType(ServiceType.ISSUEDBOOK);

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
        Integer borrowedBooksCount = Integer.valueOf(lblBorowedBooksCount.getText());

        if(borrowedBooksCount < 3){
            IssuedBookDTO issuedBook = new IssuedBookDTO();

            issuedBook.setMemberId((Integer) cmbSelectMemberId.getValue());
            issuedBook.setBookId(Integer.valueOf(lblBookId.getText()));
            issuedBook.setIssuedDate(dateOfBookIssuing.getValue());

            try {
                Boolean isIssed = issuedBooksService.isIssued(issuedBook);

                if(isIssed){
                    showAlert(Alert.AlertType.INFORMATION, "Book Issued SuccessFully!");
                    clearFields();
                    btnIssueBook.setDisable(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }else{
            showAlert(Alert.AlertType.ERROR, "Borrowing limit exceeded: A member can borrow a maximum of 3 books at a time.");
        }
    }

    @FXML
    void btnShowDetailsOnAction(ActionEvent event) {
        if(isFilled()){
            Integer selectedMemberId = (Integer) cmbSelectMemberId.getValue(); // Get selected value from select member id combo box
            try {
                MemberDTO member = memberService.searchById(selectedMemberId);

                lblFirstName.setText(member.getFirstName());
                lblLastName.setText(member.getLastName());
                lblAddress.setText(member.getAddress());
                lblNic.setText(member.getNic());
                lblEmail.setText(member.getEmail());
                lblMembershipDate.setText(String.valueOf(member.getMembershipDate()));

                BookDTO book = bookService.findBookByTitle((String) cmbSelectBookTitle.getValue());

                lblBookId.setText(String.valueOf(book.getId()));
                lblCategory.setText(book.getCategory());
                lblAvailableCopies.setText(String.valueOf(book.getNoOfCopies()));
                lblAuthor.setText(book.getAuthor());
                lblIsbn.setText(book.getIsbn());

                String borrowedBooksCount = String.valueOf(memberService.findBorrowedBooksCount(member.getId()));
                lblBorowedBooksCount.setText(borrowedBooksCount);

                btnIssueBook.setDisable(false);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
        }
    }

    Boolean isFilled(){
        return cmbSelectMemberId.getValue() != null && cmbSelectBookTitle.getValue() != null && dateOfBookIssuing.getValue() != null;
    }

    //--------------------------------Method for tigger alerts---------------------------------

    private void showAlert(Alert.AlertType alertType, String content){
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //------------------------------------Clear fields-------------------------------------------

    private void clearFields(){
        lblIsbn.setText("-");
        lblAuthor.setText("-");
        lblCategory.setText("-");
        lblBookId.setText("-");
        lblEmail.setText("-");
        lblBorowedBooksCount.setText("-");
        lblAvailableCopies.setText("-");
        lblMembershipDate.setText("-");
        lblNic.setText("-");
        lblAddress.setText("-");
        lblLastName.setText("-");
        lblFirstName.setText("-");

    }
}
