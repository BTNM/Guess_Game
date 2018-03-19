package demoJavaFX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;
import java.util.Random;

public class guessGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Pair<String, String> tempInput = intro();
        System.out.println("max value:"+tempInput.getKey());
        System.out.println("attempt number:"+tempInput.getValue());

        int randInt = Integer.valueOf(tempInput.getKey() );
        int attempts = Integer.valueOf(tempInput.getValue() );


        Button btn1 = new Button("main click");
        Button btn2 = new Button("alternative click");
        Button btn3 = new Button("Replay Game");

        btn3.setOnAction( (event -> {
            intro();
        }) );

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

        Label attemptText = new Label("Number of attempts left:");
        Label numAttempts = new Label(String.valueOf(attempts));
        Label userShowText = new Label("Previous user guess:");
        Label showHighLow = new Label("=");
        Label unknownNum = new Label("?");

        HBox attemptPart = new HBox();
        attemptPart.setSpacing(10);
        attemptPart.setPadding(new Insets(10));
        attemptPart.getChildren().addAll(attemptText,numAttempts);

        ListView<Label> prevGuesses = new ListView<>();
        VBox vbRight = new VBox();
        vbRight.setPadding(new Insets(5,50,10,10));
        vbRight.getChildren().addAll(attemptPart, prevGuesses);


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
        bp.setLeft(userInteraction);
        bp.setRight(vbRight);


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
            int aLeft = attempts-prevGuesses.getItems().size();
            numAttempts.setText(String.valueOf(aLeft ));
            if (aLeft <= 0) {
                wonLoseResult.setText("Game Over, You ran out of attempts!");
            }

        } );

        Scene mainScene = new Scene(bp,600,400);

        primaryStage.setTitle("GUESS GAME!");
        primaryStage.setScene(mainScene);
        primaryStage.show();




    }


    public Pair<String, String> intro() {
        // http://code.makery.ch/blog/javafx-dialogs-official/

//        TextInputDialog gameInput = new TextInputDialog();
//        gameInput.setTitle("Game Start");
//        gameInput.setHeaderText("Please input max limit for value, and amount tries!");
//
//        Optional<String> maxValue = gameInput.showAndWait();
//        if (maxValue.isPresent()) {
//            System.out.println(maxValue);
//        }
//
//        Optional<String> tries = gameInput.showAndWait();
//        tries.ifPresent(t -> System.out.println(t) );

        // create custom dialog
        Dialog<Pair<String, String>> gameInput = new Dialog<>();
        gameInput.setTitle("Game Start");
        gameInput.setHeaderText("Please input max limit for value, and amount tries!");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        gameInput.getDialogPane().getButtonTypes().addAll(loginButtonType,ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20)); // around element

        // labels and textfields
        TextField maxValue = new TextField();
        maxValue.setPromptText("Write max random value");
        Label mLabel = new Label("Max Value: ");

        TextField attempts = new TextField();
        attempts.setPromptText("Write number of attpemt per game");
        Label aLabel = new Label("Number of Attempts");

        grid.add(mLabel,0,0);
        grid.add(maxValue,1,0);

        grid.add(aLabel, 0,1);
        grid.add(attempts,1,1);

        gameInput.getDialogPane().setContent(grid);

        // Convert the result to a key-value-pair when the login button is clicked.
        gameInput.setResultConverter(t -> {
            if (t == loginButtonType) {
                return new Pair<>(maxValue.getText(),attempts.getText());
            }
            return null;
        } );

        Optional<Pair<String,String>> result = gameInput.showAndWait();
        result.ifPresent(t -> {
            System.out.println(result.get().getKey()+" UU "+result.get().getValue());

        });

        return new Pair<>(maxValue.getText(),attempts.getText());
    }

    public int  randomNum(int max) {
        Random rand = new Random();

        // if max not equals 0 returns true then max is returned else 100 is given
        //max = (max != 0) ? max : 100;

        int randInt = rand.nextInt(max);

        return randInt;
    }

//    public Scene gameRounds(int max) {
//        Button btn1 = new Button("main click");
//        Button btn2 = new Button("alternative click");
//
//        btn1.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("button 1 was clicked");
//            }
//        });
//
//        btn2.setOnAction(
//                (event) -> {
//                    System.out.println("button 2 was clicked");
//                }
//        );
//
//        HBox hb = new HBox();
//        hb.setSpacing(10);
//        hb.setPadding(new Insets(5,10,5,10));
//        hb.getChildren().addAll(btn1,btn2);
//
//        Text sceneTitle = new Text("Guess Game");
//        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD,20) );
//
//        Label input = new Label("Pleas guess number: ");
//        TextField userText = new TextField();
//        Label userNum = new Label("0");
//        Button submit = new Button("Submit");
//
//        ListView<Label> prevGuesses = new ListView<>();
//        VBox vbRight = new VBox();
//        vbRight.setPadding(new Insets(5,50,10,10));
//        vbRight.getChildren().add(prevGuesses);
//
//        Label userShowText = new Label("Previous user guess:");
//        Label showHighLow = new Label("=");
//        Label unknownNum = new Label("?");
//
//
//        HBox prevUserGuessRow = new HBox();
//        prevUserGuessRow.setSpacing(15);
//        prevUserGuessRow.getChildren().addAll(userShowText,userNum, showHighLow, unknownNum);
//
//        Label wonLoseResult = new Label();
//        wonLoseResult.setPadding(new Insets(15,10,5,5));
//        wonLoseResult.setFont(Font.font("Tahoma",FontWeight.BOLD,12));
//
//        VBox userInteraction = new VBox();
//        userInteraction.setPadding(new Insets(10, 10,15,10));
//        userInteraction.setSpacing(5);
//        userInteraction.getChildren().addAll(sceneTitle,input, userText, submit, prevUserGuessRow, wonLoseResult);
//
//        BorderPane bp = new BorderPane();
//        bp.setBottom(hb);
//        bp.setLeft(userInteraction);
//        bp.setRight(vbRight);
//
//
//        int randInt = randomNum(max); // value n for max value
//        System.out.println(randInt);
//
//        submit.setOnAction((event) -> {
//            if (userText.getText() != null) {
//                userNum.setText(userText.getText());
//                Label guess = new Label(userText.getText());
//                prevGuesses.getItems().add(guess);
//
//                if (Integer.valueOf(userText.getText()) < randInt ) {
//                    showHighLow.setText("<");
//                } else if (Integer.valueOf(userText.getText()) > randInt) {
//                    showHighLow.setText(">");
//                } else {
//                    showHighLow.setText("=");
//                    unknownNum.setText(String.valueOf(randInt) );
//                    wonLoseResult.setText("SUCCES, You found the unknown number!");
//                }
//
//            } else {
//                userNum.setText("No new number from user");
//            }
//        } );
//
//        Scene mainScene = new Scene(bp,600,400);
//
//        return mainScene;
//    }



}
