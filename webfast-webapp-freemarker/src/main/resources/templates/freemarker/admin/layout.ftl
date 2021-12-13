<#import '/web_macro.ftl' as web_macro>
<#import '/admin/admin_macro.ftl' as admin_macro>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class=""> <!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="${csrf_token('site')}" name="csrf-token" />
    <#assign blockTitleParent>${setting('copyright.owned', 'EduSoho')}管理后台</#assign>
    <title><#if blockTitle??><@blockTitle/><#else>${blockTitleParent!}</#if></title>
    <#if setting('site.favicon')??>
    <link href="${ctx}/${setting('site.favicon')}" rel="shortcut icon" />
    </#if>
    <link href="${ctx}/assets/libs/gallery2/bootstrap/3.1.1/css/bootstrap.css" rel="stylesheet" />
    <link href="${ctx}/assets/css/common.css" rel="stylesheet" />
    <link href="${ctx}/bundles/topadmin/css/admin.css" rel="stylesheet" />
    <link href="${ctx}/bundles/topadmin/css/admin_v2.css" rel="stylesheet" />
    <#if blockJavaScripts??><@blockJavaScripts/></#if>
    <!--[if lt IE 9]>
    <script src="${ctx}/assets/libs/html5shiv.js"></script>
    <script src="${ctx}/assets/libs/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${ctx}/admin">${setting('copyright.owned', 'WebFast')}管理后台</a>
        </div>
        <div class="navbar-collapse collapse">

            <ul class="nav navbar-nav">
                <#if userAcl.hasRole('ROLE_ADMIN')>
                <li <#if nav! == 'course'>class="active"</#if>><a href="${ctx}/admin/course">课程</a></li>
                <li <#if nav! == 'user'>class="active"</#if>><a href="${ctx}/admin/user">用户</a></li>
                <li <#if nav! == 'content'>class="active"</#if>><a href="${ctx}/admin/navigation">内容</a></li>
                <li <#if nav! == 'group'>class="active"</#if>><a href="${ctx}/admin/group">小组</a></li>
                <li <#if nav! == 'classroom'>class="active"</#if>><a href="${ctx}/admin/classroom">班级</a></li>
                <li <#if nav! == 'app'>class="active"</#if>> <a href="${ctx}/admin/app/installed">应用</a>
                </li>
                </#if>

                <#if userAcl.hasRole('ROLE_SUPER_ADMIN')>
                <li <#if nav! == 'system'>class="active"</#if>><a href="${ctx}/admin/setting/site">系统</a></li>
                </#if>

            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="${ctx}/index"><i class="glyphicon glyphicon-home"></i> 回首页</a></li>
                <li><a href="${ctx}/user/show/${(appUser.uri)!(appUser.id)!}"><i class="glyphicon glyphicon-user"></i> ${(appUser.nickname)!'佚名'}</a></li>
                <li><a href="${ctx}/logout"><i class="glyphicon glyphicon-off"></i> 退出</a></li>
            </ul>

        </div><!--/.navbar-collapse -->
    </div>
</div>

<div class="container">
    <div class="row"><#if blockContent??><@blockContent/></#if></div>
</div>


<#if blockFooter??><@blockFooter/></#if>

<#assign script_main>${ctx}/bundles/topadmin/js/admin-app.js</#assign>
<#include '/script_boot.ftl'>

<div id="modal" class="modal" ></div>
</body>
</html>