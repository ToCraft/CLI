package dev.tocraft.crafted.cli.json;

import dev.tocraft.crafted.cli.json.elements.*;

import java.util.ArrayList;
import java.util.List;

public final class JsonParser {
    public static JsonElement parseJson(String value) {
        String json = value.trim();

        try {
            if (json.startsWith("{")) {
                return parseJsonObject(json);
            } else if (json.startsWith("[")) {
                return parseJsonArray(json);
            } else if (json.startsWith("\"") && json.endsWith("\"")) {
                return new JsonString(json.substring(1, json.length() - 1));
            } else if (json.contains(".")) {
                return new JsonDouble(Double.parseDouble(json));
            } else {
                if (json.equalsIgnoreCase("true")) {
                    return new JsonBool(true);
                } else if (json.equalsIgnoreCase("false")) {
                    return new JsonBool(false);
                } else {
                    return new JsonInt(Integer.parseInt(json));
                }
            }
        } catch (Exception e) {
            throw new JsonParseException(json);
        }
    }

    public static String toPrettyJsonString(JsonElement jsonElement) {
        return jsonElement.toPrettyJson();
    }

    public static String toJsonString(JsonElement jsonElement) {
        return jsonElement.toJson();
    }

    private static JsonObject parseJsonObject(String jsonString) {
        jsonString = jsonString.trim();
        jsonString = jsonString.substring(1, jsonString.length() - 1).trim();

        JsonObject obj = new JsonObject();
        String[] entries = splitJsonEntries(jsonString);

        for (String entry : entries) {
            String[] keyValue = entry.split(":", 2);
            String key = keyValue[0].trim().replace("\"", "");
            JsonElement value = parseJson(keyValue[1]);

            obj.put(key, value);
        }
        return obj;
    }

    private static String[] splitJsonEntries(String jsonString) {
        int braceCount = 0;
        int bracketCount = 0;
        StringBuilder currentEntry = new StringBuilder();
        List<String> entries = new ArrayList<>();

        for (int i = 0; i < jsonString.length(); i++) {
            char ch = jsonString.charAt(i);

            if (ch == '{') {
                braceCount++;
            } else if (ch == '}') {
                braceCount--;
            } else if (ch == '[') {
                bracketCount++;
            } else if (ch == ']') {
                bracketCount--;
            } else if (ch == ',' && braceCount == 0 && bracketCount == 0) {
                entries.add(currentEntry.toString().trim());
                currentEntry.setLength(0);
                continue;
            }

            currentEntry.append(ch);
        }
        if (!currentEntry.toString().isEmpty()) {
            entries.add(currentEntry.toString().trim());
        }

        return entries.toArray(new String[0]);
    }

    private static JsonArray parseJsonArray(String jsonArrayString) {
        jsonArrayString = jsonArrayString.trim();
        jsonArrayString = jsonArrayString.substring(1, jsonArrayString.length() - 1).trim();

        JsonArray arrayList = new JsonArray();
        String[] values = splitJsonEntries(jsonArrayString);

        for (String value : values) {
            arrayList.add(parseJson(value));
        }
        return arrayList;
    }
}
