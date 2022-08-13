package cn.kbt.dbdtobean.comment;

import cn.kbt.dbdtobean.utils.BeanUtils;


/**
 * @author Kele-Bing
 * @version 1.6
 * @since 2021/9/20 17:27
 * 类注释信息
 */
public class HeadComment extends AbstractComment {
    private String author = "@author ";
    private String createTime = "@since ";
    private String version = "@version 1.0";
    private String describe = "";
    private StringBuilder headComments = new StringBuilder();

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public StringBuilder getHeadComments() {
        return headComments;
    }

    public void setHeadComments(StringBuilder headComments) {
        this.headComments = headComments;
    }

    public String getHeadComment() {
        return headComments.toString();
    }

    /**
     * 自定义的类注释
     *
     * @param headComment 注释内容
     */
    public void setHeadComment(String headComment) {
        // 缓存区添加自定义的类注释
        super.parseCommentType(headComments, headComment, "/**", true);
    }

    /**
     * 自定义的类注释和设置注释的类型
     *
     * @param headComment 注释内容
     * @param commentType 注释类型
     */
    public void setHeadComment(String headComment, String commentType) {
        super.parseCommentType(headComments, headComment, commentType, true);
    }

    /**
     * 生成类注释
     *
     * @param author 作者
     * @return 内容
     */
    public StringBuilder generateHeadComments(String author) {
        String oneLine = BeanUtils.getN(1);
        this.headComments.setLength(0);
        this.headComments.append("/**")
                .append(oneLine).append(" * ")
                .append(this.author).append(author)
                .append(oneLine).append(" * ")
                .append(this.createTime).append(BeanUtils.getCurrentTime())
                .append(oneLine).append(" * ")
                .append(this.version);
        if (BeanUtils.isNotEmpty(this.describe)) {
            this.headComments.append(oneLine).append(" * ")
                    .append(this.describe);
        }
        this.headComments.append(oneLine).append(" */").append(oneLine);
        return headComments;
    }

}
