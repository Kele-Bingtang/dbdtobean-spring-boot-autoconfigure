package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.io.IOException;

/**
 * @author Kele-Bing
 * @version 1.6
 * @since 2021/9/21 21:45
 */
public class DbdToService extends AbstractDbdToMvc {
    /**
     * Service 接口基础名
     **/
    protected static final String SERVICE_INTERFACE_NAME = "Service";
    /**
     * Service 实现类基础名
     **/
    protected static final String SERVICE_IMPL_NAME = "ServiceImpl";
    /**
     * Service 接口完整名
     **/
    protected static String interfacesName = null;

    /**
     * 生成 Service 接口内容
     *
     * @param createBeanName 文件名
     * @throws IOException IO 异常
     */
    protected void serviceInterfaces(String createBeanName) throws IOException {
        createBeanName = BeanUtils.underlineToUpperCase(createBeanName);
        DbdToMvcDefinition definition = DbdToBeanContext.getDbdToMvcDefinition();
        interfacesName = super.createInterfaces(definition, createBeanName, SERVICE_INTERFACE_NAME);
    }

    /**
     * 生成 Service 实现类内容
     *
     * @param createBeanName 文件名
     * @throws IOException IO 异常
     */
    protected void serviceBean(String createBeanName) throws IOException {
        createBeanName = BeanUtils.underlineToUpperCase(createBeanName);
        DbdToMvcDefinition definition = DbdToBeanContext.getDbdToMvcDefinition();
        super.createBean(definition, createBeanName, SERVICE_IMPL_NAME, interfacesName);
    }
}
