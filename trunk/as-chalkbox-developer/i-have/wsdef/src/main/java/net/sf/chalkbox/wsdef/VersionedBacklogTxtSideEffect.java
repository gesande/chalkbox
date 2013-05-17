package net.sf.chalkbox.wsdef;

import java.io.File;

import net.sf.iwant.api.model.SideEffect;
import net.sf.iwant.api.model.SideEffectContext;
import net.sf.iwant.api.model.Target;

import org.apache.commons.io.FileUtils;

public class VersionedBacklogTxtSideEffect implements SideEffect {

	private final Target backlogTxt;

	public VersionedBacklogTxtSideEffect(final Target backlogTxt) {
		this.backlogTxt = backlogTxt;

	}

	@Override
	public String name() {
		return "versioned-backlogtxt";
	}

	@Override
	public void mutate(SideEffectContext ctx) throws Exception {
		FileUtils.copyFile(ctx.iwantAsPath(backlogTxt), new File(ctx.wsRoot(), "backlog.txt"));
	}

}
