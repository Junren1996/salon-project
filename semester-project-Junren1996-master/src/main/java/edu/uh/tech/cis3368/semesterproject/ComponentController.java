package edu.uh.tech.cis3368.semesterproject;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.ButtonType.OK;


public class ComponentController implements Initializable{

    private static final String DELETE_MESSAGE = "Are you sure you want to delete this component?";

    public ListView<Component> ComponentList;
    public TextField Name;
    public TextField Price;
    public TextArea Description;
    @Autowired
    private ComponentRepository componentRepository;
    private List<TextField> fieldList;
    private Scene returnScene;

   public void addComponent(){

        Component component = new Component();
        component.setName(Name.getText());
        component.setWholesalePrice(BigDecimal.ONE);
        component.setDescription(Description.getText());
        try{
            componentRepository.save(component);
            ComponentList.getItems().add(component);
            clearFields();
            ComponentList.getSelectionModel().clearSelection();
            ComponentList.refresh();
        }catch (DataIntegrityViolationException e) {

        }

    }

    private void clearFields() {
        fieldList.forEach(textField -> textField.setText(null));
    }

    public void deleteEmployee(ActionEvent actionEvent) {
        Component component = ComponentList.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, DELETE_MESSAGE);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == OK) {
            componentRepository.delete(component);
            ComponentList.getItems().remove(component);
            clearFields();
            ComponentList.getSelectionModel().clearSelection();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Component> observableList = FXCollections.observableArrayList();
        componentRepository.findAll().forEach(observableList::add);
        ComponentList.setItems(observableList);
        ComponentList.getSelectionModel().selectedItemProperty().addListener((observable,oldval,newval) -> {
            Component component = newval;
            if (component != null) {
                Name.setText(component.getName());
                Description.setText(component.getDescription());
                BigDecimal i = component.getWholesalePrice();
                String s = i.toString();
                Price.setText(s);
                ComponentList.refresh();
            }
        });

        clearFields();
        ComponentList.getSelectionModel().clearSelection();

    }
    public void setReturnScene(Scene scene) {
        this.returnScene = scene;
    }
    public void handleDone1(ActionEvent actionEvent) {
        var stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(returnScene);

    }

}
