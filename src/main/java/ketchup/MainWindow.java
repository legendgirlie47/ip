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
 * Controller for the main JavaFX GUI.
 * Handles user interactions, updates the dialog container,
 * and delegates command processing to the {@link Ketchup} instance.
 */
public class MainWindow extends AnchorPane {

    /** ScrollPane that contains the dialog container. */
    @FXML
    private ScrollPane scrollPane;

    /** Container that holds all dialog boxes. */
    @FXML
    private VBox dialogContainer;

    /** Text field where the user enters input. */
    @FXML
    private TextField userInput;

    /** Button used to send user input. */
    @FXML
    private Button sendButton;

    /** UI helper used to generate welcome message. */
    private Ui ui = new Ui();

    /** Core application logic instance. */
    private Ketchup ketchup;

    /** Image representing the user. */
    private Image userImage =
            new Image(this.getClass().getResourceAsStream("/images/monkey1.jpeg"));

    /** Image representing Ketchup. */
    private Image ketchupImage =
            new Image(this.getClass().getResourceAsStream("/images/monkey2.jpeg"));

    /**
     * Initializes the GUI after FXML loading.
     * Displays the welcome message and binds the scroll pane
     * to automatically scroll to the bottom when new messages are added.
     */
    @FXML
    public void initialize() {
        String welcomeMsg = ui.showHello();
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(welcomeMsg, ketchupImage)
        );
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the {@link Ketchup} instance into this controller.
     *
     * @param k the core application logic instance
     */
    public void setKetchup(Ketchup k) {
        this.ketchup = k;
    }

    /**
     * Handles user input from the GUI.
     * <p>
     * Retrieves the user's input, delegates processing to the
     * {@link Ketchup} instance, displays both the user's message
     * and the application's response, and clears the input field.
     * If the command indicates exit, the JavaFX platform is terminated.
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
