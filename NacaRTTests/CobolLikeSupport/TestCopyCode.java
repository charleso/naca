import idea.onlinePrgEnv.OnlineProgram;

/**
 * 
 */

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class TestCopyCode extends OnlineProgram
{
	private TestCopyCodeContainerIntf m_container = null;
	
	public void run(TestCopyCodeContainerIntf container)
	{
		m_container = container;
		doRun();
	}
	
	private void doRun()
	{
		System.out.println("In Copy inline call");
		move("FROMCOPY", m_container.getVX10());
		System.out.println("Before performing Sub from Copy inline call");
		perform(m_container.getSub());
		System.out.println("After performing Sub from Copy inline call");		
	}
}
