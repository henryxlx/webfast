<#assign bodyClass = 'articlepage'/>
<#assign script_controller = 'index'/>
<#include '/layout.ftl'>

<#macro blockStylesheetsExtra>
<link rel="stylesheet" media="screen" href="${ctx}/bundles/topxiaweb/css/article.css" />
</#macro>

<#macro blockTitle>${setting.name} - ${blockTitleParent}</#macro>

<#macro blockContent>

  <div class="es-row-wrap container-gap">

    <#if blockArticleHeader??><@blockArticleHeader/><#else>
      <div class="row">
        <div class="col-md-12">
          <div class="page-header"><h1>${(setting.name)!}</h1></div>
        </div>
      </div>
    </#if>

    <div class="row article-row">
      <div class="col-md-8">
        <#if blockArticleMain??><@blockArticleMain/></#if>
      </div>
      <div class="col-md-4">
        <#if blockArticleSide??><@blockArticleSide/></#if>
      </div>
    </div>
  </div>

</#macro>