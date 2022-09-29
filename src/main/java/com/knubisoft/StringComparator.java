package com.knubisoft;

import lombok.AllArgsConstructor;

import java.util.regex.Pattern;

public class StringComparator extends ErrorHelper implements ObjectComparator<String> {
    private Alias alias = new AliasMap();

    @Override
    public void compare(String expected, String actual) {
        if (!expected.equals(actual)) {
            Result result = extractAction(expected);
            if (result instanceof PatternComparison) {
                Pattern pattern = alias.get(aliasOrRawPattern).getElse(Pattern.compile(aliasOrRawPattern));
                raise(!pattern.matcher(actual).matches(),
                        "Property " + actual + " should match pattern " + aliasOrRawPattern);
            } else if (result instanceof TreeComparison) {
                Comparator(mode).compare(tree, actual);
            } else if (result instanceof Empty) {
                raise("Property " + expected + " is not equal to " + actual);
            }
        }
    }

    private void compile(String pattern) {
        try {
            Pattern.compile(pattern);
        } catch (Exception e) {
            throw new RuntimeException("Illegal pattern " + pattern, e);
        }
    }

    Result extractAction(String v) {
        if (v.length() > 3) {
            if (v.startsWith("p(") && v.endsWith(")")) {
                return new PatternComparison(v.substring(2, v.length() - 1));
            } else if (v.startsWith("t(") && v.endsWith(")")) {
                return new TreeComparison(v.substring(2, v.length() - 1));
            } else {
                return new Empty();
            }
        } else {
            return new Empty();
        }
    }
}

interface Result {
}

@AllArgsConstructor
class PatternComparison implements Result {
    String pattern;
}

@AllArgsConstructor
class TreeComparison implements Result {
    String tree;
}

class Empty implements Result {
}