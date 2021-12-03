package fendoudebb.fx.tool.controller;

import fendoudebb.fx.tool.bean.TreeItemData;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * zbj: created on 2021/11/27 11:51.
 */
@Slf4j
public class MainController implements Initializable {

    public StackPane funcTree;
    public TabPane funcArea;

    private ResourceBundle resources;

    private static final String FORMAT_URL = "/fxml/%s.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        Node node = generateTreeView();
        funcTree.getChildren().add(node);

        funcArea.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    @SuppressWarnings({"unchecked"})
    private Node generateTreeView() {
        TreeView<TreeItemData> treeView = new TreeView<>();
        TreeItem<TreeItemData> root = new TreeItem<>();
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        treeView.setCellFactory(TextFieldTreeCell.forTreeView(new StringConverter<>() {
            @Override
            public String toString(TreeItemData object) {
                return object.getFuncName();
            }

            @Override
            public TreeItemData fromString(String string) {
                return null;
            }
        }));

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isLeaf()) {

                String tabName = newValue.getValue().getTabName();
                ObservableList<Tab> tabs = funcArea.getTabs();

                Optional<Tab> opt = tabs.filtered(tab -> tabName.equals(tab.getText())).stream().findFirst();

                Tab tab;
                if (opt.isPresent()) {
                    tab = opt.get();
                } else {
                    tab = new Tab(tabName);
                    tabs.add(tab);

                    Node node = switchPane(newValue.getValue().getFxmlName());
                    tab.setContent(node);
                }
                funcArea.getSelectionModel().select(tab);
            }
        });

        TreeItem<TreeItemData> nettyItem = new TreeItem<>(buildTreeItemData("func_netty", "func_netty"));

        TreeItem<TreeItemData> nettyClient = new TreeItem<>(buildTreeItemData("func_netty", "func_netty_client"));
        TreeItem<TreeItemData> nettyServer = new TreeItem<>(buildTreeItemData("func_netty", "func_netty_server"));

        nettyItem.getChildren().add(nettyClient);
        nettyItem.getChildren().add(nettyServer);

        nettyItem.setExpanded(true);


        TreeItem<TreeItemData> nettyItem2 = new TreeItem<>(buildTreeItemData("func_netty", "func_netty"));

        TreeItem<TreeItemData> nettyClient2 = new TreeItem<>(buildTreeItemData("func_netty", "func_netty_client"));
        TreeItem<TreeItemData> nettyServer2 = new TreeItem<>(buildTreeItemData("func_netty", "func_netty_server"));

        nettyItem2.getChildren().add(nettyClient2);
        nettyItem2.getChildren().add(nettyServer2);

        root.getChildren().addAll(nettyItem, nettyItem2);

        return treeView;
    }

    private TreeItemData buildTreeItemData(String rootName, String key) {
        String funcName = resources.getString(key);
        return new TreeItemData(key, funcName, resources.getString(rootName).concat(" - ").concat(funcName));
    }

    private Node switchPane(String fxmlName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            String format = String.format(FORMAT_URL, fxmlName);
            fxmlLoader.setLocation(getClass().getResource(format));
            fxmlLoader.setResources(resources);
            return fxmlLoader.load();
        } catch (Exception e) {
            log.error("switchPane Exception#{}", e.getCause().getMessage(), e);
        }
        return null;
    }

}
