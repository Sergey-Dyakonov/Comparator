package com.knubisoft;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.node.JsonNodeType.BOOLEAN;
import static com.fasterxml.jackson.databind.node.JsonNodeType.NUMBER;
import static com.fasterxml.jackson.databind.node.JsonNodeType.STRING;
import static com.fasterxml.jackson.databind.node.JsonNodeType.ARRAY;
import static com.fasterxml.jackson.databind.node.JsonNodeType.OBJECT;
import static com.fasterxml.jackson.databind.node.JsonNodeType.NULL;

public class JsonComparator extends ErrorHelper implements ObjectComparator<JsonNode> {
    @Override
    public void compare(JsonNode expected, JsonNode actual) throws MatchException {
        JsonNodeType expType = expected.getNodeType();
        JsonNodeType actType = actual.getNodeType();
        String error = "Property " + expected.asText() + " is not equal to " + actual.asText();
        if (expType.equals(BOOLEAN) && actType.equals(BOOLEAN)) {
            raise(expected.asBoolean() != actual.asBoolean(), error);
        } else if (expType.equals(NUMBER) && actType.equals(NUMBER)) {
            raise(expected.asDouble() != actual.asDouble(), error);
        } else if (expType.equals(STRING) && actType.equals(STRING) ||
                expType.equals(STRING) && actType.equals(NUMBER) ||
                expType.equals(STRING) && actType.equals(BOOLEAN)) {
            StringComparator(mode).compare(expected.asText(), actual.asText());
        } else if (expType.equals(ARRAY) && actType.equals(ARRAY)) {
            compareElements(expected.elements(), actual.elements());
        } else if (expType.equals(OBJECT) && actType.equals(OBJECT)) {
            compareFields(expected.fields(), actual.fields());
        } else if (expType.equals(NULL) && actType.equals(NULL)) {

        } else {
            raise(!expType.equals(actType),
                    "Expected " + expType + " but was " + actType);
        }
    }

    private void compareFields(List<Map.Entry<String, JsonNode>> exp, List<Map.Entry<String, JsonNode>> act) {
        mode.onStrict(raise(exp.size()!= act.size(),
                "Difference in properties or count. Missing "+
                exp.));
        for (Map.Entry<String, JsonNode> entry : exp) {
            act.stream().findAny();
        }
    }

    private void compareElements(List<JsonNode> exp, List<JsonNode> act) {
        raise(exp.size() != act.size(),
                "Expected array length is " + exp.size() + " actual " + act.size());
        for (JsonNode node : exp) {
            compare(node, act.get(exp.indexOf(node)));
        }
    }
}
