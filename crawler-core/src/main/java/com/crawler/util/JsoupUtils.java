package com.crawler.util;

import com.crawler.domain.TransferEntity;
import com.xiaoleilu.hutool.util.CollectionUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

public class JsoupUtils {

    /**
     * 根据正则表达式抓取网页的内容（返回Document对象）
     * @return
     */
    public static Document getUrlDocument(TransferEntity te, Map<String, String> reqHeadersMap, Map<String, String> postParamsMap) throws Exception {
        Connection conn = Jsoup.connect(te.getUrl());
        Document docData;
        // 设置请求头信息
        if(CollectionUtil.isNotEmpty(reqHeadersMap)) {
            conn.headers(reqHeadersMap);
        }
        // 设置代理信息
        if(StrUtil.isNotEmpty(te.getProxyIp())) {
            conn.proxy(te.getProxyIp(), te.getProxyPort());
        }
        // 设置请求体信息
        if(CollectionUtil.isNotEmpty(postParamsMap)) {
            conn.data(postParamsMap);
        }
        if("post".equals(te.getHttpMethod().toLowerCase())) {
            docData = conn.timeout(20000).post();
        }else {
            docData = conn.timeout(20000).get();
        }
        return docData;
    }

    /**
     * 根据正则表达式抓取网页的内容（返回Elements对象）
     * @return
     */
    public static Elements getUrlContentsByPattern(TransferEntity te, Map<String, String> reqHeadersMap, Map<String, String> postParamsMap) throws Exception {
            Connection conn = Jsoup.connect(te.getUrl());
            Document docData;
            // 设置请求头信息
            if(CollectionUtil.isNotEmpty(reqHeadersMap)) {
                conn.headers(reqHeadersMap);
            }
            // 设置代理信息
            if(StrUtil.isNotEmpty(te.getProxyIp())) {
                conn.proxy(te.getProxyIp(), te.getProxyPort());
            }
            // 设置请求体信息
            if(CollectionUtil.isNotEmpty(postParamsMap)) {
                conn.data(postParamsMap);
            }
            if("post".equals(te.getHttpMethod().toLowerCase())) {
                docData = conn.timeout(20000).post();
            }else {
                docData = conn.timeout(20000).get();
            }
            Elements elements = docData.select(te.getPattern());
            // 如果需要根据a标签中的某一段文本过滤a标签
            if(te.isExcludeLink()) {
                for(Element e : elements) {
                    Elements aElements = e.select("a");
                    for(Element aElement : aElements) {
                        // a标签的href属性中不含有目标字符串，删除a标签
                        if(!aElement.attr("href").contains(te.getExcludeLinkStr())) {
                            aElement.remove();
                        }
                    }
                }
            }
            return elements;
    }
}
