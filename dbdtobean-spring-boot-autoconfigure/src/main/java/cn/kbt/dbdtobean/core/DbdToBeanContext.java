package cn.kbt.dbdtobean.core;

import cn.kbt.dbdtobean.comment.DefaultComment;
import cn.kbt.dbdtobean.comment.CustomComment;
import cn.kbt.dbdtobean.config.DbdToBeanProperties;
import cn.kbt.dbdtobean.mvcbean.DbdToMVCDefinition;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Kele-Bing
 * @since  2021/9/19 15:33
 * @version 1.6
 *  上下文
 */
public class DbdToBeanContext {
    private DbdToBeanContext() {
    }

    /**
     * 支持Spring boot配置文件，只需在spring boot配置文件写入数据库配置等信息即可获取数据库源对象
     **/
    private static volatile DbdToBeanProperties dbdToBeanProperties;
    /**
     * 可自定义注释，不自定义有默认注释
     **/
    private static volatile CustomComment customComment;
    /**
     * 默认注释，判断是否生成注释等信息
     **/
    private static volatile DefaultComment defaultComment;
    /**
     * 定义类，针对单表，有很多定义信息，如生成的JavaBean文件名，去掉下划线后的首字母大写等
     **/
    private static DbdToBeanDefinition dbdToBeanDefinition;
    /**
     * 定义类集合，针对多个表
     **/
    private static volatile List<DbdToBeanDefinition> dbdToBeanDefinitions;
    /**
     * MVC定义类，生成MVC文件的信息
     **/
    private static DbdToMVCDefinition dbdToMVCDefinition;

    public static DbdToBeanProperties getDbdToBeanProperties() {
        if (dbdToBeanProperties == null) {
            synchronized (DbdToBeanContext.class) {
                if (dbdToBeanProperties == null) {
                    dbdToBeanProperties = new DbdToBeanProperties();
                    return dbdToBeanProperties;
                }
            }
        }
        return dbdToBeanProperties;
    }

    public static CustomComment getCustomComment() {
        if (customComment == null) {
            synchronized (DbdToBeanContext.class) {
                if (customComment == null) {
                    customComment = new CustomComment();
                    return customComment;
                }
            }
        }
        return customComment;
    }

    public static DefaultComment getDefaultComment() {
        if (defaultComment == null) {
            synchronized (DbdToBeanContext.class) {
                if (defaultComment == null) {
                    defaultComment = new DefaultComment();
                    return defaultComment;
                }
            }
        }
        return defaultComment;
    }

    public static DbdToBeanDefinition getDbdToBeanDefinition() {
        if (dbdToBeanDefinition == null) {
            synchronized (DbdToBeanContext.class) {
                if (dbdToBeanDefinition == null) {
                    dbdToBeanDefinition = new DbdToBeanDefinition();
                    return dbdToBeanDefinition;
                }
            }
        }
        return dbdToBeanDefinition;
    }

    public static List<DbdToBeanDefinition> getDbdToBeanDefinitions() {
        if (dbdToBeanDefinitions == null) {
            synchronized (DbdToBeanContext.class) {
                if (dbdToBeanDefinitions == null) {
                    dbdToBeanDefinitions = new ArrayList<DbdToBeanDefinition>();
                    return dbdToBeanDefinitions;
                }
            }
        }
        return dbdToBeanDefinitions;
    }

    public static DbdToMVCDefinition getDbdToMVCDefinition() {
        if (dbdToMVCDefinition == null) {
            synchronized (DbdToBeanContext.class) {
                if (dbdToMVCDefinition == null) {
                    dbdToMVCDefinition = new DbdToMVCDefinition();
                    return dbdToMVCDefinition;
                }
            }
        }
        return dbdToMVCDefinition;
    }

    public static void setDbdToBeanProperties(DbdToBeanProperties dbdToBeanProperties) {
        DbdToBeanContext.dbdToBeanProperties = dbdToBeanProperties;
    }

    public static void setDbdToBeanDefinition(DbdToBeanDefinition dbdToBeanDefinition) {
        DbdToBeanContext.dbdToBeanDefinition = dbdToBeanDefinition;
    }

    public static void setCustomComment(CustomComment customComment) {
        DbdToBeanContext.customComment = customComment;
    }

    public static void setDefaultComment(DefaultComment defaultComment) {
        DbdToBeanContext.defaultComment = defaultComment;
    }

    public static void setDbdToBeanDefinitions(List<DbdToBeanDefinition> dbdToBeanDefinitions) {
        DbdToBeanContext.dbdToBeanDefinitions = dbdToBeanDefinitions;
    }

    public static void setDbdToMVCDefinition(DbdToMVCDefinition dbdToMVCDefinition) {
        DbdToBeanContext.dbdToMVCDefinition = dbdToMVCDefinition;
    }


}
