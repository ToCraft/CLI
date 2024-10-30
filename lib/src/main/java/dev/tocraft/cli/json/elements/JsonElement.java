package dev.tocraft.cli.json.elements;

import dev.tocraft.cli.json.JsonParseException;

public interface JsonElement {
    default String toPrettyJson() {
        return toPrettyJson(0);
    }

    String toJson();

    String toPrettyJson(int indentLevel);

    default String getIndent(int indentLevel) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            s.append("    ");
        }
        return s.toString();
    }

    default JsonArray asArray() {
        try {
            return (JsonArray) this;
        } catch (Exception e) {
            throw new JsonParseException(this.toString());
        }
    }

    default JsonBool asBool() {
        try {
            return (JsonBool) this;
        } catch (Exception e) {
            throw new JsonParseException(this.toString());
        }
    }

    default JsonDouble asDouble() {
        try {
            if (this instanceof JsonInt) {
                return new JsonDouble(((JsonInt) this).get());
            } else {
                return (JsonDouble) this;
            }
        } catch (Exception e) {
            throw new JsonParseException(this.toString());
        }
    }

    default JsonInt asInt() {
        try {
            return (JsonInt) this;
        } catch (Exception e) {
            throw new JsonParseException(this.toString());
        }
    }

    default JsonObject asObject() {
        try {
            return (JsonObject) this;
        } catch (Exception e) {
            throw new JsonParseException(this.toString());
        }
    }

    default JsonString asString() {
        try {
            return (JsonString) this;
        } catch (Exception e) {
            throw new JsonParseException(this.toString());
        }
    }

    static int compareStrings(String s1, String s2) {
        int comparison;
        int c1, c2;
        for (int i = 0; i < s1.length() && i < s2.length(); i++) {
            c1 = s1.toLowerCase().charAt(i);
            c2 = s2.toLowerCase().charAt(i);
            comparison = c1 - c2;

            if (comparison != 0) {
                return comparison;
            }
        }
        return Integer.compare(s1.length(), s2.length());
    }
}
