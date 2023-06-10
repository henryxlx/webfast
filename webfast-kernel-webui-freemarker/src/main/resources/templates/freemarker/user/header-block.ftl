<div class="es-row-wrap  container-gap userpage-header">
    <div class="row">
        <div class="col-md-12">
            <img class="avatar" src="${default_path('avatar',(user.largeAvatar)!, 'large')}">
            <div class="userpage-header-info">
                <div class="actions">
                    <#assign targetUserId = (user.id)!0/>
                    <#assign appUserId = (appUser.id)!0/>
                    <#if appUserId != targetUserId>
                        <button class="btn btn-success unfollow-btn"
                                data-url="${ctx}/user/${targetUserId}/unfollow" <#if !isFollowed!false> style="display:none;"</#if>>
                            已关注
                        </button>
                        <button class="btn btn-primary follow-btn"
                                data-url="${ctx}/user/${targetUserId}/follow" <#if isFollowed!false> style="display:none;"</#if>>
                            关注
                        </button>
                        <button class="btn btn-default" data-toggle="modal" data-target="#modal"
                                data-url="${ctx}/message/create/${targetUserId}">私信
                        </button>
                    </#if>
                </div>
                <h1>${(user.username)!} <small>${(user.title)!}</small></h1>
                <div class="about">${(userProfile.aboutme)!}</div>
                <div class="links">
                    <#if userProfile??>
                        <#if (userProfile.weibo)??>
                            <a href="${userProfile.weibo}" target="_blank" class="mrm"><img
                                        src="${ctx}/assets/img/user/link-weibo.png"> 微博</a>
                        </#if>
                        <#if (userProfile.site)??>
                    <a href="${userProfile.site}" target="_blank" class="mrm"><img src="${ctx}/assets/img/user/link-site.png"> 网站</a>
                    </#if>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>