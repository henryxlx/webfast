<#assign submenu = 'base'/>
<#assign script_controller = 'setting/site'/>

<@block_title '基本信息'/>

<#include '/admin/system/site-layout.ftl'/>

<#macro blockMainContent>

    <style>
        #site-logo-container, #site-favicon-container img {
            max-width: 80%;
            margin-bottom: 10px;
        }
    </style>

    <@web_macro.flash_messages/>

<div class="page-header"><h1>网站基本信息</h1></div>

<form class="form-horizontal" id="site-form" method="post">

    <fieldset>
        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="name">网站名称</label>
            </div>
            <div class="col-md-8 controls">
                <input type="text" id="name" name="name" class="form-control" value="${(site.name)!}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="slogan">网站副标题</label>
            </div>
            <div class="col-md-8 controls">
                <input type="text" id="slogan" name="slogan" class="form-control" value="${(site.slogan)!}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="url">网站域名</label>
            </div>
            <div class="col-md-8 controls">
                <input type="text" id="url" name="url" class="form-control" value="${(site.url)!}">
                <p class="help-block">以"http://"或"https://"开头</p>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="logo">网站LOGO</label>
            </div>
            <div class="col-md-8 controls">
                <div id="site-logo-container"><#if (site.logo)?? && site.logo != ''><img
                        src="${ctx}/assets/${site.logo}"></#if></div>
                <button class="btn btn-default btn-sm" id="site-logo-upload" type="button"
                        data-url="${ctx}/admin/setting/logo/upload">上传
                </button>
                <button class="btn btn-default btn-sm" id="site-logo-remove" type="button"
                        data-url="${ctx}/admin/setting/logo/remove"
                        <#if !(site.logo)?? || site.logo == ''>style="display:none;"</#if>>删除
                </button>
                <p class="help-block">请上传png, gif, jpg格式的图片文件。LOGO图片建议不要超过50*250。</p>
                <input type="hidden" name="logo" value="${(site.logo)!}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="favicon">浏览器图标</label>
            </div>
            <div class="col-md-8 controls">
                <div id="site-favicon-container"><img
                            src="${ctx}/<#if (site.favicon)?? && site.favicon != ''>${site.favicon}<#else>assets/img/favicon.ico</#if>">
                </div>
                <button class="btn btn-default" id="site-favicon-upload" type="button"
                        data-url="${ctx}/admin/setting/favicon/upload">上传
                </button>
                <button class="btn btn-default" id="site-favicon-remove" type="button"
                        data-url="${ctx}/admin/setting/favicon/remove"
                        <#if !(site.favicon)?? || site.favicon == ''>style="display:none;"</#if>>删除
                </button>
                <p class="help-block">请上传ico格式的图标文件。</p>
                <input type="hidden" name="favicon" value="${(site.favicon)!}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="seo_keywords">SEO关键词</label>
            </div>
            <div class="col-md-8 controls">
                <input type="text" id="seo_keywords" name="seo_keywords" class="form-control" value="${(site.seo_keywords)!}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="seo_description">SEO描述信息</label>
            </div>
            <div class="col-md-8 controls">
                <input type="text" id="seo_description" name="seo_description" class="form-control" value="${(site.seo_description)!}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="master_email">管理员邮箱地址</label>
            </div>
            <div class="col-md-8 controls">
                <input type="text" id="master_email" name="master_email" class="form-control" value="${(site.master_email)!}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="copyright">课程内容版权方</label>
            </div>
            <div class="col-md-8 controls">
                <input type="text" id="copyright" name="copyright" class="form-control" value="${(site.copyright)!}">
                <div class="help-block">您可以填写网站名称或公司名称。</div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="icp">ICP备案号</label>
            </div>
            <div class="col-md-8 controls">
                <input type="text" id="icp" name="icp" class="form-control" value="${(site.icp)!}">
            </div>
        </div>

    </fieldset>

    <br>

    <fieldset>
        <legend>网站统计分析代码部署</legend>
        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="analytics">统计分析代码</label>
            </div>
            <div class="col-md-8 controls">

                <textarea id="analytics" name="analytics" class="form-control" rows="4">${(site.analytics)!}</textarea>

                <p class="help-block">
                    建议使用下列统计分析的一种：
                    <a href="http://www.google.cn/intl/zh-CN_ALL/analytics/" target="_blank">谷歌分析</a>、
                    <a href="http://tongji.baidu.com/" target="_blank">百度统计</a>、
                    <a href="http://ta.qq.com/" target="_blank">腾讯分析</a>、
                    <a href="http://www.cnzz.com/" target="_blank">CNZZ</a>。
                </p>
            </div>
        </div>
    </fieldset>

    <fieldset style="display:none;">
        <legend>站点状态</legend>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label >网站状态</label>
            </div>
            <div class="col-md-8 controls radios">
                {{ radios('status', {open:'开放', closed:'关闭'}, site.status) }}
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label>网站关闭公告</label>
            </div>
            <div class="col-md-8 controls">
                <textarea id="closed_note" name="closed_note" class="form-control" rows="4">${(site.closed_note)!}</textarea>
                <p class="help-block">网站处于关闭状态时，用户访问网站将显示此公告，支持HTML代码。</p>
            </div>
        </div>
    </fieldset>

    <div class="row form-group">
        <div class="controls col-md-offset-2 col-md-8">
            <button type="submit" class="btn btn-primary">提交</button>
        </div>
    </div>

    <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">

</form>

</#macro>