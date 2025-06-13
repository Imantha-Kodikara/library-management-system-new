package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.dto.MemberDTO;
import service.ServiceFactory;
import service.custom.MemberService;
import util.ServiceType;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddMemberFormController implements Initializable {

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


    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER); //creating reference from member service(De-coupling)



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String membershipId = String.valueOf(memberService.generateMemberId());
            lblMembershipId.setText(membershipId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddMemberOnClick(ActionEvent event) {
        if(!isFilled()){
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
            return;
        }else if(!isValidEmail()){
            showAlert(Alert.AlertType.ERROR, "Please enter valid email address");
            return;
        }else if(!isValidNic()){
            showAlert(Alert.AlertType.ERROR, "Please enter valid NIC");
            return;
        }else if (!isValidContactNumber()) {
            showAlert(Alert.AlertType.ERROR, "Please enter valid contact number");
            return;
        }else if(isExists()){
            showAlert(Alert.AlertType.ERROR, "This member already exists in the system!");
            return;
        }else{
            MemberDTO member = new MemberDTO();

            member.setFirstName(txtFirstName.getText());
            member.setLastName(txtLastName.getText());
            member.setAddress(txtAddress.getText());
            member.setEmail(txtEmail.getText());
            member.setContactNumber(txtContactNumber.getText());
            member.setNic(txtNic.getText());
            member.setMembershipDate(dateOfMembership.getValue());


            try {
                Boolean isadded = memberService.addMember(member);
                if(isadded){
                    showAlert(Alert.AlertType.INFORMATION, "Member Added Successfully!");
                    clearTextFields();

                    lblMembershipId.setText(String.valueOf(memberService.generateMemberId()));
                }else{
                    showAlert(Alert.AlertType.ERROR, "Member adding failled! Please try again");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnClearFormOnClick(ActionEvent event) {
        clearTextFields();
    }

    //-----------------------------------------------Validations--------------------------------------------------------------

    //checking all the fields are filled

    private Boolean isFilled(){
        return !txtFirstName.getText().isEmpty() && !txtLastName.getText().isEmpty() &&
                !txtAddress.getText().isEmpty() && !txtEmail.getText() .isEmpty() &&
                !txtContactNumber.getText().isEmpty() && !txtNic.getText().isEmpty() &&
                dateOfMembership.getValue() != null;
    }

    //checking email contains @ and .

    private Boolean isValidEmail(){
        return txtEmail.getText().contains("@") && txtEmail.getText().contains(".");
    }

    //checking contact number valid or not

    private Boolean isValidContactNumber(){
        return txtContactNumber.getText().startsWith("0") && txtContactNumber.getText().length() == 10 &&
                txtContactNumber.getText().chars().allMatch(Character::isDigit); //checking all the character are digits
    }

    //checking NIC valid or not

    private Boolean isValidNic(){
        return txtNic.getText().length() == 9 &&
                txtNic.getText().chars().allMatch(Character::isDigit); //checking all the character are digits
    }

    //-----------------------------------------clear textfields------------------------------------------------------

    private void clearTextFields(){
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtNic.setText("");
        txtContactNumber.setText("");
    }

    //--------------------------------Method for tigger alerts---------------------------------

    private void showAlert(Alert.AlertType alertType, String content){
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //------------------------checking member already exists---------------------------------

    private Boolean isExists(){
        try {
            return memberService.isMemberRegistered(txtNic.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
