package com.neoception.hellojavaworld;

public class OutputUtils {

    public static String Index() {
        return String.format("<body>%n")
                + Header("Hello Java and Kafka Worlds!")
                + String.format("<p>&nbsp;&nbsp;You have the following options:</p>%n")
                + String.format("<ul>%n")
                + String.format("<li><a href='/ping'>Ping!</a></li>%n")
                + String.format("</ul>%n")
                + String.format("<p>==============================================<p/>%n")
                + String.format("</body>%n");
    }

    public static String Header( String text ) {
        return String.format("<p>==============================================<br/>%n")
                + String.format("|&nbsp;%s<br/>%n", text)
                + String.format("==============================================<p/>%n");
    }

    public static String ReturnToIndex() {
        return String.format("<body>%n")
                + Header("<a href='/'>Return to the index</a>.")
                + String.format("</body>%n");
    }

    public static String PlainTextAnswer( String answer )
    {
        return ReturnToIndex()
                + String.format("%s%n", answer);
    }
}
