import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器
 *
 * <p>
 * 根据数据库中的表生成：数据库表映射对象、Mapper 接口、Mapper XML 文件（含基础的 ResultMap 和字段代码片段）、 Service
 * 接口、Service 接口实现类、基础的 Controller 类。
 * </p>
 * <p>
 * 如果数据库设计符合规范，并且代码没有特殊要求。设置好表名前缀（无则忽略）、表字段前缀（无则忽略）后， 直接修改输出的路径、JDBC
 * Url、数据库的账户和密码即可直接生成代码。
 * </p>
 *
 * @author SwayJike
 * @date 2021/3/24
 */
public class MappingGenerator {

    /**
     * 作者
     */
    private static final String AUTHOR = "SwayJike";

    /**
     * 生成代码输出路径，<strong>路径必须是绝对路径，不需要包含包名</strong>
     */
    private static final String CODE_OUTPUT_DIR = "/jike-admin-generator/src/main/java";

    /**
     * 生成mapper文件输出路径
     */
    private static final String MAPPER_OUTPUT_DIR = "/jike-admin-generator/src/main/resources/mapper/";

    /**
     * 生成代码输出的包名
     * <p>
     * 总包名，后续生成的 Mapper，Service 等将在该包下生成子包
     * </p>
     */
    private static final String PACKAGE = "cn.bdqn";

    /**
     * 数据库驱动
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    /**
     * JDBC URL
     */
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/jike_admin?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";

    /**
     * 数据库账户
     */
    private static final String USERNAME = "root";

    /**
     * 数据库账户密码
     */
    private static final String PASSWORD = "root";

    /**
     * 执行即可生成代码 需要添加 freemarker 依赖
     */
    public static void main(String[] args) {
        autoGenerator().execute();
    }

    private static AutoGenerator autoGenerator() {
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig());
        autoGenerator.setDataSource(mysqlDataSourceConfig());
        autoGenerator.setStrategy(strategyConfig());
        autoGenerator.setPackageInfo(packageConfig());

        autoGenerator.setCfg(injectionConfig());
        autoGenerator.setTemplate(new TemplateConfig().setXml(null));
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        return autoGenerator;
    }

    /**
     * 自定义配置
     *
     * @return InjectionConfig
     */
    private static InjectionConfig injectionConfig() {
        String projectPath = System.getProperty("user.dir");
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + MAPPER_OUTPUT_DIR + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 全局配置
     *
     * @return {@link GlobalConfig}
     */
    private static GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor(AUTHOR);
        // 是否打开输出目录
        globalConfig.setOpen(false);
        // 设置代码输出位置，需要绝对路径
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + CODE_OUTPUT_DIR);
        // 覆盖已有的代码
        globalConfig.setFileOverride(true);
        // 基本表的 ResultMap
        globalConfig.setBaseResultMap(true);
        // 基本表字段片段
        globalConfig.setBaseColumnList(true);
        // 开启基于 Model 操作数据库
        // globalConfig.setActiveRecord(true);
        // 设置 ID 类型
         globalConfig.setIdType(IdType.AUTO);
        // 生成基本 Swagger2 文档
         globalConfig.setSwagger2(true);
        // 开启二级缓存
        // globalConfig.setEnableCache(true);
        // 设置生成的对象名称规则，%s 表示当前的 Entity
        // 数据库表映射对象名称规则
         globalConfig.setEntityName("%s");
        //也可以使用下面这种就是末尾会以DO结尾
		//globalConfig.setEntityName("%sDO");
        // Mapper 接口名称规则
        globalConfig.setMapperName("%sMapper");
        // Mapper XML 文件名称规则
        globalConfig.setXmlName("%sMapper");
        // Controller 生成规则
        globalConfig.setControllerName("%sController");
        // Service 接口生成名称规则
        globalConfig.setServiceName("%sService");
        // Service 实现类生成名称规则
        globalConfig.setServiceImplName("%sServiceImpl");
        return globalConfig;
    }

    /**
     * 生成策略配置
     *
     * @return StrategyConfig
     */
    private static StrategyConfig strategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig();
        // 是否大写命名
        strategyConfig.setCapitalMode(false);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 填写表前缀、表字段前缀，生成代码时去除字段前缀
        // strategyConfig.setTablePrefix("tb_");
        // strategyConfig.setFieldPrefix("a_", "c_", "e_", "f_", "p_", "r_", "t_", "tf_");
        strategyConfig.setEntitySerialVersionUID(true);
        // 自定义继承的Entity类全称，带包名
        //strategyConfig.setSuperEntityClass(BaseDO.class);
        // 自定义基础的Entity类，公共字段
        //strategyConfig.setSuperEntityColumns("id");
        //strategyConfig.setSuperEntityColumns("create_time");
        //strategyConfig.setSuperEntityColumns("update_time");
        return strategyConfig;
    }

    /**
     * MySQL 数据源配置
     *
     * @return DataSourceConfig
     */
    private static DataSourceConfig mysqlDataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert());
        dataSourceConfig.setDriverName(DRIVER);
        dataSourceConfig.setUrl(JDBC_URL);
        dataSourceConfig.setUsername(USERNAME);
        dataSourceConfig.setPassword(PASSWORD);
        return dataSourceConfig;
    }

    /**
     * 包相关配置信息
     * <p>
     * 配置生成代码的包信息，如生成的 Mapper 包，Service 包等
     * </p>
     */
    private static PackageConfig packageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        // 设置父包
        packageConfig.setParent(PACKAGE);
        // 设置生成代码的包
        // Controller 包
        packageConfig.setController("controller");
        // 数据库表映射对象包
        packageConfig.setEntity("model.entity");
        // Mapper 接口包，含 XML 文件
        packageConfig.setMapper("mapper");
        // Service 接口包
        packageConfig.setService("service");
        // Service 接口实现类包
        packageConfig.setServiceImpl("service.impl");
        return packageConfig;
    }

}
