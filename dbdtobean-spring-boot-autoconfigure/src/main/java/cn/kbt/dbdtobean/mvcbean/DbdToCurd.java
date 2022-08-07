package cn.kbt.dbdtobean.mvcbean;

import cn.kbt.dbdtobean.comment.CustomComment;
import cn.kbt.dbdtobean.core.DbdToBeanContext;
import cn.kbt.dbdtobean.utils.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kele-Bing
 * @since 2021/9/23 11:31
 * @version 1.6
 */
public class DbdToCurd {
    /**
     * MVC 定义类
     **/
    DbdToMvcDefinition dbdToMvcDefinition = DbdToBeanContext.getDbdToMvcDefinition();
    private final String oneLineAndOneTab = BeanUtils.getNT(1, 1);
    private final String oneLineAndTwoTab = BeanUtils.getNT(1, 2);
    private final String oneTab = BeanUtils.getT(1);
    private final String oneLine = BeanUtils.getN(1);
    private final String twoLine = BeanUtils.getN(2);

    /**
     * 生成继承第三方 jar 包内容
     *
     * @param content 内容
     * @param entityName 实体类名
     */
    private void createImport(StringBuilder content, String entityName) {
        int endPackage = content.indexOf(";");
        String importContent = twoLine + "import " + dbdToMvcDefinition.getEntityLocation() + "." + entityName + ";";
        content.insert(endPackage + 1, importContent);
    }

