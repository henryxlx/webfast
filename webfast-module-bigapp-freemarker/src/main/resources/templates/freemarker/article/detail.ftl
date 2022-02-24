<#assign metaKeywords = seoKeyword! />

<#assign metaDescription = seoDesc[0..*100] />

<#include '/article/layout.ftl'/>

<#macro blockTitle>${article.title} - ${blockTitleParent!}</#macro>

<#macro blockArticleMain>

  <ul class="breadcrumb">
      <li><a href="${ctx}/article/show">${articleSetting.name}</a></li>
      <#list breadcrumbs! as breadcrumb>
        <li><a href="${ctx}/article/category/${breadcrumb.code}">${breadcrumb.name}</a></li>
      </#list>
      <li class="active">正文</li>
  </ul>

  <h2 class="thread-title">${article.title}</h2>
     <div class="published-time pull-left">${article.publishedTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</div>
     <div class="published-time pull-right">${article.hits!0} 次浏览</div>
     <p><br></p>
  <div class="thread-body">${article.body}</div>

  <p class="clearfix">

    <div class="dropdown pull-right">
      <a href="javascript:;" class="btn btn-link" data-toggle="dropdown"><span class="glyphicon glyphicon-share"></span> 分享到</a>
    <#include '/common/share-dropdown.ftl'/>
    </div>

  <#if tags??>
     <span class="text-muted">标签:</span>
      <#list tags as tag>
         <span class="label label-info">${tag.name}</span>
      </#list>
  </#if>
  <br>
  <#if article.source??>
     <span class="text-muted">原文出处: &nbsp;</span>
     <#if article.sourceUrl??>
       <a href="${article.sourceUrl}" target="_blank">${article.source}</a>
     <#else>
       ${article.source}
     </#if>
  </#if>
  </p>
  <ul class="pager" style="margin-top:30px;">
    <#if articlePrevious??>
      <li class="previous"><a href="{{ path('article_detail',{id:articlePrevious.id}) }}">&larr; 上一篇</a></li>
    <#else>
      <li class="previous disabled"><a href="javascript:;">&larr; 上一篇</a></li>
    </#if>

    <#if articleNext??>
      <li class="next"><a href="{{ path('article_detail',{id:articleNext.id}) }}"> 下一篇 &rarr;</a></li>
    <#else>
      <li class="next disabled"><a href="javascript:;"> 下一篇 &rarr;</a></li>
    </#if>
  </ul>
</#macro>

<#macro blockArticleSide>
  <@renderController path='/article/popularArticlesBlock' />
  <@renderController path='/article/recommendArticlesBlock' />
</#macro>