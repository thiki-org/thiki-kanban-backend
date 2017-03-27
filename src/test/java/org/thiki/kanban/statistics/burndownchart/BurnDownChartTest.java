package org.thiki.kanban.statistics.burndownchart;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Domain;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by winie on 2017/3/23.
 */

@Domain(order = DomainOrder.BURNDOWNCHART, name = "燃尽图")
@RunWith(SpringJUnit4ClassRunner.class)
public class BurnDownChartTest extends TestBase {

    @Scenario("分析昨天迭代燃尽图数据")
    @Test
    public void create_shouldReturn201WhenCreateBurnDownChartSuccessfully() throws Exception {
    }
}
