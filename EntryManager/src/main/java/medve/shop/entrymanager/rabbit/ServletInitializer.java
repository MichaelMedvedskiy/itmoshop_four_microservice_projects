package medve.shop.entrymanager.rabbit;

import medve.shop.entrymanager.EntrymanagerApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EntrymanagerApplication.class);
	}

}
