package net.sf.chalkbox;

import net.sf.chalkbox.ansi.AnsiValue;
import net.sf.chalkbox.ansi.AnsiValueAppender;

public final class Marker {

    private final static ReverseVideo reverseVideo = new ReverseVideo();
    private final AnsiValueAppender ansiValueAppender;

    public Marker() {
        this.ansiValueAppender = new AnsiValueAppender();
    }

    private static class ReverseVideo implements AnsiValue {
        @Override
        public int value() {
            return 7;
        }
    }

    public String mark(final String text) {
        return ansivalueAppender().append(text, reverseVideo());
    }

    private static ReverseVideo reverseVideo() {
        return reverseVideo;
    }

    private AnsiValueAppender ansivalueAppender() {
        return this.ansiValueAppender;
    }

}
