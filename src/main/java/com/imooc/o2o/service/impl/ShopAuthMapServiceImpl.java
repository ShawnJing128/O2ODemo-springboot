package com.imooc.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ShopAuthMapDao;
import com.imooc.o2o.dto.ShopAuthMapExecution;
import com.imooc.o2o.entity.ShopAuthMap;
import com.imooc.o2o.enums.ShopAuthMapStateEnum;
import com.imooc.o2o.exceptions.ShopAuthMapOperationException;
import com.imooc.o2o.service.ShopAuthMapService;
import com.imooc.o2o.util.PageCalculator;
@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService{
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;
	@Override
	public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
		// 空值判断
		if(shopId != null && pageIndex != null && pageSize !=null) {
			// 页转行
			int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			// 查询返回该店铺的授权信息列表
			List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(shopId, beginIndex, pageSize);
			// 返回总数
			int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);
			ShopAuthMapExecution se = new ShopAuthMapExecution();
			se.setShopAuthMapList(shopAuthMapList);
			se.setCount(count);
			return se;
		}
		return null;
	}

	@Override
	public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
		return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
	}

	@Override
	@Transactional
	public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
		//空值判断，对店铺Id和员工Id做校验
		if(shopAuthMap!=null && shopAuthMap.getShop()!=null && shopAuthMap.getShop().getShopId()!=null 
				&& shopAuthMap.getEmployee()!=null && shopAuthMap.getEmployee().getUserId()!=null) {
			shopAuthMap.setCreateTime(new Date());
			shopAuthMap.setLastEditTime(new Date());
			shopAuthMap.setEnableStatus(1);
			shopAuthMap.setTitleFlag(0);
			try {
				// 添加授权信息
				int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
				if(effectedNum <= 0) {
					throw new ShopAuthMapOperationException("添加授权失败");
				}
				return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,shopAuthMap);
			}catch (Exception e) {
				throw new ShopAuthMapOperationException("添加授权失败" + e.toString());
			}
		} else {
			return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
		}
	}

	@Override
	public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
		// 空值判断，主要是对授权Id做校验
		if (shopAuthMap == null || shopAuthMap.getShopAuthId() == null) {
			return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
		} else {
			try {
				shopAuthMap.setLastEditTime(new Date());
				int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
				if (effectedNum <= 0) {
					return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
				} else {// 创建成功
					return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS, shopAuthMap);
				}
			} catch (Exception e) {
				throw new ShopAuthMapOperationException("modifyShopAuthMap error: " + e.getMessage());
			}
		}
	}

}
