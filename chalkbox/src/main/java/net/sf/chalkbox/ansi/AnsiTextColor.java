package net.sf.chalkbox.ansi;

public enum AnsiTextColor implements AnsiValue {
    Black, Red, Green, Yellow, Blue, Magenta, Cyan, White;

    @Override
    public int value() {
        return ordinal() + 30;
    }
}