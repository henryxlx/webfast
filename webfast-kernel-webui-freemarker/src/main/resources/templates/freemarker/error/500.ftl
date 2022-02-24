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
    <div class="content">
        <h1>哇哦，有状况发生啦！</h1>
        <div class="message">服务器返回了 ${message!''}。
            <br/><br/>看起来是像出了点状况，请把该信息报告给管理员。我们会立即修复这个问题。真的非常抱歉！</div>
        <div class="actions">
            <a href="javascript:history.go(-1)"><< 后退</a>|&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="${(request.contextPath)!''}/">返回首页</a>
        </div>
    </div>
</div>
</body>
</html>