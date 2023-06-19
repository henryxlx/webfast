package com.jetwinner.webfast.mvc.block;

/**
 * 定义用于页面块Block逻辑处理的接口，空接口只是个标识，用于快速过滤和筛选
 * 本接口主要用于在系统启动时扫描过滤带有此接口的控制器，获取其中带有@BlockRenderMethod注解方法的签名与URL地址映射，
 * 供在页面上使用FreeMarker自定义宏<@renderController path=..../>通过path属性查找URL找到对应的带@BlockRenderMethod注解的方法，
 * 完成相关代码逻辑功能的调用，这是一个基于FreeMarker自己实现的页面视图调用机制
 *
 * @author xulixin
 */
public interface BlockRenderController {
}
