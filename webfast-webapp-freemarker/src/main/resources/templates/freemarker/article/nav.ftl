<#assign currentNavCategory = categoryCode!'N/A' />
<#assign rootCategory = (rootCategory.code)!'N/A' />
<ul class="nav nav-pills article-nav">
  <li <#if currentNavCategory != 'N/A'> class="active" </#if>><a href="${ctx}/article">${(articleSetting.name)!'资讯'}</a></li>
  <#list categoryTree! as cat>
    <#if cat.parentId == 0 && cat.published == 1>
       <li class="<#if rootCategory == cat.code>active</#if>"><a href="${ctx}/article/category/${cat.code}">${cat.name}</a></li>
    </#if>
  </#list>
</ul>