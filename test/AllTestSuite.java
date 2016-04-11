//@@author A0126356E
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import dooyit.common.datatype.DataTypeTestSuite;
import dooyit.logic.LogicTestSuite;
import dooyit.logic.commands.LogicCommandTestSuite;
import dooyit.parser.ParserTestSuite;
import dooyit.storage.StorageTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ IntegrationTestSuite.class, LogicCommandTestSuite.class, LogicTestSuite.class,
		ParserTestSuite.class, StorageTestSuite.class, DataTypeTestSuite.class })

public class AllTestSuite {

}
