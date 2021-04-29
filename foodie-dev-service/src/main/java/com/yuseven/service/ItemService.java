package com.yuseven.service;

import com.yuseven.pojo.Items;
import com.yuseven.pojo.ItemsImg;
import com.yuseven.pojo.ItemsParam;
import com.yuseven.pojo.ItemsSpec;
import com.yuseven.pojo.vo.CommentLevelCountsVO;
import com.yuseven.pojo.vo.ShopcartVO;
import com.yuseven.utils.PagedGridResult;

import java.util.List;

/**
 * @author Yu Qifeng
 * @Date 2021/3/15 21:25
 * @version v1.0
 */
public interface ItemService {

    /**
     * 根据商品id，查询商品详情
     * @param id 商品ID
     * @return 查询到的商品数据
     */
    Items queryItemById(String id);

    /**
     * 根据商品id，查询商品图片列表
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id，查询商品规格列表
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id，查询商品参数
     * @param itemId
     * @return
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id，查询商品的评级等级数量
     * @param itemId
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id （+评价等级），查询商品评价信息。（分页查询）
     * @param itemId
     * @param commentLevel
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryPagedComments(String itemId, Integer commentLevel, Integer page, Integer pageSize);

    /**
     * 搜索商品列表，通过搜索关键字
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 搜索商品列表，通过三级分类ID
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据规格ids查询最新的购物车中的商品数据（用于刷新渲染购物车中的商品数据）
     * @param specIds
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据规格id，获取规格对象的具体信息
     * @param itemSpecId
     * @return
     */
    ItemsSpec queryItemSpecById(String itemSpecId);

    /**
     * 根据商品id，获取商品图片主图
     * @param itemId
     * @return
     */
    String queryItemMainImgById(String itemId);

    /**
     * 扣除商品规格表中相应的库存数
     * @param itemSpecId
     * @param buyCounts
     */
    void discreaseItemSpecStock(String itemSpecId, int buyCounts);
}
