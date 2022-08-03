package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.core.DBDToBeanContext;
import cn.kbt.dbdtobean.utils.DBDToBeanUtils;

/**
 * @author Kele-Bing
 * @since  2021/9/23 11:31
 * @version  1.0
 */
public class DBDToCurd {
    /**
     * MVC定义类
     **/
    DBDToMVCDefinition dbdToMVCDefinition = DBDToBeanContext.getDbdToMVCDefinition();

    /**
     * 生成继承第三方jar包内容
     *
     * @param content 内容
     * @param entityName 实体类名
     * @return 内容
     */
    public StringBuilder createImport(StringBuilder content, String entityName) {
        int endPackage = content.indexOf(";");
        String importContent = "\n\nimport " + dbdToMVCDefinition.getEntityLocation() + "." + entityName + ";";
        content.insert(endPackage + 1, importContent);
        return content;
    }

    /**
     * 生成根据ID查询数据的方法
     *
     * @param content 内容
     * @param entityName 实体类名
     * @param isController 是否在 Controller 生成
     * @return 内容
     */
    public StringBuilder createQueryById(StringBuilder content, String entityName, boolean isController) {
        content.append("\tpublic ").append(entityName).append(" query")
                .append(entityName)
                .append("ById")
                .append("(");
        if(isController && dbdToMVCDefinition.isGenerateRequestBody()) {
            content.append("@RequestBody ");
        }
        content.append(entityName)
                .append(" ")
                .append(DBDToBeanUtils.firstCharToLowerCase(entityName))
                .append(")");
        return content;
    }

    /**
     * 生成查询所有数据的方法
     *
     * @param content 内容
     * @param entityName 实体类名
     * @return 内容
     */
    public StringBuilder createQuery(StringBuilder content, String entityName) {
        int endPackage = content.indexOf(";");
        content.insert(endPackage + 1, "\n\nimport java.util.List;");
        content.append("\tpublic ").append("List<").append(entityName).append("> query")
                .append(entityName)
                .append("List()");
        return content;
    }

    /**
     * 生成插入数据的方法
     *
     * @param content 内容
     * @param entityName 实体类名
     * @param isController 是否在 Controller 生成
     * @return 内容
     */
    public StringBuilder createInsert(StringBuilder content, String entityName, boolean isController) {
        content.append("\tpublic ").append("int").append(" insert")
                .append(entityName)
                .append("(");
        if(isController && dbdToMVCDefinition.isGenerateRequestBody()) {
            content.append("@RequestBody ");
        }
        content.append(entityName)
                .append(" ")
                .append(DBDToBeanUtils.firstCharToLowerCase(entityName))
                .append(")");
        return content;
    }
    
    /**
     * 生成更新数据的方法
     *
     * @param content 内容
     * @param entityName 实体类名
     * @param isController 是否在 Controller 生成
     * @return 内容
     */
    public StringBuilder createUpdate(StringBuilder content, String entityName, boolean isController) {
        content.append("\tpublic ").append("int").append(" update")
                .append(entityName)
                .append("(");
        if(isController && dbdToMVCDefinition.isGenerateRequestBody()) {
            content.append("@RequestBody ");
        }
        content.append(entityName)
                .append(" ")
                .append(DBDToBeanUtils.firstCharToLowerCase(entityName))
                .append(")");
        return content;
    }

    /**
     * 生成根据ID删除数据的方法
     *
     * @param content 内容
     * @param entityName 实体类名
     * @param isController 是否在 Controller 生成
     * @return 内容
     */
    public StringBuilder createDelete(StringBuilder content, String entityName, boolean isController) {
        content.append("\tpublic ").append("int").append(" delete")
                .append(entityName)
                .append("ById")
                .append("(");
        if(isController && dbdToMVCDefinition.isGenerateRequestBody()) {
            content.append("@RequestBody ");
        }
        content.append(entityName)
                .append(" ")
                .append(DBDToBeanUtils.firstCharToLowerCase(entityName))
                .append(")");
        return content;
    }

