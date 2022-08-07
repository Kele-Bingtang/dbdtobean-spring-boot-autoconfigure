package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.io.IOException;

/**
 * @author Kele-Bing
 * @version 1.6
 * @since 2021/9/21 22:50
 */
public class DbdToDao extends AbstractDbdToMVC {
    /**
     * Dao层接口基础名
     **/
    protected static final String DAO_INTERFACE_NAME = "Dao";
    /**
     * Dao层实现类基础名
     **/
    protected static final String DAO_IMPL_NAME = "DaoImpl";
    /**
     * Dao层接口完整名
     **/
    protected static String interfaceName = null;

    /**
     * 创建 Dao 层接口目录以及内容
     *
     * @param createBeanName 文件名
     * @throws IOException IO 异常
     */
    protected void daoInterfaces(String createBeanName) throws IOException {
        createBeanName = BeanUtils.underlineToUpperCase(createBeanName);
        DbdToMvcDefinition definition = DbdToBeanContext.getDbdToMvcDefinition();
        interfaceName = super.createInterfaces(definition, createBeanName, DAO_INTERFACE_NAME);
    }

    /**
     * 生成 Dao 层实现类目录以及内容
     *
     * @param createBeanName 文件名
     * @throws IOException IO 异常
     */
    protected void daoBean(String createBeanName) throws IOException {
        createBeanName = BeanUtils.underlineToUpperCase(createBeanName);
        DbdToMvcDefinition definition = DbdToBeanContext.getDbdToMvcDefinition();
        super.createBean(definition, createBeanName, DAO_IMPL_NAME, interfaceName);
    }
}
