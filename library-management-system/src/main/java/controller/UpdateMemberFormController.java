package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.dto.MemberDTO;
import service.ServiceFactory;
import service.custom.MemberService;
import util.ServiceType;

import javax.sound.midi.Soundbank;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateMemberFormController implements Initializable {

    @FXML
    private JFXButton btnUpdateMember;

    @FXML
    private DatePicker dateOfMembership;

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
    private TextField txtMembershipId;

    @FXML
    private JFXTextField txtNic;

    MemberService memberService = ServiceFactory.getInstance().getServiceType(ServiceType.MEMBER); //creating reference from member service(De-coupling)

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnUpdateMember.setDisable(true);
    }

    @FXML
    void btnSearchMemberOnClick(ActionEvent event) {

        try {
            MemberDTO member = memberService.searchById(Integer.valueOf(txtMembershipId.getText()));
            if(member == null){
                showAlert(Alert.AlertType.ERROR, "Member not found!");
                txtMembershipId.setText("");
            }else{
                //------------------------Setting member attributes values to the text fields-------------------------

                txtFirstName.setText(member.getFirstName());
                txtLastName.setText(member.getLastName());
                txtAddress.setText(member.getAddress());
                txtEmail.setText(member.getEmail());
                txtContactNumber.setText(member.getContactNumber());
                txtNic.setText(member.getNic());
                dateOfMembership.setValue(member.getMembershipDate());

                btnUpdateMember.setDisable(false); //enable btnUpdate
            }
        } catch (NumberFormatException ex){
            showAlert(Alert.AlertType.ERROR, "Please enter valid numeric membership ID");
            txtMembershipId.setText("");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateMemberOnClick(ActionEvent event) {
        if(!isFilled()){
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
            return;
        }else if(!isValidEmail()){
            showAlert(Alert.AlertType.ERROR, "Please enter valid email address");
            return;
        }else if(!isValidNic()){
            showAlert(Alert.AlertType.ERROR, "Please enter valid NIC");
            return;
        }else if (!isValidContactNumber()){
            showAlert(Alert.AlertType.ERROR, "Please enter valid contact number");
            return;
        }else {
            MemberDTO updatedMember= new MemberDTO();

            updatedMember.setId(Integer.valueOf(txtMembershipId.getText()));
            updatedMember.setFirstName(txtFirstName.getText());
            updatedMember.setLastName(txtLastName.getText());
            updatedMember.setAddress(txtAddress.getText());
            updatedMember.setEmail(txtEmail.getText());
            updatedMember.setContactNumber(txtContactNumber.getText());
            updatedMember.setNic(txtNic.getText());
            updatedMember.setMembershipDate(dateOfMembership.getValue());

            try {
                Boolean isupdated = memberService.update(updatedMember);

                if(isupdated){
                    showAlert(Alert.AlertType.INFORMATION, "Member Updated successfully!");
                    clearTextFields();
                    btnUpdateMember.setDisable(true);
                }else{
                    showAlert(Alert.AlertType.ERROR, "Member Update failed! Please try again");
                    clearTextFields();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
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


    //--------------------------------Method for tigger alerts---------------------------------

    private void showAlert(Alert.AlertType alertType, String content){
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //-----------------------------Clearing all the text fields------------------------------
    private void clearTextFields(){
        txtMembershipId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtNic.setText("");
        txtContactNumber.setText("");
        dateOfMembership.setValue(null);
    }



}
