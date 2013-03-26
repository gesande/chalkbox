package net.sf.chalkbox;

import net.sf.chalkbox.ansi.AnsiTextColor;
import net.sf.chalkbox.ansi.AnsiValue;
import net.sf.chalkbox.ansi.AnsiValueAppender;

@SuppressWarnings("static-method")
public final class ChalkBox {

    private final static AnsiValueAppender ansiValueAppender = new AnsiValueAppender();

    public ChalkBox() {
    }

    public Chalk black() {
        return Chalks.Black;
    }

    public Chalk red() {
        return Chalks.Red;
    }

    public Chalk green() {
        return Chalks.Green;
    }

    public Chalk yellow() {
        return Chalks.Yellow;
    }

    public Chalk blue() {
        return Chalks.Blue;
    }

    public Chalk magenta() {
        return Chalks.Magenta;
    }

    public Chalk cyan() {
        return Chalks.Cyan;
    }

    public Chalk white() {
        return Chalks.White;
    }

    private enum Chalks implements Chalk {
        Black(AnsiTextColor.Black), Red(AnsiTextColor.Red), Green(
                AnsiTextColor.Green), Yellow(AnsiTextColor.Yellow), Blue(
                AnsiTextColor.Blue), Magenta(AnsiTextColor.Magenta), Cyan(
                AnsiTextColor.Cyan), White(AnsiTextColor.White);

        private AnsiValue color;

        private Chalks(final AnsiValue color) {
            this.color = color;
        }

        @Override
        public String write(final String text) {
            return ansiValueAppender().append(text, ansiColor());
        }

        private AnsiValue ansiColor() {
            return this.color;
        }
    }

    private static AnsiValueAppender ansiValueAppender() {
        return ansiValueAppender;
    }
}
