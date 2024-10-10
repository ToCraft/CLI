package dev.tocraft.crafted.cli.json.elements;

public class JsonInt implements JsonElement {
    private final int value;

    public JsonInt(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    @Override
    public String toPrettyJson(int indentLevel) {
        return Integer.toString(value);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
