package org.itheima.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*
* 自定义元数据对象处理器*/

@Slf4j
@Component
public class MyMateObjecthandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
metaObject.setValue("createTime", LocalDateTime.now());
metaObject.setValue("updateTime", LocalDateTime.now());
metaObject.setValue("createUser", BaseContext.getThreadContext());
metaObject.setValue("updateUser",BaseContext.getThreadContext() );
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getThreadContext());

    }
}
