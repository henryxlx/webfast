
<table class="table table-condensed table-bordered table-striped table-hover">
    <thead>
    <tr>
        <th width="35%"></th>
        <th width="20%">今日</th>
        <th width="20%" >昨日</th>
        <#if userAcl.hasRole('ROLE_SUPER_ADMIN')>
        <th width="40%">历史</th>
        </#if>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>新注册用户数</td>
        <td><span class="pull-right">${todayRegisterNum!}</span></td>
        <td><span class="pull-right">${yesterdayRegisterNum!}</span></td>
        <#if userAcl.hasRole('ROLE_SUPER_ADMIN')>
        <td><a href="${ctx}/admin/operation/analysis/register/trend?analysisDateType=register">趋势</a> <span class="text-muted">|</span> <a href="${ctx}/admin/operation/analysis/register/detail?analysisDateType=register">详情</a></td>
        </#if>
    </tr>

    <tr>
        <td>用户登录数<small>（不含重复登录）</small></td>
        <td><span class="pull-right">${todayLoginNum!}</span></td>
        <td><span class="pull-right">${yesterdayLoginNum!}</span></td>
        <#if userAcl.hasRole('ROLE_SUPER_ADMIN')>
        <td><a href="${ctx}/admin/operation/analysis/login/trend?analysisDateType=login">趋势</a> <span class="text-muted">|</span> <a href="${ctx}/admin/operation/analysis/login/detail?analysisDateType=login">详情</a></td>
        </#if>
    </tr>

    <tr>
        <td>用户总数</td>
        <td><span class="pull-right">${todayUserSum!}</span></td>
        <td><span class="pull-right">${yesterdayUserSum!}</span></td>
        <#if userAcl.hasRole('ROLE_SUPER_ADMIN')>
        <td><a href="${ctx}/admin/operation/analysis/user-sum/trend?analysisDateType=user-sum">趋势</a> <span class="text-muted">|</span> <a href="${ctx}/admin/operation/analysis/user-sum/detail?analysisDateType=user-sum">详情</a></td>
        </#if>
    </tr>

    </tbody>
</table>
