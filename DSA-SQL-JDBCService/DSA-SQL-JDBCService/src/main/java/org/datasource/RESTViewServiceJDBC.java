package org.datasource;

import org.datasource.jdbc.JDBCDataSourceConnector;
import org.datasource.jdbc.views.customerdetails.CustomerDetailsView;
import org.datasource.jdbc.views.customerdetails.CustomerDetailsViewBuilder;
import org.datasource.jdbc.views.customers.CustomerView;
import org.datasource.jdbc.views.customers.CustomerViewBuilder;
import org.datasource.jdbc.views.customersadresses.CustomerAddressesView;
import org.datasource.jdbc.views.customersadresses.CustomerAddressesViewBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import org.datasource.jdbc.views.orders.OrderView;
import org.datasource.jdbc.views.orders.OrderViewBuilder;
import org.datasource.jdbc.views.orderitems.OrderItemView;
import org.datasource.jdbc.views.orderitems.OrderItemViewBuilder;
import org.datasource.jdbc.views.pgcustomers.PGCustomerView;
import org.datasource.jdbc.views.pgcustomers.PGCustomerViewBuilder;
import org.datasource.jdbc.views.pgreviews.PGReviewView;
import org.datasource.jdbc.views.pgreviews.PGReviewViewBuilder;

/*	REST Service URL
 	http://localhost:8090/DSA-SQL-JDBCService/rest/customers/CustomerView
 	http://localhost:8090/DSA-SQL-JDBCService/rest/customers/CustomerDetailsView
 	http://localhost:8090/DSA-SQL-JDBCService/rest/customers/CustomerAddressesView
*/
@RestController
@RequestMapping("/customers")
public class RESTViewServiceJDBC {
	private static Logger logger = Logger.getLogger(RESTViewServiceJDBC.class.getName());

	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = { MediaType.TEXT_PLAIN_VALUE })
	@ResponseBody
	public String ping() {
		logger.info(">>>> DSA-SQL-JDBCService:: RESTViewService is Up!");
		return "Ping response from DSA-SQL-JDBCService!";
	}

	@RequestMapping(value = "/CustomerView", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<CustomerView> get_CustomerView() {
		List<CustomerView> viewList = customersViewBuilder.build().getViewList();
		return viewList;
	}

	@RequestMapping(value = "/CustomerViewData", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<CustomerView> get_CustomerView(
			@RequestParam("fetch_offset") Integer fetchOffset,
			@RequestParam("fetch_size") Integer fetchSize) {
		List<CustomerView> viewList = customersViewBuilder
				.setFetchOffset(fetchOffset)
				.setFetchSize(fetchSize)
				.build().getViewList();
		return viewList;
	}

	@RequestMapping(value = "/CustomerDetailsView", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<CustomerDetailsView> get_CustomerDetailsView() {
		List<CustomerDetailsView> viewList = customersDetailsViewBuilder.build().getViewList();
		return viewList;
	}

	@RequestMapping(value = "/CustomerAddressesView", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<CustomerAddressesView> get_CustomerAddressesView() {
		List<CustomerAddressesView> viewList = customersAddressesViewBuilder.build().getViewList();
		return viewList;
	}

	// Set-up
	@Autowired
	private JDBCDataSourceConnector jdbcConnector;
	//
	@Autowired
	private CustomerViewBuilder customersViewBuilder;
	@Autowired
	private CustomerDetailsViewBuilder customersDetailsViewBuilder;
	@Autowired
	private CustomerAddressesViewBuilder customersAddressesViewBuilder;

	// Oracle - Orders endpoints
	@RequestMapping(value = "/OrderView", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<OrderView> get_OrderView() {
		return orderViewBuilder.build().getViewList();
	}

	@RequestMapping(value = "/OrderItemView", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<OrderItemView> get_OrderItemView() {
		return orderItemViewBuilder.build().getViewList();
	}

	@Autowired
	private OrderViewBuilder orderViewBuilder;
	@Autowired
	private OrderItemViewBuilder orderItemViewBuilder;

	// PostgreSQL endpoints
	@RequestMapping(value = "/PGCustomerView", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<PGCustomerView> get_PGCustomerView() {
		return pgCustomerViewBuilder.build().getViewList();
	}

	@RequestMapping(value = "/PGReviewView", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<PGReviewView> get_PGReviewView() {
		return pgReviewViewBuilder.build().getViewList();
	}

	@Autowired
	private PGCustomerViewBuilder pgCustomerViewBuilder;
	@Autowired
	private PGReviewViewBuilder pgReviewViewBuilder;
}