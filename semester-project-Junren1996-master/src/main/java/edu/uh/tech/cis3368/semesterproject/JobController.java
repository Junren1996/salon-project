package edu.uh.tech.cis3368.semesterproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Controller
public class JobController implements Initializable {

    @Autowired
    private JobService jobService;

    @FXML
    private TextField jobName;
    @FXML
    private TextField jobDescription;
    @FXML
    private TextField customerName;
    @FXML
    private TextField customerAddress;
    @FXML
    private TextField customerPhone;

    private Scene returnScene;
    @Autowired
    private JobRepository jobRepository;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    public void handleDone(ActionEvent actionEvent) {
        var stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(returnScene);
        jobService.createJobWithCustomer(jobName.getText(),
                jobDescription.getText(),
                customerName.getText(),
                customerAddress.getText(),
                customerPhone.getText());
        Job job = new Job();
        job.setName(jobName.getText());
        job.setDescription(jobDescription.getText());
        job.reportService.setStartDate(LocalDate.now());
        try {
            jobRepository.save(job);
        }catch (DataIntegrityViolationException e){

        }
    }

    public void handleCancel(ActionEvent actionEvent) {

    }

   public void setReturnScene(Scene scene) {
        this.returnScene = scene;
    }
    public void handleDone1(ActionEvent actionEvent) {
        var stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(returnScene);

    }

}
