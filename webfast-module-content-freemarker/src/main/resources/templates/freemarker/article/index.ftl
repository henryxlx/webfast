<#include '/article/layout.ftl'>

<#macro blockArticleMain>

    <#include '/article/nav.ftl'/>

<#if featuredArticles??><#else>
    <#if userAcl.hasRole('ROLE_ADMIN')>
<div class="alert alert-warning">头条大图轮播功能需先在后台资讯管理页面给正文上传图片以及添加头条属性<a href="${ctx}/admin/article">前往设置</a></div>
    </#if>
</#if>

<#if featuredArticles??>
<div class="homepage-feature homepage-feature-slides mbl" data-cycle-overlay-template='{% verbatim %}{{title}}{% endverbatim %}'>
    <div class="cycle-overlay"></div>
    {% for featuredArticle in featuredArticles %}
    <a href="{{ path('article_detail', { id:featuredArticle.id }) }}" data-cycle-title="<a href='{{ path('article_detail', { id:featuredArticle.id }) }}'>{{ featuredArticle.title }}</a>" ><img src={{ featuredArticle.picture }} alt="{{ featuredArticle.title }}" style="max-height:400px;"></a>
    {% endfor %}
</div>
</#if>

<#if latestArticles??>
<ul class="article-wide-list">
    {% for article in latestArticles %}
    <li class="media article-item clearfix">
        <div class="article-title text-muted">
            <div class="pull-left">
                {% for category in categories %}
                {% if article.categoryId == category.id %}
                <span><a href="{{ path('article_category', { categoryCode:category.code }) }}">{{ category.name }} </a>
                  </span>
                {% endif %}
                {% endfor %}
            </div>
            <div class="published-time">{{ article.publishedTime|date('Y-m-d H:i') }}</div>
        </div>
        {% if article.thumb %}
        <a class="pull-right article-picture-link hidden-xs" href="{{ path('article_detail', { id:article.id }) }}">
            <img class="article-picture" src="{{ file_url(article.thumb) }}" alt="资讯缩略图">
        </a>
        {% endif %}
        <div class="media-body">
            <h4 class="media-heading"><a href="{{ path('article_detail', { id:article.id }) }}" >{{ article.title }}</a></h4>
            {{ article.body|plain_text(150) }}
        </div>
    </li>
    {% endfor %}
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