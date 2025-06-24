package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SearchBookFormController {

    @FXML
    private JFXComboBox<?> cmbBookCategory;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXTextField txtBookId;

    @FXML
    private JFXTextField txtBookTitle;

    @FXML
    private TextField txtIsbn;

    @FXML
    private JFXTextField txtNoOfCopies;

    @FXML
    void btnSearchBookOnAction(ActionEvent event) {

    }

}
