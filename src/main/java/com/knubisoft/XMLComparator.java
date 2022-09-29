package com.knubisoft;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public class XMLComparator extends ErrorHelper implements ObjectComparator<Node> {

    private Mode mode;

    private Alias alias = new AliasMap();

    @Override
    public void compare(Node expected, Node actual) throws MatchException {
        raise(!expected.getNodeName().equals(actual.getNodeName()),
                "Expected node name " + expected.getNodeName() + " but was " + actual.getNodeName());

        if (expected.hasAttributes()) {
            raise(!actual.hasAttributes(),
                    "Attributes not found in actual document for node " + actual.getNodeName());
        } else {
            mode.onStrict(raise(actual.hasAttributes(),
                    "Additional attributes found in actual document for node " + actual.getNodeName()));
        }

        StringComparator(mode).compare(expected.getNodeValue(), actual.getNodeValue());

        if (expected.hasChildNodes()) {
            raise(!actual.hasChildNodes(), "Child nodes not found");
            compareNodes(expected.getChildNodes(), actual.getChildNodes());
        } else {
            mode.onStrict(raise(actual.hasChildNodes(), "Additional child nodes found"));
        }
    }

    private void compareNodes(NodeList expected, NodeList actual) {
        raise(expected.getLength() != actual.getLength(), "Child nodes length not equals");

        for (int i = 0; i < expected.getLength(); i++) {
            compare(expected.item(i), actual.item(i));
        }
    }

    private void compareAttributes(List<String> expected, List<String> actual){
        mode.onStrict({
                if(expected.size() != actual.size()){
                    expected.map;;
                    raise();
        }
        });
    }
}
