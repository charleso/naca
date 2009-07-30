import nacaLib.batchPrgEnv.BatchProgram;
import nacaLib.program.Section;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.FileDescriptor;
import nacaLib.varEx.Var;

public class Batch extends BatchProgram {

	DataSection workingstoragesection = declare.workingStorageSection();
	Var w60_Rpt_Filename_1 = declare.level(1).picX(48).value("batch.report")
			.var();
	Var w60_Rpt_Status_1 = declare.level(1).picX(2).var();

	DataSection filesection = declare.fileSection();
	FileDescriptor msrfil_1 = declare.file(w60_Rpt_Filename_1).status(
			w60_Rpt_Status_1);
	Var msrfil_Report_Line_1 = declare.level(1).picX(132).var();

	Section main = new Section(this);

	public void main() {
		msrfil_1.openOutput();
		msrfil_Report_Line_1.set("hello world");
		write(msrfil_1);
		msrfil_Report_Line_1.set("hello world again");
		writeAfter(msrfil_1, 2);
		msrfil_Report_Line_1.set("hello world new page");
		writeAfter(msrfil_1, -1);
		close(msrfil_1);
	}
}
