package cn.kbt.dbdtobean.config;

import cn.kbt.dbdtobean.comment.DefaultComment;
import cn.kbt.dbdtobean.core.DBDToBeanDefinition;
import cn.kbt.dbdtobean.core.DBDToBeanContext;
import cn.kbt.dbdtobean.mvcbean.DBDToMVCDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kele-Bing
 * @since  2021/9/25 14:39
 * @version   1.0
 *  上下文配置类
 */
@Configuration
@ConditionalOnMissingBean(name = {"dbdToBeanDefinition","dbdToMVCDefinition","defaultComment"})
public class ContextConfiguration {
    @Autowired
    DBDToBeanProperties dbdToBeanProperties;
    @Autowired
    DBDToBeanDefinition dbdToBeanDefinition;
    @Autowired
    DBDToMVCDefinition dbdToMVCDefinition;
    @Autowired
    DefaultComment defaultComment;
    
    @Bean
    public void setDBDToBeanContext(){
        DBDToBeanContext.setDbdToBeanProperties(dbdToBeanProperties);
        DBDToBeanContext.setDbdToBeanDefinition(dbdToBeanDefinition);
        DBDToBeanContext.setDbdToMVCDefinition(dbdToMVCDefinition);
        DBDToBeanContext.setDefaultComment(defaultComment);
    }
}
