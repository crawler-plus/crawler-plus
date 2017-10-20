var roleDetail = function () {

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

                // 得到菜单树形列表
                var treeJson = content.menuTreeInfo;
                // 得到角色关联的菜单IDs
                var roleRelatedMenuIds = content.roleRelatedMenuIds;
                console.log(roleRelatedMenuIds);
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

                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                var node = treeObj.getNodes();
                var nodes = treeObj.transformToArray(node);
                for(var i = 0; i < nodes.length; i ++) {
                    // 判断当前节点是否在这个角色对应的菜单列表中，如果是，就选中
                    var currentId = nodes[i].id;
                    if($.inArray(currentId, roleRelatedMenuIds) > 0) {
                        treeObj.checkNode(nodes[i], true, true);
                    }
                    // 所有节点都不可点击
                    treeObj.setChkDisabled(nodes[i], true, true);
                }
            },
            function () {
                layer.closeAll();
                toastr.error('请检查网络！');
            }
        );
    }

    var init = function () {
        fetchData();
    }

    return {
        init: init
    }
}();
$(function () {
    roleDetail.init();
});