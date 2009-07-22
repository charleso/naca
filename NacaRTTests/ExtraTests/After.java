import nacaLib.program.*;
import nacaLib.varEx.*;
import nacaLib.callPrg.CalledProgram;

public class After extends CalledProgram {

	DataSection workingstoragesection = declare.workingStorageSection();
	Var w30_Bin_1 = declare.level(1).picS9(8).comp().var();
	Var w30_Bin_2 = declare.level(1).picS9(8).comp().var();
	Var w30_Bin_3 = declare.level(1).picS9(8).comp().var();

	Section a000_Main = new Section(this, false);
	Paragraph a000 = new Paragraph(this);

	public void a000() {

		for (move(1, w30_Bin_1); isLessOrEqual(w30_Bin_1, 5); inc(w30_Bin_1)) {
			for (move(w30_Bin_1, w30_Bin_2); isLessOrEqual(w30_Bin_2, 6); inc(w30_Bin_2)) {
				for (move(w30_Bin_2, w30_Bin_3); isLessOrEqual(w30_Bin_3, 7); inc(w30_Bin_3)) {
					perform($1221_Check_Triplet);
				}
			}
		}

		stopRun();
	}

	Section $1221_Check_Triplet = new Section(this);

	public void $1221_Check_Triplet() {
		display(w30_Bin_1.getInt() + ":" + w30_Bin_2.getInt() + ":"
				+ w30_Bin_3.getInt());
	}
}
