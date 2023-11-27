/** @noinspection JavaModuleNaming*/module com.th25.effortlogger {
    requires javafx.controls;
    requires javafx.fxml;
    requires httpclient;
    requires httpcore;


    opens com.th25.effortlogger to javafx.fxml;
    exports com.th25.effortlogger;
}