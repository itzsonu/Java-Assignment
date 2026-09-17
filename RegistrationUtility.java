public class RegistrationUtility {

    public static void main(String[] args) {
        String name = "  aSHA kumar rao ";
        String reg = "PES20251234";

        System.out.println("normalizeName -> " + normalizeName(name));
        System.out.println("createId -> " + createId(name, reg));
        System.out.println("maskRegistration -> " + maskRegistration(reg));
    }

    public static String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        StringBuilder collapsed = new StringBuilder();
        boolean inSpace = false;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                if (!inSpace) {
                    collapsed.append(' ');
                    inSpace = true;
                }
            } else {
                collapsed.append(c);
                inSpace = false;
            }
        }

        String trimmed = collapsed.toString().trim();
        String[] words = trimmed.split(" ");

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(' ');
            }
            result.append(capitalize(words[i]));
        }

        return result.toString();
    }

    private static String capitalize(String word) {
        if (word.isEmpty()) {
            return word;
        }
        char first = Character.toUpperCase(word.charAt(0));
        String rest = word.substring(1).toLowerCase();
        return first + rest;
    }

    public static String createId(String name, String registration) {
        String normalized = normalizeName(name);
        String digits = extractDigits(registration);

        if (digits.length() < 4) {
            throw new IllegalArgumentException("Registration must have at least 4 digits");
        }

        String[] words = normalized.split(" ");
        StringBuilder id = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                id.append(Character.toLowerCase(word.charAt(0)));
            }
        }

        id.append(digits.substring(digits.length() - 4));

        return id.toString().toLowerCase();
    }

    public static String maskRegistration(String registration) {
        String digits = extractDigits(registration);

        if (digits.length() < 4) {
            throw new IllegalArgumentException("Registration must have at least 4 digits");
        }

        if (registration == null || registration.isEmpty()) {
            throw new IllegalArgumentException("Registration cannot be blank");
        }

        int digitCount = digits.length();
        int keepFrom = digitCount - 4;
        int seenDigits = 0;

        StringBuilder masked = new StringBuilder();

        for (int i = 0; i < registration.length(); i++) {
            char c = registration.charAt(i);
            if (Character.isDigit(c)) {
                if (seenDigits < keepFrom) {
                    masked.append('*');
                } else {
                    masked.append(c);
                }
                seenDigits++;
            } else {
                masked.append(c);
            }
        }

        return masked.toString();
    }

    private static String extractDigits(String registration) {
        if (registration == null || registration.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration cannot be blank");
        }

        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < registration.length(); i++) {
            char c = registration.charAt(i);
            if (Character.isDigit(c)) {
                digits.append(c);
            }
        }
        return digits.toString();
    }
}