package cn.kbt.dbdtobean.inter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kele-Bing
 * @since 2022-43-06 11:43:11
 * @version 1.6
 * DbdToBeanCore 接口
*/
public interface IDbdToBean {
    /**
     * 根据单表生成 Java 文件
     * @param tableName 表明
     * @param isConstructor 是否生成构造方法
     * @param isSetAndGet 是否生成 setter 和 getter 方法
     * @param isToString 是否生成 toString 方法
     * @return 单个 Java Bean 内容
     * @throws SQLException SQL 异常
     */
    String createBeanFromTable(String tableName, boolean isConstructor, boolean isSetAndGet, boolean isToString) throws SQLException;

    /**
     * 根据数据库生成 Java 文件
     * @param dateBaseName 数据库名
     * @param isConstructor 是否生成构造方法
     * @param isSetAndGet 是否生成 setter 和 getter 方法
     * @param isToString 是否生成 toString 方法
     * @return 多个 Java Bean 内容
     * @throws SQLException SQL 异常
     * @throws IOException IO 异常
     */
    HashMap<String,String> createBeanFromDataBase(String dateBaseName, boolean isConstructor, boolean isSetAndGet, boolean isToString) throws SQLException, IOException;

    /**
     * 导出为 Java 文件
     * @param fileContent 文件内容
     * @param path 文件路径
     * @param dirName 文件夹名
     * @return 生成的路径
     * @throws IOException IO 异常
     */
    String exportToFiles(String fileContent, String path, String dirName) throws IOException;

    /**
     * 导出为 Java 文件
     * @param fileContentMap 文件内容
     * @param path 文件路径
     * @param dirName 文件夹名
     * @return 生成的路径
     * @throws IOException IO 异常
     */
    String exportToFiles(Map<String,String> fileContentMap, String path, String dirName) throws IOException, SQLException;
}
