package com.knubisoft;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class AliasDefault {
    private static final String GOOD_IRI_CHAR = "a-zA-Z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";
    private static final String IP_ADDRESS = "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
            + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
            + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
            + "|[1-9][0-9]|[0-9]))";
    private static final String IRI = "[" + GOOD_IRI_CHAR + "]([" + GOOD_IRI_CHAR + "\\-]{0,61}[" + GOOD_IRI_CHAR + "]){0,1}";
    private static final String GOOD_GTLD_CHAR = "a-zA-Z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";
    private static final String GTLD = "[" + GOOD_GTLD_CHAR + "]{2,63}";
    private static final String HOST_NAME = "(" + IRI + "\\.)+" + GTLD;
    private static final String DOMAIN_NAME = "(" + HOST_NAME + "|" + IP_ADDRESS + ")";
    private static final String WEB_URL = "((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
            + "\\,\\;\\?&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
            + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
            + "(?:" + DOMAIN_NAME + ")"
            + "(?:\\:\\d{1,5})?)"
            + "(\\/(?:(?:[" + GOOD_IRI_CHAR + "\\;\\/\\?\\:\\@\\&\\=\\#\\~"
            + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
            + "(?:\\b|$)";
    private static final String year = "\\d{4}";
    private static final String month = "(?:0[1-9]|1[012])";
    private static final String day = "(?:0[1-9]|[12][0-9]|3[01])";
    private static final String digits = "[1-9]\\d*";

    static Map<String, Pattern> aliases = new HashMap<>();

    static {
        aliases.put("any", Pattern.compile(".*"));
        aliases.put("digit", Pattern.compile("0|[-+]?" + digits));
        aliases.put(">0", Pattern.compile("[+]?" + digits));
        aliases.put(">=0", Pattern.compile("0|[+]?" + digits));
        aliases.put("<0", Pattern.compile("-" + digits));
        aliases.put("<=0", Pattern.compile("0|-" + digits));
        aliases.put("money", Pattern.compile("0|[-+]?[1-9]\\d*([,\\.]\\d+)?"));
        aliases.put("color", Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"));
        aliases.put("notEmpty", Pattern.compile(".+"));
        aliases.put("email", email());
        aliases.put("uuid", uuid());
        aliases.put("url", url());
        aliases.put("ip", ip());
        aliases.put("y-m-d", ymd("-"));
        aliases.put("d-m-y", dmy("-"));
        aliases.put("y/m/d", ymd("/"));
        aliases.put("d/m/y", dmy("/"));
        aliases.put("datetime", dateTime());
    }

    private static Pattern uuid() {
        return Pattern.compile("\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}");
    }

    private static Pattern url() {
        return Pattern.compile(WEB_URL);
    }

    private static Pattern email() {
        return Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+");
    }

    private static Pattern ip() {
        return Pattern.compile(IP_ADDRESS);
    }

    private static Pattern dateTime() {
        return Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}(?:.\\d)?");
    }

    private static Pattern ymd(String s) {
        return Pattern.compile(year + s + month + day);
    }

    private static Pattern dmy(String s) {
        return Pattern.compile(day + s + month + s + year);
    }
}
