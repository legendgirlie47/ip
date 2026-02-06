package ketchup;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ketchup.ui.Ui;


/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Ui ui = new Ui();

    private Ketchup ketchup;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/monkey1.jpeg"));
    private Image ketchupImage = new Image(this.getClass().getResourceAsStream("/images/monkey2.jpeg"));

    @FXML
    public void initialize() {
        String welcomeMsg = ui.showHello();
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(welcomeMsg, ketchupImage)
        );
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Ketchup instance */
    public void setKetchup(Ketchup k) {
        ketchup = k;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Ketchup's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        KetchupResult result = ketchup.getResult(input);
        String response = result.getResponse();
        boolean shouldExit = result.isShouldExit();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, ketchupImage)
        );

        if (shouldExit) {
            Platform.exit();
        }

        userInput.clear();
    }
}
