package cn.kbt.dbdtobean.config;

import cn.kbt.dbdtobean.comment.DefaultComment;
import cn.kbt.dbdtobean.core.DbdToBean;
import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.core.DbdToBeanDefinition;
import cn.kbt.dbdtobean.mvcbean.DbdToMVCDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({DbdToBeanProperties.class, DbdToBeanDataSource.class, DbdToMVCDefinition.class, DbdToBeanDefinition.class, DefaultComment.class})
public class DbdToBeanConfiguration {
    @Autowired
    DbdToBeanProperties dbdToBeanProperties;
    @Autowired
    DbdToBeanDefinition dbdToBeanDefinition;
    @Autowired
    DbdToMVCDefinition dbdToMVCDefinition;
    @Autowired
    DefaultComment defaultComment;

    @Bean
    @ConditionalOnClass({DbdToBeanProperties.class})
    @ConditionalOnMissingBean(name = "getDbdToBean")
    public DbdToBean getDbdToBean(){
        return new DbdToBean();
    }

    @Bean
    public void setDBDToBeanContext(){
        DbdToBeanContext.setDbdToBeanProperties(dbdToBeanProperties);
        DbdToBeanContext.setDbdToBeanDefinition(dbdToBeanDefinition);
        DbdToBeanContext.setDbdToMVCDefinition(dbdToMVCDefinition);
        DbdToBeanContext.setDefaultComment(defaultComment);
    }
    
}
