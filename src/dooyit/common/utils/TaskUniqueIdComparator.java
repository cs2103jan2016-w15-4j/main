//@@author A0126356E
package dooyit.common.utils;

import java.util.Comparator;

import dooyit.common.datatype.Task;

public class TaskUniqueIdComparator implements Comparator<Task> {

	public int compare(Task task1, Task task2) {
		if (task1.getUniqueId() < task2.getUniqueId()) {
			return -1;
		} else if (task1.getUniqueId() > task2.getUniqueId()) {
			return 1;
		} else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		return this == obj;
	}
}
