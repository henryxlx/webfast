<#assign currentNavCategory = categoryCode!'N/A' />
<#assign rootCategory = (rootCategory.code)!'N/A' />
<ul class="nav nav-pills article-nav">
  <li <#if currentNavCategory != 'N/A'> class="active" </#if>><a href="${ctx}/article">${(articleSetting.name)!'资讯'}</a></li>
  <#list categoryTree! as cat>
    {% if cat.parentId == 0 and cat.published == 1 %}
       <li class="{% if rootCategory == cat.code %}active{% endif %}"><a href="{{ path('article_category', {categoryCode:cat.code}) }}">{{ cat.name }}</a></li>
    {% endif %}
  </#list>
</ul>