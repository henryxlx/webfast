<#assign side_nav = 'profile'/>
<#assign script_controller = 'settings/profile'/>

<#include '/settings/layout.ftl'/>

<#macro blockTitle>基础信息 - ${blockTitleParent}</#macro>

<#macro blockMain>
    <#local class='panel-col'/>
    <#include '/bootstrap/panel.ftl'/>
</#macro>

<#macro blockHeading>基础信息</#macro>
<#macro blockPanelBody>

    <#if fromCourse??>
        <div class="alert alert-info">设置头衔、自我介绍并保存后，即可开始创建课程。</div>
    </#if>

    <form id="user-profile-form" class="form-horizontal" method="post">
        <@web_macro.flash_messages/>

        <div class="form-group">
            <label class="col-md-2 control-label" >昵称</label>
            <div class="col-md-7 controls">
                <div class="control-text">
                    ${appUser.nickname!'佚名'} <#if setting('user_partner.nickname_enabled')??> <a href="${ctx}/setting/nickname">修改</a> </#if></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="profile_truename">姓名</label>
            <div class="col-md-7 controls">
                <input type="text" id="profile_truename" name="profile[truename]" class="form-control" data-widget-cid="widget-1" data-explain="" value="${(profile.truename)!}">
                <div class="help-block" style="display:none;"></div>
            </div>
        </div>

        <div class="help-block approval">
            <#if appUser.approvalStatus??>
            <#if appUser.approvalStatus == 'approving'>
            <p class="text-primary" >
                正在实名认证中，管理员会尽快答复您。
            </p>
            <#elseif  appUser.approvalStatus == 'approved'>
            <p class="text-success" >
                恭喜您，已通过实名认证！
            </p>
            <#elseif  appUser.approvalStatus == 'unapprove'>
            <p class="text-warning" >
                您尚未实名认证，<strong><a href="${ctx}/setting/approval-submit">点此认证</a>。
                </strong>
            </p>
            <#elseif appUser.approvalStatus == 'approve_fail'>
            <p class="text-danger" >
                实名认证审核尚未通过，请参照通知中的信息，修改后重新<strong><a href="${ctx}/setting/approval-submit">认证。</a>
                </strong>
            </p>
            </#if>
            </#if>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label">性别</label>
            <div class="col-md-7 controls radios">
                <div id="profile_gender">
                    <input type="radio" id="profile_gender_0" name="profile[gender]" required="required" value="male" <#if (profile.gender)! == 'male'>checked="checked"</#if>>
                    <label for="profile_gender_0" class="required">男</label>
                    <input type="radio" id="profile_gender_1" name="profile[gender]" required="required" value="female" <#if (profile.gender)! == 'female'>checked="checked"</#if>>
                    <label for="profile_gender_1" class="required">女</label>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label" for="profile_idcard">身份证号</label>
            <div class="col-md-7 controls">
                <input type="text" id="profile_idcard" name="profile[idcard]" class="form-control" value="${(profile.idcard)!''}">
                <div class="help-block" style="display:none;"></div>
            </div>
        </div>

        <#if (user.verifiedMobile)??>
        <div class="form-group">
            <label for="profile_mobile" class="col-md-2 control-label">手机号码</label>
            <div class="col-md-7 controls">
                <div class="control-text">
                    {{ blur_phone_number(user.verifiedMobile|default('')) }}
                    <small class="text-success">(已绑定)</small>
                </div>
            </div>
        </div>
        <#else>
        <div class="form-group">
            <label for="profile_mobile" class="col-md-2 control-label">手机号码</label>
            <div class="col-md-7 controls">
                <input type="text" id="profile_mobile" name="profile[mobile]" class="form-control" data-widget-cid="widget-5" data-explain="" value="${(profile.mobile)!''}">

                <div class="help-block" style="display:none"></div>
            </div>
        </div>
        <#if setting('cloud_sms.sms_enabled')! == '1'>
        <div class="help-block">
            <label class="col-md-2 control-label"></label>
            <p class="text-warning">
                还没绑定手机号，<strong><a href="${ctx}/settings/bind/mobile">前去绑定</a>。
                </strong>
            </p>
        </div>
        </#if>
        </#if>

        <div class="form-group form-forIam-group form-notStudent-group">
            <label class="col-md-2 control-label">公司</label>
            <div class="col-md-7 controls">
                <input type="text" id="profile_company" name="profile[company]" class="form-control" value="${(profile.company)!''}">
                <div class="help-block" style="display:none;"></div>
            </div>
        </div>

        <div class="form-group form-forIam-group form-notStudent-group">
            <label class="col-md-2 control-label">职业</label>
            <div class="col-md-7 controls">
                <input type="text" id="profile_job" name="profile[job]" class="form-control" value="${(profile.job)!''}">
                <div class="help-block" style="display:none;"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label">头衔</label>
            <div class="col-md-7 controls">
                <input type="text" id="profile_title" name="profile[title]" class="form-control" value="${(profile.title)!''}">
                <div class="help-block" style="display:none;"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label">个人签名</label>
            <div class="col-md-7 controls">
                <input type="text" id="profile_signature" name="profile[signature]" class="form-control" value="${(profile.signature)!''}">
                <div class="help-block" style="display:none;"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label">自我介绍</label>
            <div class="col-md-7 controls">
                <textarea name="profile[about]" rows="10" id="profile_about" class="form-control" data-image-upload-url="${ctx}/editor_upload?token=upload_token('user')">${(profile.about)!''}</textarea>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label">个人主页</label>
            <div class="col-md-7 controls">
                <input type="text" id="profile_site" name="profile[site]" class="form-control" data-widget-cid="widget-4" data-explain="" value="${(profile.site)!''}">
                <div class="help-block" style="display:none;"></div>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label">
                <label for="weibo">微博</label>
            </div>
            <div class="col-md-7 controls">
                <input type="text" id="weibo" name="profile[weibo]" class="form-control" data-widget-cid="widget-3" data-explain="" value="${(profile.weibo)!''}">
                <div class="help-block" style="display:none;"></div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label">微信</label>
            <div class="col-md-7 controls">
                <input type="text" id="profile_weixin" name="profile[weixin]" class="form-control" value="${(profile.weixin)!''}">
                <div class="help-block" style="display:none;"></div>
            </div>
        </div>

        <div class="form-group">
            <label for="profile_qq" class="col-md-2 control-label">QQ</label>
            <div class="col-md-7 controls">
                <input type="text" id="profile_qq" name="profile[qq]" class="form-control" data-widget-cid="widget-2" data-explain="" value="${(profile.qq)!''}">
                <div class="help-block" style="display:none;"></div>
            </div>
        </div>



        <#if fields??>
            <hr>
            {% for field in fields %}
            {% if field.type=="text" %}
            <div class="form-group">
                <label for="{{field.fieldName}}" class="col-md-2 control-label">{{field.title}}</label>
                <div class="col-md-7 controls">
                    <textarea id="{{field.fieldName}}" name="profile[{{field.fieldName}}]" class="{{field.type}} form-control">{{ profile[field.fieldName]|default('') }}</textarea>
                    <div class="help-block" style="display:none;"></div>
                </div>
            </div>
            {% elseif field.type=="int" %}
            <div class="form-group">
                <label for="{{field.fieldName}}" class="col-md-2 control-label">{{field.title}}</label>
                <div class="col-md-7 controls">
                    <input type="text" id="{{field.fieldName}}" placeholder="最大值为9位整数" name="profile[{{field.fieldName}}]" class="{{field.type}} form-control"  value="{{ profile[field.fieldName]|default('') }}">
                    <div class="help-block" style="display:none;"></div>
                </div>
            </div>
            {% elseif field.type=="float" %}
            <div class="form-group">
                <label for="{{field.fieldName}}" class="col-md-2 control-label">{{field.title}}</label>
                <div class="col-md-7 controls">
                    <input type="text" id="{{field.fieldName}}" placeholder="保留到2位小数" name="profile[{{field.fieldName}}]" class="{{field.type}} form-control"  value="{{ profile[field.fieldName]|default('') }}">
                    <div class="help-block" style="display:none;"></div>
                </div>
            </div>
            {% elseif field.type=="date" %}
            <div class="form-group">
                <label for="{{field.fieldName}}" class="col-md-2 control-label">{{field.title}}</label>
                <div class="col-md-7 controls">
                    <input type="text" id="{{field.fieldName}}" name="profile[{{field.fieldName}}]" class="{{field.type}} form-control" value="{% if profile[field.fieldName] %}{{profile[field.fieldName]}}{% endif %}">
                    <div class="help-block" style="display:none;"></div>
                </div>
            </div>
            {% elseif field.type=="varchar" %}
            <div class="form-group">
                <label for="{{field.fieldName}}" class="col-md-2 control-label">{{field.title}}</label>
                <div class="col-md-7 controls">
                    <input type="text" id="{{field.fieldName}}" name="profile[{{field.fieldName}}]" class="form-control" value="{{ profile[field.fieldName]|default('') }}">
                    <div class="help-block" style="display:none;"></div>
                </div>
            </div>
            {% endif %}
            {% endfor %}
        </#if>

        <div class="row">
            <div class="col-md-7 col-md-offset-2">
                <button id="profile-save-btn" data-submiting-text="正在保存" type="submit" class="btn btn-primary">保存</button>
            </div>
        </div>

        <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
    </form>
</#macro>