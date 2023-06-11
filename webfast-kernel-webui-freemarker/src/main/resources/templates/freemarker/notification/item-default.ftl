<li class="media">
  <div class="pull-left">
    <span class="glyphicon glyphicon-volume-down media-object"></span>
  </div>
  <div class="media-body">
    <div class="notification-body">
      ${notification.content}
    </div>
    <div class="notification-footer">
      ${notification.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}
    </div>
  </div>
</li>