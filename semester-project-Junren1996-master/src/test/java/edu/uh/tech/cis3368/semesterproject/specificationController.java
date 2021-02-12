package edu.uh.tech.cis3368.semesterproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Convert;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class specificationController implements Initializable {



    @FXML
    private CheckBox component1;
    @FXML
    private CheckBox component2;
    @FXML
    private CheckBox component3;
    @FXML
    private CheckBox component4;
    @FXML
    private CheckBox component5;
    @FXML
    private CheckBox component6;
    @FXML
    private TextField amount;
    @FXML
    ComboBox<Integer> comboBox1 = new ComboBox<Integer>();
    @FXML
    ComboBox<Integer> comboBox2 = new ComboBox<Integer>();
    @FXML
    ComboBox<Integer> comboBox3 = new ComboBox<Integer>();
    @FXML
    ComboBox<Integer> comboBox4 = new ComboBox<Integer>();
    @FXML
    ComboBox<Integer> comboBox5 = new ComboBox<Integer>();
    @FXML
    ComboBox<Integer> comboBox6 = new ComboBox<Integer>();
    public ListView<Job> JobList;
    @Autowired
    private JobRepository jobR;


    private Scene returnScene;
    double total=0.0;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        ObservableList<Job> observableList = FXCollections.observableArrayList();
        ObservableList<Integer> list = FXCollections.observableArrayList
                (1,2,3,4,5,6);
        comboBox1.setItems(list);
        comboBox2.setItems(list);
        comboBox3.setItems(list);
        comboBox4.setItems(list);
        comboBox5.setItems(list);
        comboBox6.setItems(list);

        jobR.findAll().forEach(observableList::add);
        JobList.setItems(observableList);
        /*JobList.getSelectionModel().selectedItemProperty().addListener((observable,oldval,newval) ->{
            Job job= newval;
            if (job != null){

            }
        }*/
    }

    public void setReturnScene(Scene scene) {
        this.returnScene = scene;
    }

    @FXML
    public void calButton(ActionEvent actionEvent){
        int n =0;

        if(component1.isSelected())
            n = comboBox1.getValue();
            total += 100*n;
        if(component2.isSelected())
            n = comboBox2.getValue();
            total += 200*n;
        if(component3.isSelected())
            n = comboBox3.getValue();
            total += 300*n;
        if(component4.isSelected())
            n = comboBox4.getValue();
            total += 400*n;
        if(component5.isSelected())
            n = comboBox5.getValue();
            total += 500*n;
        if(component6.isSelected())
            n = comboBox6.getValue();
            total += 600*n;

        String s;
        s = String.valueOf(total);
        amount.setText(s);

    }
    
    public void completeButton(ActionEvent actionEvent){
        
    }

}
