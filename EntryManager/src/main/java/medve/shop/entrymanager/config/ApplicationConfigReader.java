package medve.shop.entrymanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfigReader {
	
	@Value("${app1.exchange.name}")
	private String app1Exchange;
	
	@Value("${app1.queue.name}")
	private String app1Queue;
	
	@Value("${app1.routing.key}")
	private String app1RoutingKey;

	@Value("${app2.exchange.name}")
	private String app2Exchange;
	
	@Value("${app2.queue.name}")
	private String app2Queue;
	
	@Value("${app2.routing.key}")
	private String app2RoutingKey;


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

	public String getApp1Exchange() {
		return app1Exchange;
	}

	public void setApp1Exchange(String app1Exchange) {
		this.app1Exchange = app1Exchange;
	}

	public String getApp1Queue() {
		return app1Queue;
	}

	public void setApp1Queue(String app1Queue) {
		this.app1Queue = app1Queue;
	}

	public String getApp1RoutingKey() {
		return app1RoutingKey;
	}

	public void setApp1RoutingKey(String app1RoutingKey) {
		this.app1RoutingKey = app1RoutingKey;
	}

	public String getApp2Exchange() {
		return app2Exchange;
	}

	public void setApp2Exchange(String app2Exchange) {
		this.app2Exchange = app2Exchange;
	}

	public String getApp2Queue() {
		return app2Queue;
	}

	public void setApp2Queue(String app2Queue) {
		this.app2Queue = app2Queue;
	}

	public String getApp2RoutingKey() {
		return app2RoutingKey;
	}

	public void setApp2RoutingKey(String app2RoutingKey) {
		this.app2RoutingKey = app2RoutingKey;
	}


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
