package edu.pjwstk.sri.lab2.view;

import edu.pjwstk.sri.lab2.beans.BasketBean;
import edu.pjwstk.sri.lab2.beans.CategoryBufferBean;
import edu.pjwstk.sri.lab2.dto.OrderItem;
import edu.pjwstk.sri.lab2.model.Category;
import edu.pjwstk.sri.lab2.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "clientManagedBean")
@SessionScoped
@Slf4j
@Getter
@NoArgsConstructor
public class ClientManagedBean implements Serializable {

    private CategoryBufferBean categoryBuffer;
    private BasketBean basketBean;

    @Inject
    public void setBasketBean(BasketBean basketBean) {
        this.basketBean = basketBean;
    }

    @Inject
    public void setCategoryBuffer(CategoryBufferBean categoryBuffer) {
        this.categoryBuffer = categoryBuffer;
    }

    public void addToBasket(Product product) {
        basketBean.addToBasket(product);
    }

    public List<OrderItem> getOrderList() {
        return basketBean.getOrderItemList();
    }

    public List<Category> listAllCategories() {
        return this.categoryBuffer.findAllCategories();
    }

    public void checkout() {
        basketBean.checkout();
        categoryBuffer.loadCategoryList();
    }

    public String getMessage(){
        return basketBean.getMessage();
    }
}
