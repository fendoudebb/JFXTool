/**
 * zbj: created on 2021/11/27 11:10.
 */
module JFXTool {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.slf4j;

    opens fendoudebb.fx.tool to javafx.fxml, javafx.graphics;
    opens fendoudebb.fx.tool.controller to javafx.fxml;
    opens fendoudebb.fx.tool.bean to javafx.fxml;

}