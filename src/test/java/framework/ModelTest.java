package framework;

import com.e2open.falcon.framework.model.Model;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ModelTest {


    @Test
    public void uniqueness_off_by_default() {
        Model modelInstance = new Model() {};
        assertTrue(modelInstance.uidStrategy == Model.UIDStrategy.NONE);
        assertTrue(modelInstance.getUUID() == null);
    }

    @Test
    public void unique_test_RUN() {
        Model firstInstance = new Model(Model.UIDStrategy.BY_TEST_RUN) {};
        String uuidFirst = firstInstance.getUUID();
        assertTrue(uuidFirst != null);
        Model secondInstance = new Model(Model.UIDStrategy.BY_TEST_RUN) {};
        String uuidSecond = secondInstance.getUUID();
        assertTrue(uuidSecond != null);
        assertTrue(uuidSecond == uuidFirst);
    }

    @Test
    public void unique_instance() {
        Model firstInstance = new Model(Model.UIDStrategy.BY_CLASS_INSTANCE) {};
        String uuidFirst = firstInstance.getUUID();
        assertTrue(uuidFirst != null);
        Model secondInstance = new Model(Model.UIDStrategy.BY_CLASS_INSTANCE) {};
        String uuidSecond = secondInstance.getUUID();
        assertTrue(uuidSecond != null);
        assertTrue(uuidSecond != uuidFirst);
    }

    public void test_run_id_should_use_config_setting() {
   }
}
