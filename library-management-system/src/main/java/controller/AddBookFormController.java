package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import model.dto.BookDTO;
import service.ServiceFactory;
import service.custom.BookService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddBookFormController implements Initializable {

    @FXML
    private JFXButton btnAddBook;

    @FXML
    private JFXComboBox<String> cmbBookCategory;

    @FXML
    private Label lblBookId;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXTextField txtBookTitle;

    @FXML
    private JFXTextField txtIsbn;

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

        try {
            lblBookId.setText(String.valueOf(bookService.generateBookId())); //Generating new member id when user open add book window
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddBookOnClick(ActionEvent event) {
        if(!isFilled()){
            showAlert(Alert.AlertType.ERROR, "Please fill all the fields");
        }else if (!isValidAuthor()){
            showAlert(Alert.AlertType.ERROR, "Please enter valid author name");
        }else if(!isValidIsbn()){
            showAlert(Alert.AlertType.ERROR, "Please enter valid ISBN number");
        }else if(!isValidNoOfCopies()){
            showAlert(Alert.AlertType.ERROR, "Please enter valid number of copies");
        }else if(isExists()){
            showAlert(Alert.AlertType.ERROR, "This book already exists in the database");
        }else{
            BookDTO book = new BookDTO();

            book.setTitle(txtBookTitle.getText());
            book.setAuthor(txtAuthor.getText());
            book.setIsbn(txtIsbn.getText());
            book.setCategory(cmbBookCategory.getValue());
            book.setNoOfCopies(Integer.valueOf(txtNoOfCopies.getText()));

            try {
                Boolean isAdded = bookService.addBook(book);

                if(isAdded){
                    showAlert(Alert.AlertType.INFORMATION, "Book Added Successfully!");
                    clearFields();
                    lblBookId.setText(String.valueOf(bookService.generateBookId()));
                }else{
                    showAlert(Alert.AlertType.ERROR, "Book Ading Failed! Please Try again");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnClearFormOnClick(ActionEvent event) {

    }

    //---------------------------Validations----------------------------------------------

    //checking all the fields are filled

    Boolean isFilled(){
        return !txtBookTitle.getText().isEmpty() && !txtAuthor.getText().isEmpty() &&
                !txtIsbn.getText().isEmpty() && cmbBookCategory.getValue() != null &&
                !txtNoOfCopies.getText().isEmpty();
    }

    //Checking Author is Valid

    Boolean isValidAuthor(){
        return !txtAuthor.getText().matches(".*\\d.*"); //regex that matches any digit anywhere in the string
    }

    //checking ISBN is valid

    Boolean isValidIsbn(){
        return txtIsbn.getText().matches("^[0-9-]+$");
        //^ and $ representing start and end of the string
        //[0-9-]+ representing only digits(0-9) and hyphens(-) allowed one or more times. No letters allowed
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

    //--------------------------------Method for tigger alerts---------------------------------

    private void showAlert(Alert.AlertType alertType, String content){
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //------------------------------------Clear fields method------------------------------------

    private void clearFields(){
        txtBookTitle.setText("");
        txtAuthor.setText("");
        txtIsbn.setText("");
        cmbBookCategory.setValue("");
        txtNoOfCopies.setText("");
    }

    //-------------------------------Check book already exists---------------------------

    private Boolean isExists(){
        try {
            return bookService.isBookRegistered(txtIsbn.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
