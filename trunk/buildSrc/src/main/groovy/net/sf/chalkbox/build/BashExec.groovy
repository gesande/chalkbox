package net.sf.chalkbox.build

import org.gradle.api.tasks.Exec;


public class BashExec extends Exec {

    BashExec() {
        executable="bash"
        args("-c")
    }
}
