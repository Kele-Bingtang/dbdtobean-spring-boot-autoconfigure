package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Kele-Bing
 * @since 2021/9/21 21:18
 * @version 1.6
 */
public class DbdToController extends AbstractDbdToMVC {
    private static final Logger logger = LoggerFactory.getLogger(DbdToController.class);
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
        logger.info("开始生成 {} 的 Controller 层以及文件内容", createBeanName);
        createBeanName = BeanUtils.underlineToUpperCase(createBeanName);
        DbdToMvcDefinition definition = DbdToBeanContext.getDbdToMvcDefinition();
        super.createBean(definition, createBeanName, CONTROLLER_NAME, null);
        logger.info("生成 {} 的 Controller 层以及文件内容完成", createBeanName);
    }
}
