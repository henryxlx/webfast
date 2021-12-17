<#include '/layout.ftl'>
<#macro blockStylesheetsExtra>
  <link rel="stylesheet" media="screen" href="${ctx}/bundles/topxiaweb/css/group.css" />
</#macro>

<#macro blockContent>
   <#include '/group/group-header.ftl' />

  {% block group_body %}
    <div class="row">
      <div class="col-md-8 group-main">
        {% block group_main %}{% endblock %}
      </div>

      <div class="col-md-4 group-side">
        {% block group_side %}{% endblock %}
      </div>
    </div>
  {% endblock %}
</#macro>