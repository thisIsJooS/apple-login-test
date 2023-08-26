package demo.social.applelogin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Slf4j
@Component
public class HttpClientUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String doGet(String url) {
        String result = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        Integer statusCode = null;
        String reasonPhrase = null;

        try {
            httpclient = HttpClients.createDefault();
            HttpGet get = new HttpGet(url);
            response = httpclient.execute(get);
            statusCode = response.getStatusLine().getStatusCode();
            reasonPhrase = response.getStatusLine().getReasonPhrase();
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);

            if (statusCode != 200) {
                log.error(String.format("[doGet]http get url(%s) failed. status code:%s. reason:%s. result:%s", url, statusCode, reasonPhrase, result));
            }
        } catch (Throwable t) {
            log.error(String.format("[doGet]http get url(%s) failed. status code:%s. reason:%s.", url, statusCode, reasonPhrase), t);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                log.error(String.format("[doGet]release http get resource failed. url(%s). reason:%s.", url, e.getMessage()));
            }
        }

        return result;
    }

    public static String doPost(String url, Map<String, String> param) {
        String result = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        Integer statusCode = null;
        String reasonPhrase = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            List<NameValuePair> nvps = new ArrayList<>();
            Set<Entry<String, String>> entrySet = param.entrySet();
            for (Entry<String, String> entry : entrySet) {
                String fieldName = entry.getKey();
                String fieldValue = entry.getValue();
                nvps.add(new BasicNameValuePair(fieldName, fieldValue));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps);
            httpPost.setEntity(formEntity);
            response = httpclient.execute(httpPost);
            statusCode = response.getStatusLine().getStatusCode();
            reasonPhrase = response.getStatusLine().getReasonPhrase();
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");

            if (statusCode != 200) {
                log.error(String.format("[doPost]post url(%s) failed. status code:%s. reason:%s. param:%s. result:%s", url, statusCode, reasonPhrase, objectMapper.writeValueAsString(param), result));
            }
            EntityUtils.consume(entity);
        } catch (Throwable t) {
            try {
                log.error(String.format("[doPost]post url(%s) failed. status code:%s. reason:%s. param:%s.", url, statusCode, reasonPhrase, objectMapper.writeValueAsString(param)), t);
            } catch (JsonProcessingException e) {
            }
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                try {
                    log.error(String.format("[doPost]release http post resource failed. url(%s). reason:%s, param:%s.", url, e.getMessage(), objectMapper.writeValueAsString(param)));
                } catch (JsonProcessingException ex) {
                }
            }
        }
        return result;
    }

}