package edu.pjwstk.sri.lab2.beans;

import edu.pjwstk.sri.lab2.dao.CategoryDao;
import edu.pjwstk.sri.lab2.model.Category;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Slf4j
public class CategoryBufferBean {

    private final List<Category> categoryList = new ArrayList<>();
    private CategoryDao categoryDao;

    @Inject
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> findAllCategories() {
        log.info("Finding all categories");
        return !this.categoryList.isEmpty() ? this.categoryList : loadCategoryList();
    }

    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
    private void scheduleLoadCategoryList() {
        log.info("Working scheduler to buffer categories every 10 seconds");
        loadCategoryList();
    }

    public List<Category> loadCategoryList() {
        this.categoryList.clear();
        this.categoryList.addAll(this.categoryDao.listAll(null, null));
        log.info("Loaded list of categories: {}", this.categoryList);
        return this.categoryList;
    }
}
