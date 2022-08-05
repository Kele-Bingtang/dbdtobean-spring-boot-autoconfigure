package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.core.DbdToBeanDefinition;
import cn.kbt.dbdtobean.inter.IDBDToMVC;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author Kele-Bing
 * @since 2021/9/21 21:33
 * @version 1.6
 *  MVC核心类
 */
public class DbdToMVC implements IDBDToMVC {
    /** 定义类信息 **/
    List<DbdToBeanDefinition> definitions = DbdToBeanContext.getDbdToBeanDefinitions();

    /**
     * 生成Controller层所有内容
     * @throws IOException IO 异常
     */
    @Override
    public void dbdToController() throws IOException {
        if(BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMVCDefinition().getControllerLocation())){
            DbdToController dbdToController = new DbdToController();
            for (DbdToBeanDefinition definition : definitions) {
                dbdToController.controllerBean(definition.getCreateBeanName());
            }
        }
    }

    /**
     * 生成Service层所有内容
     * @throws IOException IO 异常
     */
    @Override
    public void dbdToService() throws IOException {
        if(BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMVCDefinition().getServiceLocation())){
            DbdToService dbdToService = new DbdToService();
            for (DbdToBeanDefinition definition : definitions) {
                dbdToService.serviceInterfaces(definition.getCreateBeanName());
                dbdToService.serviceBean(definition.getCreateBeanName());
            }
        }
    }

    /**
     * 生成Dao层所有内容
     * @throws IOException IO 异常
     */
    @Override
    public void dbdToDao() throws IOException {
        if(BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMVCDefinition().getDaoLocation())){
            DbdToDao dbdToDao = new DbdToDao();
            for (DbdToBeanDefinition definition : definitions) {
                dbdToDao.daoInterfaces(definition.getCreateBeanName());
                dbdToDao.daoBean(definition.getCreateBeanName());
            }
        }
    }

    /**
     * 生成Mapper层所有内容
     * @throws IOException IO 异常
     */
    @Override
    public void dbdToMapper() throws IOException {
        if(BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMVCDefinition().getMapperLocation())){
            DbdToMapper dbdToMapper = new DbdToMapper();
            for (DbdToBeanDefinition definition : definitions) {
                dbdToMapper.mapperInterfaces(definition.getCreateBeanName());
                dbdToMapper.mapperXML(definition.getCreateBeanName(), definition.getTableName());
            }
        }
    }

    /**
     * 整合生成MVC内容的API
     * @throws IOException IO 异常
     */
    public void dbdToMVC() throws IOException {
        this.dbdToController();
        this.dbdToService();
        this.dbdToDao();
        this.dbdToMapper();
    }
}
