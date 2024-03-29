<@block_title '搜索'/>

<#include '/layout.ftl'>

<#macro blockContent>

  <div class="panel panel-default panel-lg">
    <div class="panel-heading">

      <div class="row">
        <div class="col-sm-8"><h1>小组搜索</h1></div>
        <div class="col-sm-4">
          <form class="form-inline pull-right" action="${ctx}/group/search-group" method="get">
            <div class="form-group">
              <div class="input-group">
                <input type="text" class="form-control" placeholder="小组搜索" name="keyWord" value="${RequestParameters['keyWord']!}">
                <span class="input-group-btn">
                  <button class="btn btn-default" type="submit">
                    <span class="glyphicon glyphicon-search"></span>
                  </button>
                </span>

              </div>
            </div>
          </form>
        </div>
      </div>

    </div>

    <div class="panel-body">
      <#if groups??>
        <div class="row">
          {% for group in groups %}
          <div class="col-md-3">

            {% include 'TopxiaWebBundle:Group:group-media-small.html.twig'  %}

          </div>
          {% endfor %}
        </div>

        {{ web_macro.paginator(paginator) }}
      <#else>
        <div class="empty">暂无小组信息</div>
      </#if>

    </div>
  </div>

</#macro>