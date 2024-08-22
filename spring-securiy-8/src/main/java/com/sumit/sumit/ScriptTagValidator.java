package com.sumit.sumit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptTagValidator {

    // Regex pattern to detect script tags (case-insensitive)
    private static final String SCRIPT_TAG_PATTERN = "(?i)<script[^>]*>.*?</script>";

    public static boolean containsScriptTag(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(SCRIPT_TAG_PATTERN);
        Matcher matcher = pattern.matcher(input);

        // Returns true if any script tags are found
        return matcher.find();
    }

    public static void main(String[] args) {
        String testString1 = "<p>This is a test string.</p>";
        String testString2 = "<script>alert('This is a script');</script>";
        String testString3 = "<div><SCRIPT>alert('Script with different case');</SCRIPT></div>";

        System.out.println("Test 1 contains script tag: " + containsScriptTag(testString1)); // false
        System.out.println("Test 2 contains script tag: " + containsScriptTag(testString2)); // true
        System.out.println("Test 3 contains script tag: " + containsScriptTag(testString3)); // true
    }
}