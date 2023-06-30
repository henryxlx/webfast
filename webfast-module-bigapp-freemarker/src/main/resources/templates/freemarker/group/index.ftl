<#assign metaKeywords = '小组'/>
<#assign metaDescription = '小组首页'/>

<@block_title '小组'/>

<#include '/layout.ftl'>

<#macro blockContent>
    <div class="row">
        <div class="col-md-8">
            <div class="panel panel-default">
                <div class="panel-heading">
                    热门小组<a href="${ctx}/group/search-group" class="pull-right">&raquo;更多</a>
                </div>
      <div class="panel-body">
        <div class="row group-grids">
          <#list activeGroup! as group>

            <div class="col-lg-2 col-md-2 col-sm-2 col-xs-4 grid">
              <p>
                <a href="${ctx}/group_show',{id:group.id})}}" title="{{group.title}}">
                  <img src="{{file_path(group.logo, 'group.png')}}" alt="{{group.title}}" class="logo">
                </a>
              </p>
              <p class="title"><a  href="${ctx}/group_show',{id:group.id})}}" title="{{group.title}}" >{{group.title}}</a></p>
            </div>

          <#else>
            <div class="empty">暂无小组信息！</div>
          </#list>
        </div>
      </div>
    </div>

    <div class="panel panel-default">
      <div class="panel-heading">最近话题</div>
      <div class="panel-body">
          <#assign threads = recentlyThread! />
          <#assign groups = groupinfo! />
          <#assign users = owners! />
          <#include '/group/groups-threads-ul.ftl' />
      </div>
      
    </div>
    
  </div>

  <div class="col-md-4">
    <#if (user.id)??>
    <div class="es-row ">
        <div class="panel panel-default" >
            <div class="panel-body">
                <div class="media">
                 {{ web_macro.user_avatar(user, ' pull-left media-object media-object-small') }}
                    <div class="media-body">
                        <p >{{ web_macro.user_link(user , 'link-muted') }}</p>
                        <a href="{{ path ('group_member_center') }}" class="btn btn-primary btn-xs btn-default active" role="button">我的小组主页</a>
                        {% if is_granted('ROLE_ADMIN') %}
                        <a href="{{ path ('group_add') }}" class="btn btn-primary btn-xs btn-default active" role="button">创建小组</a>
                        {% endif %}
                    </div>
                </div>
            </div>
        </div>
    </div>
    </#if>
      <div class="mbl">
          <form action="${ctx}/group/search-group" method="get">
                <div class="input-group">
                  <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
                  <input type="text" class="form-control" placeholder="小组搜索" name="keyWord">
                  <span class="input-group-btn">
                  <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                  </span>
                </div>
          </form>
         </div>    
    <#if myJoinGroup??>
      <div class="panel panel-default">
        <div class="panel-heading">
          我的小组
          {% if user.id %} <a href="{{ path ('group_member_join',{type:'myGroup'}) }}" class="pull-right">&raquo;更多</a>{% endif %}
        </div>
        <div class="panel-body">
          <div class="row group-grids">
            {% for group in myJoinGroup %}
              <div class="col-lg-3 col-md-4 col-sm-2 col-xs-4 grid">
                <p>
                  <a href="${ctx}/group_show',{id:group.id})}}" title="{{group.title}}">
                    <img src="{{file_path(group.logo, 'group.png')}}" alt="{{group.title}}" class="logo">
                  </a> 
                </p>
                <p class="title">
                  <a href="${ctx}/group_show',{id:group.id})}}" title="{{group.title}}">{{group.title|sub_text(9)}}</a>
                </p>
              </div>
            {% endfor %}
          </div>
        </div>
      </div>
    </#if>

    <#include '/group/new-group-list.ftl' />

  </div>
</div>
</#macro>
