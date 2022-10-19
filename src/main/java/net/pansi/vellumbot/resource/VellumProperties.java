package net.pansi.vellumbot.resource;

import net.pansi.vellumbot.api.Loadable;

import java.io.IOException;
import java.util.Properties;

public class VellumProperties extends Properties implements Loadable {

    private final String resourcePath;

    public VellumProperties(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public void load() {
        try {
            load(getClass().getClassLoader().getResourceAsStream(resourcePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
