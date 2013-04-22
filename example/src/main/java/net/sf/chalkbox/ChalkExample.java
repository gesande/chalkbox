package net.sf.chalkbox;

public class ChalkExample {
    public static void main(String[] args) {
        final ChalkBox box = new ChalkBox();
        final StringBuilder sb = new StringBuilder();
        appendText(sb, box.black(), "black");
        appendText(sb, box.red(), "red");
        appendText(sb, box.green(), "green");
        appendText(sb, box.yellow(), "yellow");
        appendText(sb, box.blue(), "blue");
        appendText(sb, box.magenta(), "magenta");
        appendText(sb, box.cyan(), "cyan");
        appendText(sb, box.white(), "white");
        System.out.println(sb.toString());
    }

    private static void appendText(final StringBuilder sb, final Chalk chalk,
            final String text) {
        appendText(sb, chalk.write(text));
    }

    private static void appendText(final StringBuilder sb, final String text) {
        sb.append("this is ").append(text).append(" text").append("\n");
    }
}
