package com.jaziel.crawler.process;

import com.jaziel.crawler.process.entity.ProcessFlowData;
import com.jaziel.model.crawler.enums.CrawlerEnum;

public interface ProcessFlow {
    /**
     * 处理主业务
     *
     * @param processFlowData
     */
    public void handel(ProcessFlowData processFlowData);

    /**
     * 获取抓取类型
     *
     * @return
     */
    public CrawlerEnum.ComponentType getComponentType();

    /**
     * 获取优先级
     * @return
     */
    public int getPriority();
}
