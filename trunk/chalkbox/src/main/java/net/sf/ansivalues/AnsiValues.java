package net.sf.ansivalues;

public enum AnsiValues implements AnsiValue {
	ReverseVideo {
		@Override
		public int value() {
			return 7;
		}
	};
}