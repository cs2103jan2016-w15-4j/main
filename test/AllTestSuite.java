import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import dooyit.logic.api.LogicApiTestSuite;
import dooyit.logic.commands.LogicCommandTestSuite;


@RunWith(Suite.class)
@Suite.SuiteClasses({ LogicCommandTestSuite.class, LogicApiTestSuite.class})

public class AllTestSuite {

}
