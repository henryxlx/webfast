<div class="page-header"><h1>实名认证</h1>
</div>
<ul class="nav nav-tabs">
    <li <#if submenu! == 'approving'> class="active" </#if> >
    <a  href="${ctx}/admin/approval/approving">审核中</a>
    </li>
    <li <#if submenu! == 'approved'> class="active" </#if> >
    <a  href="${ctx}/admin/approval/approved">审核成功</a>
    </li>
</ul>
<br>

<form id="user-search-form" class="form-inline well well-sm" action="" method="get" novalidate>
    <div class="form-group">
        <select id="keywordType" class="form-control" name="keywordType">
            <@select_options {'':'--关键词类型--','nickname':'用户名','email':'邮件地址'} RequestParameters['keywordType']! />
        </select>

    </div>

    <div class="form-group">
        <input type="text" id="keyword" name="keyword" class="form-control" value="${RequestParameters['keyword']!}" placeholder="关键词">
    </div>

    <button class="btn btn-primary">搜索</button>

</form>