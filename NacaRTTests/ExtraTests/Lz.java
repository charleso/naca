import nacaLib.program.*;
import nacaLib.varEx.*;
import nacaLib.callPrg.CalledProgram;

public class Lz extends CalledProgram {

	DataSection workingstoragesection = declare.workingStorageSection();
	Var wx51_Lost_Prod_Match = declare.level(1).picX(10).var();

	Section a000_Main = new Section(this, false);
	Paragraph a000 = new Paragraph(this);

	public void a000() {

		move("   A   ", wx51_Lost_Prod_Match);
		inspectReplacing(wx51_Lost_Prod_Match).leadingSpaces().by("*");
		display(wx51_Lost_Prod_Match);

		move("000A000", wx51_Lost_Prod_Match);
		inspectReplacing(wx51_Lost_Prod_Match).leadingZeros().by("*");
		display(wx51_Lost_Prod_Match);

		stopRun();
	}
}
