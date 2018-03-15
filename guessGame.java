package demoJavaFX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.text.LabelView;
import java.util.Random;

public class guessGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button btn1 = new Button("main click");
        Button btn2 = new Button("alternative click");

        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("button 1 was clicked");
            }
        });

        btn2.setOnAction(
                (event) -> {
                    System.out.println("button 2 was clicked");
                }
        );

        HBox hb = new HBox();
        hb.setSpacing(10);
        hb.setPadding(new Insets(5,10,5,10));
        hb.getChildren().addAll(btn1,btn2);

        Text sceneTitle = new Text("Guess Game");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD,20) );

        Label input = new Label("Pleas guess number: ");
        TextField userText = new TextField();
        Label userNum = new Label("0");
        Button submit = new Button("Submit");

        ListView<Label> prevGuesses = new ListView<>();
        VBox vbRight = new VBox();
        vbRight.setPadding(new Insets(5,50,10,10));
        vbRight.getChildren().add(prevGuesses);

        Label userShowText = new Label("Previous user guess:");
        Label showHighLow = new Label("=");
        Label unknownNum = new Label("?");


        HBox prevUserGuessRow = new HBox();
        prevUserGuessRow.setSpacing(15);
        prevUserGuessRow.getChildren().addAll(userShowText,userNum, showHighLow, unknownNum);

        Label wonLoseResult = new Label();
        wonLoseResult.setPadding(new Insets(15,10,5,5));
        wonLoseResult.setFont(Font.font("Tahoma",FontWeight.BOLD,12));

        VBox userInteraction = new VBox();
        userInteraction.setPadding(new Insets(10, 10,15,10));
        userInteraction.setSpacing(5);
        userInteraction.getChildren().addAll(sceneTitle,input, userText, submit, prevUserGuessRow, wonLoseResult);

        BorderPane bp = new BorderPane();
        bp.setBottom(hb);
        //bp.setTop(sceneTitle);
        bp.setLeft(userInteraction);
        bp.setRight(vbRight);


        int randInt = randomNum(100);
        System.out.println(randInt);


        submit.setOnAction((event) -> {
            if (userText.getText() != null) {
                userNum.setText(userText.getText());
                Label guess = new Label(userText.getText());
                prevGuesses.getItems().add(guess);

                if (Integer.valueOf(userText.getText()) < randInt ) {
                    showHighLow.setText("<");
                } else if (Integer.valueOf(userText.getText()) > randInt) {
                    showHighLow.setText(">");
                } else {
                    showHighLow.setText("=");
                    unknownNum.setText(String.valueOf(randInt) );
                    wonLoseResult.setText("SUCCES, You found the unknown number!");
                }

            } else {
                userNum.setText("No new number from user");
            }
        } );




        Scene mainScene = new Scene(bp,600,400);

        primaryStage.setTitle("GUESS GAME!");
        primaryStage.setScene(mainScene);
        primaryStage.show();



        int round = 0;
        int limitRounds = 0;
        
        while (round < limitRounds) {

        }






    }

    public void round () {



    }

    public int  randomNum(int max) {
        Random rand = new Random();

        // if max not equals 0 returns true then max is returned else 100 is given
        //max = (max != 0) ? max : 100;

        int randInt = rand.nextInt(max);

        return randInt;
    }


}