package net.sf.chalkbox;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MarkerTest {

    @SuppressWarnings("static-method")
    @Test
    public void mark() {
        final Marker marker = new Marker();
        assertEquals("[7mtext[m", marker.mark("text"));
    }

    @SuppressWarnings("static-method")
    @Test
    public void markRedText() {
        final Marker marker = new Marker();
        final ChalkBox box = new ChalkBox();
        assertEquals("[7m[31mtext[m[m",
                marker.mark(box.red().write("text")));
    }
}
