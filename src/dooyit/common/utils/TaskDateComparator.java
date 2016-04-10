//@@author A0126356E
package dooyit.common.utils;

import java.util.Comparator;

import dooyit.common.datatype.Task;

public class TaskDateComparator implements Comparator<Task> {

	public int compare(Task task1, Task task2) {
		return task1.compareDateTo(task2);
	}

	public boolean equals(Object obj) {
		return this == obj;
	}

}
