package cn.lee.elasticsearch.repository;

import cn.lee.elasticsearch.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @ClassName:ItemRepository
 * @Author：Mr.lee
 * @DATE：2020/04/28
 * @TIME： 16:48
 * @Description: TODO
 */
public interface ItemRepository extends ElasticsearchRepository<Item,Long> {

     List<Item> findByTitle(String title);

     List<Item> findByPriceBetween(Double price1,Double price2);
 }
