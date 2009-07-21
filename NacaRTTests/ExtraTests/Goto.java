import nacaLib.callPrg.CalledProgram;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.varEx.DataSection;

public class Goto extends CalledProgram {

	DataSection workingstoragesection = declare.workingStorageSection();

	Section a = new Section(this, false);
	Paragraph b = new Paragraph(this);

	public void b() {
		perform(c);
		display("Finished");
		stopRun();
	}

	Section c = new Section(this, false);
	Paragraph d = new Paragraph(this);

	public void d() {
		goTo(f);
	}

	Section e = new Section(this, false);
	Paragraph f = new Paragraph(this);

	public void f() {
	}
}
