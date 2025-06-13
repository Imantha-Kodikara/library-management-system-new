package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.dto.BookDTO;
import service.ServiceFactory;
import service.custom.BookService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateBookFormController implements Initializable {

    @FXML
    private JFXButton btnUpdateBook;

    @FXML
    private JFXComboBox<String> cmbBookCategory;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXTextField txtBookTitle;

    @FXML
    private JFXTextField txtBookId;

    @FXML
    private TextField txtIsbn;

    @FXML
    private JFXTextField txtNoOfCopies;

    BookService bookService = ServiceFactory.getInstance().getServiceType(ServiceType.BOOK);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbBookCategory.setItems(FXCollections.observableArrayList("Fiction", "Non-Fiction", "Science", "Technology", "Biography",
                "History", "Children", "Fantasy", "Mystery", "Romance",
                "Horror", "Comics", "Poetry", "Adventure", "Education",
                "Programming", "Web Development", "Data Science", "Machine Learning",
                "Cybersecurity", "Software Engineering", "Artificial Intelligence",
                "Mobile Development", "Game Development", "DevOps")); //setting the values of combo box

        btnUpdateBook.setDisable(true);
        txtBookId.setEditable(false);
    }

    @FXML
    void btnSearchBookOnClick(ActionEvent event) {
        try {
            BookDTO book = bookService.searchByIsbn(txtIsbn.getText());

            if(book != null){
                txtBookId.setText(String.valueOf(book.getId()));
                txtBookTitle.setText(book.getTitle());
                txtAuthor.setText(book.getAuthor());
                cmbBookCategory.setValue(book.getCategory());
                txtNoOfCopies.setText(String.valueOf(book.getNoOfCopies()));

                btnUpdateBook.setDisable(false);
            }else{
                showAlert(Alert.AlertType.ERROR, "Book Not Found in the database!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateBookOnClick(ActionEvent event) {
        if(!isFilled()){
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
        }else if (!isValidAuthor()){
            showAlert(Alert.AlertType.ERROR, "Please enter valid author name");
        }else if(!isValidNoOfCopies()){
            showAlert(Alert.AlertType.ERROR, "Please enter valid number of copies");
        }else{
            BookDTO book = new BookDTO();
            book.setId(Integer.valueOf(txtBookId.getText()));
            book.setTitle(txtBookTitle.getText());
            book.setAuthor(txtAuthor.getText());
            book.setIsbn(txtIsbn.getText());
            book.setCategory(cmbBookCategory.getValue());
            book.setNoOfCopies(Integer.valueOf(txtNoOfCopies.getText()));

            try {
                Boolean isUpdated = bookService.update(book);

                if(isUpdated){
                    showAlert(Alert.AlertType.INFORMATION, "Book Updated Successfully!");
                    clearFields();

                }else{
                    showAlert(Alert.AlertType.ERROR, "Book Updated Failed! Please Try again");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    //--------------------------------Method for tigger alerts---------------------------------

    private void showAlert(Alert.AlertType alertType, String content){
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //---------------------------Validations----------------------------------------------

    //checking all the fields are filled

    Boolean isFilled(){
        return !txtBookTitle.getText().isEmpty() && !txtAuthor.getText().isEmpty() &&
                cmbBookCategory.getValue() != null &&
                !txtNoOfCopies.getText().isEmpty();
    }

    //Checking Author is Valid

    Boolean isValidAuthor(){
        return !txtAuthor.getText().matches(".*\\d.*"); //regex that matches any digit anywhere in the string
    }


    //Checking no of copies are valid

    Boolean isValidNoOfCopies(){
        try{
            int number = Integer.parseInt(txtNoOfCopies.getText()); //Convert string to int
            return number > 0;
        }catch(NumberFormatException ex){ //If try block fails(contains letters or symbols), it throws NumberFormatException
            return false;
        }
    }

    //-------------------------------Check book already exists---------------------------

    private Boolean isExists(){
        try {
            return bookService.isBookRegistered(txtIsbn.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //------------------------------------Clear fields method------------------------------------

    private void clearFields(){
        txtBookId.setText("");
        txtBookTitle.setText("");
        txtAuthor.setText("");
        txtIsbn.setText("");
        cmbBookCategory.setValue("");
        txtNoOfCopies.setText("");
    }


}
