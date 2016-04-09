//@@author A0124586Y
package dooyit.system;

public final class OsUtils {
	private static String osName = null;
	private static final String OS_NAME = "os.name";
	private static final String WINDOWS = "Windows";
	private static final String MAC = "Mac";

	public static String getOsName() {
		if (osName == null) {
			osName = System.getProperty(OS_NAME);
		}
		return osName;
	}

	public static boolean isWindows() {
		return OsUtils.getOsName().startsWith(WINDOWS);
	}

	public static boolean isMac() {
		return OsUtils.getOsName().startsWith(MAC);
	}
}
