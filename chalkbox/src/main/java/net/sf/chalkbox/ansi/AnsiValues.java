package net.sf.chalkbox.ansi;

public enum AnsiValues implements AnsiValue {
    ReverseVideo {
        @Override
        public int value() {
            return 7;
        }
    };
}