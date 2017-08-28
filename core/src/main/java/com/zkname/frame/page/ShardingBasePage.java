package com.zkname.frame.page;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Lists;
import com.zkname.frame.dao.row.AutoBoxingRowMapper;
import com.zkname.frame.util.ParamType;
import com.zkname.frame.util.classinfo.Constants;
import com.zkname.frame.util.classinfo.ReflectionCache;
import com.zkname.frame.util.spring.SpringContextHolder;

/**
 * ClassName:BasePage Function: 分页查询 Reason: TODO ADD REASON
 * 
 *
 * @version
 * @since Ver 1.1
 * @Date 2011-7-27
 */
public class ShardingBasePage<T> extends Page<T> {

    private static final long serialVersionUID = 8490499760803515703L;

    private static Logger logger = LoggerFactory.getLogger(ShardingBasePage.class);
    // sql
    protected StringBuffer sb = new StringBuffer();
    // 查询条件值
    protected List<Object> list = Lists.newArrayList();
    // 起始
    private static final String searchValue = " from ";
    // 结束
    private static final String orderByValue = "ORDER BY";

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public ShardingBasePage() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * query(普通查询) (这里描述这个方法适用条件 – 可选)
     * 
     * @param rowMapper
     * @return List<T>
     * @exception
     * @since 1.0.0
     */
    protected List<T> query() {
        JdbcTemplate jdbcTemplate = SpringContextHolder.getBean("jdbcTemplateSharding");
        Object[] objs = list.toArray();
        // 查询分页
        super.setTotalItems(jdbcTemplate.queryForObject(getSQLCount(sb.toString()), objs,Long.class));
        // 设置分页
        this.limit();
        // 查询列表
        super.setResult((List<T>) jdbcTemplate.query(sb.toString(), objs, new AutoBoxingRowMapper<T>(entityClass)));
        // 清除分页信息
        clear();
        return super.getResult();
    }
    
    protected List<T> all() {
        JdbcTemplate jdbcTemplate = SpringContextHolder.getBean("jdbcTemplateSharding");
        Object[] objs = list.toArray();
        // 查询列表
        super.setResult((List<T>) jdbcTemplate.query(sb.toString(), objs, new AutoBoxingRowMapper<T>(entityClass)));
        // 清除分页信息
        clear();
        return super.getResult();
    }

    /**
     * 通过反向将页面传递的参数set到变量里
     */
    public void setHttpServletRequestValue(HttpServletRequest request) {
        if (request == null) {
            return;
        }
        Map<String, Field> maps = ReflectionCache.putClassInfo(this.getClass(), Constants.METHOD_ACCESS).getFieldMap();
        Set<String> set = maps.keySet();
        Map<String, String[]> map = request.getParameterMap();
        for (String fieldName : set) {
            if (map.containsKey(fieldName)) {
            	String[] value = map.get(fieldName);
                try {
                    Field field = maps.get(fieldName);
                    ReflectionCache.setValue(this, field, getValue(field, value));
                } catch (Exception e) {
                    logger.warn("写入类数据:{}.{} value:{}错误！", new Object[] { this.getClass().getName(), fieldName, value });
                }
            }
        }
        if (request.getParameter("pageNo") != null) {
            super.setPageNo(ParamType.getint(request.getParameter("pageNo")));
        }
        if (request.getParameter("pageSize") != null) {
            super.setPageSize(ParamType.getint(request.getParameter("pageSize")));
        }
    }

    private Object getValue(Field propertyDescriptor, String[] obj) throws SQLException {
        Object val = null;
        // 判断是否是数组
        if (obj == null || obj.length > 1) {
            return obj;
        }
        String value = (String) obj[0];
        Class<?> propertyTypeClass = propertyDescriptor.getType();
        String className = propertyTypeClass.getName();
        if (className.equals(String.class.getName())) {
           return val = value;
        } else if (className.equals(Integer.class.getName()) || className.equals(Long.class.getName()) || className.equals(Float.class.getName()) || className.equals(Double.class.getName())) {
            if (value != null && "".equals(value.trim())) {
                return null;
            }
        }
        if (className.equals("int") || className.equals(Integer.class.getName())) {
            val = ParamType.getint(value);
        } else if (className.equals("short") || className.equals(Short.class.getName())) {
            val = ParamType.getShort(value);
        } else if (className.equals("byte") || className.equals(Byte.class.getName())) {
            val = ParamType.getByte(value);
        } else if (className.equals("long") || className.equals(Long.class.getName())) {
            val = ParamType.getLong(value);
        } else if (className.equals("float") || className.equals(Float.class.getName())) {
            val = ParamType.getFloat(value);
        } else if (className.equals("double") || className.equals(Double.class.getName())) {
            val = ParamType.getDouble(value);
        } else {
            val = value;
        }
        return val;
    }

    /**
     * limit(设置分页) (这里描述这个方法适用条件 – 可选) void
     * 
     * @exception
     * @since 1.0.0
     */
    protected void limit() {
        // -1 查询全部
        if (super.getPageNo() != -1 && super.getPageSize() != -1) {
            if (super.getTotalPages() < super.getPageNo()) {
                super.setPageNo(super.getTotalPages());
            }
            sb.append(" limit ").append(super.getOffset()).append(",").append(super.getPageSize());
        }
    }

    /**
     * getSQLCount(处理sql) (这里描述这个方法适用条件 – 可选)
     * 
     * @param sql
     * @return String
     * @exception
     * @since 1.0.0
     */
    protected String getSQLCount(String sql) {
        String sqlCount = "select count(*) from " + sql.substring(sql.indexOf(searchValue) + searchValue.length(), sql.length());
        // 先判断是否存在
        if (sqlCount.toUpperCase().lastIndexOf(orderByValue) != -1) {
            sqlCount = sqlCount.substring(0, sqlCount.toUpperCase().lastIndexOf(orderByValue));
        }
        return sqlCount;
    }

    // 判断null
    protected boolean isNotEmpty(Object value) {
        if (value == null || "".equals(value)) {
            return false;
        }
        return true;
    }

    // 判断数字
    protected boolean isNotZero(Integer value) {
        if (value == null || value.intValue() == 0) {
            return false;
        }
        return true;
    }

    /**
     * clear(清除分页数据) (这里描述这个方法适用条件 – 可选) void
     * 
     * @exception
     * @since 1.0.0
     */
    protected void clear() {
        sb.delete(0, sb.length());
        list.clear();
    }
}
