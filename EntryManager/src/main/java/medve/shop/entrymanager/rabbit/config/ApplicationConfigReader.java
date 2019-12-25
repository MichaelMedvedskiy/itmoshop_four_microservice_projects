package medve.shop.entrymanager.rabbit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfigReader {



	//OrderManager


	@Value("${OrderManager.exchange.name}")
	private String OrderManagerExchange;
		//add
		@Value("${OrderManagerAdd.queue.name}")
		private String OrderManagerAddQueue;

		@Value("${OrderManagerAdd.routing.key}")
		private String OrderManagerAddRoutingKey;

		//paid
		@Value("${OrderManagerPaid.queue.name}")
		private String OrderManagerPaidQueue;

		@Value("${OrderManagerPaid.routing.key}")
		private String OrderManagerPaidRoutingKey;

	//Payment
	@Value("${Payment.exchange.name}")
	private String PaymentExchange;

	@Value("${Payment.queue.name}")
	private String PaymentQueue;

	@Value("${Payment.routing.key}")
	private String PaymentRoutingKey;


	//Warehouse

	@Value("${Warehouse.exchange.name}")
	private String WarehouseExchange;

	@Value("${Warehouse.queue.name}")
	private String WarehouseQueue;

	@Value("${Warehouse.routing.key}")
	private String WarehouseRoutingKey;

	public String getOrderManagerExchange() {
		return OrderManagerExchange;
	}

	public void setOrderManagerExchange(String orderManagerExchange) {
		OrderManagerExchange = orderManagerExchange;
	}


	public String getOrderManagerAddQueue() {
		return OrderManagerAddQueue;
	}

	public void setOrderManagerAddQueue(String orderManagerAddQueue) {
		OrderManagerAddQueue = orderManagerAddQueue;
	}

	public String getOrderManagerAddRoutingKey() {
		return OrderManagerAddRoutingKey;
	}

	public void setOrderManagerAddRoutingKey(String orderManagerAddRoutingKey) {
		OrderManagerAddRoutingKey = orderManagerAddRoutingKey;
	}

	public String getOrderManagerPaidQueue() {
		return OrderManagerPaidQueue;
	}

	public void setOrderManagerPaidQueue(String orderManagerPaidQueue) {
		OrderManagerPaidQueue = orderManagerPaidQueue;
	}

	public String getOrderManagerPaidRoutingKey() {
		return OrderManagerPaidRoutingKey;
	}

	public void setOrderManagerPaidRoutingKey(String orderManagerPaidRoutingKey) {
		OrderManagerPaidRoutingKey = orderManagerPaidRoutingKey;
	}

	public String getPaymentExchange() {
		return PaymentExchange;
	}

	public void setPaymentExchange(String paymentExchange) {
		PaymentExchange = paymentExchange;
	}

	public String getPaymentQueue() {
		return PaymentQueue;
	}

	public void setPaymentQueue(String paymentQueue) {
		PaymentQueue = paymentQueue;
	}

	public String getPaymentRoutingKey() {
		return PaymentRoutingKey;
	}

	public void setPaymentRoutingKey(String paymentRoutingKey) {
		PaymentRoutingKey = paymentRoutingKey;
	}

	public String getWarehouseExchange() {
		return WarehouseExchange;
	}

	public void setWarehouseExchange(String warehouseExchange) {
		WarehouseExchange = warehouseExchange;
	}

	public String getWarehouseQueue() {
		return WarehouseQueue;
	}

	public void setWarehouseQueue(String warehouseQueue) {
		WarehouseQueue = warehouseQueue;
	}

	public String getWarehouseRoutingKey() {
		return WarehouseRoutingKey;
	}

	public void setWarehouseRoutingKey(String warehouseRoutingKey) {
		WarehouseRoutingKey = warehouseRoutingKey;
	}



}
