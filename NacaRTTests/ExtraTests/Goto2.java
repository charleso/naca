import nacaLib.callPrg.CalledProgram;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.varEx.DataSection;

/**
 * After running 'reduce' over a program, without the extra start paragraph for
 * each section a goto outside of your section would result in some strange
 * behaviour.
 */
public class Goto2 extends CalledProgram {

	DataSection workingstoragesection = declare.workingStorageSection();
	private int counter = 0;

	Paragraph pd_Ellipsej_Begin_Init = new Paragraph(this);

	public void pd_Ellipsej_Begin_Init() {
		display("pd_Ellipsej_Begin_Init");
	}

	Section re_Entry = new Section(this);

	public void re_Entry() {
		display("re_Entry");
	}

	Paragraph re_Entry_Exit = new Paragraph(this);

	public void re_Entry_Exit() {
		display("re_Entry_Exit");
	}

	Section a000_Main = new Section(this);

	public void a000_Main() {
		display("a000_Main");
		if (counter == 1) {
			exitProgram();
		}
		counter++;
		perform($0000_Check_Program_Counter);
		goTo(re_Entry_Exit);
	}

	Section $0000_Check_Program_Counter = new Section(this);

	public void $0000_Check_Program_Counter() {
		display("$0000_Check_Program_Counter");
	}

}
