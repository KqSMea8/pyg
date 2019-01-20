package com.pyg.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.GoodsService;
import com.pyg.pojo.TbGoods;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import com.pyg.vo.Goods;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference(timeout = 1000000)
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public PygResult add(@RequestBody Goods goods){
		// 获取当前商家的账号
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		// 把商家编号设置到商品对象中
		goods.getGoods().setSellerId(sellerId);
		// 调用远程服务方法
		return goodsService.add(goods);
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public PygResult update(@RequestBody TbGoods goods){
		try {
			goodsService.update(goods);
			return new PygResult(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new PygResult(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbGoods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public PygResult delete(Long [] ids){
		try {
			goodsService.delete(ids);
			return new PygResult(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new PygResult(false, "删除失败");
		}
	}

	/**
	 * 查询+分页
	 * @param goods
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		// 根据商家id查询
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		// 把商家id设置参数对象goods
		goods.setSellerId(sellerId);
		return goodsService.findPage(goods, page, rows);
	}


}
