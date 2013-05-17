package net.sf.chalkbox.wsdef;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import net.sf.chalkbox.ChalkboxBacklog;
import net.sf.iwant.api.model.Path;
import net.sf.iwant.api.model.Target;
import net.sf.iwant.api.model.TargetEvaluationContext;
import net.sf.mybacklog.BacklogAppender;
import net.sf.mybacklog.BacklogDisplay;

public class ChalkboxBacklogTxtTarget extends Target {
	private final Path backlogClasses;

	public ChalkboxBacklogTxtTarget(final Path backlogClasses) {
		super("backlog.txt");
		this.backlogClasses = backlogClasses;
	}

	@Override
	public InputStream content(TargetEvaluationContext ctx) throws Exception {
		return null;
	}

	@Override
	public List<Path> ingredients() {
		final List<Path> ingredients = new ArrayList<Path>();
		ingredients.add(backlogClasses);
		return ingredients;
	}

	@Override
	public void path(TargetEvaluationContext ctx) throws Exception {
		final File dest = ctx.cached(this);
		final FileOutputStream fileOutputStream = new FileOutputStream(dest);
		try {
			new ChalkboxBacklog(new BacklogDisplay() {
				@Override
				public void display(final BacklogAppender appender) {
					try {
						fileOutputStream.write(appender.build().getBytes(
								Charset.defaultCharset()));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}

				}
			}).show();
		} finally {
			fileOutputStream.close();
		}
	}

	@Override
	public String contentDescriptor() {
		return getClass().getCanonicalName() + ":" + ingredients();
	}

}
