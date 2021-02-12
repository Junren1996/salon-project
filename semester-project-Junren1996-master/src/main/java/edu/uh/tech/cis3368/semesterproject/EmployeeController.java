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

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.ButtonType.OK;

@Controller
public class EmployeeController implements Initializable {

    private static final String NEW_EMPLOYEE = "New Employee";
    private static final String UPDATE_EMPLOYEE = "Update Employee";
    private static final String DUP_EMAIL_MESSAGE = "The email provided has already been used.";
    private static final String DUP_EMAIL_HEADER = "Duplicate Email Found";
    private static final String VALUES_MISSING_MESSAGE = "Please provide values for all fields";
    private static final String VALUES_MISSING_HEADER = "Required Fields Missing";
    private static final String DELETE_MESSAGE = "Are you sure you want to delete this employee?";

    public ListView<Employee> employeeList;
    public TextField phone;
    public TextField email;
    public TextField lastName;

    public TextField firstName;
    public Button btnAddEmployee;
    public Button btnDeleteEmployee;
    private Scene returnScene;
    private List<TextField> fieldList;

    @Autowired
    private EmployeeRepository employeeRepository;


    public void setReturnScene(Scene scene) {
        this.returnScene = scene;
    }

    public void handleAddUpdateButton(ActionEvent actionEvent) {
        // is an employee selected?
        if(employeeList.getSelectionModel().getSelectedItems().isEmpty()) {
            addNewEmployee();
        } else {
            // handle update case
            updateSelectedEmployee();

        }

    }

    private void updateSelectedEmployee() {
        Employee employee = employeeList.getSelectionModel().getSelectedItem();
        employee.setLastName(lastName.getText());
        employee.setFirstName(firstName.getText());
        employee.setEmail(email.getText());
        employee.setPhone(phone.getText());
        try {
            employeeRepository.saveAndFlush(employee);
            clearFields();
            btnAddEmployee.setText(NEW_EMPLOYEE);
            employeeList.getSelectionModel().clearSelection();
            employeeList.refresh();
        } catch (DataIntegrityViolationException e) {
            showFieldError(e);
        }
    }

    private void addNewEmployee() {
        Employee employee = new Employee();
        employee.setFirstName(firstName.getText());
        employee.setLastName(lastName.getText());
        employee.setPhone(phone.getText());
        employee.setEmail(email.getText());
        try {
            employeeRepository.saveAndFlush(employee);
            employeeList.getItems().add(employee);
            clearFields();
            employeeList.getSelectionModel().clearSelection();
            employeeList.refresh();
        } catch (DataIntegrityViolationException e) {
            showFieldError(e);
        }
    }

    private void showFieldError(DataIntegrityViolationException e) {
        // this is ONE way to handle this - there are other approaches that
        // use translation that might be cleaner
        var message = e.getMessage();
        String instructions = VALUES_MISSING_MESSAGE;
        String headerText = VALUES_MISSING_HEADER;
        if(message.contains("EMPLOYEE_EMAIL_UINDEX")){
            instructions = DUP_EMAIL_MESSAGE;
            headerText = DUP_EMAIL_HEADER;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, instructions);
        alert.setHeaderText(headerText);
        alert.show();
    }


    private void clearFields() {
        fieldList.forEach(textField -> textField.setText(null));
        btnDeleteEmployee.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Employee> observableList = FXCollections.observableArrayList();
        employeeRepository.findAll().forEach(observableList::add);
        employeeList.setItems(observableList);
        employeeList.getSelectionModel().selectedItemProperty().addListener((observable,oldval,newval) -> {
            Employee employee = newval;
            if (employee != null) {
                phone.setText(employee.getPhone());
                email.setText(employee.getEmail());
                firstName.setText(employee.getFirstName());
                lastName.setText(employee.getLastName());
                btnAddEmployee.setText(UPDATE_EMPLOYEE);
                btnDeleteEmployee.setDisable(false);
                employeeList.refresh();
            }
        });
        btnAddEmployee.setText(NEW_EMPLOYEE);
        fieldList = Arrays.asList(lastName, firstName, phone, email);
        clearFields();
        employeeList.getSelectionModel().clearSelection();

    }

    public void deleteEmployee(ActionEvent actionEvent) {
        Employee employee = employeeList.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, DELETE_MESSAGE);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == OK) {
            employeeRepository.delete(employee);
            employeeList.getItems().remove(employee);
            btnAddEmployee.setText(NEW_EMPLOYEE);
            clearFields();
            employeeList.getSelectionModel().clearSelection();
        }

    }

    public void handleMouseClicked(MouseEvent mouseEvent) {
        // only used for deselecting
        if(mouseEvent.isControlDown() || mouseEvent.isAltDown() || mouseEvent.isShiftDown()){
            employeeList.getSelectionModel().clearSelection();
            clearFields();
        }
    }

    public void handleClearSelection(ActionEvent actionEvent) {
        employeeList.getSelectionModel().clearSelection();
        btnAddEmployee.setText(NEW_EMPLOYEE);
        clearFields();
    }

    public void handleDone(ActionEvent actionEvent) {
        var stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(returnScene);

    }
}
