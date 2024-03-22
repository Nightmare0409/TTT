import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class TicTacToe extends Application {
    // Indicating which player has a turn. X player starts.
    private char whoseTurn = 'X';

    // Creating cells.
    private Cell[][] cell = new Cell[5][5];

    // Creating the label.
    private Label lblStatus = new Label("X's turn to play");

    @Override // Overriding the start method in the Application class.
    public void start(Stage primaryStage) {
        // Pane to hold cell.
        GridPane pane = new GridPane();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                pane.add(cell[i][j] = new Cell(), j, i);
            }
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);

        // Creating scene and placing in stage.
        Scene scene = new Scene(borderPane, 450, 450);
        primaryStage.setTitle("TicTacToe"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    //Determining if the cells are all occupied.
    public boolean isFull() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (cell[i][j].getToken() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // Determining if the player with the specified token wins.
    public boolean isWon(char token) {
        for (int i = 0; i < 5; i++) {
            if (cell[i][0].getToken() == token && cell[i][1].getToken() == token && cell[i][2].getToken() == token
                    && cell[i][3].getToken() == token && cell[i][4].getToken() == token) {
                return true;
            }
        }

        for (int j = 0; j < 5; j++) {
            if (cell[0][j].getToken() == token && cell[1][j].getToken() == token && cell[2][j].getToken() == token
                    && cell[3][j].getToken() == token && cell[4][j].getToken() == token) {
                return true;
            }
        }

        if (cell[0][0].getToken() == token && cell[1][1].getToken() == token && cell[2][2].getToken() == token
                && cell[3][3].getToken() == token && cell[4][4].getToken() == token) {
            return true;
        }

        if (cell[0][4].getToken() == token && cell[1][3].getToken() == token && cell[2][2].getToken() == token
                && cell[3][1].getToken() == token && cell[4][0].getToken() == token) {
            return true;
        }

        return false;
    }

    // Inner class for a cell.
    public class Cell extends Pane {
        // Token used for cell.
        private char token = ' ';

        public Cell() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(90, 90);
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        
        public char getToken() {
            return token;
        }

        
        public void setToken(char c) {
            token = c;

            if (token == 'X') {
                Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));
                Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
                line2.startYProperty().bind(this.heightProperty().subtract(10));
                line2.endXProperty().bind(this.widthProperty().subtract(10));

                // Adding lines to the pane.
                this.getChildren().addAll(line1, line2);
            } else if (token == 'O') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10,
                        this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));

                // Adding the ellipse to the pane.
                getChildren().add(ellipse);
            }
        }

        // Handling a mouse click event.
        private void handleMouseClick() {
            // Cell is empty and game is not yet over.
            if (token == ' ' && whoseTurn != ' ') {
                setToken(whoseTurn);

                // Checking game status.
                if (isWon(whoseTurn)) {
                    lblStatus.setText(whoseTurn + " won! The game is over");
                    whoseTurn = ' ';

                } else if (isFull()) {
                    lblStatus.setText("Draw! The game is over");
                    whoseTurn = ' ';

                } else {
                    // Change the turn.
                    whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
                    // Display whose turn it is.
                    lblStatus.setText(whoseTurn + "'s turn");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
