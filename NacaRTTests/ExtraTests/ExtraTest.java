import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import nacaLib.callPrg.CalledSession;
import nacaLib.calledPrgSupport.BaseCalledPrgPublicArgPositioned;
import nacaLib.tempCache.TempCacheLocator;

public class ExtraTest extends TestCase {

	public void test() {
		_test("msbtst");
	}

	public void testIfaai() {
		_test("ifaai");
	}

	public void testMath() {
		_test("math");
	}

	public void testCond() {
		_test("condtest");
	}

	public void testLeadingZeroes() {
		_test("lz");
	}

	public void testSubstring() {
		_test("substring");
	}

	public void testGoto() {
		_test("goto");
	}

	public void testGoto2() {
		_test("goto2");
	}

	public void testAfter() {
		_test("after");
	}

	public void testBatch() {
		_test("batch", true);
	}

	private void _test(String name) {
		_test(name, false);
	}

	private void _test(String name, boolean batch) {
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
		CalledEnvironment env = new CalledEnvironment(new CalledSession(
				new CalledResourceManager()), null, new CalledResourceManager());
		ArrayList<BaseCalledPrgPublicArgPositioned> params = new ArrayList<BaseCalledPrgPublicArgPositioned>();
		String pname = Character.toUpperCase(name.charAt(0))
				+ name.substring(1).toLowerCase();
		env.setNextProgramToLoad(pname);
		programLoader.runTopProgram(env, params);
		String s = b.toString();
		if (batch) {
			try {
				s = read(new FileInputStream(name + ".report"));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			assertEquals(read(getClass().getResourceAsStream(name + ".out")), s);
		} finally {
			if (batch) {
				new File(name + ".report").delete();
			}
		}
	}

	private String read(InputStream in) {
		StringBuilder e = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			for (String line; (line = reader.readLine()) != null;)
				e.append(line).append("\n");
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		return e.toString();
	}
}
