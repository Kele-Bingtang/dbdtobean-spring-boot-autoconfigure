package cn.kbt.dbdtobean.comment;

import cn.kbt.dbdtobean.utils.BeanUtils;

import java.util.Map;

/**
 * @author Kele-Bing
 * @version 1.6
 * @since 2021/9/20 17:33
 * 抽象注释类
 */
public abstract class AbstractComment {

    /**
     * 两个参数：
     * 1. // 代表在上方生成注释，默认
     * 2. /* 代表在上方生成 /*  *'/' 注释
     * 3. /** 代表在上方生成 /**  *'/' 注释
     */
    private String commentType = "//";

    /**
     * 设置注释的类型
     *
     * @param commentType 注释的类型
     */
    public void setCommentType(String commentType) {
        if (!"//".equals(commentType) && !"/*".equals(commentType) && !"/**".equals(commentType)) {
            this.commentType = "/**";
        } else {
            this.commentType = commentType;
        }
    }

    /**
     * 给类的内容注释
     * 解析注释类型，生成不同类型的注释
     *
     * @param sb      字符串
     * @param comment 注释
     */
    protected void parseCommentType(StringBuilder sb, String comment) {
        parseCommentType(sb, comment, commentType, false);
    }

    /**
     * 给类的内容注释
     * 解析注释类型，生成不同类型的注释
     *
     * @param sb          字符串
     * @param comment     注释
     * @param commentType 注释的类型
     */
    protected void parseCommentType(StringBuilder sb, String comment, String commentType, boolean isHeadComment) {
        String oneTab = BeanUtils.getT(1);
        String oneLine = BeanUtils.getN(1);
        String oneLineOneTab = BeanUtils.getNT(1, 1);
        String oneLineTwoTab = BeanUtils.getNT(1, 2);
        if(!isHeadComment) {
            sb.append(oneTab);
        }
        if ("//".equals(commentType)) {
            sb.append("// ").append(comment).append(oneLine);
        } else if ("/*".equals(commentType)) {
            sb.append("/*").append(isHeadComment ? oneLineOneTab : oneLineTwoTab)
                    .append(comment).append(isHeadComment ? oneLine : oneLineOneTab)
                    .append("*/").append(oneLine);
        } else {
            sb.append("/**").append(isHeadComment ? oneLine : oneLineOneTab)
                    .append(" * ").append(comment).append(isHeadComment ? oneLine : oneLineOneTab)
                    .append(" */").append(oneLine);
        } 
    }

    /**
     *MVC 的 CURD 方法注释
     * @param sb 内容缓存区
     * @param content 旧的内容
     * @param params 参数的信息
     * @param returnDescribe 返回值的描述
     */
    protected void mvcComment(StringBuilder sb, String content, Map<String, String> params, String returnDescribe) {
        sb.append(BeanUtils.getT(1)).append("/**").append(BeanUtils.getNT(1, 1))
                .append(" * ").append(content).append(BeanUtils.getNT(1, 1));
        if(!BeanUtils.isEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(" * @param ").append(entry.getKey()).append(" ").append(entry.getValue()).append(BeanUtils.getNT(1, 1));
            }
        }
        sb.append(" * @return ").append(returnDescribe).append(BeanUtils.getNT(1, 1))
                .append(" */").append(BeanUtils.getN(1));
    }
}
