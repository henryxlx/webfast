<#if category.seoKeyword??><#assign metaKeywords = category.seoKeyword /></#if>
<#if category.seoDesc??><#assign metaDescription = category.seoDesc /></#if>

<#include '/article/layout.ftl'>
<#macro blockTitle>${(category.seoTitle)!category.name!} - ${blockTitleParent!}</#macro>

<#macro blockArticleMain>

  <#include '/article/nav.ftl'/>
  <#if articles??>
    <ul class="article-wide-list">
      <#list articles as article>
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
            <div class="published-time">${article.publishedTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</div>
          </div>
          <#if article.thumb??>
            <a class="hidden-xs pull-right article-picture-link" href="${ctx}/article/${article.id}">
              <img class="article-picture" src="${file_url(article.thumb)}" alt="资讯缩略图">
            </a>
          </#if>
          <div class="media-body">
            <h4 class="media-heading"><a href="${ctx}/article/${article.id}" >${article.title}</a></h4>
            <#assign articlBodyLen = article.body?length />
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

	<ul class="nav nav-pills nav-stacked article-sub">
		<#list subCategories as cat>
      <#if cat.published == 1>
  		  <li <#if category.id == cat.id >class="active"</#if>>
  		    <a href="${ctx}/article/category/${cat.code}">
  		    <#if cat.depth gt 1>
  		      <#list 0..(cat.depth-1) as i>&nbsp;&nbsp;</#list>
  		    </#if>
  		    ${cat.name}</a>
  		  </li>
      </#if>
        </#list>
	</ul>

  <@renderController path='/article/popularArticlesBlock' />
  <@renderController path='/article/recommendArticlesBlock' />

</#macro>
