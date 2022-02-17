<div <#if id??>id="${id}"</#if> class="panel panel-default ${class!}">
    <div class="panel-heading"><#if blockHeading??><@blockHeading/></#if></div>
    <div class="panel-body"><#if blockPanelBody??><@blockPanelBody/></#if></div>
</div>