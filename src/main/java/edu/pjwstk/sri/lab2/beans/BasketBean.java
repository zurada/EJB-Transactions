package edu.pjwstk.sri.lab2.beans;

import edu.pjwstk.sri.lab2.dao.ProductDao;
import edu.pjwstk.sri.lab2.dto.OrderItem;
import edu.pjwstk.sri.lab2.model.Product;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Stateful
@Data
@Slf4j
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BasketBean implements Serializable {

    private List<OrderItem> orderItemList;
    private ProductDao productDao;
    private String message = "Log messages will be here.";

    @Resource
    private EJBContext ejbContext;

    @Inject
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostConstruct
    private void postConstruct() {
        log.info("BasketBeanProxy postConstruct");
        this.orderItemList = new ArrayList<>();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addToBasket(Product product) {
        Optional<OrderItem> orderItemOptional = orderItemList.stream().filter(item -> Objects.equals(item.getProduct().getId(), product.getId())).findAny();
        if (orderItemOptional.isPresent()) {
            orderItemOptional.get().increaseAmount();
            log.info("Increased product quantity: {}", orderItemOptional.get());
            setMessage("Increased product quantity");
        } else {
            OrderItem newOrder = OrderItem.builder().amount(1).product(product).build();
            orderItemList.add(newOrder);
            log.info("Added new product to list: {}", newOrder);
            setMessage("Added new product to list: " + newOrder.getProduct().getName());
        }
        log.info("Basket is: {}", orderItemList);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void checkout() {
        updateProductsStock();
        orderItemList.clear();
    }

    private void updateProductsStock() {
        orderItemList.forEach(orderItem -> {
            Product product = productDao.findById(orderItem.getProduct().getId());
            product.setStock(product.getStock() - orderItem.getAmount());
            if (product.getStock() < 0) {
                ejbContext.setRollbackOnly();
                log.error("Not sufficient stock - rolling back the checkout");
                setMessage("Not sufficient stock - rolling back the checkout");
                return;
            }
            if (!ejbContext.getRollbackOnly()) {
                productDao.update(product);
            }
        });
    }

}