    /**
     * 生成 Controller 的 CURD 方法
     * V1.6
     *
     * @param content 内容
     * @param entityName 实体类名
     * @param importBeanName import 的类名
     */
    public void generateControllerCURD(StringBuilder content, String entityName, String importBeanName) {
        if (DBDToBeanUtils.isNotEmpty(DBDToBeanContext.getDbdToMVCDefinition().getEntityLocation())) {
            this.createImport(content, entityName);
            if (DBDToBeanUtils.isNotEmpty(importBeanName)) {
                content.append("\t@GetMapping(").append("\"query").append(DBDToBeanUtils.firstCharToUpperCase(entityName)).append("ById").append("\")\n");
                this.createQueryById(content, entityName, true).append(" {\n\t\treturn ").append(DBDToBeanUtils.firstCharToLowerCase(importBeanName)).append(".").append("query").append(entityName).append("ById(").append(DBDToBeanUtils.firstCharToLowerCase(entityName)).append(");\n\t}\n\n");
                content.append("\t@GetMapping(").append("\"query").append(DBDToBeanUtils.firstCharToUpperCase(entityName)).append("List").append("\")\n");
                this.createQuery(content, entityName).append(" {\n\t\treturn ").append(DBDToBeanUtils.firstCharToLowerCase(importBeanName)).append(".").append("query").append(entityName).append("List();\n\t}\n\n");
                content.append("\t@PostMapping(").append("\"insert").append(DBDToBeanUtils.firstCharToUpperCase(entityName)).append("\")\n");
                this.createInsert(content, entityName, true).append(" {\n\t\treturn ").append(DBDToBeanUtils.firstCharToLowerCase(importBeanName)).append(".").append("insert").append(entityName).append("(" + DBDToBeanUtils.firstCharToLowerCase(entityName) + ");\n\t}\n\n");
                content.append("\t@PostMapping(").append("\"update").append(DBDToBeanUtils.firstCharToUpperCase(entityName)).append("\")\n");
                this.createUpdate(content, entityName, true).append(" {\n\t\treturn ").append(DBDToBeanUtils.firstCharToLowerCase(importBeanName)).append(".").append("update").append(entityName).append("(" + DBDToBeanUtils.firstCharToLowerCase(entityName) + ");\n\t}\n\n");
                content.append("\t@PostMapping(").append("\"delete").append(DBDToBeanUtils.firstCharToUpperCase(entityName)).append("ById").append("\")\n");
                this.createDelete(content, entityName, true).append(" {\n\t\treturn ").append(DBDToBeanUtils.firstCharToLowerCase(importBeanName)).append(".").append("delete").append(entityName).append("ById(").append(DBDToBeanUtils.firstCharToLowerCase(entityName)).append(");\n\t}\n\n");
            } else {
                this.createQueryById(content, entityName, true).append(" {\n\t\treturn null;\n\t}\n\n");
                this.createQuery(content, entityName).append(" {\n\t\treturn null;\n\t}\n\n");
                this.createInsert(content, entityName, true).append(" {\n\t\treturn 0;\n\t}\n\n");
                this.createUpdate(content, entityName, true).append(" {\n\t\treturn 0;\n\t}\n\n");
                this.createDelete(content, entityName, true).append(" {\n\t\treturn 0;\n\t}\n\n");
            }
        } else {
            throw new RuntimeException("如果使用CURD，请设置实体类路径：.setEntityLocation(\"\")");
        }
    }
    /**
     * 生成实现类或者 Controller 的CURD
     *
     * @param content 内容
     * @param entityName 实体类名
     * @param isInterface 是否是接口
     * @param importBeanName import 的类名
     */
    public void generateImplCURD(StringBuilder content, String entityName, boolean isInterface, String importBeanName) {
        if (dbdToMVCDefinition.isGeneratecurd()) {
            if (DBDToBeanUtils.isNotEmpty(DBDToBeanContext.getDbdToMVCDefinition().getEntityLocation())) {
                this.createImport(content, entityName);
                if (DBDToBeanUtils.isNotEmpty(importBeanName)) {
                    this.createQueryById(content, entityName, false).append(" {\n\t\treturn ").append(DBDToBeanUtils.firstCharToLowerCase(importBeanName)).append(".").append("query").append(entityName).append("ById(").append(DBDToBeanUtils.firstCharToLowerCase(entityName)).append(");\n\t}\n\n");
                    this.createQuery(content, entityName).append(" {\n\t\treturn ").append(DBDToBeanUtils.firstCharToLowerCase(importBeanName)).append(".").append("query").append(entityName).append("List();\n\t}\n\n");
                    this.createInsert(content, entityName, false).append(" {\n\t\treturn ").append(DBDToBeanUtils.firstCharToLowerCase(importBeanName)).append(".").append("insert").append(entityName).append("(").append(DBDToBeanUtils.firstCharToLowerCase(entityName)).append(");\n\t}\n\n");
                    this.createUpdate(content, entityName, false).append(" {\n\t\treturn ").append(DBDToBeanUtils.firstCharToLowerCase(importBeanName)).append(".").append("update").append(entityName).append("(").append(DBDToBeanUtils.firstCharToLowerCase(entityName)).append(");\n\t}\n\n");
                    this.createDelete(content, entityName, false).append(" {\n\t\treturn ").append(DBDToBeanUtils.firstCharToLowerCase(importBeanName)).append(".").append("delete").append(entityName).append("ById(").append(DBDToBeanUtils.firstCharToLowerCase(entityName)).append(");\n\t}\n\n");
                } else {
                    this.createQueryById(content, entityName, false).append(" {\n\t\treturn null;\n\t}\n\n");
                    this.createQuery(content, entityName).append(" {\n\t\treturn null;\n\t}\n\n");
                    this.createInsert(content, entityName, false).append(" {\n\t\treturn 0;\n\t}\n\n");
                    this.createUpdate(content, entityName, false).append(" {\n\t\treturn 0;\n\t}\n\n");
                    this.createDelete(content, entityName, false).append(" {\n\t\treturn 0;\n\t}\n\n");
                }
                if (isInterface) {
                    this.generateOverride(content);
                }
            } else {
                throw new RuntimeException("如果使用CURD，请设置实体类路径：.setEntityLocation(\"\")");
            }
        }
    }

