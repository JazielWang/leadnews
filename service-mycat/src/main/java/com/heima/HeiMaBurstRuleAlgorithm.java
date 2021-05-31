package com.heima;

import io.mycat.config.model.rule.RuleAlgorithm;
import io.mycat.route.function.AbstractPartitionAlgorithm;

/**
 * @author 王杰
 * @date 2021/5/31 20:21
 */
public class HeiMaBurstRuleAlgorithm extends AbstractPartitionAlgorithm implements RuleAlgorithm {

    // 单组数据容量
    Long volume;
    // 单组DN节点数量
    Integer step;
    // 分片模
    Integer mod;

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public void setMod(Integer mod) {
        this.mod = mod;
    }

    /**
     * @param columnValue 数据ID-桶ID
     * @return
     */
    @Override
    public Integer calculate(String columnValue) {
        if (columnValue != null) {
            String[] strings = columnValue.split("-");
            if (strings.length == 2) {
                try {
                    Long dataId = Long.valueOf(strings[0]);
                    Long brustId = Long.valueOf(strings[1]);
                    Integer group = (int) (dataId / volume) * step;
                    Integer pos = group + (int) (brustId % mod);
                    System.out.println("HEIMA RULE INFO [" + columnValue + "]-[{" + pos + "}]");
                    return pos;
                } catch (Exception e) {
                    System.out.println("HEIMA RULE INFO [" + columnValue + "]-[{" + e.getMessage() + "}]");
                }
            }
        }
        return 0;
    }

    /**
     * 范围计算
     * @param beginValue
     * @param endValue
     * @return
     */
    @Override
    public Integer[] calculateRange(String beginValue, String endValue) {
        if (beginValue != null && endValue != null) {
            Integer begin = calculate(beginValue);
            Integer end = calculate(endValue);
            if (end == null || begin == null) {
                return new Integer[0];
            }
            if (end >= begin) {
                int len = end - begin + 1;
                Integer[] re = new Integer[len];
                for (int i = 0; i < len; i++) {
                    re[i] = begin + i;
                }
                return re;
            }
        }
        return new Integer[0];
    }
}
