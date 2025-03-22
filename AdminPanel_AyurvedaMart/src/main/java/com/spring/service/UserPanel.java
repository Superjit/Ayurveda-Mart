package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spring.entity.ProductWithAddressDTO;

@Service
public class UserPanel {

    private final RestTemplate restTemplate;

    @Value("${service.a.url:http://localhost:8084}") // Inject Service A URL from application properties
    private String serviceAUrl;

    public UserPanel(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch total price by product ID
    public Double getTotalPriceById(List<Long> productId) {
        String url = serviceAUrl + "/buyProduct/totalPriceByProductId";
        return restTemplate.postForObject(url,productId, Double.class);
    }
//    public Double getTotalPriceById(Long productId) {
//    	String url = serviceAUrl + "/buyProduct/totalPriceByProductId/" + productId;
//    	return restTemplate.getForObject(url, Double.class);
//    }

    // Fetch pending orders count by product ID
    public Long getPendingOrdersCountByProductId(List<Long> productId ) {
        String url = serviceAUrl + "/buyProduct/countPendingOrdersByProductId";
        return restTemplate.postForObject(url,productId, Long.class);
    }
    public Long countDeliveredOrdersByProductId(List<Long> productId ) {
    	String url = serviceAUrl + "/buyProduct/countDeliveredOrdersByProductId";
    	return restTemplate.postForObject(url,productId, Long.class);
    }
    public Long sumQuantityByProductId(List<Long> productId) {
    	String url = serviceAUrl + "/buyProduct/sumQuantityByProductId";
    	return restTemplate.postForObject(url,productId, Long.class);
    }
    public String[] findStatisticsByProductIds(List<Long> productId) {
    	String url = serviceAUrl + "/buyProduct/findStatisticsByProductIds";
    	return restTemplate.postForObject(url,productId, String[].class);
    }
    public Object[] getTotalRevenueByMonth(List<Long> productId) {
    	String url = serviceAUrl + "/buyProduct/getTotalRevenueByMonth";
    	return restTemplate.postForObject(url,productId, Object[].class);
    }
    public Object[] getTotalRevenueByDayOfWeek(List<Long> productId) {
    	String url = serviceAUrl + "/buyProduct/getTotalRevenueByDayOfWeek";
    	return restTemplate.postForObject(url,productId, Object[].class);
    }
    public ProductWithAddressDTO[] findPendingProductsWithAddresses(List<Long> productId) {
    	String url = serviceAUrl + "/buyProduct/findPendingProductsWithAddresses";
    	return restTemplate.postForObject(url,productId, ProductWithAddressDTO[].class);
    }
    public String deliver(Long buyProductId) {
        String url = serviceAUrl + "/buyProduct/deliver";
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(buyProductId), String.class).getBody();
    }
    public String dispatch(Long buyProductId) {
    	String url = serviceAUrl + "/buyProduct/dispatch";
    	return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(buyProductId), String.class).getBody();
    }
}
