package medve.shop.warehouse.rabbit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfigReader {



	//GET WAREHOUSE ITEM
	@Value("${Warehouse-item.exchange.name}")
	private String WarehouseItemExchange;

	@Value("${Warehouse-item.queue.name}")
	private String WarehouseItemQueue;

	@Value("${Warehouse-item.routing.key}")
	private String WarehouseItemRoutingKey;


	public String getWarehouseItemExchange() {
		return WarehouseItemExchange;
	}

	public void setWarehouseItemExchange(String warehouseItemExchange) {
		WarehouseItemExchange = WarehouseItemExchange;
	}

	public String getWarehouseItemQueue() {
		return WarehouseItemQueue;
	}

	public void setWarehouseItemQueue(String warehouseItemQueue) {
		WarehouseItemQueue = warehouseItemQueue;
	}

	public String getWarehouseItemRoutingKey() {
		return WarehouseItemRoutingKey;
	}

	public void setWarehouseItemRoutingKey(String warehouseItemRoutingKey) {
		WarehouseItemRoutingKey = warehouseItemRoutingKey;
	}
}
