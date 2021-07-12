package com.jaziel.admin.service.impl;

import com.jaziel.admin.dto.CommonDao;
import com.jaziel.admin.service.CommonService;
import com.jaziel.admin.service.impl.commfilter.BaseCommonFilter;
import com.jaziel.model.admin.dtos.CommonDto;
import com.jaziel.model.admin.pojos.AdUser;
import com.jaziel.model.common.dtos.ResponseResult;
import com.jaziel.model.common.enums.AppHttpCodeEnum;
import com.jaziel.utils.threadlocal.AdminThreadLocalUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Jaziel
 * @date 2021/7/10 17:54
 */

/**
 * 通用操纵类
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private ApplicationContext context;

    @Override
    public ResponseResult list(CommonDto dto) {
        if (!dto.getName().isList()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        Integer size = dto.getSize();
        String where = getWhere(dto);
        String tableName = dto.getName().name().toLowerCase();
        List<?> list = null;
        int total = 0;
        int start = (dto.getPage() - 1) * dto.getSize();
        if (where != null) {
            list = commonDao.listForWhere(tableName, where, start, size);
            total = commonDao.listCountWhere(tableName, where);
        } else {
            list = commonDao.list(tableName, start, size);
            total = commonDao.listCount(tableName);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);

        doFilter(dto, "list");

        return ResponseResult.okResult(map);
    }

    private void doFilter(CommonDto dto, String name) {
        BaseCommonFilter baseCommonFilter = findFilter(dto);
        if (baseCommonFilter != null) {
            AdUser ad = AdminThreadLocalUtils.getUser();
            if ("list".equals(name)) {
                baseCommonFilter.doListAfter(ad, dto);
            }
            if ("update".equals(name)) {
                baseCommonFilter.doUpdateAfter(ad, dto);
            }
            if ("list".equals(name)) {
                baseCommonFilter.doListAfter(ad, dto);
            }
            if ("delete".equals(name)) {
                baseCommonFilter.doDeleteAfter(ad, dto);
            }
        }
    }

    private BaseCommonFilter findFilter(CommonDto dto) {
        if (context.containsBean(dto.getName().name())) {
            return context.getBean(dto.getName().name(), BaseCommonFilter.class);
        }
        return null;
    }

    private String getWhere(CommonDto dto) {
        StringBuilder where = new StringBuilder();
        if (dto.getWhere() != null) {
            dto.getWhere().stream().forEach(w -> {
                // 字段不为空，并且字段和值不能相等（防止凭借真条件）
                if (StringUtils.isNotEmpty(w.getFiled()) && StringUtils.isNotEmpty(w.getValue()) &&
                        !w.getFiled().equalsIgnoreCase(w.getValue())) {
                    String tempF = parseValue(w.getFiled());
                    String tempV = parseValue(w.getValue());
                    String tempT = w.getType();
                    if (!tempF.matches("\\d*") && !tempF.equalsIgnoreCase(tempV)) {
                        if ("eq".equals(tempT)) {
                            where.append(" and ").append(tempF).append("=\'").append(tempV).append("\'");
                        } else if ("like".equals(tempT)) {
                            where.append(" and ").append(tempF).append(" like ").append("\'%").append(tempV).append("%\'");
                        } else if ("between".equals(tempT)) {
                            String temp[] = tempV.split(",");
                            where.append(" and ").append(tempF).append(temp[0]).append(" and ").append(temp[1]);
                        }
                    }
                }
            });
            return where.toString();
        }
        return null;
    }

    /**
     * SQL 单引号(')，分号(;) 和 注释符号(--)
     *
     * @param value
     * @return
     */
    private String parseValue(String value) {
        if (StringUtils.isNotEmpty(value)) {
            return value.replaceAll(".*([';#%]+|(--)+).*", "");
        }
        return value;
    }

    @Override
    public ResponseResult update(CommonDto dto) {
        String where = getWhere(dto);
        String model = dto.getModel();
        String tableName = dto.getName().name().toLowerCase();
        if ("add".equals(model)) {
            //新增
            if (StringUtils.isNotEmpty(where)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            } else {
                return addData(dto, tableName);
            }
        } else if ("edit".equals(model)) {
            //修改
            if (StringUtils.isEmpty(where)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            } else {
                return editData(dto, tableName, where);
            }
        }
        return null;
    }

    private ResponseResult editData(CommonDto dto, String tableName, String where) {
        if (!dto.getName().isUpdate()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        String sets = getSets(dto);
        if (sets == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        int update = commonDao.update(tableName, where, sets);
        if (update > 0) {
            doFilter(dto, "update");
        }
        return ResponseResult.okResult(update);
    }

    private String getSets(CommonDto dto) {
        StringBuffer sets = new StringBuffer();
        AtomicInteger count = new AtomicInteger();
        if (dto.getSets() != null) {
            dto.getSets().stream().forEach(w -> {
                if (StringUtils.isEmpty(w.getValue())) {
                    count.incrementAndGet();
                } else {
                    String tempF = parseValue(w.getFiled());
                    String tempV = parseValue(w.getValue());
                    if (!tempF.matches("\\d*") && !tempF.equalsIgnoreCase(tempV)) {
                        if (sets.length() > 0) {
                            sets.append(",");
                        }
                        sets.append(tempF).append("=\'").append(tempV).append("\'");
                    }
                }
            });
        }
        if (count.get() > 0) {
            return null;
        }
        return sets.toString();
    }

    private ResponseResult addData(CommonDto dto, String tableName) {
        if (!dto.getName().isAdd()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        String[] sql = getSql(dto);
        if (sql == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        int temp = commonDao.insert(tableName, sql[0], sql[1]);

        if (temp > 0) {
            doFilter(dto, "add");
        }
        return ResponseResult.okResult(temp);
    }

    private String[] getSql(CommonDto dto) {
        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        AtomicInteger count = new AtomicInteger();
        if (dto.getSets() != null) {
            dto.getSets().stream().forEach(w -> {
                if (w.getValue() == null) {
                    count.incrementAndGet();
                } else {
                    String tempF = parseValue(w.getFiled());
                    String tempV = parseValue(w.getValue());
                    if (!tempF.equalsIgnoreCase(tempV) && !tempF.matches("\\d*")) {
                        if (fields.length() > 0) {
                            fields.append(",");
                            values.append(",");
                        }
                        fields.append(tempF);
                        values.append("\'").append(tempV).append("\'");
                    }
                }
            });
            if (count.get() > 0) {
                return null;
            } else {
                return new String[]{fields.toString(), values.toString()};
            }
        }
        return null;
    }


    @Override
    public ResponseResult delete(CommonDto dto) {
        if (!dto.getName().isDelete()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        String where = getWhere(dto);
        String tableName = dto.getName().name().toLowerCase();
        if (StringUtils.isEmpty(where)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        int temp = commonDao.delete(tableName, where);
        if (temp > 0) {
            doFilter(dto, "delete");
        }
        return ResponseResult.okResult(temp);
    }
}
