package com.xiao.sell.test;

import com.xiao.sell.domain.ProductCategory;
import com.xiao.sell.repository.ProductCategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Test
    public  void  saveTest(){
        ProductCategory productCategory=new ProductCategory("管饱",8);
        ProductCategory save = productCategoryRepository.save(productCategory);
        Assert.assertNotNull(save.getCategoryId());
    }
}
