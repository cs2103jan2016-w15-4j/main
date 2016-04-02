package dooyit.common.datatype;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ CategoryTest.class, DateTimeTest.class, EventTaskTest.class,
					FloatingTaskTest.class, TaskGroupTest.class, TaskTest.class,
					DeadlineTaskTest.class })
public class DataTypeTestSuite {

}
