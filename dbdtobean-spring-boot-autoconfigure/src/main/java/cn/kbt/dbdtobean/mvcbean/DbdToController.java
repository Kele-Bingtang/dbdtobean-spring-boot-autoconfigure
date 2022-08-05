package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.io.IOException;

/**
 * @author Kele-Bing
 * @since 2021/9/21 21:18
 * @version 1.6
 */
public class DbdToController extends AbstractDbdToMVC {
    /**
     * Controller名
     **/
    protected final static String CONTROLLER_NAME = "Controller";

    /**
     * 创建Controller层以及文件内容
     *
     * @param createBeanName 文件名
     * @return 内容
     * @throws IOException IO 异常
     */
    public String controllerBean(String createBeanName) throws IOException {
        createBeanName = BeanUtils.underlineToUpperCase(createBeanName);
        DbdToMVCDefinition definition = DbdToBeanContext.getDbdToMVCDefinition();
        return super.createBean(definition, createBeanName, CONTROLLER_NAME, null);
    }

}
