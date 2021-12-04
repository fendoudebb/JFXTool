package fendoudebb.fx.tool.controller;

import fendoudebb.fx.tool.netty.server.ChatServer;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * zbj: created on 2021/11/27 22:23.
 */
@Slf4j
public class FuncNettyServerController implements Initializable {

    public VBox serverContainer;
    public TextField listenPort;
    public Label listenStatus;
    public Label serverConnections;

    private ResourceBundle resources;
    private final SimpleIntegerProperty connections= new SimpleIntegerProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.serverConnections.textProperty().bind(this.connections.asString());

        listenStatus.setTextFill(Color.RED);
        listenPort.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getText();
            if (text.matches("[0-9]*") && text.length() < 6) {
                return change;
            }
            return null;
        }));
    }

    public void startBtnClick(ActionEvent actionEvent) {
        String port = listenPort.getText().strip();
        if (port.isBlank()) {
            return;
        }
        Button button = (Button) actionEvent.getSource();
        button.setDisable(true);
        ChatServer chatServer = new ChatServer();
        new Thread(() -> chatServer.start(Integer.parseInt(port), future -> {
            Platform.runLater(() -> {
                listenStatus.setText(resources.getString("func_netty_listen_status_started"));
                listenStatus.setTextFill(Color.GREEN);
            });

        }, future -> Platform.runLater(() -> {
            Platform.runLater(() -> {
                listenStatus.setText(resources.getString("func_netty_listen_status_not_started"));
                listenStatus.setTextFill(Color.RED);
                button.setDisable(false);
            });
        }))).start();


//        ObservableList<Node> children = serverContainer.getChildren();
//        children.add(children.size() - 1, new Label("errorMsg"));

//        connections.setValue(connections.get() + 1);
    }
}
