package controller;

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

public class SearchBookFormController implements Initializable {

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
        txtBookId.setEditable(false);
        txtBookTitle.setEditable(false);
        txtAuthor.setEditable(false);
        txtCategory.setEditable(false);
        txtNoOfCopies.setEditable(false);
    }

    @FXML
    void btnSearchBookOnAction(ActionEvent event) {
        if(!txtIsbn.getText().isEmpty()){
            try {
                BookDTO book = bookService.searchByIsbn(txtIsbn.getText());

                if(book != null){
                    txtBookTitle.setText(book.getTitle());
                    txtAuthor.setText(book.getAuthor());
                    txtBookId.setText(String.valueOf(book.getId()));
                    txtCategory.setText(book.getCategory());
                    txtNoOfCopies.setText(String.valueOf(book.getNoOfCopies()));
                }else{
                    showAlert(Alert.AlertType.ERROR, "Book not found in Database!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            showAlert(Alert.AlertType.ERROR, "Please enter ISBN number");
        }
    }

    //--------------------------------Method for tigger alerts---------------------------------

    private void showAlert(Alert.AlertType alertType, String content){
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
