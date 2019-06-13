package com.example.websocket;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: energy-system
 * @description: 自动生成代码
 * @author: Thomas.Yang
 * @create: 2018-11-12 14:48
 **/
public class CodeGenerator {

    @Test
    public void generateCode() {
        String projectPath = System.getProperty("user.dir");
        //指定包名
        String packageName = "com.example.websocket";
        String modelName = "main_part";
        //指定生成的表名
        String[] tableNames = new String[]{"user"};
        generateByTables(true, projectPath, packageName, modelName, tableNames);
    }

    /**
     * 根据表自动生成
     *
     * @param serviceNameStartWithI 默认为false
     * @param packageName           包名
     * @param tableNames            表名
     * @author Terry
     */
    private void generateByTables(boolean serviceNameStartWithI, String projectPath, String packageName, String modelName, String... tableNames) {
        //配置数据源
        DataSourceConfig dataSourceConfig = getDataSourceConfig();
        // 策略配置
        StrategyConfig strategyConfig = getStrategyConfig(tableNames);
        //全局变量配置
        GlobalConfig globalConfig = getGlobalConfig(serviceNameStartWithI, projectPath);
        //包名配置
        PackageConfig packageConfig = getPackageConfig(packageName, modelName);
        // 自定义配置
        InjectionConfig injectionConfig = getInjectionConfig(projectPath, modelName);
        //自动生成
        atuoGenerator(dataSourceConfig, strategyConfig, globalConfig, packageConfig, injectionConfig);
    }

    /**
     * 集成
     *
     * @param dataSourceConfig 配置数据源
     * @param strategyConfig   策略配置
     * @param config           全局变量配置
     * @param packageConfig    包名配置
     * @author Terry
     */
    private void atuoGenerator(DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, GlobalConfig config, PackageConfig packageConfig, InjectionConfig injectionConfig) {
        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setTemplate(new TemplateConfig().setXml(null))
                .setCfg(injectionConfig)
                .execute();
    }

    /**
     * 设置包名
     *
     * @param packageName 父路径包名
     * @return PackageConfig 包名配置
     * @author Terry
     */
    private PackageConfig getPackageConfig(String packageName, String modelName) {
        return new PackageConfig()
                .setModuleName(modelName)
                .setParent(packageName)
                .setXml("com/example/websocket/mapper")
                .setMapper("dao")
                .setController("controller")
                .setEntity("entity");
    }

    /**
     * 全局配置
     *
     * @param serviceNameStartWithI false
     * @return GlobalConfig
     * @author Terry
     */
    private GlobalConfig getGlobalConfig(boolean serviceNameStartWithI, String projectPath) {

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig
                .setBaseColumnList(true)
                .setBaseResultMap(true)
                .setActiveRecord(false)
                .setAuthor("lp")
                //设置输出路径
                .setOutputDir(projectPath + "/src/main/java")
                .setFileOverride(false)
                .setOpen(false);
        if (!serviceNameStartWithI) {
            //设置service名
            globalConfig.setServiceName("%sService");
        }
        return globalConfig;
    }

    /**
     * 返回项目路径
     *
     * @param projectName 项目名
     * @return 项目路径
     * @author Terry
     */
    private String getOutputDir(String projectName) {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        int index = path.indexOf(projectName);
        return path.substring(1, index) + projectName + "/src/main/java/";
    }

    /**
     * 策略配置
     *
     * @param tableNames 表名
     * @return StrategyConfig
     * @author Terry
     */
    private StrategyConfig getStrategyConfig(String... tableNames) {
        return new StrategyConfig()
                // 全局大写命名 ORACLE 注意
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                // 表名、字段名、是否使用下划线命名（默认 false）
                //.setDbColumnUnderline(true)
                //从数据库表到文件的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                //需要生成的的表名，多个表名传数组
                .setInclude(tableNames)
                .setRestControllerStyle(true);
    }

    /**
     * 配置数据源
     *
     * @return 数据源配置 DataSourceConfig
     * @author Terry
     */
    private DataSourceConfig getDataSourceConfig() {
        String dbUrl = "jdbc:mysql://47.103.41.208:3306/my_test?useUnicode=true&useSSL=false&characterEncoding=utf8";
        return new DataSourceConfig().setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("lp1992LP")
                .setDriverName("com.mysql.cj.jdbc.Driver");
    }

    private InjectionConfig getInjectionConfig(String projectPath, String modelName) {
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/java/com/example/websocket/" + modelName
                        + "/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }


}
