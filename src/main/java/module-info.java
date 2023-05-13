module com.example.finalprojectblackjack {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.finalprojectblackjack to javafx.fxml;
    exports com.example.finalprojectblackjack;
}