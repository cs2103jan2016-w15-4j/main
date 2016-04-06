package dooyit.storage;

public final class OsUtils {
	private static String OS = null;
	private static final String OS_NAME = "os.name";
	private static final String WINDOWS = "Windows";
	private static final String MAC = "Mac";
	
	public static String getOsName() {
		if(OS == null) {
			OS = System.getProperty(OS_NAME);
		}
		return OS;
	}
	
	public static boolean isWindows() {
		return OsUtils.getOsName().startsWith(WINDOWS);
	}
	
	public static boolean isMac() {
		return OsUtils.getOsName().startsWith(MAC);
	}
}
