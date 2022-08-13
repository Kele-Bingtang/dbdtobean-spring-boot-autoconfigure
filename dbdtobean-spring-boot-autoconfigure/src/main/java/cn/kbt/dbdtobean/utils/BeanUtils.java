package cn.kbt.dbdtobean.utils;


import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.core.DbdToBeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Kele-Bing
 * @version 1.6
 * 工具类
 * @since 2022-35-06 00:35:43
 */
public class BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    private static final Random RANDOM;

    static {
        RANDOM = new SecureRandom();
    }

    /**
     * 关闭资源
     *
     * @param rs    结果集
     * @param pstmt PreparedStatement 对象
     */
    public static void close(ResultSet rs, PreparedStatement pstmt) {
        try {
            if (null != rs) {
                rs.close();
            }
            if (null != pstmt) {
                pstmt.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 内容是否为空
     *
     * @param content 内容
     * @return true：为空，false：不为空
     */
    public static boolean isEmpty(Object content) {
        return null == content || "".equals(content);
    }

    /**
     * 内容是否为空
     *
     * @param content 内容
     * @return true：不为空，false：为空
     */
    public static boolean isNotEmpty(Object content) {
        return !isEmpty(content);
    }

    /**
     * 去掉字符串的下划线，并且下划线后的首字母大写
     *
     * @param name 字符串
     * @return 字符串
     */
    public static String underlineToUpperCase(String name) {
        StringBuilder charUpper = new StringBuilder();
        String[] splitName = name.split("_");
        charUpper.append(splitName[0]);
        for (int i = 1; i < splitName.length; i++) {
            if (splitName[i] != null) {
                String s = firstCharToUpperCase(splitName[i]);
                charUpper.append(s);
            }
        }
        return charUpper.toString();
    }

    /**
     * 将字符串的首字母转为大写
     *
     * @param fieldName 字符串
     * @return 字符串
     */
    public static String firstCharToUpperCase(String fieldName) {
        char[] chars = fieldName.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] -= 32;
            return String.valueOf(chars);
        } else {
            return fieldName;
        }
    }

    /**
     * 将字符串的首字母转为小写
     *
     * @param fieldName 字符串
     * @return 字符串
     */
    public static String firstCharToLowerCase(String fieldName) {
        if (fieldName.substring(0, 1).equals(fieldName.substring(0, 1).toUpperCase())) {
            char[] chars = fieldName.toCharArray();
            chars[0] += 32;
            return String.valueOf(chars);
        } else {
            return fieldName;
        }
    }

    /**
     * 字符串转为小写
     *
     * @param content 字符串
     * @return 字符串
     */
    public String toLowerCase(String content) {
        char[] contentChars = content.toCharArray();
        for (int i = 0; i < contentChars.length; i++) {
            if (contentChars[i] >= 'A' && contentChars[i] <= 'Z') {
                contentChars[i] += 32;
            }
        }
        return String.valueOf(contentChars);
    }

    /**
     * 字符串转为大写
     *
     * @param content 字符串
     * @return 字符串
     */
    public String toUpperCase(String content) {
        char[] contentChars = content.toCharArray();
        for (int i = 0; i < contentChars.length; i++) {
            if (contentChars[i] >= 'a' && contentChars[i] <= 'z') {
                contentChars[i] -= 32;
            }
        }
        return String.valueOf(contentChars);
    }

    /**
     * 如果字符串第二个位置的字母为大写，则返回 true，反之 false
     * 符合 setter 和 getter 方法，生成 setter 和 getter 为：settTs(){}、gettTs(){}
     * 而不是 setTTs(){}、getTTs(){}
     *
     * @param content 字符串
     * @return boolean
     */
    public static boolean isTwoCharUpper(String content) {
        if (content.length() > 2) {
            return content.substring(1, 2).equals(content.substring(1, 2).toUpperCase());
        }
        return false;
    }

    /**
     * 0-1000 随机数字，生成文件名
     *
     * @return 文件名
     */
    public static int randomNum() {
        // 随机数字，生成文件名
        return RANDOM.nextInt(1000);
    }

    /**
     * 格式化并获取当前时间
     *
     * @return 当前时间
     */
    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据格式格式化并获取当前时间
     *
     * @param format 字符串
     * @return 当前时间
     */
    public static String getCurrentTime(String format) {
        Date date = new Date();
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 全类名转为类路径
     *
     * @param content 全类名
     * @return 类路径
     */
    public static String packageToPath(String content) {
        return content.replace(".", "/");
    }

    /**
     * 判断大小写
     * 返回不同的参数
     *
     * @param content 内容
     * @return 内容
     */
    public static String parseFieldName(String content) {
        DbdToBeanDefinition definition = DbdToBeanContext.getDbdToBeanDefinition();
        if (definition.isFieldNameAllLower() && definition.isLowerCamelCase()) {
            return BeanUtils.underlineToUpperCase(content.toLowerCase());
        } else if ((definition.isFieldNameAllLower())) {
            return content.toLowerCase();
        } else if (definition.isLowerCamelCase()) {
            return BeanUtils.underlineToUpperCase(content);
        } else {
            return content;
        }
    }

    /**
     * 获取 Mysql 的数据库源对象
     *
     * @param driverName 驱动
     * @param url        地址
     * @param username   数据库用户名
     * @param password   数据库密码
     * @return 连接对象
     */
    public static Connection getConnection(String driverName, String url, String username, String password) {
        Connection conn = null;
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取 \n，根据传入的数量返回相应的 \n
     *
     * @param nNum \n 的数量
     * @return nNum 数量的 \n
     */
    public static String getN(int nNum) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nNum; i++) {
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * 获取 \t，根据传入的数量返回相应的 \t
     *
     * @param tNum \t 的数量
     * @return tNum 数量的 \t
     */
    public static String getT(int tNum) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tNum; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    /**
     * 获取 \n 和 \t，根据传入的数量返回相应的 \n 和 \t
     *
     * @param nNum \n 的数量
     * @param tNum \t 的数量
     * @return nNum 数量的 \n 加上 tNum 数量的 \t
     */
    public static String getNT(int nNum, int tNum) {
        return getN(nNum) + getT(tNum);
    }

    /**
     * 用 "" 将 content 包围起来
     *
     * @param content 内容
     * @return "" 包围的 content
     */
    public static String addColon(String content) {
        return "\"" + content + "\"";
    }

    /**
     * 解释信息
     * 查询的表的信息的内容
     *
     * @param rs 结果集
     * @throws SQLException SQL 异常
     */
    public static void explain(ResultSet rs) throws SQLException {
        ResultSetMetaData data = rs.getMetaData();
        for (int i = 1; i <= data.getColumnCount(); i++) {
            // 获得所有列的数目及实际列数
            int columnCount = data.getColumnCount();
            // 获得指定列的列名
            String columnName = data.getColumnName(i);
            // 获得指定列的列值
            int columnType = data.getColumnType(i);
            // 获得指定列的数据类型名
            String columnTypeName = data.getColumnTypeName(i);
            // 所在的Catalog名字
            String catalogName = data.getCatalogName(i);
            // 对应数据类型的java类
            String columnClassName = data.getColumnClassName(i);
            // 在数据库中类型的最大字符个数
            int columnDisplaySize = data.getColumnDisplaySize(i);
            // 默认的列的标题
            String columnLabel = data.getColumnLabel(i);
            //获取查询数据所在的表名
            String tableName = data.getTableName(i);
            // 获得列的模式
            String schemaName = data.getSchemaName(i);
            // 某列类型的精确度(类型的长度)
            int precision = data.getPrecision(i);
            // 小数点后的位数
            int scale = data.getScale(i);
            // 是否自动递增
            boolean isAutoInctement = data.isAutoIncrement(i);
            // 在数据库中是否为货币型
            boolean isCurrency = data.isCurrency(i);
            // 是否为空
            int isNullable = data.isNullable(i);
            // 是否为只读
            boolean isReadOnly = data.isReadOnly(i);
            // 能否出现在where中
            boolean isSearchable = data.isSearchable(i);
            logger.info("获得所有列的数目及实际列数：{}", columnCount);
            logger.info("获得列 {} 的字段名称：{}", i, columnName);
            logger.info("获得列 {} 的类型,返回 SqlType 中的编号：{}", i, columnType);
            logger.info("获得列 {} 的数据类型名：{}", i, columnTypeName);
            logger.info("获得列 {} 的所在的 Catalog 名字：{}", i, catalogName);
            logger.info("获得列 {} 的对应数据类型的 java类：{}", i, columnClassName);
            logger.info("获得列 {} 的在数据库中类型的最大字符个数：{}", i, columnDisplaySize);
            logger.info("获得列 {} 的默认的列的标题：{}", i, columnLabel);
            logger.info("获得列 {} 的所在的表名：{}", i, tableName);
            logger.info("获得列 {} 的所在的模式名：{}", i, schemaName);
            logger.info("获得列 {} 的精确度（类型的长度）：{}", i, precision);
            logger.info("获得列 {} 的小数点后的位数：{}", i, scale);
            logger.info("获得列 {} 是否自动递增：{}", i, isAutoInctement);
            logger.info("获得列 {} 是否为货币型：{}", i, isCurrency);
            logger.info("获得列 {} 是否为空：{}", i, isNullable);
            logger.info("获得列 {} 是否为只读：{}", i, isReadOnly);
            logger.info("获得列 {} 是否能出现在where中：{}", i, isSearchable);
        }
    }
}
