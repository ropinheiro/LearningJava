public class Converter {

    // Converts a sentence to a Title Case
    // The first word, plus all other words with more than 4 chars will be capitalized.
    public static String ToTitleCase(String input) {
        StringBuilder builder = new StringBuilder();

        int count = 0;
        for(String word: input.split("\\s")) {
            count++;
            if(word.length() < 4 && count != 1) {
                // Every word (except the first) less than 4 chars will be all lowercase
                builder.append(word.toLowerCase());
            } else {
                // All other words will be capitalized
                builder.append(word.substring(0,1).toUpperCase());
                builder.append(word.substring(1).toLowerCase());
            }
            builder.append(" ");
        }

        return builder.toString();
    }
}