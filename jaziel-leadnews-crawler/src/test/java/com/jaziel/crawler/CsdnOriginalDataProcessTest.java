package com.jaziel.crawler;

import com.jaziel.crawler.process.entity.ProcessFlowData;
import com.jaziel.crawler.process.original.impl.CsdnOriginalDataProcess;
import com.jaziel.model.crawler.core.parse.ParseItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CsdnOriginalDataProcessTest {
    @Autowired
    private CsdnOriginalDataProcess csdnOriginalDataProcess;

    @Test
    public void test() {
        List<ParseItem> parseItems =
                csdnOriginalDataProcess.parseOriginalRequestData(new ProcessFlowData());
        System.out.println(parseItems);
    }
}