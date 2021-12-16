<#assign ctx = request.contextPath />
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class=""> <!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><#if blockTitle??><@blockTitle/><#else>WebFast安装程序</#if></title>
    <link href="${ctx}/assets/libs/gallery2/bootstrap/3.0.1/css/bootstrap.css" rel="stylesheet" />
    <link href="${ctx}/install/assets/install.css" rel="stylesheet" />
    <!--[if lt IE 9]>
    <script src="${ctx}/assets/libs/html5shiv.js"></script>
    <![endif]-->

    <!--[if IE 8]>
    <script src="${ctx}/assets/libs/respond.min.js"></script>
    <![endif]-->

</head>
<body>
<div class="install-container">
    <div class="paper">
        <div class="paper-heading">
            <h1><span class="brand">WebFast开发平台</span> <span class="text-muted">安装向导 <small class="text-info">v${(appConst.version)!'5.3.2'}</small></span></h1>
        </div>
        <div class="paper-body">
            <#if step?? && step gt 0>
            <ul class="nav nav-pills nav-justified">
                <li class="disabled <#if step == 1>active</#if>"><a>1. 环境检测</a></li>
                <li class="disabled <#if step == 2>active</#if>"><a>2. 创建数据库</a></li>
                <li class="disabled <#if step == 3>active</#if>"><a>3. 初始化系统</a></li>
                <li class="disabled <#if step == 4>active</#if>"><a>4. 完成安装</a></li>
            </ul>
            </#if>
            <#if blockMain??><@blockMain/></#if>
        </div>
    </div>
</div>

<script>var app = {}; app.debug = false;</script>
<script src="${ctx}/assets/libs/seajs/seajs/2.2.1/sea.js"></script>
<script src="${ctx}/assets/libs/seajs-global-config.js"></script>
<#if blockBottomScripts??><@blockBottomScripts/></#if>

</body>
</html>