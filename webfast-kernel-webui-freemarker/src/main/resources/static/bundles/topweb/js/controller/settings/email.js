define(function(require, exports, module) {

    var Validator = require('bootstrap.validator');

    exports.run = function() {

        var validator = new Validator({
            element: '#setting-email-form',
            onFormValidated: function(error){
                if (error) {
                    return false;
                }
                $('#email-save-btn').button('submiting').addClass('disabled');
            }
        });

        validator.addItem({
            element: '[id="form_password"]',
            required: true
        });

        validator.addItem({
            element: '[id="form_email"]',
            required: true,
            rule: 'email'
        });

        $('#send-verify-email').click(function(){
            var $btn = $(this);
            $.post($btn.data('url'), function(){
                window.location.reload();
            });
        });
    };

});