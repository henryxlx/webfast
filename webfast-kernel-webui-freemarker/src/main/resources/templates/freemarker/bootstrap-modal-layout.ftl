<#import '/web_macro.ftl' as web_macro>
<div class="modal-dialog ${modal_class!''}">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title"><#if blockTitle??><@blockTitle/></#if></h4>
        </div>
        <div class="modal-body"><#if blockBody??><@blockBody/></#if></div>
        <#if !hideFooter!false>
        <div class="modal-footer"><#if blockFooter??><@blockFooter/></#if></div>
        </#if>
    </div>
</div>