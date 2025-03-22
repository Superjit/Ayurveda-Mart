package com.spring.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.BuyProduct;
import com.spring.entity.ProductWithAddressDTO;

import jakarta.transaction.Transactional;

public interface BuyProductRepo extends JpaRepository<BuyProduct, Long> {

	@Modifying
	@Transactional
	@Query("UPDATE BuyProduct b SET b.deliveredDate = :deliveredDate, b.status = :status WHERE b.buyProductId = :buyProductId")
	int updateDeliveredDetailsById(Long buyProductId, LocalDateTime deliveredDate, String status);
	
	@Modifying
	@Transactional
	@Query("UPDATE BuyProduct b SET b.dispatchedDate = :dispatchedDate, b.status = :status WHERE b.buyProductId = :buyProductId")
	int updatedispatchedDetailsById(Long buyProductId, LocalDateTime dispatchedDate, String status);
	
	@Query(value = "SELECT * FROM buyproduct WHERE email = :email", nativeQuery = true)
    List<BuyProduct> findByEmail(@Param("email") String email);

	
	  // Query to find the count of purchases and total price for a specific productId
    @Query("SELECT COUNT(bp), SUM(bp.price * bp.quantity) FROM BuyProduct bp WHERE bp.productId = :productId")
    Object[] findCountAndTotalPriceByProductId(@Param("productId") Long productId);
    
    @Query("SELECT SUM(bp.price) FROM BuyProduct bp WHERE bp.productId in :productId")
    Double findTotalPriceByProductId(@Param("productId") List<Long> productId);
    
    @Query("SELECT COUNT(bp) FROM BuyProduct bp WHERE bp.productId in :productId AND bp.status = 'Pending'")
    Long countPendingOrdersByProductId(@Param("productId") List<Long> productId);
    
    @Query("SELECT COUNT(bp) FROM BuyProduct bp WHERE bp.productId in :productId AND bp.status = 'Delivered Successfully'")
    Long countDeliveredOrdersByProductId(@Param("productId") List<Long> productId);
    
    @Query("SELECT COUNT(bp) FROM BuyProduct bp WHERE bp.productId in :productId AND bp.status = 'Canceled'")
    Long countCanceledOrdersByProductId(@Param("productId") List<Long> productId);
    
    @Query("SELECT SUM(bp.quantity) FROM BuyProduct bp WHERE bp.productId in :productId")
    Long sumQuantityByProductId(@Param("productId") List<Long> productId);
   
    @Query(value =
            "SELECT  CONCAT('', SUM(bp.price)) AS value " +
            "FROM BuyProduct bp WHERE bp.productId IN :productIds " +
            "UNION ALL " +
            "SELECT  CONCAT('', COUNT(bp.productId)) AS value " +
            "FROM BuyProduct bp WHERE bp.productId IN :productIds AND bp.status = 'Pending' " +
            "UNION ALL " +
            "SELECT  CONCAT('', SUM(bp.price)) AS value " +
            "FROM BuyProduct bp WHERE bp.productId IN :productIds AND bp.status = 'Pending' " +
            "UNION ALL " +
            "SELECT  CONCAT('', SUM(bp.quantity)) AS value " +
            "FROM BuyProduct bp WHERE bp.productId IN :productIds AND bp.status = 'Delivered Successfully' " +
            "UNION ALL " +
            "SELECT  CONCAT('', SUM(bp.price)) AS value " +
            "FROM BuyProduct bp WHERE bp.productId IN :productIds AND bp.status = 'Delivered Successfully' " +
            "UNION ALL " +
            "SELECT  CONCAT('', COUNT(bp.productId)) AS value " +
            "FROM BuyProduct bp WHERE bp.productId IN :productIds AND bp.status = 'Cancelled' " +
            "UNION ALL " +
            "SELECT  CONCAT('', SUM(bp.quantity)) AS value " +
            "FROM BuyProduct bp WHERE bp.productId IN :productIds")
    List<String> findStatisticsByProductIds(@Param("productIds") List<Long> productIds);



    @Query("SELECT FUNCTION('MONTH', b.buyDate) AS month, b.productId, SUM(b.price) AS totalRevenue " +
            "FROM BuyProduct b " +
            "GROUP BY FUNCTION('MONTH', b.buyDate), b.productId " +
            "ORDER BY FUNCTION('MONTH', b.buyDate), b.productId")
     List<Object[]> getTotalRevenueByMonth();


     @Query("SELECT YEAR(bp.deliveredDate) AS year, MONTH(bp.deliveredDate) AS month, SUM(bp.price) AS totalRevenue " +
    	       "FROM BuyProduct bp " +
    	       "WHERE bp.productId IN :productIds " +
    	       "GROUP BY YEAR(bp.deliveredDate), MONTH(bp.deliveredDate) " +
    	       "ORDER BY YEAR(bp.deliveredDate), MONTH(bp.deliveredDate)")
    	List<Object[]> getTotalRevenueByMonth(List<Long> productIds);

    	@Query("SELECT DAYOFWEEK(bp.buyDate) AS dayOfWeek, SUM(bp.price) AS totalRevenue " +
    		       "FROM BuyProduct bp " +
    		       "WHERE bp.productId IN :productIds " +
    		       "GROUP BY DAYOFWEEK(bp.buyDate) " +
    		       "ORDER BY DAYOFWEEK(bp.buyDate)")
    		List<Object[]> getTotalRevenueByDayOfWeek(List<Long> productIds);


    		
    		@Query("SELECT new com.spring.entity.ProductWithAddressDTO(bp.buyProductId, bp.email, bp.productId, bp.categoryName, " +
    			       "bp.quantity, bp.price, bp.paymentMethod, bp.addressId, bp.buyDate, bp.status, bp.deliveredDate, " +
    			       "ca.email, ca.mobileNumber, ca.firstName, ca.lastName, ca.street, ca.city, ca.state, ca.country, ca.postalCode) " +
    			       "FROM BuyProduct bp JOIN CustomerAddress ca ON bp.addressId = ca.id " +
    			       "WHERE (bp.status = 'Pending' or bp.status = 'Dispatched') AND bp.productId IN :productIds " +
    			       "ORDER BY bp.buyDate DESC") // Order by buyDate in descending order
    			List<ProductWithAddressDTO> findPendingProductsWithAddresses(@Param("productIds") List<Long> productIds);

    
    
    long countByStatus(String status);
    
    

}
 