package edu.uh.tech.cis3368.semesterproject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.stage.Stage;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.ButtonType.OK;

public class moveProductController implements Initializable{

    private static final String DELETE_MESSAGE = "Are you sure you want to move this?";

    public TableView<Job> jobTableView;
    public TableColumn<Job,String> col1 = new TableColumn<>();
    public TableColumn<Job,Integer> col2 = new TableColumn<>();
    public TextArea textArea1;
    public TextArea textArea2;
    public TextArea textArea3;
    private Scene returnScene;
    @Autowired
    private JobStageRepository jobStageRepository;
    @Autowired
    private  JobRepository jobRepository;

    public void movetoProduct(MouseEvent mouseEvent) {
        Dragboard dragboard = jobTableView.startDragAndDrop((TransferMode.COPY_OR_MOVE));
        ClipboardContent clipboardContent = new ClipboardContent();
        dragboard.setContent(clipboardContent);
        mouseEvent.consume();

    }

    public void dragOver(DragEvent dragEvent){
        Dragboard dragboard = dragEvent.getDragboard();
        if(dragboard.hasContent(DataFormat.PLAIN_TEXT)){
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

    }

    public void dragtoTarget(DragEvent dragEvent){
        Dragboard dragboard = dragEvent.getDragboard();
        var dragComplete = false;
        if(dragboard.hasContent(DataFormat.PLAIN_TEXT)){
            Job job1 = jobTableView.getSelectionModel().getSelectedItem();
            job1.jobStage.setOrdinal(1);
            col2.setCellValueFactory(c-> new SimpleObjectProperty<>(c.getValue().getJobStage().getOrdinal()));
            jobTableView.refresh();
            dragComplete = true;
        }
        dragEvent.setDropCompleted(dragComplete);
        dragEvent.consume();
    }
    public void dragtoTarget2(DragEvent dragEvent){
        Dragboard dragboard = dragEvent.getDragboard();
        var dragComplete = false;
        if(dragboard.hasContent(DataFormat.PLAIN_TEXT)){
            Job job1 = jobTableView.getSelectionModel().getSelectedItem();
            job1.jobStage.setOrdinal(2);
            col2.setCellValueFactory(c-> new SimpleObjectProperty<>(c.getValue().getJobStage().getOrdinal()));
            jobTableView.refresh();
            dragComplete = true;
        }
        dragEvent.setDropCompleted(dragComplete);
        dragEvent.consume();
    }
    public void dragtoTarget3(DragEvent dragEvent){
        Dragboard dragboard = dragEvent.getDragboard();
        var dragComplete = false;
        if(dragboard.hasContent(DataFormat.PLAIN_TEXT)){
            Job job1 = jobTableView.getSelectionModel().getSelectedItem();
            job1.jobStage.setOrdinal(3);
            col2.setCellValueFactory(c-> new SimpleObjectProperty<>(c.getValue().getJobStage().getOrdinal()));
            jobTableView.refresh();
            dragComplete = true;
        }
        dragEvent.setDropCompleted(dragComplete);
        dragEvent.consume();
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
       ObservableList<Job> observableList = FXCollections.observableArrayList();
        jobRepository.findAll().forEach(observableList::add);
        jobTableView.setItems(observableList);
        jobTableView.getSelectionModel().selectedItemProperty().addListener((observable,oldval,newval) -> {
            Job job = newval;
            if (job!= null) {
                jobTableView.refresh();
            }
        });
        jobTableView.getSelectionModel().clearSelection();
        col1.setCellValueFactory(new PropertyValueFactory<Job,String>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<Job,Integer>("jobstage.ordinal"));
        }

    public void setReturnScene(Scene scene) {
        this.returnScene = scene;
    }
    public void handleDone1(ActionEvent actionEvent) {
        var stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(returnScene);

    }
    }


