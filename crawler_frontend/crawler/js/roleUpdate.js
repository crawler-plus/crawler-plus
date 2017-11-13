var roleUpdate = function () {

    // 角色id
    var roleId = commonUtil.getUrlParam("roleId");

    // 获取角色相关数据
    var fetchData = function () {

        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'role/getRoleInfoById/' + roleId,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                var content = data.content;
                var roleInfo = content.roleInfo;
                $("#roleName").val(roleInfo.roleName);
                $("#remark").val(roleInfo.remark);
                $("#version").val(roleInfo.version);
                // 得到菜单树形列表
                var treeJson = content.menuTreeInfo;
                // 得到角色关联的菜单IDs
                var roleRelatedMenuIds = content.roleRelatedMenuIds;
                var setting = {
                    check: {
                        enable: true,
                        chkboxType:  { "Y": "ps", "N": "ps" }
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    }
                };

                $.fn.zTree.init($("#treeDemo"), setting, treeJson).expandAll(true);

                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                var node = treeObj.getNodes();
                var nodes = treeObj.transformToArray(node);
                for(var i = 0; i < nodes.length; i ++) {
                    // 判断当前节点是否在这个角色对应的菜单列表中，如果是，就选中
                    var currentId = nodes[i].id;
                    if($.inArray(currentId, roleRelatedMenuIds) > -1) {
                        treeObj.checkNode(nodes[i], true, false);
                    }
                }

            }
        );
    };

    var init = function () {

        fetchData();

        $("#roleUpdateForm").validate({
            rules: {
                remark: {
                    required : true
                }
            },
            submitHandler: function () {
                _checkRoleExists(roleId, function () {
                    // 放置角色菜单的数组
                    var checkedArr = [];
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    var nodes = treeObj.getCheckedNodes(true);
                    if(nodes.length) {
                        for(var i = 0; i < nodes.length; i ++) {
                            checkedArr.push(nodes[i].id);
                        }
                    }
                    var checkedArrStr = checkedArr.join(",");
                    $("#roleMenuStr").val(checkedArrStr);
                    $("#roleId").val(roleId);
                    // 通过表单验证
                    var signOptions = {
                        formID : 'roleUpdateForm',
                        isFormData : true
                    };
                    var ajaxOptions = {
                        url: comm.url + 'role/updateRole',
                        method : 'PUT'
                    };
                    dataRequest.requestSend(
                        signOptions,
                        ajaxOptions,
                        function (data) {
                            if(data.msgCode === '400') {
                                toastr.error(data.content);
                            }else {
                                commonUtil.closeTab();
                            }
                        }
                    );
                });
            }
        });
    };

    /**
     * 判断角色是否存在
     * @param id
     * @private
     */
    var _checkRoleExists = function (id, callback) {
        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'role/checkRoleExists/' + id,
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                if(data.msgCode === '400') {
                    toastr.error(data.content);
                }else {
                    callback();
                }
            }
        );
    };

    return {
        init: init
    }
}();
$(function () {

    $.validator.setDefaults({
        highlight: function (element) {
            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function (element) {
            element.closest('.form-group').removeClass('has-error').addClass('has-success');
        },
        errorElement: "span",
        errorPlacement: function (error, element) {
            if (element.is(":radio") || element.is(":checkbox")) {
                error.appendTo(element.parent().parent().parent());
            } else {
                error.appendTo(element.parent());
            }
        },
        errorClass: "help-block m-b-none",
        validClass: "help-block m-b-none"
    });

    roleUpdate.init();

});