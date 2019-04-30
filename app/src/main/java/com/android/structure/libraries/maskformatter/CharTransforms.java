package com.android.structure.libraries.maskformatter;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CharTransforms {

    private static Map<Character, TransformPattern> transformMap = new HashMap<>();

    private static class TransformPattern {

        private Pattern pattern;
        private boolean upperCase;
        private boolean lowerCase;

        public TransformPattern(String pattern, boolean upperCase, boolean lowerCase) {
            this.pattern = Pattern.compile(pattern);
            this.upperCase = upperCase;
            this.lowerCase = lowerCase;
        }

        public char transformChar(char stringChar) throws InvalidTextException {
            char modified;
            if (upperCase) {
                modified = Character.toUpperCase(stringChar);
            } else if (lowerCase) {
                modified = Character.toLowerCase(stringChar);
            } else {
                modified = stringChar;
            }

            if (!pattern.matcher(modified + "").matches()) {
                throw new InvalidTextException();
            }
            return modified;
        }

    }

    static {
        transformMap.put('9', new TransformPattern("[0-9]", false, false));
        transformMap.put('8', new TransformPattern("[0-8]", false, false));
        transformMap.put('7', new TransformPattern("[0-7]", false, false));
        transformMap.put('6', new TransformPattern("[0-6]", false, false));
        transformMap.put('5', new TransformPattern("[0-5]", false, false));
        transformMap.put('4', new TransformPattern("[0-4]", false, false));
        transformMap.put('3', new TransformPattern("[0-3]", false, false));
        transformMap.put('2', new TransformPattern("[0-2]", false, false));
        transformMap.put('1', new TransformPattern("[0-1]", false, false));
        transformMap.put('0', new TransformPattern("[0]", false, false));

        transformMap.put('*', new TransformPattern(".", false, false));
        transformMap.put('W', new TransformPattern("\\W", false, false));
        transformMap.put('d', new TransformPattern("\\d", false, false));
        transformMap.put('D', new TransformPattern("\\D", false, false));
        transformMap.put('s', new TransformPattern("\\s", false, false));
        transformMap.put('S', new TransformPattern("\\S", false, false));

        transformMap.put('A', new TransformPattern("[A-Z]", true, false));
        transformMap.put('a', new TransformPattern("[a-z]", false, true));

        transformMap.put('Z', new TransformPattern("[A-ZÇÀÁÂÃÈÉÊẼÌÍÎĨÒÓÔÕÙÚÛŨ]", true, false));
        transformMap.put('z', new TransformPattern("[a-zçáàãâéèêẽíìĩîóòôõúùũüû]", false, true));

        transformMap.put('@', new TransformPattern("[a-zA-Z]", false, false));
        transformMap.put('#', new TransformPattern("[A-Za-zçáàãâéèêẽíìĩîóòôõúùũüûÇÀÁÂÃÈÉÊẼÌÍÎĨÒÓÔÕÙÚÛŨ]", false, false));

        transformMap.put('%', new TransformPattern("[A-Z0-9]", true, false));
        transformMap.put('w', new TransformPattern("\\w", false, false));
    }

    public static char transformChar(char stringChar, char maskChar) throws InvalidTextException {
        TransformPattern transform = transformMap.get(maskChar);
        if (transform == null) {
            return stringChar;
        }

        return transform.transformChar(stringChar);
    }

}