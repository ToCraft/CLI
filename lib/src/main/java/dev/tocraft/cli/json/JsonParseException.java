package dev.tocraft.cli.json;

public class JsonParseException extends RuntimeException {
    public JsonParseException(String json) {
        super("Couldn't parse json: " + json.replaceAll("\\s*", ""));
    }
}
