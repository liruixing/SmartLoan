package com.lrx.module_dbdata;

import android.content.Context;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * create by Dennis
 * on 2020-01-06
 * description：
 **/
public class TestDBOption {

    private static TestDBOption instance = null;

    private TestDBOption(){}

    public TestDBOption getInstance(){
        if(instance == null)
            instance = new TestDBOption();
        return instance;
    }


    public void addTestData(){
        TestDB testDB = new TestDB();

        testDB.setCreateTime(System.currentTimeMillis()+"");
        testDB.setTitle("张三");
        testDB.setContent("李四");
        testDB.save();

        List<TestDB> listData = new ArrayList<>();
        for(int i=0;i<10;i++){
            TestDB testDB1 = new TestDB();
            testDB1.setCreateTime(System.currentTimeMillis()+"");
            testDB1.setTitle("张三");
            testDB1.setContent("李四");
            listData.add(testDB1);
        }

        LitePal.saveAll(listData);
    }

    public void deleteTestData(int position){
        //删除单个记录，id=1
        LitePal.delete(TestDB.class, position);
        //删除数据库中NewsBean表的所有记录
        LitePal.deleteAll(TestDB.class);
        //删除数量大于100的
        LitePal.deleteAll(TestDB.class, "number > ?", "100");
        //删除标题为张三的
        LitePal.deleteAll(TestDB.class, "title = ?", "张三");
        //删除标题为张三，内容为李四的记录
        LitePal.deleteAll(TestDB.class, "title = ? and content = ?", "张三", "李四");
    }

    public void updateTestData(Context ctx){
        //①：修改ID为1的记录，并将标题和内容分别设置为张三、李四
        TestDB newsBean = LitePal.find(TestDB.class, 1);
        newsBean.setTitle("张三");
        newsBean.setContent("李四");
        if (newsBean.save()) {
            Toast.makeText(ctx, "修改成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ctx, "修改失败", Toast.LENGTH_SHORT).show();
        }
        //如下方式同上
        TestDB newsBean1 = new TestDB();
        newsBean1.setTitle("张三");
        newsBean1.setContent("李四");
        //直接更新id为1的记录
        newsBean1.update(1);
        //③：修改ID为1的记录
        TestDB newsBean2 = new TestDB();
        //更新所有title为张三的记录,更新为李四
        newsBean2.setTitle("李四");
        newsBean2.updateAll("title = ?", "张三");
        //④：将title为张三，content为李四的数据修改为王五、赵六
        TestDB newsBean3 = new TestDB();
        newsBean3.setTitle("王五");
        newsBean3.setContent("赵六");
        newsBean3.updateAll("title = ? and content = ?", "张三", "李四");
    }

    private void queryData(){
        //①：查询表中所有的记录，返回的是泛型为NewsBean的List集合
        List<TestDB> newsBeanList = LitePal.findAll(TestDB.class);

        //②：查找表id为1的记录
        TestDB newsBean = LitePal.find(TestDB.class, 1);

        //③：获取表中的第一条数据与最后一条数据
        TestDB firstNews = LitePal.findFirst(TestDB.class);
        TestDB latNews = LitePal.findLast(TestDB.class);

        //④：查询表中的第5、10、15条数据
        List<TestDB> newsList1 = LitePal.findAll(TestDB.class, 5, 10, 15);
        //或者定义一个数组
        long[] ids = new long[]{5, 10, 15};
        List<TestDB> newsList2 = LitePal.findAll(TestDB.class, ids);

        //⑤：查找title为张三的记录,并且以时长作排序(按时间desc倒序  asc 正序)，where()方法接收任意个字符串参数，
        //其中第一个参数用于进行条件约束，从第二个参数开始，都是用于替换第一个参数中的占位符的。那这个where()方法就对应了一条SQL语句中的where部分。
        List<TestDB> movies = LitePal.where("title = ?", "张三").
                order("time desc").find(TestDB.class);
        //将查询出的新闻按照发布的时间倒序排列，只要title和content这两列数据，即最新发布的新闻放在最前面，那就可以这样写：
        List<TestDB> newsList = LitePal.select("title", "content")
                .where("title > ?", "张三")
                .order("time desc").find(TestDB.class);
        //设置查询的数量与偏移量
        List<TestDB> newsBeans = LitePal.select("title", "content")
                .where("title > ?", "张三")
                .order("time desc")
                .limit(30)//只查询前面30条
                .offset(20)//分页查询，每次查20条
                .find(TestDB.class);
    }


    public void syncQuery(){

        //1、 异步查询示例
        //使用findallasync() 代替 findall() ，然后调用 listen() 方法，查询完成就会回调onFinish(）方法
        LitePal.findAllAsync(TestDB.class).listen(new FindMultiCallback<TestDB>() {
            @Override
            public void onFinish(List<TestDB> allSongs) {
                //查询结果
            }
        });
        //2、异步保存示例
        TestDB newsBean = new TestDB();
        newsBean.setTitle("张三");
        newsBean.setContent("李四");
        newsBean.saveAsync().listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
            }
        });
    }

}
