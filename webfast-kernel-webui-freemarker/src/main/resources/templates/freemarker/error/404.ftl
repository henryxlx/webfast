<#--<#assign ctx=request.contextPath/>-->

<!DOCTYPE html>
<!--[if lt IE 7]><html class="lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]><html class="lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]><html class="lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class=""> <!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>找不到 - WebFast - Powered By EduNext</title>
    <meta name="keywords" content=" " />
    <meta name="description" content=" " />
    <meta content="" name="csrf-token" />
    <link href="${ctx}/${setting('site.favicon', 'assets/img/favicon.ico')}" rel="shortcut icon" />

    <link href="${ctx}/assets/libs/gallery2/bootstrap/3.1.1/css/bootstrap.css" rel="stylesheet" />
    <link rel="stylesheet" media="screen" href="${ctx}/assets/css/common.css" />
    <link rel="stylesheet" media="screen" href="${ctx}/assets/css/bootstrap-extends.css" />
    <link rel="stylesheet" media="screen" href="${ctx}/themes/default/css/class-default.css" />
    <link rel="stylesheet" media="screen" href="${ctx}/bundles/topweb/css/web.css" />
    <link rel="stylesheet" media="screen" href="${ctx}/bundles/topweb/css/member.css" />
    <link rel="stylesheet" media="screen" href="${ctx}/assets/css/font-awesome.min.css" />
    <!--[if lt IE 8]>
    <link href="${ctx}/assets/css/oldie.css" rel="stylesheet">
    <![endif]-->

    <!--[if lt IE 9]>
    <script src="${ctx}/assets/libs/html5shiv.js"></script>
    <![endif]-->

    <!--[if IE 8]>
    <script src="${ctx}/assets/libs/respond.min.js"></script>
    <![endif]-->

    <style>

        .error-title {
            font-family: "Phosphate";
            font-size: 200px;
            text-transform: uppercase;
            color: #9a9a9a;
            margin-right: 30px;
        }

        .error-panel {
            margin-top: 10%;
        }
        .error-panel .panel-heading {
            text-align: center;
            font-size: 22px;
            background: #FFF none repeat scroll 0% 0%;
        }

    </style>

</head>
<body >
<div class="container">
    <div class="panel panel-default error-panel">
        <div class="panel-heading">
            <span class="error-title">404</span>该内容不存在或已被删除！
        </div>
        <div class="panel-body">
            <div class="well">
                <p>该内容似乎已经被删除，或者已被系统遗忘。</p>
                <p>请确认您的来源链接是否正确？</p>
                <p><a href="javascript:history.go(-1)"><< 后退</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    |&nbsp;&nbsp;&nbsp;&nbsp;<a href="${ctx}/">返回首页</a></p>
                <span class="error-404-icon hidden-xs"></span>
            </div>
        </div>
    </div>
</div>
</body>
</html>