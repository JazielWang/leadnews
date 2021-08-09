package com.jaziel.crawler.servicie;

import com.jaziel.model.crawler.core.proxy.CrawlerProxy;
import com.jaziel.model.crawler.pojos.ClIpPool;

import java.util.List;

/**
 * @Author Jaziel
 * @date 2021/8/8 17:39
 */
public interface CrawlerPoolService {
    /**
     * 保存方法
     *
     * @param clIpPool
     */
    public void saveCrawlerIpPool(ClIpPool clIpPool);

    /**
     * 检查代理Ip 是否存在
     *
     * @param host
     * @param port
     * @return
     */
    public boolean checkExist(String host, int port);

    /**
     * 更新方法
     *
     * @param clIpPool
     */
    public void updateCrawlerIpPool(ClIpPool clIpPool);

    /**
     * 查询所有数据
     *
     * @param clIpPool
     */
    public List<ClIpPool> queryList(ClIpPool clIpPool);

    /**
     * 获取可用的列表
     *
     * @return
     */
    public List<ClIpPool> queryAvailableList(ClIpPool clIpPool);

    public void delete(ClIpPool clIpPool);

    void unvailableProxy(CrawlerProxy proxy, String errorMsg);
}
