package net.sf.chalkbox.wsdef;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
import net.sf.iwant.api.javamodules.JavaClassesAndSources;
import net.sf.iwant.api.javamodules.JavaModule;
import net.sf.iwant.api.javamodules.JavaSrcModule;
import net.sf.iwant.api.javamodules.JavaSrcModule.IwantSrcModuleSpex;
import net.sf.iwant.api.model.Path;
import net.sf.iwant.api.model.SideEffect;
import net.sf.iwant.api.model.Source;
import net.sf.iwant.api.model.StringFilter;
import net.sf.iwant.api.model.Target;
import net.sf.iwant.plugin.findbugs.FindbugsDistribution;
import net.sf.iwant.plugin.findbugs.FindbugsOutputFormat;
import net.sf.iwant.plugin.findbugs.FindbugsReport;
import net.sf.iwant.plugin.findbugs.FindbugsReport.FindbugsReportSpex;

public class ChalkboxWorkspace implements IwantWorkspace {

	@Override
	public List<? extends Target> targets() {
		return Arrays.asList(emmaCoverage(), backlogTxt(), findbugsReport());
	}

	private static Target findbugsReport() {
		return findbugsReportFor("findbugs-report", FindbugsOutputFormat.HTML);
	}

	private static Target findbugsReportFor(String name,
			FindbugsOutputFormat outputFormat) {
		FindbugsDistribution findbugs = FindbugsDistribution.ofVersion("2.0.2");
		FindbugsReportSpex report = FindbugsReport.with().name(name)
				.using(findbugs, ant(), antLauncher())
				.outputFormat(outputFormat);

		FindbugsReportHelper.reportWithModules(report, allSrcModules());
		return report.end();
	}

	private static Path ant() {
		return TestedIwantDependencies.antJar();
	}

	private static Path antLauncher() {
		return TestedIwantDependencies.antLauncherJar();
	}

	private static class FindbugsReportHelper {

		public static FindbugsReportSpex reportWithModules(
				FindbugsReportSpex report,
				SortedSet<? extends JavaModule> modules) {
			for (JavaModule module : modules) {
				reportWithModule(report, module);
			}
			return report;
		}

		private static void reportWithModule(FindbugsReportSpex report,
				JavaModule module) {
			Set<JavaModule> allDeps = module.effectivePathForTestRuntime();
			for (JavaModule dep : allDeps) {
				if (!(dep instanceof JavaSrcModule)) {
					report.auxClasses(dep.mainArtifact());
					continue;
				}
				JavaSrcModule srcDep = (JavaSrcModule) dep;
				if (srcDep.mainArtifact() != null) {
					report.classesToAnalyze(new JavaClassesAndSources(srcDep
							.mainArtifact(), srcDep.mainJavasAsPaths()));
				}
				if (srcDep.testArtifact() != null) {
					report.classesToAnalyze(new JavaClassesAndSources(srcDep
							.testArtifact(), srcDep.testJavasAsPaths()));
				}
			}
		}

	}

	@Override
	public List<? extends SideEffect> sideEffects(
			SideEffectDefinitionContext ctx) {
		return Arrays.asList(EclipseSettings.with().name("eclipse-settings")
				.modules(ctx.wsdefdefJavaModule(), ctx.wsdefJavaModule())
				.modules(allSrcModules()).end(), versionedBacklogTxt());
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

	private static SideEffect versionedBacklogTxt() {
		return new VersionedBacklogTxtSideEffect(backlogTxt());
	}

	private static Target backlogTxt() {
		return new ChalkboxBacklogTxtTarget(backlog().mainArtifact());
	}

	private static Target emmaCoverage() {
		return emmaTarget(allSrcModules(), "coverage-report");
	}

	private static Target emmaTarget(final SortedSet<JavaSrcModule> modules,
			String name) {
		final EmmaTargetsOfJavaModules emmaTargets = EmmaTargetsOfJavaModules
				.with()
				.antJars(TestedIwantDependencies.antJar(),
						TestedIwantDependencies.antLauncherJar())
				.emma(TestedIwantDependencies.emma()).modules(modules).end();
		return emmaTargets.emmaReport(name);
	}

	private static SortedSet<JavaSrcModule> allSrcModules() {
		return asTreeSet(Arrays.asList(backlog(), chalkbox(), example()));
	}

	private static SortedSet<JavaSrcModule> asTreeSet(
			final List<JavaSrcModule> asList) {
		return new TreeSet<JavaSrcModule>(asList);
	}

	private static JavaSrcModule chalkbox() {
		return chalkboxSrcModule().name("chalkbox").mainDeps()
				.testDeps(junit()).end();
	}

	private static IwantSrcModuleSpex chalkboxSrcModule() {
		return JavaSrcModule.with().mavenLayout()
				.codeStyle(chalkboxCodeStylePolicy())
				.testedBy(new TestClassNameFilter());
	}

	private static CodeStylePolicy chalkboxCodeStylePolicy() {
		return CodeStylePolicy.defaultsExcept()
				.warn(CodeStyle.REPORT_METHOD_CAN_BE_POTENTIALLY_STATIC).end();
	}

	@SuppressWarnings("unused")
	private static JavaModule junitAsProviding() {
		return JavaBinModule
				.providing(
						Source.underWsroot("junit-4.10/lib/junit-4.10.jar"),
						Source.underWsroot("junit-4.10/lib-sources/junit-4.10-src.jar"))
				.end();
	}

	private static JavaModule junit() {
		return JavaBinModule.named("lib/junit-4.10.jar")
				.source("lib-sources/junit-4.10-src.jar")
				.inside(JavaSrcModule.with().name("junit-4.10").end()).end();
	}

	private static JavaSrcModule example() {
		return JavaSrcModule.with().name("example").mainJava("src/main/java")
				.codeStyle(chalkboxCodeStylePolicy()).mainDeps(chalkbox())
				.end();
	}

	private static JavaSrcModule backlog() {
		return JavaSrcModule.with().name("backlog").mainJava("src/main/java")
				.codeStyle(chalkboxCodeStylePolicy()).mainDeps(myBacklog())
				.end();
	}

	private static JavaModule myBacklog() {
		return JavaBinModule.named("lib/my-backlog-1.0.2.jar")
				.source("lib-sources/my-backlog-1.0.2-sources.jar")
				.inside(JavaSrcModule.with().name("backlog").end()).end();
	}
}
