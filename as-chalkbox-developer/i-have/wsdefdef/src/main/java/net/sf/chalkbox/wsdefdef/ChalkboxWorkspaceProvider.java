package net.sf.chalkbox.wsdefdef;

import net.sf.iwant.api.IwantWorkspaceProvider;
import net.sf.iwant.api.WorkspaceDefinitionContext;
import net.sf.iwant.api.javamodules.JavaBinModule;
import net.sf.iwant.api.javamodules.JavaModule;
import net.sf.iwant.api.javamodules.JavaSrcModule;

public class ChalkboxWorkspaceProvider implements IwantWorkspaceProvider {

	@Override
	public JavaSrcModule workspaceModule(WorkspaceDefinitionContext ctx) {
		return JavaSrcModule.with().name("chalkbox-workspace")
				.locationUnderWsRoot("as-chalkbox-developer/i-have/wsdef")
				.mainJava("src/main/java").mainDeps(ctx.iwantApiModules())
				.mainDeps(backlog(), myBacklog()).end();
	}

	@Override
	public String workspaceClassname() {
		return "net.sf.chalkbox.wsdef.ChalkboxWorkspace";
	}

	private static JavaSrcModule backlog() {
		return JavaSrcModule.with().name("backlog").mavenLayout()
				.mainDeps(myBacklog()).end();
	}

	private static JavaModule myBacklog() {
		return JavaBinModule.named("lib/my-backlog-1.0.2.jar")
				.source("lib-sources/my-backlog-1.0.2-sources.jar")
				.inside(JavaSrcModule.with().name("backlog").end());
	}

}
