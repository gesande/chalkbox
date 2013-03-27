package net.sf.chalkbox;

import net.sf.chalkbox.ansi.AnsiValueAppender;
import net.sf.chalkbox.ansi.AnsiValues;

public final class Marker {

    private final AnsiValueAppender ansiValueAppender;

    public Marker() {
        this.ansiValueAppender = new AnsiValueAppender();
    }

    public String mark(final String text) {
        return ansivalueAppender().append(text, AnsiValues.ReverseVideo);
    }

    private AnsiValueAppender ansivalueAppender() {
        return this.ansiValueAppender;
    }

}
