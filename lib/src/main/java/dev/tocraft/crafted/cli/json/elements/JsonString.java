package dev.tocraft.crafted.cli.json.elements;

public class JsonString implements JsonElement {
    private final String value;

    public JsonString(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

    @Override
    public String toJson() {
        return "\"" + value + "\"";
    }

    @Override
    public String toPrettyJson(int indentLevel) {
        return toJson();
    }

    @Override
    public String toString() {
        return toJson();
    }
}
