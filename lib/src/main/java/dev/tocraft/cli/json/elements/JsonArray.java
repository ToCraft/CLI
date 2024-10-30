package dev.tocraft.cli.json.elements;

import java.util.ArrayList;
import java.util.List;

public class JsonArray extends ArrayList<JsonElement> implements JsonElement {
    @Override
    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");

        List<String> values = new ArrayList<>();
        for (JsonElement element : this) {
            values.add(element.toJson());
        }

        values.sort(JsonElement::compareStrings);

        jsonBuilder.append(String.join(",", values));
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    @Override
    public String toPrettyJson(int indentLevel) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        List<String> values = new ArrayList<>();
        for (JsonElement element : this) {
            values.add(getIndent(indentLevel + 1) + element.toPrettyJson(indentLevel + 1));
        }

        values.sort(JsonElement::compareStrings);

        jsonBuilder.append(String.join(",\n", values));
        jsonBuilder.append("\n").append(getIndent(indentLevel)).append("]");
        return jsonBuilder.toString();
    }

    @Override
    public String toString() {
        return toJson();
    }
}
