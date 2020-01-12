package com.example.readhub.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.readhub.data.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;

import static com.example.readhub.Constants.HEADLINE;
import static com.example.readhub.Constants.ID;
import static com.example.readhub.Constants.SITE_SOURCE;
import static com.example.readhub.Constants.TIME_STAMP;
import static com.example.readhub.Constants.TYPE;
import static com.example.readhub.Constants.URL;


/**
 * DAO模式，实现数据层操作
 */
public class NewsDao {

    /**
     * select * from LinkData
     *
     * @return 所有新闻的列表
     */
    public List<NewsEntity> getAllNews() {

        List<NewsEntity> list = new ArrayList<>();

        DBHelper helper = DBHelper.getInstance();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from News", null);

        if (cursor.moveToFirst()) {
            do {

                String headline = cursor.getString(cursor.getColumnIndex(HEADLINE));
                String siteSource = cursor.getString(cursor.getColumnIndex(SITE_SOURCE));
                String url = cursor.getString(cursor.getColumnIndex(URL));
                int timeStamp = cursor.getInt(cursor.getColumnIndex(TIME_STAMP));
                String type = cursor.getString(cursor.getColumnIndex(TYPE));
                int id = cursor.getInt(cursor.getColumnIndex(ID));

                list.add(new NewsEntity(headline, siteSource, url, type, timeStamp, id));

            } while (cursor.moveToNext());

        }

        cursor.close();
        return list;
    }


    /**
     * 把每条DB中没有的数据插入
     *
     * @param newsList 要插入的新闻列表
     */
    void insertNewsIfNotExist(@NonNull List<NewsEntity> newsList) {

        // 防御性编程，通常要求入参应该做null判断
        if (newsList == null || newsList.isEmpty()) {
            return;
        }
        for (NewsEntity news : newsList) {

            if (news == null) continue;
            String headline = news.getHeadline();
            String siteSource = news.getSiteSource();
            String url = news.getUrl();
            String type = news.getType();

            long timeStamp = news.getTimeStamp();
            int id = news.getID();

            DBHelper helper = DBHelper.getInstance();

            SQLiteDatabase db = helper.getWritableDatabase();

            db.execSQL("insert or ignore into News(timeStamp, headline, siteSource, url, type, id) values(?, ?, ?, ?, ?, ?)",
                    new String[]{String.valueOf(timeStamp), headline, siteSource, url, type, String.valueOf(id)});
        }
    }

    /**
     * @param endTime    得到新闻的时间戳的上界
     * @param numOfPiece 想得到新闻的条数
     * @param type       想得到新闻的种类
     * @return 返回查询得到时间逆序排列的新闻的List
     */

    List<NewsEntity> getLatestNews(int numOfPiece, @NonNull String type, long endTime) {

        List<NewsEntity> list = new ArrayList<>();

        DBHelper helper = DBHelper.getInstance();

        SQLiteDatabase db = helper.getReadableDatabase();

        // 这里编译器提示了，应该释放cursor, 注意在finally里释放
        Cursor cursor = db.rawQuery("select * from News where timeStamp < ? and type = ? "
                        + "order by timeStamp desc limit ? ",
                new String[]{String.valueOf(endTime), type, String.valueOf(numOfPiece)});

        if (cursor.moveToFirst()) {
            do {
                String headline = cursor.getString(cursor.getColumnIndex(HEADLINE));
                String siteSource = cursor.getString(cursor.getColumnIndex(SITE_SOURCE));
                String url = cursor.getString(cursor.getColumnIndex(URL));
                int timeStamp = cursor.getInt(cursor.getColumnIndex(TIME_STAMP));
                int id = cursor.getInt(cursor.getColumnIndex(ID));

                list.add(new NewsEntity(headline, siteSource, url, type, timeStamp, id));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    /**
     * 删掉太旧的新闻
     *
     * @param timeLimits 保留最近多长时间段的新闻
     */
    void deleteOldNews(long timeLimits) {

        long endTime = System.currentTimeMillis() / 1000 - timeLimits;

        DBHelper helper = DBHelper.getInstance();

        SQLiteDatabase db = helper.getWritableDatabase();

        db.execSQL("delete from News where timeStamp < ?",
                new String[]{String.valueOf(endTime)});
    }
}
