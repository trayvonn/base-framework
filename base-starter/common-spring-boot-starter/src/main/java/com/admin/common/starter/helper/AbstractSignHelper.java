package com.admin.common.starter.helper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 吴邪
 * @date 2020/11/27 17:03
 */
@Slf4j
public abstract class AbstractSignHelper {

    public abstract String getSalt();

    /**
     * 生成签名 MD5(ASC(params) + salt + req_time)
     */
    public  String generateSignature(Map<String,Object> params,long timestamp){
        //业务参数默认ASC排序
        TreeMap<String, Object> sortMap = MapUtil.sort(params);
        StringBuffer sb = new StringBuffer(1000);
        //按照key1=value1&key2=value2格式
        sortMap.forEach((k,v)-> sb.append(k).append("=").append(v).append("&"));
        //去掉最后一个&
        sb.deleteCharAt(sb.length() - 1);
        //追加salt和reqTime
        String beforeSign = sb.append(getSalt()).append(timestamp).toString();
        log.info("签名工具 >> 签名加密前：{}",beforeSign);
        String afterSign = DigestUtils.md5DigestAsHex(beforeSign.getBytes(StandardCharsets.UTF_8));
        log.info("签名工具 >> 签名加密后：{}",afterSign);
        return afterSign;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> buildParams(Object data){
        Map<String, Object> params;
        //判断data是列表还是对象
        if (data instanceof List) {
            //如果是列表的话，List<Map>->Map，方便签名
            Map<String, Object> allMap = new HashMap<>();
            AtomicInteger index = new AtomicInteger(0);
            ((List<Object>) data).stream().map(BeanUtil::beanToMap).forEach(map-> map.forEach((key, value)-> allMap.put(key+index.getAndIncrement(),value)));
            params = allMap;
        }else{
            params = BeanUtil.beanToMap(data);
        }
        return params;
    }
}
