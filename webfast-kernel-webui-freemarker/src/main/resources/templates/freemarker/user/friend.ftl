<#assign pageNav = 'friend'/>

<#include '/user/layout.ftl'/>

<#macro blockMain>

<div class="row">
	<div class="col-md-12">
		<ul class="nav nav-pills nav-mini userpage-sec-nav">
			<li <#if friendNav == 'following'>class="active"</#if>><a href="${ctx}/user/${user.id}/following">关注</a></li>
			<li <#if friendNav == 'follower'>class="active"</#if>><a href="${ctx}/user/${user.id}/follower">粉丝</a></li>
		</ul>
	</div>
</div>


<div class="row">
	<#list friends! as friend>
	<div class="col-md-4">
		<div class="friendcard clearfix">
			<a href="${ctx}/user/${friend.id}"><img src="${default_path('avatar', friend.mediumAvatar, '')}" class="avatar"></a>
			<div class="infos">
				<a href="${ctx}/user/${friend.id}" class="nickname">${friend.username}</a>
				<div class="title">${friend.title}</div>
			</div>
		</div>
	</div>
	<#else>
	  <#if friendNav == 'following'>
		  <div class="empty">无关注的人</div>
	  <#elseif friendNav == 'follower'>
		  <div class="empty">无粉丝</div>
	  </#if>
	</#list>
</div>


</#macro>