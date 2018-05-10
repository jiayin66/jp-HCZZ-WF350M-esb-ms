package com.jp.hczz.dsj350m.test;

import com.jp.hczz.dsj350m.ApplicationStarter;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationStarter.class)
public class ApplicationStarterTest extends TestCase {

    /**
     * 测试业务层：根据id查找指定学生
     */
    @Test
    public void testGet() {
        String testId = "testStu1";
    }
}
