package model;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.*;
import javafx.stage.*;
import java.util.*;


public class Invoice extends Application {

    @Override
    public void start(Stage Window) {
        // invoke loginView
        loginView(Window);

        // invoke invoiceView
        // 
        invoiceView(Window);

        Window.show();
    }

    // LoginView ----------------------------------------------------------------------
    public void loginView(Stage Window) {
    //create
    //-panes
    VBox vbox = new VBox(10);
    GridPane gpane = new GridPane(10, 10);
    HBox hbox = new HBox(10);
    //-Scene
    Scene loginView = new Scene(vbox);
    //-Textfields
    TextField useridTF = new TextField(); 
    TextField passwordTF = new TextField();
    //-Buttons
    Button loginBT = new Button("Login");

    //configure
    //-Buttons
    loginBT.setOnAction(e -> {processLogin(useridTF, passwordTF, message, Window);});

    // FOR THE TEXT MESSAGE 
    message.setText("                           ");
    
    //-panes
    gpane.add(new Text("User id:"), 0, 1); gpane.add(useridTF, 1,1); gpane.add(new Text("Password:"), 0,2); gpane.add(passwordTF, 1, 2);
    hbox.getChildren().add(clearErrorMessage, loginBT);
    vbox.getChildren().addAll(new Text("Login hoe"), gpane, hbox);
    vbox.setAlignment(Pos.CENTER); // hbox.setAlignment(Pos.CENTER); gpane.setAlignment(Pos.CENTER); 

    //display
    Window.setScene(loginView);
    }

    // processLogin() verifies if the users authorized or not 
    public void processLogin(TextField id, TextField pw, Text message, Stage Window) {
        // List of authorized users
        ArrayList<AccountHolder> accounts = new ArrayList<>();

        accounts.add(new AccountHolder("trao", "RaoT1", "T M Rao"));
        accounts.add(new AccountHolder("smitra", "MitraS1", "S Mitra"));
        accounts.add(new AccountHolder("mcoleman", "Melissa1", "M Coleman"));
        accounts.add(new AccountHolder("smitra", "MitraS1", "S Mitra"));
        accounts.add(new AccountHolder("rnsmith", "Rebekah1", "R Smith"));

        // Check id and pw
        // when there's no input
        if (id.getText() == null || pw.getText() == null) {
            message.setText("Please enter your user id and password!");
            formatMessage(message);
            id.setText("");
            pw.setText("");
            return;
        }

        // when the input is correct
        for (int i = 0; i < accounts.size(); i++) {
            if (id.getText().equals(accounts.get(i).username) && pw.getText().equals(accounts.get(i).password)) {
                invoiceView(Window);
                return;
            }
        }

        // when the input is incorrect
        message.setText("Incorrect userid or password!");
        formatMessage(message);
        return;
    }
   
    public void formatMessage(Text message) {
        message.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        message.setTextAlignment(TextAlignment.LEFT);
        message.setFill(Color.RED);
    }

    // Invoice View -------------------------------------------------------------------
    public void invoiceView(Stage Window) {

        // VBox - containing the Text (title), GridPane (TextField), and Buttons
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        // Text
        vBox.getChildren().add(new Text("Mom and Pop’s Clothing Store"));

        // GridPane
        GridPane gPane = new GridPane();

        // TextField
        TextField shirtsTF = new TextField();   shirtsTF.setText("0.00");
        TextField pantsTF = new TextField();    pantsTF.setText("0.00");
        TextField tiesTF = new TextField();     tiesTF.setText("0.00");
        TextField shoesTF = new TextField();    shoesTF.setText("0.00");
        TextField billTF = new TextField();     billTF.setEditable(false);

        gPane.add(new Label("Shirts"), 0, 0);        gPane.add(shirtsTF, 1, 0);
        gPane.add(new Label("Pants"), 0, 1);         gPane.add(pantsTF, 1, 1);
        gPane.add(new Label("Ties"), 0, 2);          gPane.add(tiesTF, 1, 2);
        gPane.add(new Label("Shoes"), 0, 3);         gPane.add(shoesTF, 1, 3);
        gPane.add(new Label("Sales Tax"), 0, 4);
        // ComboBox for Sales Tax
        ComboBox<Double> cmb = new ComboBox<>();
        for (double i = 3.00; i <= 9.5; i += 0.25) {cmb.getItems().add(i);}
        cmb.setValue(8.25);
        gPane.add(cmb, 1, 4);

        gPane.add(new Label("Total Bill"), 0, 5);    gPane.add(billTF, 1, 5);

        // Add TextField into vBox
        gPane.setAlignment(Pos.CENTER);
        vBox.getChildren().add(gPane);

        // Buttons
        Button calculateBT = new Button("Calculate");
        Button backBT = new Button("Back");

        calculateBT.setOnAction(e -> {
            String total="0.00";
            total = Double.parseDouble(shirtsTF.getText())
                + Double.parseDouble(pantsTF.getText()) 
                + Double.parseDouble(tiesTF.getText()) 
                + Double.parseDouble(shoesTF.getText())
                + "";
            total = Double.parseDouble(total) * (cmb.getValue() + 100)/ 100 + "";
            billTF.setText(total);
            gPane.add(billTF, 1, 5);
        });
        backBT.setOnAction(e -> {loginView(Window);});

        // Add Buttons into vBox
        HBox hBox = new HBox(calculateBT, backBT);
        hBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox);

        // Display nodes
        Scene scene2 = new Scene(vBox);
        Window.setScene(scene2);
    }

    // main method---------------------------
    public static void main(String[] args) {
        launch();
    }
    // --------------------------------------
}
