package net.sf.chalkbox;

import static net.sf.chalkbox.BacklogTag.backlogging;
import static net.sf.chalkbox.BacklogTag.build;
import static net.sf.chalkbox.BacklogTag.developmentSupport;
import static net.sf.chalkbox.BacklogTag.feature;
import static net.sf.chalkbox.BacklogTag.refactoring;
import net.sf.mybacklog.AbstractBacklogging;
import net.sf.mybacklog.Backlog;
import net.sf.mybacklog.DefaultBacklogFactory;
import net.sf.mybacklog.SysoutBacklogDisplay;

public class ChalkboxBacklog extends AbstractBacklogging {

    public static void main(String[] args) {
        new ChalkboxBacklog().show();
    }

    public void show() {
        newBacklog()
                .title("Chalkbox backlog")

                .done()
                .title("DONE")
                .tasks(done("extracted chalkbox from perftence", feature),
                        done("extracted ansi stuff into its own package",
                                refactoring),
                        done("introduced marker", feature),
                        done("introduced examples for creating screenshot",
                                developmentSupport),
                        done("introduced example module", developmentSupport),
                        done("introduced two separate examples",
                                developmentSupport),
                        done("provide gradle stuff for running all examples",
                                developmentSupport),
                        done("provided java backlogging using mybacklog",
                                backlogging),
                        done("applied my-gradle-build-1.0.3", build)

                )
                .inProgress()
                .title("IN PROGRESS")
                .noTasks()
                .

                waiting()
                .title("WAITING")
                .tasks(waiting(
                        "provide shell scripts for running examples (using distribution)",
                        developmentSupport),
                        waiting("take a look at http://git.springsource.org/spring-security/spring-security/blobs/5fe589e36074d51409c9ad5f87daf6564877534c/gradle/emma.gradle",
                                build)).

                show();
    }

    @Override
    protected Backlog newBacklog() {
        return DefaultBacklogFactory.displayedBy(new SysoutBacklogDisplay())
                .newBacklog();
    }
}