    /**
     * 生成接口的CURD
     *
     * @param content 内容
     * @param entityName 实体类名
     */
    public void generateInterCURD(StringBuilder content, String entityName) {
        if (dbdToMVCDefinition.isGeneratecurd()) {
            if (DBDToBeanUtils.isNotEmpty(DBDToBeanContext.getDbdToMVCDefinition().getEntityLocation())) {
                this.createImport(content, entityName);
                this.createQueryById(content, entityName, false).append(";\n\t\n");
                this.createQuery(content, entityName).append(";\n\t\n");
                this.createInsert(content, entityName, false).append(";\n\t\n");
                this.createUpdate(content, entityName, false).append(";\n\t\n");
                this.createDelete(content, entityName, false).append(";\n\t\n");
            } else {
                throw new RuntimeException("如果使用CURD，请设置实体类路径：.setEntityLocation(\"\")");
            }
        }
    }

    /**
     * 生成实现类的重写注解
     *
     * @param content 内容
     * @return 内容
     */
    public StringBuilder generateOverride(StringBuilder content) {
        final String OVERRIDE = "@Override";
        final int beforeInsertIndex = 2;
        int index = content.indexOf("public");
        while (index > -1 && index <= content.length()) {
            index = content.indexOf("public", index + OVERRIDE.length() + beforeInsertIndex + 1);
            if (index > -1) {
                content.insert(index - beforeInsertIndex, "\n\t" + OVERRIDE);
            }
        }
        return content;
    }

}
