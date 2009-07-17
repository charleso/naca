import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jlib.log.Log;
import jlib.log.LogCenterConsole;
import jlib.log.LogCenterLoader;
import jlib.log.LogFlowStd;
import jlib.log.LogLevel;
import jlib.log.LogParams;
import jlib.log.PatternLayoutConsole;
import jlib.misc.BasePic9Comp3BufferSupport;
import junit.framework.TestCase;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.callPrg.CalledEnvironment;
import nacaLib.callPrg.CalledProgramLoader;
import nacaLib.callPrg.CalledResourceManager;
import nacaLib.calledPrgSupport.BaseCalledPrgPublicArgPositioned;
import nacaLib.tempCache.TempCacheLocator;

public class TestSuite extends TestCase {

	public void test() {
		_test("msbtst");
	}

	private void _test(String name) {
		final StringBuilder b = new StringBuilder();
		LogCenterConsole logCenter = new LogCenterConsole(
				new LogCenterLoader() {
					{
						m_logLevel = LogLevel.Normal;
						m_logFlow = LogFlowStd.Any;
						m_csChannel = "NacaRT";
					}
				}) {
			@Override
			protected void sendOutput(LogParams logParam) {
				b.append(logParam.toString()).append("\n");
			}
		};
		logCenter.setPatternLayout(new PatternLayoutConsole("%Message"));
		Log.registerLogCenter(logCenter);
		BasePic9Comp3BufferSupport.init();
		TempCacheLocator.setTempCache();
		BaseProgramLoader programLoader = new CalledProgramLoader(null, null);
		CalledEnvironment env = new CalledEnvironment(null, null,
				new CalledResourceManager());
		ArrayList<BaseCalledPrgPublicArgPositioned> params = new ArrayList<BaseCalledPrgPublicArgPositioned>();
		String pname = Character.toUpperCase(name.charAt(0))
				+ name.substring(1).toLowerCase();
		env.setNextProgramToLoad(pname);
		programLoader.runTopProgram(env, params);
		StringBuilder e = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					getClass().getResourceAsStream(name + ".out")));
			for (String line; (line = reader.readLine()) != null;)
				e.append(line).append("\n");
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		assertEquals(e.toString(), b.toString());
	}
}
