package net.sf.c64assembly;

import static org.pirinen.c64dslj.builder.WordHex2.$2000;
import static org.pirinen.c64dslj.builder.WordHexD.$d020;
import static org.pirinen.c64dslj.model.Instruction.INC;
import static org.pirinen.c64dslj.model.Instruction.JMP;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.pirinen.c64dslj.model.Program;

public class Flicker {
    public static void main(String[] args) throws IOException {
        Program p = Program.with().i(INC.absolute($d020))
                .i(JMP.absolute($2000)).build($2000);
        OutputStream out = new FileOutputStream("/tmp/example.cbm");
        p.toCbmFormat(out);
        out.close();
    }
}
