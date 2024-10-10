package dev.tocraft.crafted.cli.json.elements;

import dev.tocraft.crafted.cli.json.JsonParseException;

public interface JsonElement {
    default String toPrettyJson() {
        return toPrettyJson(0);
    }

    default String toJson() {
        return toPrettyJson().replaceAll("\\s*", "");
    }

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
}
