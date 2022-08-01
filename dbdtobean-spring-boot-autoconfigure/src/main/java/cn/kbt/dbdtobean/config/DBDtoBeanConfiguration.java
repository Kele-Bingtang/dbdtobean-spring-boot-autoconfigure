package cn.kbt.dbdtobean.config;

import cn.kbt.dbdtobean.comment.DefaultComment;
import cn.kbt.dbdtobean.core.DBDToBean;
import cn.kbt.dbdtobean.core.DBDToBeanContext;
import cn.kbt.dbdtobean.core.DBDToBeanDefinition;
import cn.kbt.dbdtobean.mvcbean.DBDToMVCDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({DBDToBeanProperties.class, DBDToBeanDataSource.class, DBDToMVCDefinition.class, DBDToBeanDefinition.class, DefaultComment.class})
@ConditionalOnClass(ContextConfiguration.class)
public class DBDtoBeanConfiguration {

    @Bean("dBDToBean")
    @ConditionalOnClass({DBDToBeanProperties.class})
    @ConditionalOnMissingBean(name = "getDbdToBean")
    public DBDToBean getDbdToBean(){
        return new DBDToBean();
    }
    
}
