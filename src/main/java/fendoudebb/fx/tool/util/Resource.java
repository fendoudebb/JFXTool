package fendoudebb.fx.tool.util;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * zbj: created on 2021/11/29 9:04.
 */
public class Resource {

    public static ResourceBundle config() {
        return ResourceBundle.getBundle("config.app");
    }

    public static ResourceBundle i18n() {
        return ResourceBundle.getBundle("i18n.language");
    }

    public static String url(String path) {
        return Objects.requireNonNull(Resource.class.getResource(path)).toExternalForm();
    }

}
