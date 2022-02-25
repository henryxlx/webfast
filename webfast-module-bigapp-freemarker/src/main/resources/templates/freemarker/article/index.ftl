<#include '/article/layout.ftl'>

<#macro blockArticleMain>

    <#include '/article/nav.ftl'/>

<#if featuredArticles??><#else>
    <#if userAcl.hasRole('ROLE_ADMIN')>
<div class="alert alert-warning">头条大图轮播功能需先在后台资讯管理页面给正文上传图片以及添加头条属性<a href="${ctx}/admin/article">前往设置</a></div>
    </#if>
</#if>

<#if featuredArticles??>
<div class="homepage-feature homepage-feature-slides mbl" data-cycle-overlay-template='{{title}}'>
    <div class="cycle-overlay"></div>
    <#list featuredArticles as featuredArticle>
    <a href="${ctx}/article/${featuredArticle.id}" data-cycle-title="<a href='${ctx}/article/${featuredArticle.id}'>${featuredArticle.title}</a>" ><img src=${featuredArticle.picture!} alt="${featuredArticle.title!}" style="max-height:400px;"></a>
    </#list>
</div>
</#if>

<#if latestArticles??>
<ul class="article-wide-list">
    <#list latestArticles as article>
    <li class="media article-item clearfix">
        <div class="article-title text-muted">
            <div class="pull-left">
                <#if categories??>
                    <#list categories?values as category>
                        <#if article.categoryId == category.id>
                <span><a href="${ctx}/article/category/${category.code}">${category.name} </a>
                  </span>
                        </#if>
                    </#list>
                </#if>
            </div>
            <div class="published-time">${article.publishedTime?number_to_datetime?string('yyyy-MM-dd HH:mm')}</div>
        </div>
        <#if article.thumb??>
        <a class="pull-right article-picture-link hidden-xs" href="${ctx}/article/${article.id}">
            <img class="article-picture" src="{file_url(article.thumb)}" alt="资讯缩略图">
        </a>
        </#if>
        <div class="media-body">
            <h4 class="media-heading"><a href="${ctx}/article/${article.id}" >${article.title}</a></h4>
            ${article.body[0..*150]}
        </div>
    </li>
    </#list>
</ul>
<#else>
<div class="empty tac text-muted mvl">还没有任何资讯</div>
</#if>
<@web_macro.paginator paginator! />

</#macro>

<#macro blockArticleSide>
    <@renderController path='/article/popularArticlesBlock' />
    <@renderController path='/article/recommendArticlesBlock' />
</#macro>