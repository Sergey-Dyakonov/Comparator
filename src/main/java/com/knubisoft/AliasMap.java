package com.knubisoft;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class AliasMap implements Alias {

    private Map<String, Pattern> map;

    @Override
    public Alias add(String alias, String pattern) {
        return null;
    }

    @Override
    public Alias add(String alias, Pattern pattern) {
        return null;
    }

    @Override
    public Optional<Pattern> add(String alias) {
        return Optional.ofNullable(map.get(alias));
    }
}
