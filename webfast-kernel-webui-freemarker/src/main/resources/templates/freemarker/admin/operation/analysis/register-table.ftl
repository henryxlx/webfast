<table id="user-table" class="table table-striped table-hover" data-search-form="#user-search-form">
    <thead>
    <tr>
        <th>用户名</th>
        <th>注册邮箱</th>
        <th>注册来源</th>
        <th>IP地址</th>
        <th>注册时间</th>
    </tr>
    </thead>
    <tbody>
    <#list registerDetail! as data>
    <tr>
        <td>${data.username}</td>
        <td>${data.email}</td>
        <td><#if data.type=="default">网站注册
            <#elseif data.type=="weibo">新浪微博
            <#elseif data.type=="qq">QQ
            <#elseif data.type=="renren">人人
            <#elseif data.type=="discuz">discuz
            <#elseif data.type=="phpwind">phpwind
            <#else>其他
            </#if></td>
        <td>${data.createdIp}</td>
        <td>${data.createdTime?number_to_date?string("yyyy-MM-dd HH:mm:ss")}</td>
    </tr>
    </#list>
    </tbody>
</table>
<@web_macro.paginator paginator!/>