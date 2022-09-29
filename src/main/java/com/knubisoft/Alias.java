package com.knubisoft;

import java.util.Optional;
import java.util.regex.Pattern;

public interface Alias {
    Alias add(String alias, String pattern);

    Alias add(String alias, Pattern pattern);

    Optional<Pattern> add(String alias);

}

