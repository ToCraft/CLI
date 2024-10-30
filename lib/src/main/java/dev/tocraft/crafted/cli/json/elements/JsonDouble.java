package dev.tocraft.crafted.cli.json.elements;

public class JsonDouble implements JsonElement {
    private final double value;

    public JsonDouble(double value) {
        this.value = value;
    }

    public double get() {
        return value;
    }

    @Override
    public String toJson() {
        return Double.toString(value);
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
