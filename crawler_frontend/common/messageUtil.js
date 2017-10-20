var icon = "<i class='fa fa-times-circle'></i> ";
jQuery.extend(jQuery.validator.messages, {
    required: icon + "不能为空",
    maxlength: jQuery.validator.format(icon + "位数不得超过{0}")
});