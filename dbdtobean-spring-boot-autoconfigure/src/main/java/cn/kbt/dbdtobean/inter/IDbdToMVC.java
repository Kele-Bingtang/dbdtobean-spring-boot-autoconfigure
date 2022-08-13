package cn.kbt.dbdtobean.inter;

import java.io.IOException;

/**
 * @author Kele-Bing
 * @version 1.6
 * MVC接口
 * @since 2021/9/21 21:50
 */
public interface IDbdToMVC {
    /**
     * 生成 Controller 相关的内容
     *
     * @throws IOException IO异常
     */
    void dbdToController() throws IOException;

    /**
     * 生成 Service 相关的内容
     *
     * @throws IOException IO异常
     */
    void dbdToService() throws IOException;

    /**
     * 生成 Dao 相关的内容
     *
     * @throws IOException IO异常
     */
    void dbdToDao() throws IOException;

    /**
     * 生成 Mapper 相关的内容
     *
     * @throws IOException IO异常
     */
    void dbdToMapper() throws IOException;
}
