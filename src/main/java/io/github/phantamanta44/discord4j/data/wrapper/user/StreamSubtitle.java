package io.github.phantamanta44.discord4j.data.wrapper.user;

public class StreamSubtitle implements ISubtitle {

    private final String message, url;

    public StreamSubtitle(String message, String url) {
        this.message = message;
        this.url = url;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getUrl() {
        return url;
    }

}
