package com.example.readhub.list.helper;

import com.example.readhub.data.entity.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.List;

import static com.example.readhub.Constants.ID;
import static com.example.readhub.Constants.URL;


/**
 * 用于解析JSON格式数据的类
 */
public class JSONParser {

    //表示解析失败
    private static final long FAILED = -1;

    private static final String TITLE = "title";

    private static final String DATA = "data";

    private static final String SITE_NAME = "siteName";

    private static final String PUBLISH_DATE = "publishDate";

    /**
     * 解析JSON数据并利用时间戳把判断是否在数据库中 如果不在就加入
     *
     * @param list        把解析得到的新闻列表加入list中
     * @param dataToParse 待解析的JSON数据
     * @param type        该新闻的种类
     * @return 当前该类新闻最小的时间戳
     */
    public static long parseJSONAndReturnMinTime(String dataToParse, String type, List<News> list) {

        long minTimeStamp = Long.MAX_VALUE;

        try {
            JSONObject jsonObject = new JSONObject(dataToParse);
            JSONArray jsonArray = jsonObject.optJSONArray(DATA);

            if (jsonArray == null || jsonArray.length() == 0) {
                return FAILED;
            }

            //每条数据分别处理
            for (int i = 0; i < jsonArray.length(); ++i) {

                JSONObject obj = jsonArray.optJSONObject(i);

                String headline = obj.optString(TITLE);
                String siteSource = obj.optString(SITE_NAME);
                String url = obj.optString(URL);
                String publishTime = obj.optString(PUBLISH_DATE);
                int id = obj.optInt(ID);

                if (headline.isEmpty() || publishTime.isEmpty() || siteSource.isEmpty() || url.isEmpty()) {
                    return FAILED;
                }

                long timeStamp = getTimeStamp(publishTime);

                //得到的数据插入List
                News news = new News(headline, siteSource, url, type, timeStamp, id);
                list.add(news);

                minTimeStamp = Math.min(minTimeStamp, timeStamp);  //更新最小时间戳
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return minTimeStamp;
    }


    /**
     * 把零时区的yyyy-mm-dd T hh:mm:ss.000Z格式String转化为时间戳
     *
     * @param dataStr 要解析的时间字符串
     * @return timeStamp 解析得到的时间戳
     */
    private static long getTimeStamp(String dataStr) {

        //去掉字符串末尾的Z +00:00表示零时区
        String dataStrPopZ = dataStr.substring(0, dataStr.length() - 1) + "+00:00";

        OffsetDateTime odt = OffsetDateTime.parse(dataStrPopZ);
        return odt.toEpochSecond();
    }
}