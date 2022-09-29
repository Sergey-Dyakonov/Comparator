package com.knubisoft;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringLinesComparator extends ErrorHelper implements ObjectComparator<String> {

    private Alias alias = new AliasMap();

    @Override
    public void compare(String expected, String actual) throws MatchException {
        List<String> expectedLines = expected.lines().toList();
        List<String> actualLines = actual.lines().toList();

        if (expectedLines.size() != actualLines.size()) {
            raise("Content doesn't match");
        } else {
            expectedLines.forEach(line ->
                    compareLine(line, actualLines.get(expectedLines.indexOf(line))));
        }
    }

    private void compareLine(String expected, String actual) {
        if (!expected.equals(actual)) {
            Pattern pattern = alias.get(expected).orElse(Pattern.compile(expected));
            raise(!pattern.matcher(actual).matches(),
                    "Lines " + actual + " should match " + expected);
        }
    }

    private void compile(String pattern) {
        try {
            Pattern.compile(pattern);
        } catch (Exception e) {
            throw new RuntimeException("Illegal pattern " + pattern, e);
        }
    }
}
