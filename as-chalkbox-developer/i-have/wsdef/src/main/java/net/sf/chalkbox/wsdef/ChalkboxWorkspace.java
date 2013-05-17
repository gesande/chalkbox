package net.sf.chalkbox.wsdef;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.iwant.api.EclipseSettings;
import net.sf.iwant.api.EmmaTargetsOfJavaModules;
import net.sf.iwant.api.IwantWorkspace;
import net.sf.iwant.api.SideEffectDefinitionContext;
import net.sf.iwant.api.TestedIwantDependencies;
import net.sf.iwant.api.javamodules.CodeStyle;
import net.sf.iwant.api.javamodules.CodeStylePolicy;
import net.sf.iwant.api.javamodules.JavaBinModule;
import net.sf.iwant.api.javamodules.JavaModule;
import net.sf.iwant.api.javamodules.JavaSrcModule;
import net.sf.iwant.api.model.SideEffect;
import net.sf.iwant.api.model.Source;
import net.sf.iwant.api.model.StringFilter;
import net.sf.iwant.api.model.Target;

public class ChalkboxWorkspace implements IwantWorkspace {

	@Override
	public List<? extends Target> targets() {
		return Arrays.asList(emmaCoverage());
	}

	@Override
	public List<? extends SideEffect> sideEffects(
			SideEffectDefinitionContext ctx) {
		return Arrays.asList(EclipseSettings.with().name("eclipse-settings")
				.modules(ctx.wsdefdefJavaModule(), ctx.wsdefJavaModule())
				.modules(allSrcModules()).end());
	}

	static class TestClassNameFilter implements StringFilter {

		@Override
		public boolean matches(String candidate) {
			return candidate.matches(".*Test$")
					&& !candidate.matches(".*Abstract[^.]*Test$");
		}

		@Override
		public String toString() {
			return getClass().getCanonicalName();
		}

	}

	private static Target emmaCoverage() {
		EmmaTargetsOfJavaModules emmaTargets = EmmaTargetsOfJavaModules
				.with()
				.antJars(TestedIwantDependencies.antJar(),
						TestedIwantDependencies.antLauncherJar())
				.emma(TestedIwantDependencies.emma()).modules(allSrcModules())
				.end();
		return emmaTargets.emmaReport();
	}

	private static SortedSet<JavaSrcModule> allSrcModules() {
		return new TreeSet<JavaSrcModule>(Arrays.asList(chalkbox()));
	}

	private static JavaSrcModule chalkbox() {
		return JavaSrcModule.with().name("chalkbox").mavenLayout().mainDeps()
				.testDeps(junit2()).testedBy(new TestClassNameFilter())
				.codeStyle(chalkboxCodeStylePolicy()).end();
	}

	private static CodeStylePolicy chalkboxCodeStylePolicy() {
		return CodeStylePolicy.defaultsExcept()
				.warn(CodeStyle.REPORT_METHOD_CAN_BE_POTENTIALLY_STATIC).end();
	}

	private static JavaModule junit() {
		return JavaBinModule
				.providing(
						Source.underWsroot("junit-4.10/lib/junit-4.10.jar"),
						Source.underWsroot("junit-4.10/lib-sources/junit-4.10-src.jar"));
	}

	private static JavaModule junit2() {
		return JavaBinModule.named("lib/junit-4.10.jar")
				.source("lib-sources/junit-4.10-src.jar")
				.inside(JavaSrcModule.with().name("junit-4.10").end());
	}

	// private static JavaModule junit3() {
	// return
	// JavaSrcModule.with().exportsClasses(Source.underWsroot("junit-4.10/"),
	// Source.underWsroot("junit-4.10/");
	// }

}
