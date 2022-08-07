package cn.kbt.dbdtobean.comment;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Kele-Bing
 * @version 1.6
 * @since 2021/9/19 15:01
 * 自定义注释类
 */
public class CustomComment extends AbstractComment {

    private static final String NULL_CONSTRUCTOR = "无参构造器";
    private static final String PARAM_CONSTRUCTOR = "有参构造器，进行属性值的初始化";
    private static final String SET = "设置「";
    private static final String GET = "获取「";
    private static final String REMARKS = "REMARKS";
    private static final String FIELD_NAME = "」的属性值";
    private static final String TO_STRING = "重写 toString 方法，使用该方法可以在控制台打印属性的数据";
    /**
     * JavaBean 内容的自定义注释
     */
    private List<String> filedComment = null;
    private String nullConstructorComment = "";
    private String paramConstructorComment = "";
    private String toStringComment = "";
    private List<String> setterComment = null;
    private List<String> getterComment = null;

    public List<String> getFiledComment() {
        return filedComment;
    }

    public void setFiledComment(List<String> filedComment) {
        this.filedComment = filedComment;
    }

    public String getNullConstructorComment() {
        return nullConstructorComment;
    }

    public void setNullConstructorComment(String nullConstructorComment) {
        this.nullConstructorComment = nullConstructorComment;
    }

    public String getParamConstructorComment() {
        return paramConstructorComment;
    }

    public void setParamConstructorComment(String paramConstructorComment) {
        this.paramConstructorComment = paramConstructorComment;
    }


    public String getToStringComment() {
        return toStringComment;
    }

    public void setToStringComment(String toStringComment) {
        this.toStringComment = toStringComment;
    }

    public List<String> getSetterComment() {
        return setterComment;
    }

    public void setSetterComment(List<String> setterComment) {
        this.setterComment = setterComment;
    }


    public List<String> getGetterComment() {
        return getterComment;
    }

    public void setGetterComment(List<String> getterComment) {
        this.getterComment = getterComment;
    }

    public void setConstructorComment(String nullConstructorComment, String constructorComment) {
        this.nullConstructorComment = nullConstructorComment;
        this.paramConstructorComment = constructorComment;
    }

    public void setSetAndGetComment(List<String> getComment, List<String> setComment) {
        this.getterComment = getComment;
        setterComment = setComment;
    }

    /**
     * 自定义属性注释
     *
     * @param sb          新的内容：在 content 里加上了自定义注解
     * @param columnsInfo 字段信息
     * @param content     旧的内容
     * @param i           自定义注解的长度
     * @throws SQLException SQL 异常
     */
    public void customFiledComment(StringBuilder sb, ResultSet columnsInfo, String content, int i) throws SQLException {
        List<String> comment = DbdToBeanContext.getCustomComment().getFiledComment();
        String oneTab = BeanUtils.getT(1);
        String oneLine = BeanUtils.getN(1);
        String oneLineAndOneTab = BeanUtils.getNT(1, 1);
        boolean startSwagger = DbdToBeanContext.getDbdToMvcDefinition().isOpenSwagger();
        if (BeanUtils.isNotEmpty(comment) && comment.size() >= i) {
            columnsInfo.next();
            if(startSwagger) {
                sb.append(oneTab).append("@ApiModelProperty(").append(BeanUtils.addColon(comment.get(i - 1))).append(")").append(oneLine);
            } else {
                // 解析注释类型，生成不同类型的注释
                super.parseCommentType(sb, comment.get(i - 1));
            }
        } else {  // 没有自定义注释，获取数据库的注释
            if (DbdToBeanContext.getDefaultComment().isFieldComment()) {
                if (BeanUtils.isNotEmpty(columnsInfo) && columnsInfo.next()) {
                    if(startSwagger) {
                        sb.append(oneLineAndOneTab).append("@ApiModelProperty(")
                                .append(BeanUtils.addColon(BeanUtils.isNotEmpty(columnsInfo.getString(REMARKS)) ? columnsInfo.getString(REMARKS) : content))
                                .append(")").append(oneLine);
                    } else {
                        // 获取数据库的注释，如果数据库的字段没有注释，且没有自定义注释，则生成规定的注释
                        super.parseCommentType(sb, BeanUtils.isNotEmpty(columnsInfo.getString(REMARKS)) ? columnsInfo.getString(REMARKS) : content);
                    }
                } else {  // 没有自定义注释，数据库的字段没有注释，生成规定的注释
                    if(startSwagger) {
                        sb.append(oneTab).append("@ApiModelProperty(")
                                .append(BeanUtils.addColon(content))
                                .append(")").append(oneLine);
                    } else {
                        // 解析注释类型，生成不同类型的注释
                        super.parseCommentType(sb, content);
                    }
                }
            }
        }
    }

