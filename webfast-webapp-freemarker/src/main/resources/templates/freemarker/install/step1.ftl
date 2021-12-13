<#include '/install/layout.ftl'/>

<#macro blockMain>

<#assign pass = true/>

<table  class="table table-striped table-bordered">
    <thead>
    <tr>
        <th width="40%">环境检测</th>
        <th width="20%">推荐配置</th>
        <th width="20%">当前状态</th>
        <th width="20%">最低要求</th>
    </tr>
    </thead>
    <tbody>

    <tr>
        <td>操作系统</td>
        <td>Linux</td>
        <td>
            <span class="text-success">√ ${envOs!}</span>
        </td>
        <td>--</td>
    </tr>

    <tr>
        <td>Java语言</td>
        <td>11</td>
        <td>
            <#if javaVersion?? && javaVersion gte 8>
            <span class="text-success">√ ${javaVersion!}(${jvmVersion!})</span>
            <#else>
            <span class="text-danger">X ${javaVersion!}(${jvmVersion!})</span>
            </#if>
        </td>
        <td>8</td>
    </tr>

    <tr>
        <td>MySQL JDBC Driver</td>
        <td>必须</td>
        <td>
            <#if mysqlOk!false>
            <span class="text-success">√ 已安装</span>
            <#else>
                <#assign pass = false />
            <span class="text-danger">X 尚未安装MySQL JDBC Driver</span>
            </#if>
        </td>
        <td>必须</td>
    </tr>

    <tr>
        <td>
            文件上传大小
            <div class="text-muted">该值决定可以上传视频的最大大小</div>
        </td>
        <td>大于200M</td>
        <td>
            <#if uploadMaxFilesize?substring(0, (uploadMaxFilesize?length - 2))?number gte 2>
            <span class="text-success">√ ${uploadMaxFilesize!}</span>
            <#else>
                <#assign pass = false />
            <span class="text-danger">X ${uploadMaxFilesize!}</span>
            </#if>
        </td>
        <td>2M</td>
    </tr>
    <tr>
        <td>
            表单数据大小
            <div class="text-muted">该值不能小于文件上传大小的值</div>
        </td>
        <td>大于200M</td>
        <td>

            <#if postMaxsize?substring(0, postMaxsize?length - 2)?number gte 8>
            <span class="text-success">√ ${postMaxsize!}</span>
            <#else>
                <#assign pass = false />
            <span class="text-danger">X ${postMaxsize!}</span>
            </#if>
        </td>
        <td>8M</td>
    </tr>
    </tbody>
</table>

<table class="table table-hover table-striped table-bordered">
    <thead>
    <tr>
        <th width="60%">文件、目录权限检查</th>
        <th width="20%">当前状态</th>
        <th width="20%">所需状态</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td> 安全模式 </td>
        <td>
            <#if safemode! == 'Off'>
                <#assign pass = false />
            <span class="text-danger">√ 开启</span>
            <#else>
            <span class="text-success">√ 关闭</span>
            </#if>
        </td>
        <td>关闭</td>
    </tr>
    <tr>
        <td>${appStoragePath}</td>
        <td>
            <#if appStoragePathOk>
            <span class="text-success">√ 存在</span>
            <#else>
                <#assign pass = false />
            <span class="text-danger">X 不存在</span>
            </#if>
        </td>
        <td>存在</td>

    </tr>
    </tbody>
</table>
<div class="actions">
    <#if pass>
        <form id="_form" method="post" action="${ctx}/install/step1">
            <input type="hidden" name="step" value="1" />
            <a class="btn btn-primary btn-lg" onclick="document.getElementById('_form').submit();">下一步</a>
        </form>
    <#else>
    <a class="btn btn-danger btn-lg" href="${ctx}/install/step1">重新检测</a>
    </#if>
</div>
</#macro>