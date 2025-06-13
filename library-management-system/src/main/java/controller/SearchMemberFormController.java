package controller;

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

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchMemberFormController implements Initializable {

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
        txtFirstName.setEditable(false);
        txtLastName.setEditable(false);
        txtAddress.setEditable(false);
        txtEmail.setEditable(false);
        txtContactNumber.setEditable(false);
        txtNic.setEditable(false);
        dateOfMembership.setDisable(true);
    }

    @FXML
    void btnSearchMemberOnClick(ActionEvent event) {
        try {
            MemberDTO member = memberService.searchById(Integer.valueOf(txtMembershipId.getText()));
            if(member == null){
                showAlert(Alert.AlertType.ERROR, "Member not found!");
                clearTextFields();

            }else{
                //------------------------Setting member attributes values to the text fields-------------------------

                txtFirstName.setText(member.getFirstName());
                txtLastName.setText(member.getLastName());
                txtAddress.setText(member.getAddress());
                txtEmail.setText(member.getEmail());
                txtContactNumber.setText(member.getContactNumber());
                txtNic.setText(member.getNic());
                dateOfMembership.setValue(member.getMembershipDate());

            }
        } catch (NumberFormatException ex){
            showAlert(Alert.AlertType.ERROR, "Please enter valid numeric membership ID");
            clearTextFields();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
