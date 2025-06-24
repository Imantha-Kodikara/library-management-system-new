package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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

public class DeleteBookFormController implements Initializable {

    @FXML
    private JFXButton btnDeleteBook;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXTextField txtBookId;

    @FXML
    private JFXTextField txtBookTitle;

    @FXML
    private JFXTextField txtCategory;

    @FXML
    private TextField txtIsbn;

    @FXML
    private JFXTextField txtNoOfCopies;

    BookService bookService = ServiceFactory.getInstance().getServiceType(ServiceType.BOOK);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDeleteBook.setDisable(true);

        txtBookTitle.setEditable(false);
        txtCategory.setEditable(false);
        txtAuthor.setEditable(false);
        txtBookId.setEditable(false);
        txtNoOfCopies.setEditable(false);
    }

    @FXML
    void btnDeleteBookOnAction(ActionEvent event) {

        try {
            Boolean isDeleted = bookService.deleteById(Integer.valueOf(txtBookId.getText()));

            System.out.println(isDeleted);
            if(isDeleted){
                showAlert(Alert.AlertType.INFORMATION, "Book Deleted Successfully!");
                clearFields();
                btnDeleteBook.setDisable(true);
            }else{
                showAlert(Alert.AlertType.ERROR, "Book Deleting Failed! Please Try again");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnSearchBookOnAction(ActionEvent event) {
        try {
            BookDTO book = bookService.searchByIsbn(txtIsbn.getText());

            if(book != null){
                txtBookId.setText(String.valueOf(book.getId()));
                txtBookTitle.setText(book.getTitle());
                txtAuthor.setText(book.getAuthor());
                txtCategory.setText(book.getCategory());
                txtNoOfCopies.setText(String.valueOf(book.getNoOfCopies()));

                btnDeleteBook.setDisable(false);

            }else{
                showAlert(Alert.AlertType.ERROR, "Book Not Found in the database!");
                clearFields();
                btnDeleteBook.setDisable(true);
            }
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

    //------------------------------------Clear fields method------------------------------------

    private void clearFields(){
        txtBookId.setText("");
        txtBookTitle.setText("");
        txtAuthor.setText("");
        txtIsbn.setText("");
        txtCategory.setText("");
        txtNoOfCopies.setText("");
    }



}