    /**
     * 自定义构造器注释
     *
     * @param sb            内容
     * @param nullConstruct 是否生成无参构造器的注释
     */
    public void customConstructComment(StringBuilder sb, boolean nullConstruct) {
        CustomComment customComment = DbdToBeanContext.getCustomComment();
        if (nullConstruct) {
            // 是否生成无参构造器注释，没有自定义注释则生成规定的注释
            if (DbdToBeanContext.getDefaultComment().isConstructorComment()) {
                if (BeanUtils.isNotEmpty(customComment.getNullConstructorComment())) {
                    super.parseCommentType(sb, DbdToBeanContext.getCustomComment().getNullConstructorComment());
                } else {
                    super.parseCommentType(sb, NULL_CONSTRUCTOR);
                }
            }
        } else {
            // 是否生成有参构造器注释，没有自定义注释则生成规定的注释
            if (DbdToBeanContext.getDefaultComment().isConstructorComment()) {
                if (BeanUtils.isNotEmpty(customComment.getParamConstructorComment())) {
                    super.parseCommentType(sb, customComment.getParamConstructorComment());
                } else {
                    super.parseCommentType(sb, PARAM_CONSTRUCTOR);
                }
            }
        }
    }

    /**
     * 自定义 setter 和 getter 注释
     *
     * @param sb      新的内容：在 content 里加上了自定义注解
     * @param columns 字段信息
     * @param content 旧的内容
     * @param i       自定义注解的长度
     * @param set     是否生成 set 方法的注释
     * @throws SQLException SQL 异常
     */
    public void customSetGetComment(StringBuilder sb, ResultSet columns, String content, int i, boolean set) throws SQLException {
        CustomComment customComment = DbdToBeanContext.getCustomComment();
        // 是否生成 setter 注释，没有自定义注释则生成规定的注释
        if (BeanUtils.isNotEmpty(customComment.getGetterComment()) && customComment.getGetterComment().size() >= i) {
            if (set && BeanUtils.isNotEmpty(columns)) {
                columns.next();
            }
            super.parseCommentType(sb, customComment.getGetterComment().get(i - 1));
        } else {
            // 是否生成 getter 注释，没有自定义注释则生成规定的注释
            generateSetGetComment(sb, columns, set, content);
        }
    }

    /**
     * 生成 setter 和 getter 注释
     *
     * @param sb        内容
     * @param columns   字段信息
     * @param set       是否生成 set 方法的注释
     * @param fieldName 字段名称
     * @throws SQLException SQL 异常
     */
    private void generateSetGetComment(StringBuilder sb, ResultSet columns, boolean set, String fieldName) throws SQLException {
        String setGetString = GET;
        if (DbdToBeanContext.getDefaultComment().isSetAndGetComment()) {
            if (BeanUtils.isNotEmpty(columns)) {
                if (!set) {
                    columns.next();
                } else {
                    setGetString = SET;
                }
                if (BeanUtils.isNotEmpty(columns.getString(REMARKS))) {
                    super.parseCommentType(sb, setGetString + columns.getString(REMARKS) + FIELD_NAME);
                } else {
                    super.parseCommentType(sb, setGetString + fieldName + FIELD_NAME);
                }
            } else {
                super.parseCommentType(sb, setGetString + fieldName + FIELD_NAME);
            }
        }
    }

    /**
     * 自定义 toString 注释
     *
     * @param sb 内容
     */
    public void customToString(StringBuilder sb) {
        if (DbdToBeanContext.getDefaultComment().isToStringComment()) {
            if (BeanUtils.isNotEmpty(DbdToBeanContext.getCustomComment().getToStringComment())) {
                super.parseCommentType(sb, DbdToBeanContext.getCustomComment().getToStringComment());
            } else {
                super.parseCommentType(sb, TO_STRING);
            }
        }
    }

    /**
     * 自定义 MVC CURD 的方法注释
     * @param sb 内容缓存区
     * @param content 旧的内容
     * @param params 参数的信息
     * @param returnDescribe 返回值的描述
     */
    public void mvcComment(StringBuilder sb, String content, Map<String, String> params, String returnDescribe) {
        super.mvcComment(sb, content, params, returnDescribe);
    }
}
