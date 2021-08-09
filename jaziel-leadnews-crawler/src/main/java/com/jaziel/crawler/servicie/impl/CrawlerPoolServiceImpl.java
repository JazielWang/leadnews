package com.jaziel.crawler.servicie.impl;

import com.jaziel.crawler.servicie.CrawlerPoolService;
import com.jaziel.model.crawler.core.proxy.CrawlerProxy;
import com.jaziel.model.crawler.pojos.ClIpPool;
import com.jaziel.model.mappers.crawerls.ClIpPoolMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Jaziel
 * @date 2021/8/8 17:41
 */
@Service
@Log4j2
@SuppressWarnings("all")
public class CrawlerPoolServiceImpl implements CrawlerPoolService {

    @Autowired
    private ClIpPoolMapper clIpPoolMapper;

    @Override
    public void saveCrawlerIpPool(ClIpPool clIpPool) {
        clIpPoolMapper.insertSelective(clIpPool);
    }

    @Override
    public boolean checkExist(String host, int port) {
        ClIpPool pool = new ClIpPool();
        pool.setPort(port);
        pool.setIp(host);
        List<ClIpPool> clIpPools = clIpPoolMapper.selectList(pool);
        if (clIpPools != null && !clIpPools.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public void updateCrawlerIpPool(ClIpPool clIpPool) {
        clIpPoolMapper.updateByPrimaryKey(clIpPool);
    }

    @Override
    public List<ClIpPool> queryList(ClIpPool clIpPool) {
        return clIpPoolMapper.selectList(clIpPool);
    }

    @Override
    public List<ClIpPool> queryAvailableList(ClIpPool clIpPool) {
        return clIpPoolMapper.selectAvailableList(clIpPool);
    }

    @Override
    public void delete(ClIpPool clIpPool) {
        clIpPoolMapper.deleteByPrimaryKey(clIpPool.getId());
    }

    @Override
    public void unvailableProxy(CrawlerProxy proxy, String errorMsg) {
        ClIpPool clIpPoolQuery = new ClIpPool();
        clIpPoolQuery.setIp(proxy.getHost());
        clIpPoolQuery.setPort(proxy.getPort());
        clIpPoolQuery.setEnable(true);
        List<ClIpPool> clIpPoolList = clIpPoolMapper.selectList(clIpPoolQuery);
        if (null != clIpPoolList && !clIpPoolList.isEmpty()) {
            for (ClIpPool clIpPool : clIpPoolList) {
                clIpPool.setEnable(false);
                clIpPool.setError(errorMsg);
                clIpPoolMapper.updateByPrimaryKey(clIpPool);
            }
        }
    }
}
