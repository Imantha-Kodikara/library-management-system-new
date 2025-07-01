package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.dto.IssuedBookDTO;
import model.dto.MemberDTO;
import model.dto.ReturnBookDTO;
import service.ServiceFactory;
import service.custom.IssuedBooksService;
import service.custom.MemberService;
import service.custom.ReturnBookService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

public class ReturnBookFormController implements Initializable {

    @FXML
    private JFXButton btnReturnBook;

    @FXML
    private JFXComboBox cmbSelectBookTitle;

    @FXML
    private JFXComboBox cmbSelectMemberId;

    @FXML
    private DatePicker dateOfReturning;

    @FXML
    private Label lblBookIssuedate;

    @FXML
    private Label lblFine;

    @FXML
    private Label lblMember;


    IssuedBooksService issuedBooksService = ServiceFactory.getInstance().getServiceType(ServiceType.ISSUEDBOOK);
    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER);
    ReturnBookService returnBookService = ServiceFactory.getInstance().getServiceType(ServiceType.RETURNBOOK);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnReturnBook.setDisable(true);

        cmbSelectMemberId.setItems(getIssuedBooksMembersId());

        cmbSelectMemberId.setOnAction(event -> {
            Integer selectedMemberId = (Integer) cmbSelectMemberId.getValue();
            if(selectedMemberId != null){
                try {
                    cmbSelectBookTitle.setItems(issuedBooksService.getIssuedBooksTitles(selectedMemberId));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void btnReturnBookOnAction(ActionEvent event) {
        try {
            IssuedBookDTO issuedBookDTO = issuedBooksService.getIssuedBook((Integer) cmbSelectMemberId.getValue(), (String) cmbSelectBookTitle.getValue());

            ReturnBookDTO returnBookDTO = new ReturnBookDTO(
                    issuedBookDTO.getIssuedId(),
                    issuedBookDTO.getMemberId(),
                    issuedBookDTO.getBookId(),
                    calculateFine(issuedBookDTO.getIssuedDate()),
                    dateOfReturning.getValue(),
                    "Returned"
            );

           Boolean isUpdated = returnBookService.update(returnBookDTO);

           if(isUpdated){
               showAlert(Alert.AlertType.INFORMATION, "Book returned successfully!");
               btnReturnBook.setDisable(true);
           }else{
               showAlert(Alert.AlertType.ERROR, "Book Returned Failed! Please try again");
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnShowDetailsOnAction(ActionEvent event) {
        if(isFilled()){
            fillLables();
            btnReturnBook.setDisable(false);
        }else{
            showAlert(Alert.AlertType.ERROR, "Please select above fields!");
        }
    }

    ObservableList<Integer>getIssuedBooksMembersId(){
        try {
            return issuedBooksService.getIssuedBooksMembersId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Boolean isFilled(){
        return cmbSelectMemberId.getValue() != null && cmbSelectBookTitle.getValue() != null && dateOfReturning.getValue() != null;
    }

    void fillLables(){
        try {
            IssuedBookDTO issuedBookDTO = issuedBooksService.getIssuedBook((Integer) cmbSelectMemberId.getValue(), (String) cmbSelectBookTitle.getValue());
            MemberDTO memberDTO = memberService.searchById((Integer) cmbSelectMemberId.getValue());

            lblMember.setText(memberDTO.getFirstName()+" "+memberDTO.getLastName());
            lblBookIssuedate.setText(String.valueOf(issuedBookDTO.getIssuedDate()));
            lblFine.setText(String.valueOf(calculateFine(issuedBookDTO.getIssuedDate())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------------------------Method for tigger alerts---------------------------------

    private void showAlert(Alert.AlertType alertType, String content){
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.showAndWait();
    }

    Double calculateFine(LocalDate issuedDate){
        Integer daysBetween = (int) ChronoUnit.DAYS.between(issuedDate, dateOfReturning.getValue());
        return daysBetween > 14 ? (daysBetween - 14)*10 : 0.0;
    }





}
