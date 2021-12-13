<#include '/install/layout.ftl'/>

<#macro blockMain>

<h4 class="text-success">恭喜，系统已安装成功！</h4>
${reponseData!}
<div class="well key-result-well key-creating">
    正在生成授权码，请稍等...
    <br>
    <small class="text-muted">(如长时间未响应，请刷新页面重试。)</small>
</div>

<div class="well key-result-well key-create-fail hide">
    <p class="text-danger">授权码生成失败！ 原因是：</p>
    <p class="text-danger" id="error-reason"></p>
    <p><a href="" class="btn btn-danger">重试</a></p>
</div>

<div class="well key-result-well key-created hide">
    <p><strong>AccessKey:</strong> <span id="access-key"></span></p>
    <p><strong>SecretKey:</strong> <span id="secret-key"></span></p>
    <p class="text-warning"><strong>请妥善保管授权码，升级系统、购买应用、使用云服务，都需使用此授权码。</strong></p>
</div>
<p>&nbsp;</p>
<div class="actions">
    <p><a href="${ctx}/" class="btn btn-primary disabled" id="go-btn">进入系统！</a></p>
</div>

</#macro>

<#macro blockBottomScripts>
<script>
    seajs.use(['jquery'], function($){
        window.$ = $;

        $.post('${ctx}/install/finish?step=999', function(retData) {
            $('.key-result-well').addClass('hide');
            if (retData.accessKey && retData.secretKey) {
                $("#access-key").text(retData.accessKey);
                $("#secret-key").text(retData.secretKey);
                $('.key-created').removeClass('hide');
            } else {
                if(retData.error) {
                    $("#error-reason").html(retData.error);
                } else {
                    $("#error-reason").html('生成Key失败，请联系WebFast官方技术支持！');
                }
                $('.key-create-fail').removeClass('hide');
            }
        }, 'json').done(function(){
            $("#go-btn").removeClass('disabled');
        }).fail(function(){
            $("#error-reason").html('生成Key失败，请联系WebFast官方技术支持！');
            $('.key-create-fail').removeClass('hide');
        });

    });
</script>

</#macro>