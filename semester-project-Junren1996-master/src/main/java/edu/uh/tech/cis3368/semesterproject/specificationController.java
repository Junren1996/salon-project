package edu.uh.tech.cis3368.semesterproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Convert;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.ButtonType.OK;

public class specificationController implements Initializable {

    private static final String MESSAGES = "Are you sure to assign this component to this job? ";
    Stage window;
    Scene scene;
    Button button;
    Text txt;
    TextField tf;

    public ListView<Component> ComponentListView;
    public ListView<Job> jobList;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ComponentRepository componentRepository;
    private BigDecimal total =new BigDecimal(0);
    private Scene returnScene;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        ObservableList<Job> observableList = FXCollections.observableArrayList();
        jobRepository.findAll().forEach(observableList::add);
        jobList.setItems(observableList);
        jobList.getSelectionModel().selectedItemProperty().addListener((observable,oldval,newval) -> {
            Job job = newval;
            if (job!= null) {
                jobList.refresh();
            }
        });
        jobList.getSelectionModel().clearSelection();

        ObservableList<Component> observableList2 = FXCollections.observableArrayList();
        componentRepository.findAll().forEach(observableList2::add);
        ComponentListView.setItems(observableList2);
        ComponentListView.getSelectionModel().selectedItemProperty().addListener((observable,oldval,newval) -> {
            Component productComponent = newval;
            if (productComponent != null){
                ComponentListView.refresh();
            }
        });
        ComponentListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);



    }

    public void setReturnScene(Scene scene) {
        this.returnScene = scene;
    }

    @FXML
    public void calButton(){
        ObservableList<Component> lists;
        lists =  ComponentListView.getSelectionModel().getSelectedItems();
        for(Component s: lists){

            if(lists != null) {
                BigDecimal i = new BigDecimal(0);
                       i = s.getWholesalePrice();
                total.add(i);
            }
        }
    }

    public void AssignButton(ActionEvent actionEvent){
        Job job11 = jobList.getSelectionModel().getSelectedItem();
        ComponentListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Component> clists = new ArrayList<>();
        clists.addAll(ComponentListView.getSelectionModel().getSelectedItems());
        ObservableList<Component> observableList2 = FXCollections.observableArrayList();
        observableList2.addAll(clists);
        txt.setText(job11.getName());
        ListView<Component> listView = new ListView<>();
        listView.getItems().addAll(observableList2);
        window.setTitle("Assign Components to Job");
        button = new Button("Submit");
        button.setOnAction(e -> Click(job11,clists));
        calButton();
        Button button2 = new Button("Calculate!");
        button2.setOnAction(e -> Click2());
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(40,40,40,40));
        layout.getChildren().addAll(txt,listView,button2,tf,button);

        scene = new Scene(layout,450,300);
        window.setScene(scene);
        window.show();

        
    }

    private void Click(Job job,List<Component> components){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, MESSAGES);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == OK) {
            Product product = new Product();
            String name = job.getName();
            product.setName(name + "'s Product");
            product.setComponents(components);
            product.setDescription(job.getName() + " " + job.getDescription()+" Price: "+total.toString());
            job.setProduct(product);
        }
        else{

        }

    }
    private void Click2(){
        tf.setText(total.toString());
    }


    public void handleDone1(javafx.event.ActionEvent actionEvent) {
        var stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(returnScene);

    }
}
