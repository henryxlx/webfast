<#assign metaKeywords = (content.title)! + setting('site.name') />
<#assign metaDescription = "${(content.body)!''?substring(0, 100)}" />

<#assign siteNav = "page/${(content.alias)!(content.id)!}"/>
<#assign bodyClass = "contentpage" />
<#include '/layout.ftl'>

<#macro blockTitle>${(content.title)!} - ${blockTitleParent}</#macro>

<#macro blockContent>
<div class="es-row-wrap container-gap">
  <div class="row">
    <div class="col-md-12">
      <div class="page-header"><h1>${(content.title)!}</h1></div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <#if (content.body)?? >
      	${content.body}
      <#else>
        <div class="empty">当前页面尚未编辑内容，请在管理后台编辑。</div>
      </#if>
    </div>
  </div>

</div>
</#macro>