package cs601.project0;

public class Strings {

    /** Checks if a given string is null or empty
     * @param s string to compare
     * @return true if string is null or empty else false
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isBlank() || s.isEmpty();
    }
}
