package dev.tocraft.crafted.cli.json.elements;

public class JsonBool implements JsonElement {
    private final boolean value;

    public JsonBool(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }

    @Override
    public String toJson() {
        return Boolean.toString(value);
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
