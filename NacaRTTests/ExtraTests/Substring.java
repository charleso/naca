import nacaLib.program.*;
import nacaLib.varEx.*;
import nacaLib.callPrg.CalledProgram;

public class Substring extends CalledProgram {

	DataSection workingstoragesection = declare.workingStorageSection();
	Var ignoreMe = declare.level(1).picX(10).var();
	Var ws_Out_String = declare.level(1).picX(10).var();

	Section a000_Main = new Section(this, false);
	Paragraph a000 = new Paragraph(this);

	public void a000() {
		move("     ", ws_Out_String);
		inspectReplacing(subString(ws_Out_String, 3)).leadingSpaces().by("0");
		display(subString(ws_Out_String, 3));
		display(ws_Out_String);
		move("1234567890", ws_Out_String);
		move(subString(ws_Out_String, 2), ignoreMe);
		display(ignoreMe);
		stopRun();
	}
}
