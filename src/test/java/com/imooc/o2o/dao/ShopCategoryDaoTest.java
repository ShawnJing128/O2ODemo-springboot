package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.o2o.entity.ShopCategory;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryDaoTest {
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	@Test
	public void testQueryShopCategory() {
//		//传入空对象
//		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
//		assertEquals(2, shopCategoryList.size());
//		//传入父对象
//		ShopCategory testCategory = new ShopCategory();
//		ShopCategory parentCategory = new ShopCategory();
//		parentCategory.setShopCategoryId(1L);
//		testCategory.setParent(parentCategory);
//		shopCategoryList = shopCategoryDao.queryShopCategory(testCategory);
//		assertEquals(1, shopCategoryList.size());
//		System.out.println(shopCategoryList.get(0).getShopCategoryName());//输出咖啡
		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
		System.out.println(shopCategoryList.size());
	}
}
