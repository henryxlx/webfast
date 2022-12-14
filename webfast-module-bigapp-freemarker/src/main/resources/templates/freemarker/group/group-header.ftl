  <div class="row group-header">
    <div class="col-md-12"  >
      <div class="group-header-inner" style="background:url({{file_path(groupinfo.backgroundLogo, 'background_group.jpg')}});background-repeat: no-repeat;background-size: 100% 100%;">
        <div class="media">
          <a class="pull-left" href="#">
            <img src="{{file_path(groupinfo.logo, 'group.png')}}" alt="" class="media-object" >
          </a>
          <div class="media-body">
            <h1 class="media-heading">{{groupinfo.title}}</h1>
            <div class="media-metas">
              {{groupinfo.memberNum}}个成员
            </div>
          </div>
        </div>

        <ul class="nav nav-pills">
          <li {% if groupNav=="index" %}class="active"{% endif %}><a href="{{path('group_show',{id:groupinfo.id})}}">小组首页</a></li>
          <li {% if groupNav=="member" %}class="active"{% endif %}><a href="{{path('group_member',{id:groupinfo.id})}}">小组成员</a></li>
          {% if is_groupmember==2 or is_granted('ROLE_ADMIN') or is_groupmember==3%}
            <li {% if groupNav=="setting" %}class="active" {% endif %}><a  href="{{path('group_set',{id:groupinfo.id})}}">小组设置</a></li>
          {% endif %}
        </ul>

      </div>
    </div>
  </div>