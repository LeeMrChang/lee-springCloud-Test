package cn.lee.elasticsearch.test;

import cn.lee.elasticsearch.pojo.Item;
import cn.lee.elasticsearch.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName:elasticsearchTest
 * @Author：Mr.lee
 * @DATE：2020/04/28
 * @TIME： 16:37
 * @Description: TODO
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {

    /**
     * 注入弹性搜索服务
     */
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * 创建索引与映射
     */
    @Test
    public void IndexTest(){
        //创建 es 的索引库
        this.elasticsearchTemplate.createIndex(Item.class);
        //创建 es 的映射
        this.elasticsearchTemplate.putMapping(Item.class);
    }

    /**
     * 在ES中创建item对象
     * 这个方法本身也是修改方法，如果id相同 ，就覆盖修改的字段
     */
    @Test
    public void index() {
        Item item = new Item(1L, "小米手机7", " 手机",
                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
        itemRepository.save(item);
    }

    /**
     * 在ES中批量增加对象的方法
     */
    @Test
    public void indexList(){

//        List<Item> list = new ArrayList<>();
//        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
//        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));

        this.itemRepository.saveAll(list);
    }

    /**
     * this.itemRepository.delete(Item);  这个是删除的，简单，重点学习查询的
     */


    /**
     * 查询所有
     */
    @Test
    public void testQuery(){
        Optional<Item> item = this.itemRepository.findById(2L);
        System.out.println(item.get());
    }

    /**
     *查询全部，并按照价格降序排序
     */
    @Test
    public void testFind(){
        // 查询全部，并按照价格降序排序
        Iterable<Item> all = this.itemRepository.findAll(Sort.by(Sort.Direction.DESC,"price"));
        //方法引用，直接打印
        all.forEach(System.out::println);
    }

    /**
     * 自定义方法查询 SpringData的强大之处
     */
    @Test
    public void testSpringData(){
        List<Item> items = this.itemRepository.findByTitle("手机");
        items.forEach(System.out::println);
    }

    /**
     * 自定义方法 查看价格区间的数据信息
     */
    @Test
    public void testPriceBetWeen(){
        List<Item> items = this.itemRepository.findByPriceBetween(3600.0,4500.0);
        items.forEach(System.out::println);
    }


}