    /**
     * 生成根据ID查询数据的方法
     *
     * @param content 内容
     * @param entityName 实体类名
     * @param isController 是否在 Controller 生成
     * @return 内容
     */
    private StringBuilder createQueryById(StringBuilder content, String entityName, boolean isController) {
        content.append(oneTab).append("public ").append(entityName).append(" query")
                .append(entityName)
                .append("ById")
                .append("(");
        content.append(entityName)
                .append(" ")
                .append(BeanUtils.firstCharToLowerCase(entityName))
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
    private StringBuilder createQuery(StringBuilder content, String entityName) {
        int endPackage = content.indexOf(";");
        content.insert(endPackage + 1, twoLine + "import java.util.List;");
        content.append(oneTab).append("public ").append("List<").append(entityName).append("> query")
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
    private StringBuilder createInsert(StringBuilder content, String entityName, boolean isController) {
        content.append(oneTab).append("public ").append("int").append(" insert")
                .append(entityName)
                .append("(");
        if(isController && dbdToMvcDefinition.isGenerateRequestBody()) {
            content.append("@RequestBody ");
        }
        content.append(entityName)
                .append(" ")
                .append(BeanUtils.firstCharToLowerCase(entityName))
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
    private StringBuilder createUpdate(StringBuilder content, String entityName, boolean isController) {
        content.append(oneTab).append("public ").append("int").append(" update")
                .append(entityName)
                .append("(");
        if(isController && dbdToMvcDefinition.isGenerateRequestBody()) {
            content.append("@RequestBody ");
        }
        content.append(entityName)
                .append(" ")
                .append(BeanUtils.firstCharToLowerCase(entityName))
                .append(")");
        return content;
    }

    /**
     * 生成根据 ID 删除数据的方法
     *
     * @param content 内容
     * @param entityName 实体类名
     * @param isController 是否在 Controller 生成
     * @return 内容
     */
    private StringBuilder createDeleteById(StringBuilder content, String entityName, boolean isController) {
        content.append(oneTab).append("public ").append("int").append(" delete")
                .append(entityName)
                .append("ById")
                .append("(");
        content.append(entityName)
                .append(" ")
                .append(BeanUtils.firstCharToLowerCase(entityName))
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
    protected void generateControllerCurd(StringBuilder content, String entityName, String importBeanName) {
        if (BeanUtils.isNotEmpty(dbdToMvcDefinition.getEntityLocation())) {
            this.createImport(content, entityName);
            if (BeanUtils.isNotEmpty(importBeanName)) {
                // Swagger 注解
                if(dbdToMvcDefinition.isOpenSwagger()) {
                    String importContent = twoLine + "import io.swagger.annotations.Api;" + oneLine + "import io.swagger.annotations.ApiOperation;";
                    content.insert(content.indexOf(";") + 1, importContent);
                    content.append(oneTab).append("@ApiOperation(").append(BeanUtils.addColon("根据 ID 查询一条数据")).append(")").append(oneLine);
                }
                // GetMapping 注解
                content.append(oneTab).append("@GetMapping(")
                        .append(BeanUtils.addColon("/query" + BeanUtils.firstCharToUpperCase(entityName) + "ById"))
                        .append(")").append(oneLine);
                // QueryById 方法
                this.createQueryById(content, entityName, true).append(" {")
                        .append(oneLineAndTwoTab).append("return ")
                        .append(BeanUtils.firstCharToLowerCase(importBeanName)).append(".")
                        .append("query").append(entityName).append("ById(").append(BeanUtils.firstCharToLowerCase(entityName))
                        .append(");").append(oneLineAndOneTab).append("}").append(twoLine);

                // Swagger 注解
                if(dbdToMvcDefinition.isOpenSwagger()) {
                    content.append(oneTab).append("@ApiOperation(").append(BeanUtils.addColon("查询所有数据")).append(")").append(oneLine);
                }
                // GetMapping 注解
                content.append(oneTab).append("@GetMapping(")
                        .append(BeanUtils.addColon("/query" + BeanUtils.firstCharToUpperCase(entityName) + "List"))
                        .append(")").append(oneLine);
                // QueryList 方法
                this.createQuery(content, entityName).append(" {")
                        .append(oneLineAndTwoTab).append("return ")
                        .append(BeanUtils.firstCharToLowerCase(importBeanName)).append(".")
                        .append("query").append(entityName).append("List();")
                        .append(oneLineAndOneTab).append("}").append(twoLine);

                // Swagger 注解
                if(dbdToMvcDefinition.isOpenSwagger()) {
                    content.append(oneTab).append("@ApiOperation(").append(BeanUtils.addColon("插入一条数据")).append(")").append(oneLine);
                }
                // PostMapping 注解
                content.append(oneTab).append("@PostMapping(")
                        .append(BeanUtils.addColon("/insert" + BeanUtils.firstCharToUpperCase(entityName)))
                        .append(")").append(oneLine);
                // Insert 方法
                this.createInsert(content, entityName, true).append(" {")
                        .append(oneLineAndTwoTab).append("return ")
                        .append(BeanUtils.firstCharToLowerCase(importBeanName)).append(".")
                        .append("insert").append(entityName).append("(").append(BeanUtils.firstCharToLowerCase(entityName))
                        .append(");").append(oneLineAndOneTab).append("}").append(twoLine);

                // Swagger 注解
                if(dbdToMvcDefinition.isOpenSwagger()) {
                    content.append(oneTab).append("@ApiOperation(").append(BeanUtils.addColon("更新一条数据")).append(")").append(oneLine);
                }
                // PostMapping 注解
                content.append(oneTab).append("@PostMapping(")
                        .append(BeanUtils.addColon("/update" + BeanUtils.firstCharToUpperCase(entityName)))
                        .append(")").append(oneLine);
                // Update 方法
                this.createUpdate(content, entityName, true).append(" {")
                        .append(oneLineAndTwoTab).append("return ")
                        .append(BeanUtils.firstCharToLowerCase(importBeanName)).append(".")
                        .append("update").append(entityName).append("(").append(BeanUtils.firstCharToLowerCase(entityName))
                        .append(");").append(oneLineAndOneTab).append("}").append(twoLine);

                // Swagger 注解
                if(dbdToMvcDefinition.isOpenSwagger()) {
                    content.append(oneTab).append("@ApiOperation(").append(BeanUtils.addColon("根据 ID 删除一条数据")).append(")").append(oneLine);
                }
                // PostMapping 注解
                content.append(oneTab).append("@PostMapping(")
                        .append(BeanUtils.addColon("/delete" + BeanUtils.firstCharToUpperCase(entityName) + "ById"))
                        .append(")").append(oneLine);
                // Delete 方法
                this.createDeleteById(content, entityName, true).append(" {")
                        .append(oneLineAndTwoTab).append("return ")
                        .append(BeanUtils.firstCharToLowerCase(importBeanName)).append(".")
                        .append("delete").append(entityName).append("ById(").append(BeanUtils.firstCharToLowerCase(entityName))
                        .append(");").append(oneLineAndOneTab).append("}").append(twoLine);
            } else {
                this.createQueryById(content, entityName, true)
                        .append(" {").append(oneLineAndTwoTab).append("return null;").append(oneLineAndOneTab)
                        .append("}").append(twoLine);
                this.createQuery(content, entityName)
                        .append(" {").append(oneLineAndTwoTab).append("return null;").append(oneLineAndOneTab)
                        .append("}").append(twoLine);
                this.createInsert(content, entityName, true)
                        .append(" {").append(oneLineAndTwoTab).append("return 0;").append(oneLineAndOneTab)
                        .append("}").append(twoLine);
                this.createUpdate(content, entityName, true)
                        .append(" {").append(oneLineAndTwoTab).append("return 0;").append(oneLineAndOneTab)
                        .append("}").append(twoLine);
                this.createDeleteById(content, entityName, true)
                        .append(" {").append(oneLineAndTwoTab).append("return 0;").append(oneLineAndOneTab)
                        .append("}").append(twoLine);
            }
        } else {
            throw new RuntimeException("如果使用CURD，请设置实体类路径：.setEntityLocation()");
        }
    }
    /**
     * 生成实现类的 CURD
     *
     * @param content 内容
     * @param entityName 实体类名
     * @param isInterface 是否是接口
     * @param importBeanName import 的类名
     */
    protected void generateImplCurd(StringBuilder content, String entityName, boolean isInterface, String importBeanName) {
        if (dbdToMvcDefinition.isGenerateCurd()) {
            if (BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation())) {
                this.createImport(content, entityName);
                if (BeanUtils.isNotEmpty(importBeanName)) {
                    this.createQueryById(content, entityName, false)
                            .append(" {").append(oneLineAndTwoTab).append("return ")
                            .append(BeanUtils.firstCharToLowerCase(importBeanName))
                            .append(".").append("query").append(entityName).append("ById(")
                            .append(BeanUtils.firstCharToLowerCase(entityName)).append(");")
                            .append(oneLineAndOneTab).append("}").append(twoLine);
                    
                    this.createQuery(content, entityName)
                            .append(" {").append(oneLineAndTwoTab).append("return ")
                            .append(BeanUtils.firstCharToLowerCase(importBeanName))
                            .append(".").append("query")
                            .append(entityName).append("List();")
                            .append(oneLineAndOneTab).append("}").append(twoLine);
                    
                    this.createInsert(content, entityName, false)
                            .append(" {").append(oneLineAndTwoTab).append("return ")
                            .append(BeanUtils.firstCharToLowerCase(importBeanName))
                            .append(".").append("insert").append(entityName).append("(")
                            .append(BeanUtils.firstCharToLowerCase(entityName)).append(");")
                            .append(oneLineAndOneTab).append("}").append(twoLine);
                    
                    this.createUpdate(content, entityName, false)
                            .append(" {").append(oneLineAndTwoTab).append("return ")
                            .append(BeanUtils.firstCharToLowerCase(importBeanName))
                            .append(".").append("update").append(entityName).append("(")
                            .append(BeanUtils.firstCharToLowerCase(entityName)).append(");")
                            .append(oneLineAndOneTab).append("}").append(twoLine);;
                    
                    this.createDeleteById(content, entityName, false)
                            .append(" {").append(oneLineAndTwoTab).append("return ")
                            .append(BeanUtils.firstCharToLowerCase(importBeanName))
                            .append(".").append("delete").append(entityName).append("ById(")
                            .append(BeanUtils.firstCharToLowerCase(entityName)).append(");")
                            .append(oneLineAndOneTab).append("}").append(twoLine);;
                } else {
                    this.createQueryById(content, entityName, false)
                            .append(" {").append(oneLineAndTwoTab)
                            .append("return null;").append(oneLineAndOneTab)
                            .append("}").append(twoLine);
                    
                    this.createQuery(content, entityName)
                            .append(" {").append(oneLineAndTwoTab)
                            .append("return null;").append(oneLineAndOneTab)
                            .append("}").append(twoLine);
                    
                    this.createInsert(content, entityName, false)
                            .append(" {").append(oneLineAndTwoTab)
                            .append("return 0;").append(oneLineAndOneTab)
                            .append("}").append(twoLine);
                    
                    this.createUpdate(content, entityName, false).append(" {").append(oneLineAndTwoTab)
                            .append("return 0;").append(oneLineAndOneTab)
                            .append("}").append(twoLine);
                    
                    this.createDeleteById(content, entityName, false).append(" {").append(oneLineAndTwoTab)
                            .append("return 0;").append(oneLineAndOneTab)
                            .append("}").append(twoLine);
                }
                if (isInterface) {
                    this.generateOverride(content);
                }
            } else {
                throw new RuntimeException("如果使用CURD，请设置实体类路径：.setEntityLocation()");
            }
        }
    }

    /**
     * 生成接口的 CURD
     *
     * @param content 内容
     * @param entityName 实体类名
     */
    protected void generateInterCurd(StringBuilder content, String entityName) {
        if (dbdToMvcDefinition.isGenerateCurd()) {
            if (BeanUtils.isNotEmpty(DbdToBeanContext.getDbdToMvcDefinition().getEntityLocation())) {
                CustomComment customComment = DbdToBeanContext.getCustomComment();
                Map<String, String> map = new HashMap<>();
                map.put(BeanUtils.firstCharToLowerCase(entityName), "实体对象");
                this.createImport(content, entityName);
                
                customComment.mvcComment(content, "根据 ID 查询一条数据", map, "根据 ID 查询出的实体对象");
                this.createQueryById(content, entityName, false).append(";").append(oneLineAndOneTab).append(oneLine);
                
                customComment.mvcComment(content, "查询所有数据", null, "所有数据的实体对象集合");
                this.createQuery(content, entityName).append(";").append(oneLineAndOneTab).append(oneLine);
                
                customComment.mvcComment(content, "插入一条数据", map, "受影响的行数");
                this.createInsert(content, entityName, false).append(";").append(oneLineAndOneTab).append(oneLine);
                
                customComment.mvcComment(content, "更新一条数据", map, "受影响的行数");
                this.createUpdate(content, entityName, false).append(";").append(oneLineAndOneTab).append(oneLine);
                
                customComment.mvcComment(content, "根据 ID 删除一条数据", map, "受影响的行数");
                this.createDeleteById(content, entityName, false).append(";").append(oneLineAndOneTab).append(oneLine);
            } else {
                throw new RuntimeException("如果使用CURD，请设置实体类路径：.setEntityLocation()");
            }
        }
    }

    /**
     * 生成实现类的重写注解
     *
     * @param content 内容
     */
    private void generateOverride(StringBuilder content) {
        final String OVERRIDE = "@Override";
        final int beforeInsertIndex = 2;
        int index = content.indexOf("public");
        while (index > -1 && index <= content.length()) {
            index = content.indexOf("public", index + OVERRIDE.length() + beforeInsertIndex + 1);
            if (index > -1) {
                content.insert(index - beforeInsertIndex, oneLineAndOneTab + OVERRIDE);
            }
        }
    }
}
