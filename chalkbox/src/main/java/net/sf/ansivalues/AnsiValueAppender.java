package net.sf.ansivalues;

public class AnsiValueAppender {
	private static final String PREFIX = "\u001b[";
	private static final String SUFFIX = "m";
	private static final String END = PREFIX + SUFFIX;

	@SuppressWarnings("static-method")
	public String append(final String text, final AnsiValue ansiValue) {
		return new StringBuilder(PREFIX).append(ansiValue.value())
				.append(SUFFIX).append(text).append(END).toString();
	}
}
