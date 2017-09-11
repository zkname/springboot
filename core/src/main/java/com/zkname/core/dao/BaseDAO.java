package com.zkname.core.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.zkname.core.dao.row.AutoBoxingRowMapper;
import com.zkname.core.dao.sql.SqlFactory;
import com.zkname.core.entity.IdEntity;
import com.zkname.core.util.AssertUtils;
import com.zkname.core.util.exception.DaoException;

public abstract class BaseDAO<T> implements IBaseDAO<T>{

    final static Logger logger = LoggerFactory.getLogger(BaseDAO.class);

	@Autowired
	private SqlFactory sqlFactory;
    
    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseDAO() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
    * spring jdbc
    */
    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    
    
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

	/**
	 * findById(主键查询)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param id
	 * @return 
	 * T
	 * @exception 
	 * @since  1.0.0
	 */
	public T findById(Object id){
		try {
			AssertUtils.notNull(id);
			List<T> list=getJdbcTemplate().query(sqlFactory.findById(entityClass),new Object[]{id},new AutoBoxingRowMapper<T>(entityClass));
			if(list!=null && list.size()>0){
				return list.get(0);
			}
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (DaoException e) {
			throw e;
		} catch (Exception e) {
			throw new DaoException("主键查询错误！",e);
		}
		return null;
	}
	
	/**
	 * findAll(查询所有)
	 * (这里描述这个方法适用条件 – 可选)
	 * @return 
	 * List<T>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<T> findAll(){
		try {
			return getJdbcTemplate().query(sqlFactory.findAll(entityClass),new AutoBoxingRowMapper<T>(entityClass));
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (DaoException e) {
			throw e;
		} catch (Exception e) {
			throw new DaoException("ALL查询错误！",e);
		}
	}
	
	
	/**
	 * saveOrUpdate(添加or修改)
	 * (只支持自增主键的)
	 * @param t 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveOrUpdate(T t) {
		try{
			AssertUtils.notNull(t);
			//是否为超类判断
			AssertUtils.isAssignable(IdEntity.class, t.getClass());
			IdEntity id=(IdEntity<?>) t;
			//非自增数据库无法调用这个方法
			if(!sqlFactory.get(entityClass).isAuto()){
				throw new DaoException("saveOrUpdate错误，非自增主键表无法使用此方法！");
			};
			if(id.getId()==null || "0".equals(id.getId().toString())){//新增的插入后更新主键
				id.setId(saveKey(sqlFactory.getSaveSql(entityClass),sqlFactory.getSaveParam(t,entityClass)));
			}else{//更新的
				getJdbcTemplate().update(sqlFactory.getUpdateSql(entityClass),sqlFactory.getUpdateParam(t,entityClass));
			}
		}catch (IllegalArgumentException e) {
			throw e;
		} catch (DaoException e) {
			throw e;
		} catch (Exception e) {
			throw new DaoException("saveOrUpdate错误！",e);
		}
	}
	
	
	/**
	 * save(添加)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param t 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void save(T t) {
		try{
			AssertUtils.notNull(t);
			//是否为超类判断
			AssertUtils.isAssignable(IdEntity.class, t.getClass());
			if(!sqlFactory.get(entityClass).isAuto()){
				getJdbcTemplate().update(sqlFactory.getSaveSql(entityClass),sqlFactory.getSaveParam(t,entityClass));
			}else{
				IdEntity id=(IdEntity<?>) t;
				id.setId(saveKey(sqlFactory.getSaveSql(entityClass),sqlFactory.getSaveParam(t,entityClass)));
			}
		}catch (IllegalArgumentException e) {
			throw e;
		} catch (DaoException e) {
			throw e;
		} catch (Exception e) {
			throw new DaoException("saveOrUpdate错误！",e);
		}
	}
	
	
	/**
	 * update(修改)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param t 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void update(T t) {
		try{
			AssertUtils.notNull(t);
			//是否为超类判断
			AssertUtils.isAssignable(IdEntity.class, t.getClass());
			if(getJdbcTemplate().update(sqlFactory.getUpdateSql(entityClass),sqlFactory.getUpdateParam(t,entityClass))<1){
				throw new DaoException("saveOrUpdate错误！");
			}
		}catch (IllegalArgumentException e) {
			throw e;
		} catch (DaoException e) {
			throw e;
		} catch (Exception e) {
			throw new DaoException("saveOrUpdate错误！",e);
		}
	}
	
	
	/**
	 * update(修改指定字段)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param t 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void update(T t,String... fields) {
		try{
			AssertUtils.notNull(t);
			//是否为超类判断
			AssertUtils.isAssignable(IdEntity.class, t.getClass());
			if(getJdbcTemplate().update(sqlFactory.getUpdateSql(entityClass,fields),sqlFactory.getUpdateParam(t,entityClass,fields))<1){
				throw new DaoException("saveOrUpdate错误！");
			}
		}catch (IllegalArgumentException e) {
			throw e;
		} catch (DaoException e) {
			throw e;
		} catch (Exception e) {
			throw new DaoException("saveOrUpdate错误！",e);
		}
	}

	
	/**
	 * saveKey(返回id)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param sql
	 * @param args
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	public long saveKey(final String sql, final Object[] args) {
        try {
            logger.debug("sql:" + sql + ";\n   Object[]：" + ArrayUtils.toString(args));
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int value = this.jdbcTemplate.update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                    PreparedStatement ps = null;
                    ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    if (args != null && args.length > 0) {
                        for (int i = 0; i < args.length; i++) {
                            ps.setObject(i + 1, args[i]);
                        }
                    }
                    return ps;
                }
            }, keyHolder);
            if(value==0){
            	throw new DaoException("添加错误；\n sql:" + sql + ";\n Object[]：" + ArrayUtils.toString(args));
            }
            return keyHolder.getKey() == null ? null : keyHolder.getKey().longValue();
        } catch (Exception e) {
            throw new DaoException("添加错误；\n sql:" + sql + ";\n Object[]：" + ArrayUtils.toString(args), e);
        }
	}
	
	/**
	 * deleteId(id删除)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param id
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	public int deleteId(Object... ids) {
		try {
			AssertUtils.notNull(ids);
			int i=0;
			for(Object id:ids){
				i+=getJdbcTemplate().update(sqlFactory.getDeleteSql(entityClass),id);
			}
			return i;
		}catch (IllegalArgumentException e) {
			throw e;
		} catch (DaoException e) {
			throw e;
		} catch (Exception e) {
			throw new DaoException("删除错误！",e);
		}
	}
	
	/**
	 * delete(实体删除对象)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param t
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	@SuppressWarnings("rawtypes")
	public int delete(T t) {
		try {
			AssertUtils.notNull(t);
			//是否为超类判断
			AssertUtils.isAssignable(IdEntity.class, t.getClass());
			IdEntity id=(IdEntity) t;
			//判断是否为null
			AssertUtils.notNull(id.getId());
			return getJdbcTemplate().update(sqlFactory.getDeleteSql(entityClass),id.getId());
		}catch (IllegalArgumentException e) {
			throw e;
		} catch (DaoException e) {
			throw e;
		} catch (Exception e) {
			throw new DaoException("删除错误！",e);
		}
	}

	/**
	 * findBy(单一对象查询)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @return 
	 * T
	 * @exception 
	 * @since  1.0.0
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T findBy(String sql,RowMapper<?> rowMapper, Object... args) {
		List<T> list=(List<T>) getJdbcTemplate().query(sql, args, rowMapper);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}


	/**
	 * queryCount(查询总数)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param sql
	 * @param args
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	public int queryCount(String sql, final Object... args) {
        Number number = this.getJdbcTemplate().queryForObject(sql,args,Long.class);
        return (number != null ? number.intValue() : 0);
	}
	
	

    /**
     * saveOrUpdate(插入数据-memcache缓存) 
     * 
     * @param args
     * @exception
     * @since 1.0.0
     */
    protected int update(final String sql, Object... args) {
        return this.getJdbcTemplate().update(sql, args);
    }

    public Long saveId(final String sql, final Object... args) {
        try {
            logger.debug("sql:" + sql + ";\n   Object[]：" + ArrayUtils.toString(args));
            KeyHolder keyHolder = new GeneratedKeyHolder();
			int value = this.getJdbcTemplate().update((conn) -> {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				if (args != null && args.length > 0) {
					for (int i = 0; i < args.length; i++) {
						ps.setObject(i + 1, args[i]);
					}
				}
				return ps;
			}, keyHolder);
            if(value==0){
            	throw new DaoException("添加错误；\n sql:" + sql + ";\n Object[]：" + ArrayUtils.toString(args));
            }
            return keyHolder.getKey() == null ? null : keyHolder.getKey().longValue();
        } catch (Exception e) {
            throw new DaoException("添加错误；\n sql:" + sql + ";\n Object[]：" + ArrayUtils.toString(args), e);
        }
    }
    
    
    public long saveNum(final String sql, final Object... args) {
        try {
            logger.debug("sql:" + sql + ";\n   Object[]：" + ArrayUtils.toString(args));
            int value = this.getJdbcTemplate().update((conn)->{
	              PreparedStatement ps = conn.prepareStatement(sql);
	              if (args != null && args.length > 0) {
	                  for (int i = 0; i < args.length; i++) {
	                      ps.setObject(i + 1, args[i]);
	                  }
	              }
	              return ps;
            });
            return value;
        } catch (Exception e) {
            throw new DaoException("添加错误；\n sql:" + sql + ";\n Object[]：" + ArrayUtils.toString(args), e);
        }
    }

    /**
     * delete(删除数据-memcache缓存) 
     * 
     * @param args
     * @exception
     * @since 1.0.0
     */
    protected void delete(final String sql, Object... args) {
        this.getJdbcTemplate().update(sql, args);
    }

    /**
     * find(返回数组) (这里描述这个方法适用条件 – 可选)
     * 
     * @param sql
     * @param args
     * @return List<?>
     * @exception
     * @since 1.0.0
     */
    protected List<T> find(final String sql, final Object... args) {
        return this.getJdbcTemplate().query(sql, args, new BeanPropertyRowMapper<T>(entityClass));
    }
    
    /**
     * find(返回数组) (这里描述这个方法适用条件 – 可选)
     * 
     * @param sql
     * @param args
     * @return List<?>
     * @exception
     * @since 1.0.0
     */
    public List<?> find(final String sql, RowMapper<?> rowMapper,final Object... args) {
        return (List<?>) this.getJdbcTemplate().query(sql, args, rowMapper);
    }

    /**
     * findBy(返回单一对象)
     * (这里描述这个方法适用条件 – 可选)
     * @param sql
     * @param args
     * @return 
     * T
     * @exception 
     * @since  1.0.0
     */
    protected T findBy(final String sql, final Object... args) {
        List<T> list = this.find(sql, args);
        if (list == null || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }
    
    
    public boolean isTable(String tableName) {
    	try (Connection conn = jdbcTemplate.getDataSource().getConnection();
    		ResultSet tabs = conn.getMetaData().getTables(null, null, tableName, new String[]{"TABLE"}); ){
    		if (tabs.next()) {
    			return true;
    		}
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    	return false;
    }
    
    
    /**
     * 查询原表建立新表
     * @param original_table 原表名
     * @param table_suffix 新建表后缀
     */
	public void createTable(String original_table,String table_suffix){
	    Map<String,Object> map = this.getJdbcTemplate().queryForMap("show create table "+original_table);
	    String tableDdl=map.get("Create Table").toString();
		//查询是否创建了表
		if(!this.isTable(original_table+"_"+table_suffix)){
			this.getJdbcTemplate().execute(tableDdl.replace(original_table, original_table+"_"+table_suffix));
		}
	}
}
