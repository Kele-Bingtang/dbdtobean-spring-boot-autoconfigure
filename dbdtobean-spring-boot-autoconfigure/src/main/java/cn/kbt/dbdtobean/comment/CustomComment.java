package cn.kbt.dbdtobean.comment;

import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    private static final String GET = "获取」";
    private static final String REMARKS = "REMARKS";
    private static final String FIELD_NAME = "「的属性值";
    private static final String TO_STRING = "重写 toString 方法，使用该方法可以在控制台打印属性的数据";
    /**
     * JavaBean内容的自定义注释
     */
    private List<String> filedComment = null;
    private String nullConstructorComment = "";
    private String paramConstructorComment = "";
    private String toStringComment = "";
    private List<String> SetComment = null;
    private List<String> GetComment = null;

    public CustomComment() {
    }

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

    public List<String> getSetComment() {
        return SetComment;
    }

    public void setSetComment(List<String> setComment) {
        SetComment = setComment;
    }


    public List<String> getGetComment() {
        return GetComment;
    }

    public void setGetComment(List<String> getComment) {
        GetComment = getComment;
    }

    public void setConstructorComment(String nullConstructorComment, String constructorComment) {
        this.nullConstructorComment = nullConstructorComment;
        this.paramConstructorComment = constructorComment;
    }

    public void setSetAndGetComment(List<String> getComment, List<String> setComment) {
        this.GetComment = getComment;
        SetComment = setComment;
    }

    /*
        自定义属性注释
    */
    public StringBuilder customFiledComment(StringBuilder sb, ResultSet columnsInfo, String content, int i) throws SQLException {
        if (DbdToBeanContext.getCustomComment().getFiledComment() != null && DbdToBeanContext.getCustomComment().getFiledComment().size() >= i) {
            if (BeanUtils.isNotEmpty(columnsInfo)) {
                columnsInfo.next();
            }
            //解析注释类型，生成不同类型的注释
            super.parseCommentType(sb, DbdToBeanContext.getCustomComment().getFiledComment().get(i - 1));
        } else {  //没有自定义注释，获取数据库的注释
            if (DbdToBeanContext.getDefaultComment().isFieldComment()) {
                if (BeanUtils.isNotEmpty(columnsInfo) && columnsInfo.next()) {
                    //获取数据库的注释
                    if (BeanUtils.isNotEmpty(columnsInfo.getString(REMARKS))) {
                        super.parseCommentType(sb, columnsInfo.getString(REMARKS));
                    } else {  //没有自定义注释，数据库的字段没有注释，生成规定的注释
                        //解析注释类型，生成不同类型的注释
                        super.parseCommentType(sb, content);
                    }
                } else {  //没有自定义注释，数据库的字段没有注释，生成规定的注释
                    //解析注释类型，生成不同类型的注释
                    super.parseCommentType(sb, content);
                }
            }
        }
        return sb;
    }

    /*
         自定义构造器注释
     */
    public StringBuilder customConstructComment(StringBuilder sb, boolean nullConstruct) {
        CustomComment customComment = DbdToBeanContext.getCustomComment();
        if (nullConstruct) {
            //是否生成无参构造器注释，没有自定义注释则生成规定的注释
            if (DbdToBeanContext.getDefaultComment().isConstructorComment()) {
                if (BeanUtils.isNotEmpty(customComment.getNullConstructorComment())) {
                    super.parseCommentType(sb, DbdToBeanContext.getCustomComment().getNullConstructorComment());
                } else {
                    super.parseCommentType(sb, NULL_CONSTRUCTOR);
                }
            }
        } else {
            //是否生成有参构造器注释，没有自定义注释则生成规定的注释
            if (DbdToBeanContext.getDefaultComment().isConstructorComment()) {
                if (BeanUtils.isNotEmpty(customComment.getParamConstructorComment())) {
                    super.parseCommentType(sb, customComment.getParamConstructorComment());
                } else {
                    super.parseCommentType(sb, PARAM_CONSTRUCTOR);
                }
            }
        }
        return sb;
    }

    /*
        自定义setter和getter注释
    */
    public StringBuilder customSetGetComment(StringBuilder sb, ResultSet columns, String content, int i, boolean set) throws SQLException {
        CustomComment customComment = DbdToBeanContext.getCustomComment();
        //是否生成set注释，没有自定义注释则生成规定的注释
        if (BeanUtils.isNotEmpty(customComment.getGetComment()) && customComment.getGetComment().size() >= i) {
            if (set) {
                if (BeanUtils.isNotEmpty(columns)) {
                    columns.next();
                }
            }
            super.parseCommentType(sb, customComment.getGetComment().get(i - 1));
        } else {
            //是否生成get注释，没有自定义注释则生成规定的注释
            generateSetGetComment(sb, columns, set, content);
        }
        return sb;
    }

    /*
        生成setter和getter注释
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

    /*
         自定义toString注释
    */
    public StringBuilder customToString(StringBuilder sb) throws SQLException {
        if (DbdToBeanContext.getDefaultComment().isToStringComment()) {
            if (BeanUtils.isNotEmpty(DbdToBeanContext.getCustomComment().getToStringComment())) {
                super.parseCommentType(sb, DbdToBeanContext.getCustomComment().getToStringComment());
            } else {
                super.parseCommentType(sb, TO_STRING);
            }
        }
        return sb;
    }

}
