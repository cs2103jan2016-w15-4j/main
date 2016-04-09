//@@author A0124586Y
package dooyit.storage;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Saver<T> {
	abstract boolean save(ArrayList<T> t) throws IOException;
}
