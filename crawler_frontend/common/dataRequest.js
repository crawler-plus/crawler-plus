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
        ajaxURL = ajaxURL + "?uid=" + commonUtil.getUserId() + "&token=" + commonUtil.getAccessToken();
		if(!signOptions.isFormData && !signOptions.isUploadReq) {
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
		// 如果为上传文件请求
		if(signOptions.isUploadReq) {
            ajaxOpts.processData = false;
            ajaxOpts.contentType = false;
		}
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
			// 证明TOKEN不合法或者用户无操作权限
			if(error.status === 401) {
                if(ajaxURL.indexOf("user/logout") > -1) {
                    location.href = 'login.html';
				}else {
                    location.href = '../login.html';
				}
			}
			// 参数错误
			else if(error.status === 400) {
                layer.msg('请检查参数', {icon: 5});
			}
			// 服务器内部错误
			else if(error.status === 500) {
				if(ajaxURL.indexOf("captcha/create") > -1 || ajaxURL.indexOf("user/login") > -1 || ajaxURL.indexOf("user/logout") > -1) {
                    location.href = '500.html';
				}else {
                    location.href = '../500.html';
				}
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

