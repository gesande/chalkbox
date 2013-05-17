package net.sf.chalkbox.wsdefdef;

import net.sf.iwant.api.IwantWorkspaceProvider;
import net.sf.iwant.api.WorkspaceDefinitionContext;
import net.sf.iwant.api.javamodules.JavaSrcModule;

public class ChalkboxWorkspaceProvider implements IwantWorkspaceProvider {

	@Override
	public JavaSrcModule workspaceModule(WorkspaceDefinitionContext ctx) {
		return JavaSrcModule.with().name("chalkbox-workspace")
				.locationUnderWsRoot("as-chalkbox-developer/i-have/wsdef")
				.mainJava("src/main/java").mainDeps(ctx.iwantApiModules())
				.end();
	}

	@Override
	public String workspaceClassname() {
		return "net.sf.chalkbox.wsdef.ChalkboxWorkspace";
	}

}
