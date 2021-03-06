package io.github.phantamanta44.discord4j.data.wrapper.user;

import io.github.phantamanta44.discord4j.data.icon.UrlIcon;

public class Application {

    private final String name, clientId;
    private final UrlIcon icon;

    public Application(String name, String clientId, String iconUrl) {
        this.name = name;
        this.clientId = clientId;
        this.icon = new UrlIcon(iconUrl);
    }

    public String name() {
        return name;
    }

    public String clientId() {
        return clientId;
    }

    public UrlIcon icon() {
        return icon;
    }

}
