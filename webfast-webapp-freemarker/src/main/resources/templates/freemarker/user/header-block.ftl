<div class="es-row-wrap  container-gap userpage-header">
    <div class="row">
        <div class="col-md-12">
            <img class="avatar" src="${default_path('avatar',(user.largeAvatar)!, 'large')}">
            <div class="userpage-header-info">
                <div class="actions">
                    <#if !appUser?? || (appUser.id)!0 != (user.id)!0>
                    <button class="btn btn-success unfollow-btn" data-url="${ctx}/user/unfollow?id=${user.id}" <#if isFollowed == false> style="display:none;"</#if>>已关注</button>
                    <button class="btn btn-primary follow-btn" data-url="${ctx}/user/follow?id=${user.id}" <#if isFollowed == true> style="display:none;"</#if>>关注</button>
                    <button class="btn btn-default" data-toggle="modal" data-target="#modal" data-url="${ctx}/message/create?toId=${user.id}">私信</button>
                    </#if>
                </div>
                <h1>${(user.nickname)!} <small>${(user.title)!}</small></h1>
                <div class="about">${(user.about)!}</div>
                <div class="links">
                    <#if user??>
                    <#if (user.weibo)??>
                    <a href="${user.weibo}" target="_blank" class="mrm"><img src="${ctx}/assets/img/user/link-weibo.png"> 微博</a>
                    </#if>
                    <#if (user.site)??>
                    <a href="${user.site}" target="_blank" class="mrm"><img src="${ctx}/assets/img/user/link-site.png"> 网站</a>
                    </#if>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>