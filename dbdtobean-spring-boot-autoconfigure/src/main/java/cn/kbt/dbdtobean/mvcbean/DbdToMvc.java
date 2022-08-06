package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.core.DbdToBeanDefinition;
import cn.kbt.dbdtobean.inter.IDbdToMVC;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author Kele-Bing
 * @since 2021/9/21 21:33
 * @version 1.6
 *  MVC核心类
 */
public class DbdToMvc implements IDbdToMVC {
    /** 定义类信息 **/
    List<DbdToBeanDefinition> definitions = DbdToBeanContext.getDbdToBeanDefinitions();

    /**
     * 生成 Controller 层所有内容
     * @throws IOException IO 异常
     */
    @Override
    public void dbdToController() throws IOException {
        if(BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMvcDefinition().getControllerLocation())){
            DbdToController dbdToController = new DbdToController();
            for (DbdToBeanDefinition definition : definitions) {
                dbdToController.controllerBean(definition.getCreateBeanName());
            }
        }
    }

    /**
     * 生成 Service 层所有内容
     * @throws IOException IO 异常
     */
    @Override
    public void dbdToService() throws IOException {
        if(BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMvcDefinition().getServiceLocation())){
            DbdToService dbdToService = new DbdToService();
            for (DbdToBeanDefinition definition : definitions) {
                dbdToService.serviceInterfaces(definition.getCreateBeanName());
                dbdToService.serviceBean(definition.getCreateBeanName());
            }
        }
    }

    /**
     * 生成 Dao 层所有内容
     * @throws IOException IO 异常
     */
    @Override
    public void dbdToDao() throws IOException {
        if(BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMvcDefinition().getDaoLocation())){
            DbdToDao dbdToDao = new DbdToDao();
            for (DbdToBeanDefinition definition : definitions) {
                dbdToDao.daoInterfaces(definition.getCreateBeanName());
                dbdToDao.daoBean(definition.getCreateBeanName());
            }
        }
    }

    /**
     * 生成 Mapper 层所有内容
     * @throws IOException IO 异常
     */
    @Override
    public void dbdToMapper() throws IOException {
        if(BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMvcDefinition().getMapperLocation())){
            DbdToMapper dbdToMapper = new DbdToMapper();
            for (DbdToBeanDefinition definition : definitions) {
                dbdToMapper.mapperInterfaces(definition.getCreateBeanName());
                dbdToMapper.mapperXml(definition.getCreateBeanName(), definition.getTableName());
            }
        }
    }

    /**
     * 整合生成 MVC 内容的 API
     * @throws IOException IO 异常
     */
    public void dbdToMvc() throws IOException {
        this.dbdToController();
        this.dbdToService();
        this.dbdToDao();
        this.dbdToMapper();
    }
}
