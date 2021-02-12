package edu.uh.tech.cis3368.semesterproject;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static javafx.application.Application.launch;


@SpringBootApplication
public class SemesterProjectApplication {
    private ConfigurableApplicationContext springContext;
    private Parent root;



    public static void main(String[] args) {

        launch(args);
    }

    public void init() throws Exception{
        springContext = SpringApplication.run(SemesterProjectApplication.class);
        FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("main.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        root = fxmlLoader.load();


        springContext.getAutowireCapableBeanFactory().autowireBean(this);

    }

    public void start(Stage primaryStage) throws Exception{
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    }


