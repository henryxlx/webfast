<ul class="media-list thread-list">
  <#list threads! as thread>
    {% set group = groups[thread.groupId]|default(null) %}
    {% if users|default(null) %}
    {% set user = users[thread.userId]|default(null) %}
    {% endif %}
    <li class="media">
      <a class="pull-left" href="{{ path('user_show', {id:user.id}) }}">
        <img src="{{ default_path('avatar',user.smallAvatar, '') }}" class="media-object">
      </a>
      <div class="media-body">
        {% if thread.postNum %}<span class="badge pull-right">{{thread.postNum}}</span>{% endif %}
        <div class="media-heading">
          {% if thread.isStick and not group %}
            <span class="label label-danger">置顶</span>
          {% endif %}
          <a class="title" href="{{ path('group_thread_show', {id:thread.groupId, threadId:thread.id}) }}">{{thread.title|sub_text(90)}}</a>
          {% if thread.isElite %}
            <span class="label label-warning">精</span>
          {% endif %}

          {% if is_feature_enabled('group_reward') %}
            {% if thread.type == 'reward' and thread.rewardCoin > 0 %}
              <span class="label label-danger">奖</span>
            {% endif %}
          {% endif %}
        </div>
        
        <div class="metas">
          {% if group %}
            <a class="link-muted" href="{{path('group_show',{id:thread.groupId})}}" title="{{ group.title }}" >{{ group.title }}</a>
            <span class="divider">•</span>
          {% endif %}
          {{ web_macro.user_link(user , 'link-muted') }}
          <span class="divider">•</span>
          {{thread.createdTime|smart_time}}
          {% if thread.lastPostTime %}
            <span class="divider">•</span>
            最后回复 {{ web_macro.user_link(lastPostMembers[thread.lastPostMemberId] , 'link-muted') }} {{ thread.lastPostTime|smart_time}}
          {% endif %}
        </div> 
      </div>
    </li>
  <#else>
    <li class="empty">暂无话题！</li>
  </#list>
</ul>