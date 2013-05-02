package net.sf.c64assembly;

import static org.pirinen.c64dslj.builder.WordHex1.$1200;
import static org.pirinen.c64dslj.builder.WordHex9.$900f;
import static org.pirinen.c64dslj.model.Instruction.INC;
import static org.pirinen.c64dslj.model.Instruction.JMP;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.pirinen.c64dslj.model.Program;

public class FlickerVic {
    public static void main(String[] args) throws IOException {
        Program p = Program.with().i(INC.absolute($900f))
                .i(JMP.absolute($1200)).build($1200);
        OutputStream out = new FileOutputStream("/tmp/example.vic");
        p.toCbmFormat(out);
        out.close();
    }

}
