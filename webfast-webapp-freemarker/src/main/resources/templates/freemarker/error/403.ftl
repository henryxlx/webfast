<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>出错啦 - WebFast - Powered By EduNext</title>

    <style>

        body {
            background-color: #f9f9f9;
            font-size: 14px;
        }

        h1 {
            font-size: 20px;
            font-weight: bold;
            margin: 0 0 20px;
            padding: 0;
            color: #444;
        }

        a {
            color: #428bca;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .container {
            width: 800px;
            margin: 60px auto;
            background: #fff;
            border: 1px solid #ddd;
        }

        .content {
            padding: 40px;
        }

        .message {
            margin-bottom: 20px;
            color: #666;
            line-height: 24px;
        }

        .actions a {
            margin-right: 20px;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="panel panel-default error-panel">
        <div class="panel-heading">
            <span class="error-title">403</span>访问被拒绝
        </div>
        <div class="panel-body">
            <div class="well">
                <p>你没有权限访问该页面！</p>
                <p>请确认您的来源链接是否正确？是否拥有该权限？</p>
                <p><a href="${ctx}/index?ref=403">返回首页 ></a></p>
                <span class="error-403-icon hidden-xs"></span>
            </div>
        </div>
    </div>
</div>
</body>
</html>