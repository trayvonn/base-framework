package com.admin.common.starter.helper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.admin.common.starter.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author 吴邪
 * @since  2020/11/18 14:46
 */
@Slf4j
public class BaseFeignHelper {

    private BaseResponse response;

    public BaseFeignHelper(BaseResponse response){
        this.response = Objects.requireNonNull(response);
    }

    public static BaseFeignHelper of(BaseResponse response){
        return new BaseFeignHelper(response);
    }

    /**
     * 获取BaseResponse的data值，如果正常则返回，失败则返回null
     */
    public <T> T get(Class<T> clazz){
        if(response.isSuccess()){
            return BeanUtil.toBean(response.getData(),clazz);
        }
        return null;
    }

    public <T> T orElse(Class<T> clazz){
        if(response.isSuccess()){
            return BeanUtil.toBean(response.getData(),clazz);
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取BaseResponse的data过程中异常的话，由操作者自定义抛出异常
     */
    public <T,E extends Throwable> T orElseThrow(Class<T> clazz,Function<Object,? extends E> func) throws E {
        if(response.isSuccess()){
            return BeanUtil.toBean(response.getData(),clazz, CopyOptions.create().setFieldNameEditor(StrUtil::toCamelCase));
        }else{
            throw func.apply(response);
        }
    }
}
