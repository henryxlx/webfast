<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>离线啦 - WebFast - Powered By Jetwinner</title>

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
        <h1>哇哦，暂时不能访问啦！</h1>
        <div class="message">应用现在是离线状态。
            <br/><br/>应用程序正在进行维护，暂时不能被访问。如果疑问请联系管理员。我们会在工作完成后立即恢复网站访问。真的非常抱歉！</div>
        <div class="actions">
            <a href="javascript:history.go(-1)"><< 后退</a>|&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="${(request.contextPath)!''}/">返回首页</a>
        </div>
    </div>
</div>
</body>
</html>