package org.hbs.security.resource;

import org.hbs.rezoom.util.CommonValidator;
import org.hbs.rezoom.util.IConstProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer, IConstProperty
{

	private static final long	serialVersionUID	= -7514925071729240560L;

	@Value("${application.physical.paths}")
	private String				applicationPhysicalPaths;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		if (CommonValidator.isNotNullNotEmpty(applicationPhysicalPaths) && applicationPhysicalPaths.contains(HASH))
		{
			String[] resource = null;
			for (String resources : applicationPhysicalPaths.split(COMMA_SPACE.trim()))
			{
				resource = resources.split(HASH);
				registry.addResourceHandler(SLASH + resource[0].trim() + SLASH_STARS).addResourceLocations(resource[1].trim());
			}
		}
		else
		{
			System.out.println("-----------Application Physical Paths not having key value pair by # seperated---------------------");
			registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		}

		System.out.println("-----------applicationPhysicalPaths---------------------" + applicationPhysicalPaths);
	}
}
