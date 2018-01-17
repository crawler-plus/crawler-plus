var dataRequest = function() {
	/**
	 * 序列化form数据为Array
	 * @param {Object} formID
	 */
	var _serializeFormArray = function(formID) {
		return $("#" + formID).serializeArray();
	};

    /**
     * 序列化form数据
     * @param {Object} formID
     */
    var _serializeForm = function(formID) {
        return $("#" + formID).serialize();
    };

	/**
	 * ajax 公用封装
	 * @param signOptions
	 * @param ajaxOptions
	 * @param ajaxSuccCallback
	 * @param ajaxFailedCallback
	 * @private
	 */
	var _ajaxWrapper=function(signOptions, ajaxOptions, ajaxSuccCallback, ajaxFailedCallback) {
        var ajaxURL = ajaxOptions.url;
		if(!signOptions.isFormData) {
			ajaxOptions.headers= {
				'Accept': 'application/json',
				'Content-Type': 'application/json'
			}
		}
		var ajaxOpts = {
            url:ajaxURL,
            method: ajaxOptions.method,
            data: ajaxOptions.data,
            dataType: "json",
            headers: ajaxOptions.headers,
            cache: false
        };
        commonUtil.showLoadingMessage();
		$.ajax(ajaxOpts).done(function(data){
            layer.closeAll();
            ajaxSuccCallback(data);
		}).fail(function (error, status) {
            layer.closeAll();
			// 断网
			if(error.statusText === 'error' && error.status !== 500) {
                layer.msg('请检查网络', {icon: 5});
        	}
        }).always(function () {
        });
	};

	/**
	 * 提交AJAX,与服务器数据交互
	 * @param signOptions
	 * @param ajaxOptions
	 * @param ajaxSuccCallback
	 * @param ajaxFailedCallback
     * @private
     */
	var _requestSendWithOutSign = function(signOptions, ajaxOptions, ajaxSuccCallback, ajaxFailedCallback) {
        if(signOptions.formID) {
			ajaxOptions.data = _serializeFormArray(signOptions.formID);
		}
		_ajaxWrapper(signOptions, ajaxOptions, ajaxSuccCallback, ajaxFailedCallback);
	};

	return {
		requestSend:_requestSendWithOutSign
	}
}();

