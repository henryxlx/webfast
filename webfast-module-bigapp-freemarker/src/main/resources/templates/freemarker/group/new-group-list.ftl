<#if newGroups??>
  <div class="panel panel-default">
    <div class="panel-heading">新晋小组</div>
    <div class="panel-body">
      <ul class="media-list">
        <#list newGroups as group>
          <li class="media">
            <a href="{{path('group_show',{id:group.id})}}" class="pull-left">
              <img src="{{file_path(group.logo, 'group.png')}}" class="media-object group-logo-sm">
            </a>
            <div class="media-body">
              <p>
                <a href="{{path('group_show',{id:group.id})}}" title="{{group.title}}">{{group.title}}</a>
                <span class="text-success fsn">{{group.memberNum}}个成员</span>
              </p>
              <p class="fsn text-muted">{{ group.about|plain_text(30) }}</p>
              
            </div>
            
          </li>
        </#list>
      </ul>
    </div>
  </div>
</#if>