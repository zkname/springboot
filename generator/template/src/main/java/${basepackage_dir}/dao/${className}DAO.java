<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dao;

import java.util.*;
import ${basepackage}.entity.*;
import com.zkname.core.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class ${className}DAO extends BaseDAO<${className}> {

}