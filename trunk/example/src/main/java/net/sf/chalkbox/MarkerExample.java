package net.sf.chalkbox;


public class MarkerExample {
    public static void main(String[] args) {
        final ChalkBox box = new ChalkBox();
        final StringBuilder sb = new StringBuilder();
        final Marker marker = new Marker();
        appendText(sb, marker.mark("marked plain"));
        appendText(sb, box.black(), marker, "marked");
        appendText(sb, box.red(), marker, "marked");
        appendText(sb, box.green(), marker, "marked");
        appendText(sb, box.yellow(), marker, "marked");
        appendText(sb, box.blue(), marker, "marked");
        appendText(sb, box.magenta(), marker, "marked");
        appendText(sb, box.cyan(), marker, "marked");
        appendText(sb, box.white(), marker, "marked");
        System.out.println(sb.toString());
    }

    private static void appendText(final StringBuilder sb, final Chalk chalk,
            final Marker marker, final String text) {
        appendText(sb, marker.mark(chalk.write(text)));
    }

    private static void appendText(final StringBuilder sb, final String text) {
        sb.append("this is ").append(text).append(" text").append("\n");
    }

}
