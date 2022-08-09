package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.io.IOException;

/**
 * @author Kele-Bing
 * @since 2021/9/21 21:18
 * @version 1.6
 */
public class DbdToController extends AbstractDbdToMvc {
    /**
     * Controller 名
     **/
    protected static final String CONTROLLER_NAME = "Controller";

    /**
     * 创建 Controller 层以及文件内容
     *
     * @param createBeanName 文件名
     * @throws IOException IO 异常
     */
    protected void controllerBean(String createBeanName) throws IOException {
        createBeanName = BeanUtils.underlineToUpperCase(createBeanName);
        DbdToMvcDefinition definition = DbdToBeanContext.getDbdToMvcDefinition();
        super.createBean(definition, createBeanName, CONTROLLER_NAME, null);
    }
}
