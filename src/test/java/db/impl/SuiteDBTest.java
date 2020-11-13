package db.impl;


import db.impl.auto.*;
import db.impl.personal.UnitPostgreDriverTest;
import db.impl.personal.UnitPostgreFarmerTest;
import db.impl.personal.UnitPostgreOwnerTest;
import db.impl.zones.*;
import io.vertx.ext.unit.junit.RunTestOnContext;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        UnitPostgreAutoGroupTest.class,
        UnitPostgreAutoTest.class,
        UnitPostgreAutoTypeTest.class,
        UnitPostgreTrailerGroupTest.class,
        UnitPostgreTrailerTest.class,

        UnitPostgreDriverTest.class,
        UnitPostgreFarmerTest.class,
        UnitPostgreOwnerTest.class,

        UnitPostgreAgroCultureTest.class,
        UnitPostgreAgroStatusTest.class,
        UnitPostgreFieldGroupTest.class,
        UnitPostgreFieldStatusTest.class,
        UnitPostgreFieldsTest.class,
        UnitPostgrePoiTest.class,
        UnitPostgreRayonTest.class,
        UnitPostgreViloyatTest.class

})
public class SuiteDBTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();
}
