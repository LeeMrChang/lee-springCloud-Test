package cn.lee.elasticsearch.test;

import cn.lee.elasticsearch.pojo.Item;
import cn.lee.elasticsearch.repository.ItemRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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

    /**
     * 高级查询
     * 根据构建器查询
     */
    @Test
    public void testQuery1(){
        // 查询条件构造器MatchQueryBuilder   设置词条条件查询
        //QueryBuilders.matchQuery   用来创建条件
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "小米");
        // 执行查询
        Iterable<Item> items = this.itemRepository.search(queryBuilder);
        items.forEach(System.out::println);
    }

    /**
     * 自定义构建器查询
     */
    @Test
    public void testNativeQuery(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米"));
        // 执行搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 打印总条数
        System.out.println(items.getTotalElements());
        // 打印总页数
        System.out.println(items.getTotalPages());
        items.forEach(System.out::println);
    }

    /**
     * 自定义构建器分页查询
     */
    @Test
    public void testNativeQuery1(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));

        // 初始化分页参数
        int page = 0;
        int size = 3;
        // 设置分页参数
        queryBuilder.withPageable(PageRequest.of(page, size));

        // 执行搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 打印总条数
        System.out.println(items.getTotalElements());
        // 打印总页数
        System.out.println(items.getTotalPages());
        // 每页大小
        System.out.println(items.getSize());
        // 当前页
        System.out.println(items.getNumber());
        items.forEach(System.out::println);
    }

    /**
     * 高级查询，排序查询
     */
    @Test
    public void testSort(){
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询  类目根据手机查
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));

        // 排序     查出来的手机根据价钱 倒序展示
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));

        // 执行搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 打印总条数
        System.out.println(items.getTotalElements());
        items.forEach(System.out::println);
    }

    /**
     * 聚合查询，前提必须有自定义条件查询构建器
     */
    @Test
    public void testAgg(){
        /**
         *  NativeSearchQueryBuilder
         */
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }
    }

    /**
     * 嵌套聚合查询
     */
    @Test
    public void testSubAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            // 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }

    }

}
