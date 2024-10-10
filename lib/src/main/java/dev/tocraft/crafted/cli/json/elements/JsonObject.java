package dev.tocraft.crafted.cli.json.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonObject extends HashMap<String, JsonElement> implements JsonElement {
    @Override
    public String toPrettyJson(int indentLevel) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");

        List<String> entries = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : entrySet()) {
            String key = getIndent(indentLevel + 1) + "\"" + entry.getKey() + "\"";
            String value = entry.getValue().toPrettyJson(indentLevel + 1);
            entries.add(key + ": " + value);
        }

        jsonBuilder.append(String.join(",\n", entries));
        jsonBuilder.append("\n").append(getIndent(indentLevel)).append("}");
        return jsonBuilder.toString();
    }

    @Override
    public String toString() {
        return toJson();
    }
}
