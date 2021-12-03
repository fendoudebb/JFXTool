package fendoudebb.fx.tool.controller;

import fendoudebb.fx.tool.bean.ChatData;
import fendoudebb.fx.tool.util.DateFormatter;
import fendoudebb.fx.tool.util.Resource;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * zbj: created on 2021/11/27 22:23.
 */
@Slf4j
public class FuncNettyClientController implements Initializable {
    public SplitPane clientContainer;
    public ListView<ChatData> clientContent;
    public TextArea clientInput;
    public SplitMenuButton clientSend;
    public Label listenStatus;

    private static final String LINE_SEPARATOR = "\n";

    private boolean useEnterSend = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientContainer.setDividerPositions(0.7, 0.3);
        listenStatus.setTextFill(Color.RED);

        ContextMenu contextMenu = new ContextMenu();
        clientContent.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ChatData> call(ListView<ChatData> param) {
                ListCell<ChatData> listCell = new ListCell<>() {
                    @Override
                    public void updateSelected(boolean selected) {
//                        super.updateSelected(selected);
                    }

                    @Override
                    protected void updateItem(ChatData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            VBox cellContainer = new VBox();
                            cellContainer.setAlignment(Pos.CENTER);
                            cellContainer.setStyle("-fx-background-color: #F5F5F5;");

                            HBox contentContainer = new HBox(10);

                            TextFlow textFlow = new TextFlow();

                            Text content = new Text(item.getContent());
                            content.setFontSmoothingType(FontSmoothingType.LCD);
                            content.setFont(Font.font(14));

                            textFlow.getChildren().add(content);

                            textFlow.setOnMouseClicked(event -> {
                                if (event.getButton() == MouseButton.SECONDARY) {
                                    contextMenu.getItems().clear();
//                                    contextMenu.hide();
                                    MenuItem copy = new MenuItem(resources.getString("func_netty_client_copy"));
                                    contextMenu.getItems().addAll(copy);
                                    copy.setOnAction(e -> {
                                        Clipboard clipboard = Clipboard.getSystemClipboard();
                                        ClipboardContent clipboardContent = new ClipboardContent();
                                        clipboardContent.putString(content.getText());
                                        clipboard.setContent(clipboardContent);
                                    });
                                    contextMenu.show(textFlow, event.getScreenX(), event.getScreenY());
                                }
                            });

                            Image image = new Image(Resource.url("/assets/icon.png"));
                            ImageView imageView = new ImageView(image);
                            imageView.setFitWidth(38);
                            imageView.setFitHeight(38);

                            if (item.isSelf()) {
                                textFlow.setStyle("-fx-padding: 10;-fx-background-radius: 3;-fx-background-color: #9EEA6A");
                                contentContainer.setAlignment(Pos.TOP_RIGHT);
                                contentContainer.setPadding(new Insets(10, 10, 10, 150));
                                contentContainer.getChildren().addAll(textFlow, imageView);
                            } else {
                                textFlow.setStyle("-fx-padding: 10;-fx-background-radius: 3;-fx-background-color: white");
                                contentContainer.setPadding(new Insets(10, 150, 10, 10));
                                contentContainer.getChildren().addAll(imageView, textFlow);
                            }

                            int index = this.getIndex();

                            if (index <= 0 ||
                                    item.getTimestamp() - param.getItems().get(index - 1).getTimestamp() > Long.parseLong(Resource.config().getString("func_netty_show_ts_interval"))) {
                                String time = DateFormatter.tsToString(item.getTimestamp());
                                Label ts = new Label(time);
                                VBox.setMargin(ts, new Insets(20, 0, 0, 0));
                                // text fill must add the alpha bit
                                ts.setStyle("-fx-text-fill: #FFFFFFFF;-fx-background-color: #DADADA;-fx-background-radius:3;-fx-padding: 3 5 3 5;");
                                cellContainer.getChildren().addAll(ts, contentContainer);
                            } else {
                                cellContainer.getChildren().add(contentContainer);
                            }

                            this.setGraphic(cellContainer);
                            // Subtract any value. Fit the width to the ListView
                            this.prefWidthProperty().bind(param.widthProperty().subtract(50));
                        } else {
                            this.setGraphic(null);
                            this.prefWidthProperty().unbind();
                        }

                    }
                };
                return listCell;
            }
        });

        clientContent.getItems().addListener((ListChangeListener<ChatData>) c -> {
            while (c.next()) {
                Platform.runLater(() -> {
                    clientContent.scrollTo(c.getTo() - 1);
                });
            }
        });

        clientInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                if (event.isShiftDown()) {
                    if (useEnterSend) {
                        clientInput.appendText(LINE_SEPARATOR);
                    } else {
                        send();
                    }
                } else {
                    if (useEnterSend) {
                        send();
                    } else {
                        clientInput.appendText(LINE_SEPARATOR);
                    }
                }
            }
        });
    }

    public void onEnterSend(ActionEvent actionEvent) {
        useEnterSend = true;
    }

    public void onShiftEnterSend(ActionEvent actionEvent) {
        useEnterSend = false;
    }

    private void send() {
        String text = clientInput.getText();
        clientInput.clear();
        if (!text.isBlank()) {
            text = text.stripTrailing();
            ChatData d = new ChatData();
            d.setUsername("Hello");
            d.setContent(text);
            d.setTimestamp(System.currentTimeMillis());
            d.setSelf(true);
            clientContent.getItems().add(d);
        }
    }

    public void connectBtnClick(ActionEvent actionEvent) {

    }
}
