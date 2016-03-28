import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import dooyit.logic.LogicTestSuite;
import dooyit.logic.commands.LogicCommandTestSuite;


@RunWith(Suite.class)
@Suite.SuiteClasses({ LogicCommandTestSuite.class, LogicTestSuite.class})

public class AllTestSuite {

}
