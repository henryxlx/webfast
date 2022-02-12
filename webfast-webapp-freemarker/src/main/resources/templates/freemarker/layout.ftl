<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class=""> <!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <#assign blockTitleParent>${setting('site.name', 'WebFast')}<#if setting('site.slogan')??> - ${setting('site.slogan')}</#if><#if setting('copyright.owned')??><#else> - Powered By WebFast</#if></#assign>
        <#if blockTitle??><@blockTitle/><#else>${blockTitleParent}</#if>
    </title>
    <meta name="keywords" content="${metaKeywords!}" />
    <meta name="description" content="${metaDescription!}" />
    <meta content="${csrf_token('site')}" name="csrf-token" />
    ${setting('login_bind.verify_code', '')}
    <link href="${ctx}/${setting('site.favicon', 'assets/img/favicon.ico')}" rel="shortcut icon" />

    <#if blockStylesheets??>
        <@blockStylesheets/>
    <#else>
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
        <#if blockStylesheetsExtra??><@blockStylesheetsExtra/></#if>
    </#if>
    <!--[if lt IE 9]>
    <script src="${ctx}/assets/libs/html5shiv.js"></script>
    <![endif]-->

    <!--[if IE 8]>
    <script src="${ctx}/assets/libs/respond.min.js"></script>
    <![endif]-->

    <#if blockHeadScripts??><@blockHeadScripts/></#if>

</head>
<body <#if bodyClass??>class="${bodyClass}"</#if>>

<#if blockBody??>
    <@blockBody/>
<#else>

    <div class="navbar navbar-inverse site-navbar" id="site-navbar"  data-counter-url="${ctx}/user/remind/counter">
        <div class="container">
            <div class="container-gap">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <#assign siteLogo = setting('site.logo')!'' />
                    <#if siteLogo != ''>
                        <a class="navbar-brand-logo" href="${ctx}/"><img src="${ctx}/siteLogo"></a>
                    <#else>
                        <a class="navbar-brand" href="${ctx}/index">${setting('site.name', 'WebFast')}</a>
                    </#if>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                    </ul>
                    <@renderController path='/default/topNavigation'/>

                    <ul class="nav navbar-nav navbar-right">
                        <#if appUser??>
                            <li><a href="${ctx}/my"> 我的 </a></li>
                            <#if setting('mobile.enabled')??>
                                <li><a href="${ctx}/mobile" class="mobile-badge-container">
                                        <span class="glyphicon glyphicon-phone"></span>
                                        <span class="badge">v2</span>
                                    </a></li>
                            </#if>
                            <li><a href="${ctx}/search"><span class="glyphicon glyphicon-search"></span></a></li>
                            <li><a href="${ctx}/notification" class="badge-container notification-badge-container">
                                    <span class="glyphicon glyphicon-bullhorn hidden-lt-ie8"></span>
                                    <span class="visible-lt-ie8">通知</span>
                                    <#if (appUser.newNotificationNum)?? && appUser.newNotificationNum gt 0><span class="badge">${appUser.newNotificationNum}</span></#if></a></li>
                            <li>
                                <a href="${ctx}/message" class="badge-container message-badge-container">
                                    <span class="glyphicon glyphicon-envelope hidden-lt-ie8"></span>
                                    <span class="visible-lt-ie8">私信</span>
                                    <#if (appUser.newMessageNum)?? && appUser.newMessageNum gt 0><span class="badge">${appUser.newMessageNum}</span></#if>
                                </a>
                            </li>
                            <li><a href="<#if setting('coin.coin_enabled')??>${ctx}/my/coin<#else>${ctx}/my/bill</#if>" class="qiandai">
                                    <img src="${default_path('qiandai.png')}"/>
                                </a>
                            </li>
                            <li class="visible-lt-ie8"><a href="${ctx}/settings">${(appUser.username)!}</a></li>
                            <li class="dropdown hidden-lt-ie8">
                                <a href="javascript:;" class="dropdown-toggle"  data-toggle="dropdown">${(appUser.username)!'佚名'} <b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="${ctx}/user/${(appUser.id)!}"><i class="glyphicon glyphicon-home"></i> 我的主页</a></li>
                                    <li><a href="${ctx}/settings"><i class="glyphicon glyphicon-cog"></i> 个人中心</a></li>
                                    <li class="divider"></li>
                                    <#if userAcl?? && userAcl.isGranted('ACCESS_BACKEND')>
                                        <li><a href="${ctx}/admin"><i class="glyphicon glyphicon-dashboard"></i> 管理后台</a></li>
                                        <li class="divider"></li>
                                    </#if>
                                    <li><a href="${ctx}/logout"><i class="glyphicon glyphicon-off"></i> 退出</a></li>
                                </ul>
                            </li>
                        <#else> <#-- appUser is not existed -->
                            <#if setting('mobile.enabled')?? >
                                <li><a href="${ctx}/mobile"><span class="glyphicon glyphicon-phone"></span> 手机版</a></li>
                            </#if>
                            <li><a href="${ctx}/search"><span class="glyphicon glyphicon-search"></span> 搜索</a></li>
                            <li><a href="${ctx}/login?_target_path=${(request.requestUri)!''}">登录</a></li>
                            <li><a href="${ctx}/register?_target_path=${(request.requestUri)!''}">注册</a></li>
                        </#if> <#-- <#if appUser??> -->

                    </ul>
                </div><!--/.navbar-collapse -->
            </div>
        </div>
    </div>

    <div id="content-container" class="container">
        <#if (appUser.setup)?? && appUser.setup == 0 && (!hideSetupHint?? || hideSetupHint != true)>
            <div class="alert alert-warning">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                为了帐号的安全，以及更好的使用体验，请设置您的Email地址。
                <a href="${ctx}/settings/setup">现在就去设置</a>
            </div>
        </#if>
        <#if blockContent??><@blockContent/></#if>

    </div><!-- /container -->

    <div class="site-footer container clearfix">

        <@renderController path='/default/footNavigation' />

        <div class="text-gray" data-role="default-foot-bar">
            <#include '/powered-by.ftl'>
            ${setting('site.analytics', '')}
            <div class="pull-right"><#if setting('site.copyright')??>课程内容版权均归<a href="/">${setting('site.copyright')}</a>所有</#if>&nbsp;<#if setting('site.icp')??><a href="http://www.miibeian.gov.cn/" target="_blank">${setting('site.icp')}</a></#if></div>

            <div class="pull-right mhs"><a href="${ctx}/course/archive">课程存档</a></div>
        </div>
    </div>

    <#if blockBottom??><@blockBottom/></#if>
    <div id="login-modal" class="modal" data-url="/login/ajax"></div>
    <div id="modal" class="modal"></div>
</#if>
<#--End of block body-->

<#assign script_main>${ctx}/bundles/topweb/js/app.js</#assign>
<#include '/script_boot.ftl'>

</body>
</html>