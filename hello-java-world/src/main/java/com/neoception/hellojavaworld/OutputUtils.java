package com.neoception.hellojavaworld;

public class OutputUtils {

    public static final String Bar = "==============================================";

    public static String Index() {
        return String.format("<body>%n")
                + Header("Hello Java and Kafka Worlds!")
                + String.format("<p>&nbsp;&nbsp;You have the following options:</p>")
                + String.format("<ul>")
                + String.format("<li><a href='/ping'>Ping!</a></li>")
                + String.format("<li><a href='/kafkaWithApacheLibs'>Kafka with Apache libs</a></li>")
                + String.format("</ul>")
                + String.format("<p>%s<p/>", Bar)
                + String.format("</body>");
    }

    public static String Header( String text ) {
        return String.format("<p>%s<br/>", Bar)
                + String.format("|&nbsp;%s<br/>", text)
                + String.format("%s<p/>", Bar);
    }

    public static String ReturnToIndex() {
        return String.format("<body>")
                + Header("<a href='/'>Return to the index</a>.")
                + String.format("</body>");
    }

    public static String PlainTextAnswer( String answer )
    {
        return ReturnToIndex() + Line(answer);
    }

    public static String Line( String text ) {
        return String.format("%s<br/>", text);
    }
}
