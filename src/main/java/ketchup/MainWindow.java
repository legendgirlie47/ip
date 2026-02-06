package ketchup;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.OutputStream;
import java.io.PrintStream;

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

    private Ketchup ketchup;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/monkey1.jpeg"));
    private Image ketchupImage = new Image(this.getClass().getResourceAsStream("/images/monkey2.jpeg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                Platform.runLater(() -> {
                    dialogContainer.getChildren().add(
                            DialogBox.getDukeDialog(
                                    String.valueOf((char) b),
                                    ketchupImage
                            )
                    );
                });
            }
        }));
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
        Boolean shouldExit = ketchup.run(input);

        if (shouldExit) {
            Platform.exit();
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );

        userInput.clear();
    }
}
