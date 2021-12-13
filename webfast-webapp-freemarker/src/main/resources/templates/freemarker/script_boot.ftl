<script>
    var app = {};
    app.debug = <#if appDebug?? && appDebug>true<#else>false</#if>;
    app.version = '5.3.2';
    app.httpHost = '${webExtPack.getSchemeAndHttpHost()}';
    app.basePath = '${webExtPack.getBasePath()}';
    app.theme = '${setting("theme.uri", "default")}';
    app.themeGlobalScript = '';
    app.jsPaths = {"common":"common","theme":"${ctx}/themes/default/js",
        "customwebbundle":"${ctx}/bundles/customweb/js",
        "customadminbundle":"${ctx}/bundles/customadmin/js"};

    app.crontab = '${ctx}/common/crontab';
    <#assign crontabNextExecutedTime = setting('crontab_next_executed_time')!0/>
    <#if crontabNextExecutedTime gt 0>
    <#if crontabNextExecutedTime?date lt .now?date>
    app.scheduleCrontab = '${ctx}/common/crontab';
    </#if>
    </#if>

    var CKEDITOR_BASEPATH = app.basePath + '/assets/libs/ckeditor/4.6.7/';

    app.config = {"api":{"weibo":{"key":""},"qq":{"key":""},"douban":{"key":""},"renren":{"key":""}},
        "cloud":{"video_player":"http://cdn.staticfile.org/GrindPlayerCN/1.0.2/GrindPlayer.swf",
            "video_player_watermark_plugin":"http://cdn.staticfile.org/GrindPlayerCN/1.0.2/Watermake-1.0.3.swf",
            "video_player_fingerprint_plugin":"http://cdn.staticfile.org/GrindPlayerCN/1.0.2/Fingerprint-1.0.1.swf"},

        "loading_img_path":"${ctx}/assets/img/default/loading.gif"};

    app.arguments = {};
    <#if script_controller??>
    app.controller = '${script_controller}';
    </#if>
    <#if script_arguments??>
    app.arguments = ${script_arguments};  // {{ script_arguments|json_encode|raw }}
    </#if>

    app.scripts = null; // {{ export_scripts()|default(null)|json_encode|raw }};

    app.mainScript = '${script_main!}';
</script>
<#assign cdnUrl = ctx>
<#if setting('cdn.enabled')??>
    <#assign cdnUrl = setting('cdn.url')/>
</#if>
<script src="${cdnUrl}/assets/libs/seajs/seajs/2.2.1/sea.js"></script>
<script src="${cdnUrl}/assets/libs/seajs/seajs-style/1.0.2/seajs-style.js"></script>
<script src="${cdnUrl}/assets/libs/seajs-global-config.js"></script>
<script>
    seajs.use(app.mainScript);
</script>