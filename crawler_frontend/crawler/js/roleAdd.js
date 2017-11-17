var roleAdd = function () {

    // 获取菜单列表数据
    var fetchData = function () {

        var signOptions = {
            formID : null,
            isFormData : false
        };
        var ajaxOptions = {
            url: comm.url + 'role/initMenuTree',
            method : 'GET'
        };
        dataRequest.requestSend(
            signOptions,
            ajaxOptions,
            function (data) {
                var treeJson = data.content;

                var setting = {
                    check: {
                        enable: true,
                        nocheckInherit: false
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    }
                };

                $.fn.zTree.init($("#treeDemo"), setting, treeJson).expandAll(true);

            }
        );
    };

    var init = function () {

        fetchData();

        $("#roleAddForm").validate({
            rules: {
                roleName: {
                    required : true
                }
            },
            submitHandler: function () {
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
                // 通过表单验证
                var signOptions = {
                    formID : 'roleAddForm',
                    isFormData : true
                };
                var ajaxOptions = {
                    url: comm.url + 'role/addRole',
                    method : 'POST'
                };
                dataRequest.requestSend(
                    signOptions,
                    ajaxOptions,
                    function (data) {
                        if(data.msgCode === '400') {
                            toastr.error(data.content);
                        }else {
                            parent.roleMgmt.refreshCurrentTable();
                            parent.layer.closeAll();
                        }
                    }
                );
            }
        });
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

    roleAdd.init();

});