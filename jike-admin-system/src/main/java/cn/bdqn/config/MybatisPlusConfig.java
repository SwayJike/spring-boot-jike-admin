package cn.bdqn.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.bdqn.mapper")
public class MybatisPlusConfig {


	/**
	 * 3.4.0版本对此部分有更新，如果是旧版本升级，会出现分页失效问题，同时idea会提示PaginationInterceptor过时，新版本改用了MybatisPlusInterceptor
	 *
	 * @return MybatisPlusInterceptor
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 分页插件
		//interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		// 防止全表更新插件
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

		return interceptor;
	}

	/**
	 * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
	 * @return ConfigurationCustomizer
	 */
	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return configuration -> configuration.setUseDeprecatedExecutor(false);
	}

}
