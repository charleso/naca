import nacaLib.callPrg.CalledProgram;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.varEx.DataSection;

public class Goto extends CalledProgram {

	DataSection workingstoragesection = declare.workingStorageSection();

	Section $1 = new Section(this, false);
	Paragraph a = new Paragraph(this);

	public void a() {
		display("a");
		perform($2);
		perform($4);
		exitProgram();
	}

	Section $4 = new Section(this, false);
	Paragraph d = new Paragraph(this);

	public void d() {
		display("d");
		goTo(e);
	}

	Section $6 = new Section(this, false);
	Paragraph g = new Paragraph(this);

	public void g() {
		display("g");
	}

	Paragraph h = new Paragraph(this);

	public void h() {
		display("h");
		exitProgram();
	}

	Section $5 = new Section(this, false);
	Paragraph e = new Paragraph(this);

	public void e() {
		display("e");
	}

	Paragraph f = new Paragraph(this);

	public void f() {
		display("f");
		goTo(g);
	}

	Section $2 = new Section(this, false);
	Paragraph b = new Paragraph(this);

	public void b() {
		display("b");
		goTo(c);
	}

	Section $3 = new Section(this, false);
	Paragraph c = new Paragraph(this);

	public void c() {
		display("c");
	}
}
