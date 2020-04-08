package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryImpl();
    @Override
    public List<Category> Findall() {
        List<Category> cs = null;
        //1.获取redis
        Jedis jedis = new Jedis();
        //2.查询redis是否有category
//        Set<String> categorys = jedis.zrange("category", 0, -1);
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        //3.redis中没有  从数据库中查询
        if (categorys==null||categorys.size()==0){
            //从数据库中获取
             cs = dao.Findall();
            //将获取信息存入redis中
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
            //如果不为空redis信息存入 List中
            cs= new ArrayList<Category>();
            for (Tuple n : categorys) {
                Category category = new Category();
                category.setCname(n.getElement());
                category.setCid((int)n.getScore());
                cs.add(category);

            }
        }
        return cs;
    }
}
