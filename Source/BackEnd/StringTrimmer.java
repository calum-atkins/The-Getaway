package BackEnd;

    /**
    *Small class to trim off certain parts of a String
     * FYI This is not made by the group but is code from StackOverflow
    */
    public class StringTrimmer {
        public static String trim(String string, char ch){
            return trim(string, ch, ch);
        }

        public static String trim(String string, char leadingChar, char trailingChar){
            return string.replaceAll("^["+leadingChar+"]+|["+trailingChar+"]+$", "");
        }

        public static String trim(String string, String regex){
            return trim(string, regex, regex);
        }

        public static String trim(String string, String leadingRegex, String trailingRegex){
            return string.replaceAll("^("+leadingRegex+")+|("+trailingRegex+")+$", "");
        }
}
